# spring-demo-aws

Spring project with SQS and Dynamodb

docker-compose up -d

# DYNAMODB LOCAL

## DELETE TABLE

aws dynamodb delete-table \
--endpoint-url http://localhost:8000
--table-name Person \

## CREATE TABLE

aws dynamodb create-table \
--endpoint-url http://localhost:8000
--table-name Person \
--attribute-definitions AttributeName=cpf,AttributeType=S AttributeName=name,AttributeType=S
AttributeName=age,AttributeType=N \
--key-schema AttributeName=cpf,KeyType=HASH \
--provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \

# LOCALSTACK

curl http://localhost:4566/health

# DLQ

aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs create-queue --queue-name person_dlq
"http://localhost:4566/000000000000/person_dlq"
aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs get-queue-attributes
--queue-url "http://localhost:4566/000000000000/person_dlq" --attribute-names All

# MAIN

aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs delete-queue
--queue-url "http://localhost:4566/000000000000/person"
aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs create-queue --queue-name person --attributes '{"
RedrivePolicy": "{\"deadLetterTargetArn\": \"arn:aws:sqs:us-east-1:000000000000:person_dlq\",\"maxReceiveCount\":
\"1\"}","VisibilityTimeout":"5"}'

aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs create-queue --queue-name person
"http://localhost:4566/000000000000/person"

aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs get-queue-attributes
--queue-url "http://localhost:4566/000000000000/person" --attribute-names All aws --region=us-east-1
--endpoint-url=http://localhost:4566 sqs set-queue-attributes --queue-url "http://localhost:4566/000000000000/person"
--attributes '{"RedrivePolicy": "{\"deadLetterTargetArn\": \"arn:aws:sqs:us-east-1:000000000000:
person_dlq\",\"maxReceiveCount\": \"1\"}","VisibilityTimeout":"5"}'

# LIST

aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs list-queues

# MESSAGE

aws --region=us-east-1 --endpoint-url=http://localhost:4566 sqs send-message
--queue-url "http://localhost:4566/000000000000/person" --message-body '{"cpf": "123456", "age": 12, "name": "test"}'
