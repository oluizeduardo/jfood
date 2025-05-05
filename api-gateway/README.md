# API Gateway

Este é o serviço **API Gateway** do projeto **JFood**, responsável por atuar como **ponto de entrada único** para todas as requisições externas, roteando dinamicamente as chamadas para os microsserviços registrados no **Eureka Server**.

Além do roteamento, o Gateway também implementa rate limiting para controlar o número de requisições por IP, utilizando o Redis como armazenamento rápido para esse controle.

## ⚠️ Pré-requisitos importantes

Para que o mecanismo de rate limiting funcione corretamente, o serviço Redis precisa estar em execução antes de subir o API Gateway.

Você pode iniciar um container Redis localmente com o seguinte comando:

```
docker run -d --name redis -p 6379:6379 redis
```
> Certifique-se de que a porta `6379` esteja disponível e que nenhum outro serviço Redis esteja em execução.

## 🚀 Ordem de execução

> Atenção à ordem de inicialização dos serviços para garantir o funcionamento correto:

1. [Service Registry - Eureka Server](https://github.com/oluizeduardo/jfood/tree/main/ms-service-registry)
2. Redis (necessário para o rate limiting do Gateway)
3. API Gateway (api-gateway)
4. Demais microsserviços (ex: ms-users, ms-notification, etc.)

## 🛠️ Execução

### Rodando localmente

```bash
# 1. Clone o repositório
git clone https://github.com/oluizeduardo/jfood.git

# 2. Acesse a pasta do serviço.
cd jfood/api-gateway

# 3. Compile e execute a aplicação
mvn spring-boot:run
```