package br.com.alison.aws.awsdynamodb.core.usecases.person;

import br.com.alison.aws.awsdynamodb.core.dataproviders.PersonCreate;
import br.com.alison.aws.awsdynamodb.core.dataproviders.PersonFraudProcess;
import br.com.alison.aws.awsdynamodb.core.dataproviders.PersonNotify;
import br.com.alison.aws.awsdynamodb.core.model.Person;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PersonRegisterUseCaseImpl implements PersonRegisterUseCase {

    private final PersonCreate repository;

    private final PersonNotify notify;

    private final PersonFraudProcess fraudService;

    @Override
    public Person register(Person person) {
        Person personCreated = repository.create(person);
        notify.sendEvent(personCreated);
        fraudService.commandFraudProcess(personCreated);
        return personCreated;
    }
}
