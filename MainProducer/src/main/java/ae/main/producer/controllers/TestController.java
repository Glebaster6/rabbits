package ae.main.producer.controllers;

import main.dto.GetDataDto;
import main.dto.SendDataDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @MessageMapping("/getData")
    @SendTo("/topic/sendData")
    public SendDataDto greeting(GetDataDto message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new SendDataDto();
    }

}
