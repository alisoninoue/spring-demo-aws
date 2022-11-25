package br.com.alison.aws.awsdynamodb.core.dataproviders;

import br.com.alison.aws.awsdynamodb.core.model.Person;

public interface PersonFraudProcess {
    void commandFraudProcess(Person person);
}
