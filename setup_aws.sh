#!/bin/bash
export AWS_PAGER=""
export MSYS2_ARG_CONV_EXCL="*"

printf "deleting dynamodb \n"
aws dynamodb delete-table \
  --region=us-east-1 \
  --endpoint-url http://localhost:4566 \
  --table-name Person
printf "creating dynamodb \n"
aws dynamodb create-table \
  --region=us-east-1 \
  --endpoint-url http://localhost:4566 \
  --table-name Person \
  --attribute-definitions AttributeName=cpf,AttributeType=S \
  --key-schema AttributeName=cpf,KeyType=HASH \
  --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5

printf "deleting main queue \n"
aws sqs delete-queue \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --queue-url "http://localhost:4566/000000000000/person"
printf "deleting dlq queue \n"
aws sqs delete-queue \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566  \
  --queue-url "http://localhost:4566/000000000000/person_dlq"
printf "deleting queue for subscription to SNS  \n"
aws sqs delete-queue \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --queue-url "http://localhost:4566/000000000000/personSubSns"
printf "deleting queue for fraud process  \n"
aws sqs delete-queue \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --queue-url "http://localhost:4566/000000000000/commandFraudProcessor"

printf "creating dlq queue \n"
aws sqs create-queue \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566  \
  --queue-name person_dlq

printf "creating main queue with redrive policy \n"
aws sqs create-queue \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566  \
  --queue-name person \
  --attributes '{"RedrivePolicy": "{\"deadLetterTargetArn\": \"arn:aws:sqs:us-east-1:000000000000:person_dlq\",\"maxReceiveCount\": \"2\"}","VisibilityTimeout":"5"}'

printf "creating queue for subscription to SNS  \n"
aws sqs create-queue \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566  \
  --queue-name personSubSns

printf "creating queue for fraud process  \n"
aws sqs create-queue \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566  \
  --queue-name commandFraudProcessor

printf "sending message \n"
aws sqs send-message \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --queue-url "http://localhost:4566/000000000000/person" \
  --message-body '{"cpf": "123456", "age": 12, "name": "test"}'

printf "listing queues \n"
aws sqs list-queues \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566
aws sqs get-queue-attributes \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566  \
  --queue-url "http://localhost:4566/000000000000/person" \
  --attribute-names All
aws sqs get-queue-attributes \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566  \
  --queue-url "http://localhost:4566/000000000000/person_dlq" \
  --attribute-names All
aws sqs get-queue-attributes \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566  \
  --queue-url "http://localhost:4566/000000000000/personSubSns" \
  --attribute-names All
aws sqs get-queue-attributes \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566  \
  --queue-url "http://localhost:4566/000000000000/commandFraudProcessor" \
  --attribute-names All

#Secrets Manager
printf "deleting secrets manager \n"
aws secretsmanager delete-secret \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --secret-id "secrets/test1" \
  --force-delete-without-recovery
aws secretsmanager delete-secret \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --secret-id "secrets/test2" \
  --force-delete-without-recovery

printf "creating secrets manager \n"
aws secretsmanager create-secret \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --name "secrets/test1" \
  --description "My test1 secret created with the CLI." \
  --secret-string "{\"user1\":\"nametest1\",\"password1\":\"EXAMPLE-PASSWORD1\"}"
aws secretsmanager create-secret \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --name "secrets/test2" \
  --description "My test2 secret created with the CLI." \
  --secret-string "{\"user2\":\"nametest2\",\"password2\":\"EXAMPLE-PASSWORD2\"}"

#SSM Parameter Store
printf "deleting ssm parameter store \n"

aws ssm delete-parameter \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --name "/test/parameterstore1"

printf "creating ssm parameter store \n"
aws ssm put-parameter \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --name "/test/parameterstore1" \
  --type "String" \
  --value "parameterstoreTest1" \
  --overwrite

aws ssm get-parameters-by-path \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --path "/test/"

printf "deleting SNS topic \n"
aws sns delete-topic \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --topic-arn "arn:aws:sns:us-east-1:000000000000:personCreatedTopic"

printf "creating SNS topic \n"
aws sns create-topic \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --name personCreatedTopic

printf "listing SNS topic \n"
aws sns list-topics \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566

printf "subscribe HTTP SNS topic, the app has to be started and running locally \n"
aws sns subscribe \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --topic-arn "arn:aws:sns:us-east-1:000000000000:personCreatedTopic" \
  --protocol http \
  --notification-endpoint http://host.docker.internal:8099/personCreatedTopic

printf "subscribe SQS to SNS topic \n"
aws sns subscribe \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --topic-arn "arn:aws:sns:us-east-1:000000000000:personCreatedTopic" \
  --protocol sqs \
  --notification-endpoint http://localhost:4566/000000000000/personSubSns

aws sns list-subscriptions \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566

aws sns get-topic-attributes \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --topic-arn "arn:aws:sns:us-east-1:000000000000:personCreatedTopic"