# spring-demo-aws

Spring project with SQS and Dynamodb

```
docker-compose -p spring-demo-aws up -d
```

# LOCALSTACK

## Health check services
```
http://localhost:4566/health
```

## DYNAMODB LOCAL

### Delete Table

```
aws dynamodb delete-table --endpoint-url http://localhost:4566 --table-name Person
```

### Create Table

```
aws dynamodb create-table --endpoint-url http://localhost:4566 --table-name Person --attribute-definitions AttributeName=cpf,AttributeType=S --key-schema AttributeName=cpf,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
```

## SQS

### DLQ

#### Create DLQ
```
aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs create-queue --queue-name person_dlq
```

- "http://localhost:4566/000000000000/person_dlq"

#### Get Attributes
```
aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs get-queue-attributes --queue-url "http://localhost:4566/000000000000/person_dlq" --attribute-names All
```

### QUEUE

#### Delete Queue
```
aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs delete-queue --queue-url "http://localhost:4566/000000000000/person"
```

#### Create Queue with DLQ and Redrive
```
aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs create-queue --queue-name person --attributes '{"RedrivePolicy": "{\"deadLetterTargetArn\": \"arn:aws:sqs:us-east-1:000000000000:person_dlq\",\"maxReceiveCount\":\"1\"}","VisibilityTimeout":"5"}'
```

#### Get Attributes
```
aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs get-queue-attributes --queue-url "http://localhost:4566/000000000000/person" --attribute-names All aws --region=us-east-1
```

#### Set Attributes
```
aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs set-queue-attributes --queue-url "http://localhost:4566/000000000000/person" --attributes '{"RedrivePolicy": "{\"deadLetterTargetArn\": \"arn:aws:sqs:us-east-1:000000000000:person_dlq\",\"maxReceiveCount\": \"1\"}","VisibilityTimeout":"5"}'
```

### List Queues
```
aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs list-queues
```

### Send test messages to SQS
#### Send Ok Message
```
aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs send-message --queue-url "http://localhost:4566/000000000000/person" --message-body '{"cpf": "123456", "age": 12, "name": "test"}'
```

#### Send Error Message to test redrive policy and DLQ
```
aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs send-message --queue-url "http://localhost:4566/000000000000/person" --message-body '{"cpf": "123456", "age": 11111111111111112, "name": "test"}'
```

## Secrets Manager
### Create secret
```
aws --region=us-east-1 --endpoint-url=http://localhost:4566 secretsmanager create-secret --name "secrets/test1" --description "My test1 secret created with the CLI." --secret-string "{\"user1\":\"nametest1\",\"password1\":\"EXAMPLE-PASSWORD1\"}"
aws --region=us-east-1 --endpoint-url=http://localhost:4566 secretsmanager create-secret --name "secrets/test2" --description "My test2 secret created with the CLI." --secret-string "{\"user2\":\"nametest2\",\"password2\":\"EXAMPLE-PASSWORD2\"}"
```

## SSM Parameter Store
```
aws ssm put-parameter \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --name "/test/parameterstore1" \
  --type "String" \
  --value "parameterstoreTest1" \
  --overwrite
  --path "/test/"
```
```
aws ssm get-parameters-by-path \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --path "/test/"
```

