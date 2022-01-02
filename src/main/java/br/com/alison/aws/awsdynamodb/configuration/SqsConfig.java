package br.com.alison.aws.awsdynamodb.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

import java.util.Collections;

@Configuration
public class SqsConfig {

    @Value("${amazon.aws.region}")
    private String region;

    @Value("${amazon.sqs.endpoint}")
    private String sqsEndpoint;

    @Bean
    public AwsClientBuilder.EndpointConfiguration endpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(sqsEndpoint, region);
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync(final AwsClientBuilder.EndpointConfiguration endpointConfiguration, final AWSCredentials credentials) {
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory(MessageConverter messageConverter) {
        var factory = new QueueMessageHandlerFactory();
        factory.setMessageConverters(
                Collections.singletonList(messageConverter));

        return factory;
    }

    @Bean
    protected MessageConverter messageConverter() {
        var converter = new MappingJackson2MessageConverter();
        converter.setStrictContentTypeMatch(false);
        return converter;
    }
}
