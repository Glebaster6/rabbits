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
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Instant;
import java.util.List;

@Service
public class PredictionServiceImpl implements PredictionService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @SneakyThrows
    @Override
    public void makePrediction(MainDto mainDto) {
        String path = "src/main/resources/temp/" + Instant.now().toString() + ".csv";
        MainUtil.byteArrayToFile(path, mainDto.getFile());
        File file = new File(path);
        MultiLayerNetwork newModel = ModelSerializer.restoreMultiLayerNetwork(file);

        String csvPath = "src/main/resources/temp/" + Instant.now().toString() + ".csv";
        RecordReader recordReader = new CSVRecordReader(0, ";");
        recordReader.initialize(new FileSplit(new File("src/main/resources/temp/" + Instant.now().toString() + ".csv")));

        int labelIndex = recordReader.getLabels().size();
        int numClasses = 6;
        int batchSize = 150;

        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize, labelIndex, numClasses);

        List<String> prediction = newModel.predict(iterator.next());


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
