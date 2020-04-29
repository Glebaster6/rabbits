package ae.updater.messaging.listeners;

import ae.updater.utils.ExcelDataReader;
import lombok.SneakyThrows;
import main.dto.MainDto;
import main.dto.MainUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.Instant;

@Component
public class AgregatorListener {

    @Autowired
    private ExcelDataReader excelDataReader;

    @SneakyThrows
    @RabbitListener(queues = "agregatorToUpdaterQueue")
    public void listen(byte[] in) {
        ByteArrayInputStream bis = new ByteArrayInputStream(in);
        ObjectInputStream ois = new ObjectInputStream(bis);
        MainDto mainDto = (MainDto) ois.readObject();

        if (mainDto.getAction().equals(MainDto.Action.PARSE_EXCEL)){
            if (mainDto.getFile().length > 0){
                String path = "src/main/resources/temp/" + Instant.now().toString() + ".xlsx";
                File file = new File(path);
                OutputStream os = new FileOutputStream(file);
                os.write(mainDto.getFile());
                os.close();


            }
        }
    }
}
