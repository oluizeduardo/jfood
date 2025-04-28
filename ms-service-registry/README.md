# 🧭 ms-service-registry

Este projeto é um **Service Registry** criado com **Spring Cloud Eureka Server**, responsável por registrar e manter a descoberta de serviços do ambiente de microsserviços do JFood.

## 📦 Objetivo

Este serviço permite que microsserviços se registrem dinamicamente e descubram uns aos outros, promovendo **desacoplamento**, **escalabilidade** e **tolerância a falhas** dentro da arquitetura.

## ⚙️ Executando localmente

```bash
# 1. Clone o repositório
git clone https://github.com/oluizeduardo/jfood.git

# 2. Acesse a pasta do serviço.
cd jfood/ms-service-registry

# 3. Compile e execute a aplicação
mvn spring-boot:run

# 4. Acesse a aplicação do Eureka pelo browser.
http://localhost:8081
```
