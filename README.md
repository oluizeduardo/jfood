# JFood - API REST para Gerenciamento de Usuários e Cardápio

JFood é uma aplicação que permite o gerenciamento de usuários e um cardápio de itens.

## 🚀 Tecnologias Utilizadas
- **Java 21**
- **Spring Boot 3.4.4**
- **Spring Security**
- **Spring Data JPA**
- **Spring Eureka**
- **Maven**
- **PostgresSQL**
- **Flyway**
- **Keycloak**
- **Docker**


## 🛠️ Instalação e Execução
1. **Clone o repositório:**
   ```sh
   git clone https://github.com/seu-usuario/jfood.git
   cd jfood
   ```
2. **Configure as variáveis de ambiente:**
- Crie um arquivo `.env` com base no exemplo disponível e edite-o com suas credenciais e configurações desejadas:
   ```sh
   cp .env.example .env
   ```

3. **Execute os serviços com Docker Compose:**
   ```sh
   docker-compose up -d
   ```

4. **Acesse os serviços nos seguintes endereços:** :

   | Serviço     | URL                     | Usuário / Senha             |
   |-------------|-------------------------|------------------------------|
   | PostgreSQL  | http://localhost:5432   | conforme `.env`             |
   | PgAdmin     | http://localhost:7000   | conforme `.env`             |
   | Keycloak    | http://localhost:7001   | conforme `.env`             |
   | SonarQube   | http://localhost:7002   | admin / admin               |
   | Jenkins     | http://localhost:7003   | senha gerada no container   |

5. **Para parar e remover os containers:**
   ```sh
   docker-compose down
      ```
   
## Postman Collection
- [Collection](https://documenter.getpostman.com/view/2828428/2sB2cVg2vU)
