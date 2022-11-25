package br.com.alison.aws.awsdynamodb.dataproviders.sns;

import br.com.alison.aws.awsdynamodb.core.dataproviders.PersonNotify;
import br.com.alison.aws.awsdynamodb.core.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendEventPersonCreated implements PersonNotify {

    private final SnsNotificationSender sender;
    @Override
    public void sendEvent(Person person) {
        log.info("SNS - Sendind event: {}", person);
        sender.send(person);
        log.info("SNS - Event sent!");
    }
}
