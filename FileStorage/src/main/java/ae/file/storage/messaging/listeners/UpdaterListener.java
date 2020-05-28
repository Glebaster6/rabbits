package ae.file.storage.messaging.listeners;

import ae.file.storage.services.storage.StorageService;
import main.dto.MainDto;
import main.dto.MainUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdaterListener {
    @Autowired
    private StorageService storageService;

    @RabbitListener(queues = "dataStorageToUpdaterQueue")
    public void listen(byte[] in) {
        MainDto mainDto = (MainDto) MainUtil.byteArrayToObject(in);

        switch (mainDto.getAction()) {
            case SAVE_DATASET:
            case SAVE_TRAINED_MODEL:
                storageService.saveFile(mainDto);
                break;
        }
    }
}
