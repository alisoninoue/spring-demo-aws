package br.com.alison.aws.awsdynamodb.dataproviders.sns;

import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SnsNotificationSender {

    private final NotificationMessagingTemplate notificationMessagingTemplate;

    public void send(Object message) {
        this.notificationMessagingTemplate.convertAndSend("personCreatedTopic", message);
    }
}
