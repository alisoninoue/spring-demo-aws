package br.com.alison.aws.awsdynamodb.dataproviders.database.dynamodb.entity;

import br.com.alison.aws.awsdynamodb.core.model.Person;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "Person")
public class PersonEntity {

    private String cpf;
    private Integer age;
    private String name;

    @DynamoDBHashKey
    public String getCpf() {
        return cpf;
    }

    @DynamoDBAttribute
    public Integer getAge() {
        return age;
    }

    @DynamoDBAttribute
    public String getName() {
        return name;
    }

    public Person toPerson() {
        return new Person(this.cpf, this.age, this.name);
    }
}
