package br.com.alison.aws.awsdynamodb.configuration;

import br.com.alison.aws.awsdynamodb.core.dataproviders.PersonCreate;
import br.com.alison.aws.awsdynamodb.core.dataproviders.PersonFraudProcess;
import br.com.alison.aws.awsdynamodb.core.dataproviders.PersonNotify;
import br.com.alison.aws.awsdynamodb.core.usecases.person.PersonRegisterUseCase;
import br.com.alison.aws.awsdynamodb.core.usecases.person.PersonRegisterUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    PersonRegisterUseCase personRegisterUseCase(PersonCreate repository, PersonNotify notify, PersonFraudProcess fraudProcess) {
        return new PersonRegisterUseCaseImpl(repository, notify, fraudProcess);
    }
}
