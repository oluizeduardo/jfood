# API Gateway

Este é o serviço **API Gateway** do projeto **jFood**, responsável por atuar como **ponto de entrada único** para todas as requisições externas, roteando dinamicamente as chamadas para os microsserviços registrados no **Eureka Server**.

---
## 🚀 Ordem de execução

> Atenção à ordem de inicialização dos serviços para garantir o funcionamento correto:

1. [Service Registry - Eureka Server](https://github.com/oluizeduardo/jfood/tree/main/ms-service-registry)
2. API Gateway (api-gateway)
3. Demais microsserviços (ex: ms-users, ms-notification, etc.)
---

## 🛠️ Execução

### Rodando localmente

```bash
# 1. Clone o repositório
git clone https://github.com/oluizeduardo/jfood.git

# 2. Acesse a pasta do serviço.
cd jfood/api-gateway

# 3. Compile e execute a aplicação
mvn spring-boot:run
```



