# 🛒 Products-MS

**Products-MS** é o microserviço responsável pelo cadastro e gerenciamento dos **produtos** disponíveis para venda na aplicação **JFood**. Ele garante que apenas usuários autenticados e com permissão de **ADMIN** possam realizar operações sensíveis, como o cadastro de novos produtos.

A segurança do serviço é implementada via **Keycloak**, garantindo controle de acesso baseado em **roles**.

---

## ⚙️ Funcionalidades

- ➕ Cadastro de novos produtos (restrito a usuários com papel `ADMIN`)
- 🔍 Consulta de produtos disponíveis
- ✏️ Atualização e exclusão de produtos
- 🔐 Autenticação e autorização via Keycloak

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4.4**
- **Spring Security**
- **Spring Data JPA**
- **Keycloak**
- **Maven**
- **PostgreSQL**
- **Flyway**

---

## 🔐 Segurança e Autorização

Este serviço utiliza o **Keycloak** como provedor de autenticação. As rotas de **cadastro, edição e exclusão** de produtos são protegidas e acessíveis **somente por usuários com a role `ADMIN`**.

- É necessário fornecer um token JWT válido no cabeçalho das requisições protegidas.
- O token deve ser emitido pelo **Keycloak** e conter a role `ADMIN` no realm ou client.

---

## 🛠️ Configuração e Execução

### Rodando localmente

```bash
# 1. Clone o repositório
git clone https://github.com/oluizeduardo/jfood.git

# 2. Acesse a pasta do serviço de usuários.
cd jfood/products-ms

# 3. Configure as variáveis de ambiente:
# Crie um arquivo .env com base no exemplo disponível e edite-o com suas credenciais e configurações desejadas:
cp .env.example .env

# 4. Compile e execute a aplicação
mvn spring-boot:run
```
