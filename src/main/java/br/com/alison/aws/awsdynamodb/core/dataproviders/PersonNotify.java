package br.com.alison.aws.awsdynamodb.core.dataproviders;

import br.com.alison.aws.awsdynamodb.core.model.Person;

public interface PersonNotify {

    void sendEvent(Person person);
}
