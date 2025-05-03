# 🍔 JFood

O JFood é uma aplicação moderna desenvolvida em arquitetura de microserviços, focada no gerenciamento completo de restaurantes. Ele oferece funcionalidades essenciais para operações como cadastro e autenticação de usuários, cadastro de produtos e controle de pedidos.

O sistema é composto por um **API Gateway**, um **Service Registry** e diversos microserviços independentes. Alguns desses serviços comunicam-se de forma assíncrona por meio de uma fila **RabbitMQ**, garantindo escalabilidade e desacoplamento entre os módulos.

A autenticação é realizada utilizando o **Keycloak**, com suporte à geração de tokens de acesso (OAuth2) e validação de roles para controle de permissões.

## 📚 Sumário
- [🧩 Arquitetura da Solução](#arquitetura-da-solução)
- [🚀 Tecnologias Utilizadas](#tecnologias-utilizadas)
- [🛠️ Instalação e Execução](#instalação-e-execução)
- [📬 Testes de API](#testes-de-api)

## Arquitetura da Solução
![](imgs/JFood-architecture.PNG)

## Tecnologias Utilizadas
![Java](https://img.shields.io/badge/Java-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring%20Security-brightgreen)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-brightgreen)
![Spring Eureka](https://img.shields.io/badge/Spring%20Eureka-brightgreen)
![Maven](https://img.shields.io/badge/Maven-red)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-cc0000)
![Keycloak](https://img.shields.io/badge/Keycloak-0a58ca)
![Docker](https://img.shields.io/badge/Docker-%230db7ed?logo=docker&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-ff6600)

## Instalação e Execução
1. **Clone o repositório:**
   ```sh
   git clone https://github.com/oluizeduardo/jfood.git
   cd jfood
   ```
2. **Configure as variáveis de ambiente:**
- Crie um arquivo `.env` com base no exemplo disponível e edite-o com suas credenciais e configurações desejadas:
   ```sh
   cp .env.example .env
   ```

3. **Suba os serviços auxiliares com Docker Compose:**
   ```sh
   docker-compose up -d
   ```

4. **Acesse os serviços auxiliares nos seguintes endereços:**

   | Serviço     | URL                     | Usuário / Senha             |
   |-------------|-------------------------|-----------------------------|
   | PostgreSQL  | http://localhost:5432   | conforme `.env`             |
   | PgAdmin     | http://localhost:7000   | conforme `.env`             |
   | Keycloak    | http://localhost:7001   | conforme `.env`             |   
   | SonarQube   | http://localhost:7002   | admin / admin               |
   | Jenkins     | http://localhost:7003   | senha gerada no container   |
   | RabbitMQ    | http://localhost:15672  | `guest`/`guest`             |

   **Para parar e remover os containers:**
   ```sh
   docker-compose down
   ```
5. **Executando os serviços do JFood:**

   Inicie os serviços principais do JFood seguindo a ordem abaixo:
   - ms-service-registry (Eureka Server)
   - api-gateway
   - ms-users
   - ms-products
   - ms-notification
   - ms-orders

   > Importante: Os quatro últimos microserviços (ms-users, ms-products, ms-notification e ms-orders) podem ser iniciados em qualquer ordem, desde que o Service Registry e o API Gateway já estejam ativos.
   
## Testes de API
Uma collection Postman está disponível para facilitar o teste dos endpoints da aplicação:
- [Clique aqui para acessar a Collection](https://documenter.getpostman.com/view/2828428/2sB2cVg2vU)
