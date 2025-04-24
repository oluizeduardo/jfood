# 📬 ms-notification

Microserviço da aplicação **JFood** responsável por gerenciar o envio de **notificações por e-mail**. Ele opera de forma reativa, consumindo eventos da fila do **RabbitMQ** para realizar comunicações automáticas com os usuários.

Este serviço atua exclusivamente como um consumidor da fila, e só realiza envios de e-mails quando os eventos são processados com sucesso.

---

## ⚙️ Funcionalidades

- 📧 Envio de e-mail de **boas-vindas** ao novo usuário cadastrado
- 🗑️ Envio de e-mail de **confirmação de exclusão de conta** quando um usuário é removido
- 🔁 Consumo assíncrono de eventos da fila do **RabbitMQ**

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4.4**
- **Spring AMQP (RabbitMQ)**
- **Maven**
- **RabbitMQ**

---

## 🧱 Estrutura de Pacotes

- `amqp` – Configuração do consumidor de mensagens RabbitMQ
- `config` – Configurações da aplicação, incluindo e-mail e filas
- `listener` – Componentes responsáveis por consumir e processar os eventos recebidos
- `service` – Lógica de envio de e-mails e tratamento das mensagens
- `dto` – Objetos de transferência de dados entre as camadas

---

## 🛠️ Configuração e Execução

### Rodando localmente

```bash
# 1. Clone o repositório
git clone https://github.com/oluizeduardo/jfood.git

# 2. Acesse a pasta do serviço de usuários.
cd jfood/ms-notification

# 3. Configure as variáveis de ambiente:
# Crie um arquivo .env com base no exemplo disponível e edite-o com suas credenciais e configurações desejadas:
cp .env.example .env

# 4. Compile e execute a aplicação
mvn spring-boot:run
```
