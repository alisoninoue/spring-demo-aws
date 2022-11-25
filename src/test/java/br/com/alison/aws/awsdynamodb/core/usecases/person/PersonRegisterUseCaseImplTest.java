package br.com.alison.aws.awsdynamodb.core.usecases.person;

import br.com.alison.aws.awsdynamodb.core.dataproviders.PersonCreate;
import br.com.alison.aws.awsdynamodb.core.dataproviders.PersonFraudProcess;
import br.com.alison.aws.awsdynamodb.core.dataproviders.PersonNotify;
import br.com.alison.aws.awsdynamodb.core.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonRegisterUseCaseImplTest {

    @Mock
    PersonCreate repository;

    @Mock
    PersonNotify notify;

    @Mock
    PersonFraudProcess fraudProcess;

    @Test
    @DisplayName("Should Register a person successfully")
    void shouldRegister() {
        Person personActual = new Person("123", 1, "Test");
        when(repository.create(any())).thenReturn(personActual);

        PersonRegisterUseCaseImpl personRegisterUseCase = new PersonRegisterUseCaseImpl(repository, notify, fraudProcess);
        Person personExpected = personRegisterUseCase.register(personActual);
        assertThat(personActual).isEqualTo(personExpected);

    }
}