package ae.agregator.messgaing.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NeuralNetAnalysisListener {
    @RabbitListener(queues = "neuralNetAnalysisToAgregatorQueue")
    public void listen(String in) {
        System.out.println("Message read from myQueue : " + in);
    }
}