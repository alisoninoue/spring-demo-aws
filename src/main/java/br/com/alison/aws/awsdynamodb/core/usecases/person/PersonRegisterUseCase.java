package br.com.alison.aws.awsdynamodb.core.usecases.person;

import br.com.alison.aws.awsdynamodb.core.model.Person;

public interface PersonRegisterUseCase {
    Person register(Person person);
}
