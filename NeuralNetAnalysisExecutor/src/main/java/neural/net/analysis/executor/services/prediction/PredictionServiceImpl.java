package neural.net.analysis.executor.services.prediction;

import lombok.SneakyThrows;
import main.dto.MainDto;
import main.dto.MainUtil;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionServiceImpl implements PredictionService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @SneakyThrows
    @Override
    public void makePrediction(MainDto mainDto) {
        List<String> names = new ArrayList<>();
        names.add("Увеличение до 25%");
        names.add("Увеличение до 50%");
        names.add("Увеличение до 75%");
        names.add("Уменьшение до 25%");
        names.add("Уменьшение до 50%");
        names.add("Уменьшение до 75%");

        String path = "src/main/resources/temp/" + Instant.now().toString() + ".csv";
        MainUtil.byteArrayToFile(path, mainDto.getFile());
        File file = new File(path);
        MultiLayerNetwork newModel = ModelSerializer.restoreMultiLayerNetwork(file);

        RecordReader recordReader = new CSVRecordReader(0, ";");
        String dataPath = "src/main/resources/temp/" + Instant.now().toString() + ".csv";
        recordReader.initialize(new FileSplit(new File(dataPath)));


        int labelIndex = recordReader.getLabels().size();
        int numClasses = 6;
        int batchSize = 1;


        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader,batchSize,labelIndex,numClasses);
        DataSet allData = iterator.next();
        allData.shuffle();
        SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(1);

        DataSet testData = testAndTrain.getTest();

        String prediction = newModel.predict(testData).get(0);


        rabbitTemplate.convertAndSend(
                "neuralNetAnalysisExecutorToAgregator",
                MainUtil.objectToByteArray(
                        MainDto.builder()
                                .user(mainDto.getUser())
                                .action(MainDto.Action.RETURN_PREDICTION)
                                .json(MainUtil.objectToJsonString(prediction))
                                .build()
                )
        );
    }

    @Override
    public void getModel(MainDto mainDto) {
        rabbitTemplate.convertAndSend(
                "neuralNetAnalysisExecutorToFileStorage",
                MainDto.builder()
                        .action(MainDto.Action.GET_MODEL)
                        .user(mainDto.getUser())
                        .build()
        );
    }
}
