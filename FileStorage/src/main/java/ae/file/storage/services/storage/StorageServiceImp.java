package ae.file.storage.services.storage;

import lombok.SneakyThrows;
import main.dto.MainDto;
import main.dto.MainUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;

@Service
public class StorageServiceImp implements StorageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void saveFile(MainDto mainDto) {
        String path;
        if (mainDto.getAction().equals(MainDto.Action.SAVE_TRAINED_MODEL)) {
            path = "src/main/resources/storage/models/" + mainDto.getUser() + ".ser";
            MainUtil.byteArrayToFile(path, mainDto.getFile());
        } else if (mainDto.getAction().equals(MainDto.Action.SAVE_DATASET)) {
            path = "src/main/resources/storage/datasets/" + mainDto.getUser() + ".csv";
            MainUtil.byteArrayToFile(path, mainDto.getFile());
        }
    }

    @SneakyThrows
    @Override
    public void getModelFile(String user) {
        String path = "src/main/resources/storage/models/" + user + ".ser";
        MainDto result = MainDto.builder()
                .user(user)
                .action(MainDto.Action.RETURN_PREDICTION)
                .file(Files.readAllBytes(new File(path).toPath()))
                .build();

        rabbitTemplate.convertAndSend(
                "fileStorageToNeuralNetAnalysisExecutor",
                MainUtil.objectToByteArray(result)
        );
    }
}
