package ae.data.storage.application;

import ae.data.storage.models.hot.AbcResult;
import ae.data.storage.models.hot.Evaluation;
import ae.data.storage.models.hot.EvaluationData;
import ae.data.storage.repositories.hot.AbcResultRepository;
import ae.data.storage.repositories.hot.EvalualtionDataRepository;
import ae.data.storage.services.evaluation.EvaluationService;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private AbcResultRepository abcResultRepository;

    @Autowired
    private EvalualtionDataRepository evalualtionDataRepository;

    @Override
    public void run(String... args) throws Exception {
    }
}
