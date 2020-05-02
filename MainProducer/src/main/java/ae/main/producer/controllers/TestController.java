package ae.main.producer.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import javax.websocket.Session;

@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/websocket")
    public String getWebSocket() {
        return "ws-broadcast";
    }
}
