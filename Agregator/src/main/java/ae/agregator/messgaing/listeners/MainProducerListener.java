package ae.agregator.messgaing.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class MainProducerListener {
    @RabbitListener(queues = "updaterToDataStorageQueue")
    public void listen(String in) {
        System.out.println("Message read from myQueue : " + in);
    }
}
