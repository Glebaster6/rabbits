package ae.updater.services;

import lombok.SneakyThrows;
import main.dto.MainDto;
import main.dto.MainUtil;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.time.Instant;

@Service
public class ModelTrainerImpl implements ModelTrainer {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @SneakyThrows
    @Override
    public void trainModel(MainDto mainDto) {

        String path = "src/main/resources/temp/" + Instant.now().toString() + ".csv";
        MainUtil.byteArrayToFile(path, mainDto.getFile());

        int numLinesToSkip = 0;
        char delimiter = ',';
        RecordReader recordReader = new CSVRecordReader(numLinesToSkip, delimiter);
        recordReader.initialize(new FileSplit(new File(path)));

        int labelIndex = 4;
        int numClasses = 3;
        int batchSize = 150;

        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize, labelIndex, numClasses);
        DataSet allData = iterator.next();
        allData.shuffle();
        SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.8);

        DataSet trainingData = testAndTrain.getTrain();
        DataSet testData = testAndTrain.getTest();

        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(trainingData);
        normalizer.transform(trainingData);
        normalizer.transform(testData);

        final int numInputs = iterator.getLabels().size();
        int outputNum = 6;
        long seed = 6;


        int inputParams = 10;


        NeuralNetConfiguration.ListBuilder listBuilder = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .updater(new Sgd(0.1))
                .l2(1e-4)
                .list()
                .layer(
                        new DenseLayer
                                .Builder()
                                .nIn(numInputs)
                                .nOut(--inputParams)
                                .build()
                );


        for (int i = inputParams; i > 6; i--) {
            listBuilder = listBuilder.layer(
                    new DenseLayer
                            .Builder()
                            .nIn(numInputs)
                            .nOut(--inputParams)
                            .build()
            );
        }

        MultiLayerConfiguration conf = listBuilder
                .layer(
                        new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                                .activation(Activation.SOFTMAX)
                                .nIn(outputNum)
                                .nOut(outputNum)
                                .build()
                )
                .build();


        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(100));
        for (int i = 0; i < 1000; i++) {
            model.fit(trainingData);
        }

        String modelPath = "src/main/resources/temp/model_"+ mainDto.getUser() +".ser";
        File file = new File(modelPath);
        ModelSerializer.writeModel(model, file, true);

        MainDto result = MainDto.builder()
                .user(mainDto.getUser())
                .action(MainDto.Action.SAVE_TRAINED_MODEL)
                .file(Files.readAllBytes(new File(modelPath).toPath()))
                .build();

        MainDto dataset = MainDto.builder()
                .action(MainDto.Action.SAVE_DATASET)
                .user(mainDto.getUser())
                .file(mainDto.getFile())
                .build();

        rabbitTemplate.convertAndSend("updaterToFileStorageQueue", MainUtil.objectToByteArray(result));
        rabbitTemplate.convertAndSend("updaterToFileStorageQueue", MainUtil.objectToByteArray(dataset));
    }
}
