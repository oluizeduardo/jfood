# 📦 ms-users

Microsserviço responsável pela gestão de usuários dentro da aplicação **JFood**. Ele oferece funcionalidades como cadastro, consulta e exclusão de usuários, além de realizar a integração com o **Keycloak** para registro e autenticação.

## ⚙️ Funcionalidades

- 📋 Cadastro de novos usuários
- 🔍 Consulta de usuários por ID
- ❌ Exclusão de usuários
- 🔐 Cadastro automático de usuários no **Keycloak** para autenticação e autorização

## 🚀 Ordem de execução

> Atenção à ordem de inicialização dos serviços para garantir o funcionamento correto:

1. [Service Registry - Eureka Server](https://github.com/oluizeduardo/jfood/tree/main/ms-service-registry)
2. [API Gateway (api-gateway)](https://github.com/oluizeduardo/jfood/tree/main/api-gateway)
3. Demais microsserviços (ex: ms-users, ms-notification, etc.)

## ⚙️ Executando localmente
```bash
# 1. Clone o repositório
git clone https://github.com/oluizeduardo/jfood.git

# 2. Acesse a pasta do serviço
cd jfood/ms-users

# 3. Compile e execute a aplicação
mvn spring-boot:run
```


## 📬 Postman Collection

Para facilitar o teste e a exploração dos endpoints do serviço **Users-MS**, você pode utilizar a collection abaixo no Postman:

- 👉 [Acessar Collection no Postman](https://documenter.getpostman.com/view/2828428/2sB2cVg2vU)

> 💡 A collection inclui exemplos de requisições para cadastro, consulta, exclusão de usuários e integração com o Keycloak.
