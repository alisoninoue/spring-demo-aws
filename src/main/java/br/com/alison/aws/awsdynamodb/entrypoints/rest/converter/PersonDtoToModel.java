package br.com.alison.aws.awsdynamodb.entrypoints.rest.converter;

import br.com.alison.aws.awsdynamodb.core.model.Person;
import br.com.alison.aws.awsdynamodb.entrypoints.rest.dto.PersonDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PersonDtoToModel implements Converter<PersonDto, Person> {
    @Override
    public Person convert(PersonDto personDto) {
        return Person.builder()
                .cpf(personDto.getCpf())
                .age(personDto.getAge())
                .name(personDto.getName())
                .build();
    }
}
