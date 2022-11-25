package br.com.alison.aws.awsdynamodb.entrypoints.rest;

import br.com.alison.aws.awsdynamodb.core.model.Person;
import br.com.alison.aws.awsdynamodb.core.usecases.person.PersonRegisterUseCase;
import br.com.alison.aws.awsdynamodb.entrypoints.rest.converter.PersonDtoToModel;
import br.com.alison.aws.awsdynamodb.entrypoints.rest.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class PersonRestController {

    private final PersonRegisterUseCase registerUseCase;
    private final PersonDtoToModel converter;

    @PostMapping("/register")
    public Person register(@RequestBody PersonDto personDto) {
        return registerUseCase.register(converter.convert(personDto));
    }

    @GetMapping("/teste")
    public String register() {
        return "teste";
    }
}
