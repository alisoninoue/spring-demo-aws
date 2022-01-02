package br.com.alison.aws.awsdynamodb.core.usecases.person.ports;

import br.com.alison.aws.awsdynamodb.core.model.Person;

public interface PersonCreate {
    Person create(Person person);
}
