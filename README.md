# How to run 

1. To have a rabbit mq server: docker run -d -p 15672:15672 -p 5672:5672 --name demo rabbitmq:3-management
2. Change logs path in application.properties from both modules
3. Run deploy.sh or run both modules: http and calculator
4. Access module http in {server.address}:{server.port} configured on application.properties from module http

# Properties

Check configurable properties of the application in application.properties file of each module. 

# Rest API

1. Sum : GET /calculator/sum?a=1&b=3
2. Subtraction: GET /calculator/sub?a=1&b=3
3. Multiplication: GET /calculator/mul?a=1&b=3
4. Division: GET /calculator/div?a=1&b=3

# Architecture

1. Common module


2. Http module


3. Calculator module





