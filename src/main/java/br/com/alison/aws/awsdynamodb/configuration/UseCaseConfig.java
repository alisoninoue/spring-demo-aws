package br.com.alison.aws.awsdynamodb.configuration;

import br.com.alison.aws.awsdynamodb.core.usecases.person.PersonRegisterUseCase;
import br.com.alison.aws.awsdynamodb.core.usecases.person.PersonRegisterUseCaseImpl;
import br.com.alison.aws.awsdynamodb.core.usecases.person.ports.PersonCreate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    PersonRegisterUseCase personRegisterUseCase(PersonCreate repository) {
        return new PersonRegisterUseCaseImpl(repository);
    }
}
