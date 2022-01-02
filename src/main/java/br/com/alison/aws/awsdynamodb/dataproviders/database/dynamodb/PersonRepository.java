package br.com.alison.aws.awsdynamodb.dataproviders.database.dynamodb;

import br.com.alison.aws.awsdynamodb.core.model.Person;
import br.com.alison.aws.awsdynamodb.core.usecases.person.ports.PersonCreate;
import br.com.alison.aws.awsdynamodb.dataproviders.database.dynamodb.converter.PersonToEntityConverter;
import br.com.alison.aws.awsdynamodb.dataproviders.database.dynamodb.springdata.PersonJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class PersonRepository implements PersonCreate {

    private final PersonJpaRepository repository;
    private final PersonToEntityConverter converter;

    @Override
    public Person create(Person person) {
        return repository.save(Objects.requireNonNull(converter.convert(person))).toPerson();
    }
}
