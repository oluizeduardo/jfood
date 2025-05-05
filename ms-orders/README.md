# 🧾 ms-orders

Microsserviço responsável pelo **cadastro e gerenciamento de pedidos de compra** realizados pelos usuários na aplicação **JFood**. 

## ⚙️ Funcionalidades

- 🛒 Criação de novos pedidos de compra
- 📦 Consulta de pedidos por usuário
- 🧾 Detalhamento de um pedido específico

## 🚀 Ordem de execução

> Atenção à ordem de inicialização dos serviços para garantir o funcionamento correto:

1. [Service Registry - Eureka Server](https://github.com/oluizeduardo/jfood/tree/main/ms-service-registry)
2. API Gateway (api-gateway)
3. Demais microsserviços (ex: ms-users, ms-notification, etc.)

## 🔐 Autenticação

Todas as operações do **ms-orders** exigem que o usuário esteja **autenticado via Keycloak**.

- O token JWT deve ser fornecido no cabeçalho `Authentication: Bearer` das requisições.
- O token deve conter informações do usuário autenticado, que será associado ao pedido.

## 🛠️ Configuração e Execução

### Rodando localmente

```bash
# 1. Clone o repositório
git clone https://github.com/oluizeduardo/jfood.git

# 2. Acesse a pasta do serviço
cd jfood/ms-orders

# 3. Compile e execute a aplicação
mvn spring-boot:run
```



