# How to run 

1. To have a rabbit mq server: docker run -d -p 15672:15672 -p 5672:5672 --name demo rabbitmq:3-management
2. Change the logs path in application.properties from both modules
3. Run deploy.sh or run both modules: HTTP and calculator
4. Access module HTTP in {server.address}:{server.port} configured on application.properties from module HTTP

# Properties

Check the configurable properties of the application in application.properties file of each module. 

# Rest API

1. Sum : GET /calculator/sum?a=1&b=3
2. Subtraction: GET /calculator/sub?a=1&b=3
3. Multiplication: GET /calculator/mul?a=1&b=3
4. Division: GET /calculator/div?a=1&b=3

# Modules

- Operands are received as strings in both modules, in order to preserve their precision. Only on the calculator implementation it's decided what java number type to use.

1. Common module

- The module that contains common POJOS, enums, constants, etc. Example: rabbit mq request POJO; operation enum

2. Http module

- Receives an HTTP get request in CalculatorController.java to perform an operation for operands a and b
- Uses a handler interceptor to generate a unique id per HTTP request and store it in MDC (used in logs)
- Requests are non-blocking, on each one a different thread, from an executor, is used to communicate and get the result from the calculator module and on completion it uses the ResponseBodyEmitter to send the request. The HTTP request MDC context is copied to that thread.
- Sends a request to the calculator module thru the RabbitMqSender.java using rabbit mq
- RabbitMqSender.java uses rabbitTemplate.convertSendAndReceive() method to as a client send a request message and get the server  (calculator module) response message 


3. Calculator module

- It receives events thru rabbit mq in RabbitMqListener.java listener and calls BigDecimalCalculator.java to execute the operation
- The listener on each request it receives from the client a request id and stores it in MDC to show it on logs
- BigDecimalCalculator.java do not have logic to deal with different number formats by locale. Also, in its divide operation params like round type and scale are defined on application.properties 

Notes: RabbitMqSender.java sender; RabbitMqListener.java listener and BigDecimalCalculator.java classes implement interfaces so the communication between modules and the calculator can have different implementations
