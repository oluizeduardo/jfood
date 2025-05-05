# 🛍️ ms-products

Microsserviço responsável pelo cadastro, gerenciamento e consulta de produtos disponíveis na aplicação JFood.

O serviço oferece endpoints públicos e privados, permitindo tanto a listagem aberta de produtos quanto a gestão controlada por usuários autenticados.

## ⚙️ Funcionalidades

- 📋 Listagem pública de produtos disponíveis (`GET /products`) — não requer autenticação.

- 🔎 Consulta de detalhes de um produto específico (`GET /products/{id}`) — não requer autenticação.

- 🛠️ Cadastro, atualização e exclusão de produtos — requer autenticação.

## 🚀 Ordem de execução

> Atenção à ordem de inicialização dos serviços para garantir o funcionamento correto:

1. [Service Registry - Eureka Server](https://github.com/oluizeduardo/jfood/tree/main/ms-service-registry)
2. API Gateway (api-gateway)
3. Demais microsserviços (ex: ms-users, ms-notification, etc.)

## 🔐 Autenticação

As operações no `ms-products` seguem a seguinte política de autenticação:
- As rotas para listagem geral de produtos (`GET /products`), e detalhes de um produto específico (`GET /products/{id}`) são públicas e pode ser acessada sem token.
- Operações de criação, atualização e remoção de produtos exigem autenticação via Keycloak.
> O token JWT deve ser enviado no cabeçalho **Authorization Bearer** das requisições privadas.

## 🛠️ Configuração e Execução

### Rodando localmente

```bash
# 1. Clone o repositório
git clone https://github.com/oluizeduardo/jfood.git

# 2. Acesse a pasta do serviço
cd jfood/ms-products

# 3. Compile e execute a aplicação
mvn spring-boot:run
```



