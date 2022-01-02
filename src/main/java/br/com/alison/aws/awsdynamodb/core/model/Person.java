package br.com.alison.aws.awsdynamodb.core.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Person {
    private String cpf;
    private Integer age;
    private String name;
}
