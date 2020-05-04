package ae.updater.messaging.listeners;

import ae.updater.utils.ExcelDataReader;
import lombok.SneakyThrows;
import main.dto.MainDto;
import main.dto.MainUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.Instant;

@Component
public class AgregatorListener {

    @Autowired
    private ExcelDataReader excelDataReader;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @SneakyThrows
    @RabbitListener(queues = "agregatorToUpdaterQueue")
    public void listen(byte[] in) {
        MainDto mainDto = (MainDto) MainUtil.byteArrayToObject(in);

        try {
            switch (mainDto.getAction()){
                case REGISTER:
                case GET_FACILITY_DATA:
                case GET_EVALUATION_DATA:
                case DELETE_EVALUATION:
                case LOGIN:{
                    rabbitTemplate.convertAndSend("updaterToDataStorageQueue", MainUtil.objectToByteArray(mainDto));
                    break;
                }
                case PARSE_EXCEL:{
                    if (mainDto.getFile().length > 0) {
                        String path = "src/main/resources/temp/" + Instant.now().toString() + ".xlsx";
                        MainUtil.byteArrayToFile(path, mainDto.getFile());
                        excelDataReader.readData(new File(path), mainDto.getJson());
                    }
                    break;
                }
            }

        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
