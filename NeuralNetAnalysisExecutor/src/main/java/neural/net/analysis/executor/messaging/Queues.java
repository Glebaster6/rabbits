package neural.net.analysis.executor.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Queues {

    @Bean
    public Queue neuralNetAnalysisExecutorToAgregator(){
        return new Queue("neuralNetAnalysisExecutorToAgregator", false);
    }

    @Bean
    public Queue neuralNetAnalysisExecutorToFileStorage(){
        return new Queue("neuralNetAnalysisExecutorToFileStorage", false);
    }

    @Bean
    public Queue agregatorToNeuralNetAnalysisExecutor(){
        return new Queue("agregatorToNeuralNetAnalysisExecutor", false);
    }

    @Bean
    public Queue fileStorageToNeuralNetAnalysisExecutor(){
        return new Queue("fileStorageToNeuralNetAnalysisExecutor", false);
    }
}
