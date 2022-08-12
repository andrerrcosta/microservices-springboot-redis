# microservices-springboot-redis

Para rodar a aplicação:
1) rode o docker-compose up para instalar o Consul, o rabbitmq, o mysql e o redis.
2) Rode mvn clean install para gerar os mappers do mapstruct
2) Rode os microserviços. (eles estão utilizando o profile local "mvn spring-boot:run -Dspring-boot.run.profiles=local")
