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

printf "deleting main queueu \n"
aws sqs delete-queue \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566 \
  --queue-url "http://localhost:4566/000000000000/person"
printf "deleting dlq queueu \n"
aws sqs delete-queue \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566  \
  --queue-url "http://localhost:4566/000000000000/person_dlq"

printf "creating dlq queueu \n"
aws sqs create-queue \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566  \
  --queue-name person_dlq

printf "creating main queueu with redrive policy \n"
aws sqs create-queue \
  --region=us-east-1 \
  --endpoint-url=http://localhost:4566  \
  --queue-name person \
  --attributes '{"RedrivePolicy": "{\"deadLetterTargetArn\": \"arn:aws:sqs:us-east-1:000000000000:person_dlq\",\"maxReceiveCount\": \"2\"}","VisibilityTimeout":"5"}'

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
