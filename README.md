# JFood - API REST para Gerenciamento de Usuários e Cardápio

JFood é uma API REST desenvolvida com **Java** e **Spring Boot**, que permite o gerenciamento de usuários e um cardápio de itens. Apenas usuários com papel de **ADMIN** podem cadastrar novos itens no cardápio.

## 🚀 Tecnologias Utilizadas
- **Java 21**
- **Spring Boot 3.4.4**
- **Maven**

## 📌 Funcionalidades
- Gerenciamento de Usuários: Criar, listar e buscar por ID.  
- Usuários possuem um papel (CUSTOMER ou ADMIN).  
- Apenas ADMIN pode cadastrar itens no cardápio.  
- Cardápio com descrição e preço dos itens.  
- Dados armazenados em uma lista em memória (simulação de banco de dados).


## 🛠️ Instalação e Execução
1. **Clone o repositório:**
   ```sh
   git clone https://github.com/seu-usuario/jfood.git
   cd jfood
   ```

2. **Compilar o projeto:**
   ```sh
   mvn clean install
   ```

3. **Executar a aplicação:**
   ```sh
   mvn spring-boot:run
   ```

## 🎯 Endpoints
### Usuários
- **GET /users** → Lista todos os usuários
- **GET /users/{id}** → Busca usuário por ID
- **POST /users** → Cria um novo usuário

### Cardápio
- **GET /menu** → Lista todos os itens do cardápio
- **POST /menu** → Cria um novo item (apenas para ADMIN)

