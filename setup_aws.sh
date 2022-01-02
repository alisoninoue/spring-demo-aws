export AWS_PAGER=""
printf "deleting dynamodb \n"
aws dynamodb delete-table \
  --region=us-east-1 \
  --endpoint-url http://localhost:8000 \
  --table-name Person
printf "creating dynamodb \n"
aws dynamodb create-table \
  --region=us-east-1 \
  --endpoint-url http://localhost:8000 \
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