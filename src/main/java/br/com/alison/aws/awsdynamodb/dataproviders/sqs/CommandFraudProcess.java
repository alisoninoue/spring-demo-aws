package br.com.alison.aws.awsdynamodb.dataproviders.sqs;

import br.com.alison.aws.awsdynamodb.core.dataproviders.PersonFraudProcess;
import br.com.alison.aws.awsdynamodb.core.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommandFraudProcess implements PersonFraudProcess {

    private final SqsProducer producer;

    @Override
    public void commandFraudProcess(Person person) {
        log.info("SQS - Fraud Sendind event: {}", person);
        producer.send("commandFraudProcessor", person);
        log.info("SQS - Fraud Event sent!");
    }
}
