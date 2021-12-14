# microservices-springboot-redis

Para rodar a aplicação:
1) rode o docker-compose up para instalar o Consul, o rabbitmq, o mysql e o redis.
2) Rode os microserviços.

Perguntas: 
- Está funcionando? Está. Pode ter bugs porque eu não completei os testes.
abaixo estão as razões.

FALTANDO:

* Estrutura
- Criar excessões customizadas condizentes com os erros (Todos estão retornando BadRequest).
- Colocar as rotas no Gateway
- Customizar o LogBack
- Hierarquizar o POM

* Testes
Admin
- Testar todas as entradas do controller com o TestRestTemplate (integração)
- Testar todos os métodos do serviço (Unit)
- Testar o AMQP

Scheduler
- Criar todos os testes em geral
- Criar testes de integração com a aplicação completa

Outros
- Criar um supplier para fluxo de teste
- Criar um teste de performance em geral para
gerar um fluxo imenso de agendas e votos e logar através do spring-sleuth

# NOTAS:
- Eu comentei (desativei) a verificação do https://user-info.herokuapp.com/users/ porque ele
está retornando "UNABLE_TO_VOTE" para todos os cpfs. está atrapalhando testar o código.
- Criar uma documentação, versionar, etc.
- Eu não consigo terminar essa aplicação com menos de 3 semanas

