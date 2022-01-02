package br.com.alison.aws.awsdynamodb.entrypoints.sqs;

import br.com.alison.aws.awsdynamodb.core.model.Person;
import br.com.alison.aws.awsdynamodb.core.usecases.person.PersonRegisterUseCase;
import br.com.alison.aws.awsdynamodb.entrypoints.rest.converter.PersonDtoToModel;
import br.com.alison.aws.awsdynamodb.entrypoints.rest.dto.PersonDto;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonSqsListener {

    private final PersonRegisterUseCase registerUseCase;
    private final PersonDtoToModel converter;

    @SqsListener(value = "person", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receive(PersonDto personDto) {
        log.info("SQS person received: {}", personDto);
        Person register = registerUseCase.register(converter.convert(personDto));
        log.info("Registered: {}", register);
    }
}
