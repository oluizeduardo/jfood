# 📦 Users-MS

**Users-MS** é um microserviço responsável pela gestão de usuários dentro da aplicação **JFood**. Ele oferece funcionalidades como cadastro, consulta e exclusão de usuários, além de realizar a integração com o **Keycloak** para registro e autenticação.

Este microserviço é parte essencial da arquitetura distribuída da aplicação **JFood**, garantindo o gerenciamento seguro e eficiente dos usuários.

---

## ⚙️ Funcionalidades

- 📋 Cadastro de novos usuários
- 🔍 Consulta de usuários por ID
- ❌ Exclusão de usuários
- 🔐 Cadastro automático de usuários no **Keycloak** para autenticação e autorização

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4.4**
- **Spring Security**
- **Spring Data JPA**
- **Maven**
- **PostgreSQL**
- **Flyway**
- **Keycloak**

---

## 🧱 Estrutura de Pacotes

Este microserviço segue as boas práticas de desenvolvimento com **Spring Boot**, organizando o código em camadas bem definidas:

- `amqp` – Responsável pela configuração e comunicação com a fila do **RabbitMQ**
- `config` – Contém as configurações gerais da aplicação, incluindo segurança, ExceptionHandlers, e beans customizados
- `controller` – Define os endpoints REST e lida com as requisições da camada de apresentação
- `dto` – Objetos de transferência de dados utilizados na comunicação entre camadas
- `model` – Entidades de domínio que representam as tabelas do banco de dados
- `repository` – Interfaces de acesso a dados, utilizando o **Spring Data JPA**
- `service` – Contém a lógica de negócio e orquestra as operações do sistema
- `versionlogger` – Componente responsável por exibir logs com informações da versão da aplicação durante a inicialização

---

## ⚙️ Executando localmente

```bash
# 1. Clone o repositório.
git clone https://github.com/oluizeduardo/jfood.git

# 2. Acesse a pasta do serviço.
cd jfood/users-ms

# 3. Compile e execute a aplicação.
# As migrações do Flyway vão executar automaticamente ao subir a aplicação
mvn spring-boot:run
```
---

## 📬 Postman Collection

Para facilitar o teste e a exploração dos endpoints do serviço **Users-MS**, você pode utilizar a collection abaixo no Postman:

- 👉 [Acessar Collection no Postman](https://documenter.getpostman.com/view/2828428/2sB2cVg2vU)

> 💡 A collection inclui exemplos de requisições para cadastro, consulta, exclusão de usuários e integração com o Keycloak.
