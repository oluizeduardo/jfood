# 🧾 ms-orders

Microsserviço responsável pelo **cadastro e gerenciamento de pedidos de compra** realizados pelos usuários na aplicação **JFood**. 

Sempre que um novo pedido é criado com sucesso, o serviço publica um **evento na fila RabbitMQ**, permitindo que outros microserviços (como notificações, faturamento, etc.) reajam de forma assíncrona a esse evento.

## ⚙️ Funcionalidades

- 🛒 Criação de novos pedidos de compra
- 📦 Consulta de pedidos por usuário
- 🧾 Detalhamento de um pedido específico
- 📤 Emissão de evento na **fila RabbitMQ** após o registro do pedido

## 🔐 Autenticação

Todas as operações do **Orders-MS** exigem que o usuário esteja **autenticado via Keycloak**.

- O token JWT deve ser fornecido no cabeçalho Authentication Bearer das requisições.
- O token deve conter informações do usuário autenticado, que será associado ao pedido.

## 🛠️ Configuração e Execução

### Rodando localmente

```bash
# 1. Clone o repositório
git clone https://github.com/oluizeduardo/jfood.git

# 2. Acesse a pasta do serviço de usuários.
cd jfood/ms-orders

# 3. Compile e execute a aplicação
mvn spring-boot:run
```



