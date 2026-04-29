# 📦 DeliveryTech API

API RESTful desenvolvida com **Spring Boot 3.2.5** e **Java 21** para gerenciar um sistema de
delivery completo. O projeto simula as funcionalidades principais de plataformas como iFood e
Uber Eats, incluindo autenticação JWT, cache, rastreamento distribuído, monitoramento via
Actuator/Micrometer e pipeline CI/CD com GitHub Actions.

---

## 🚀 Funcionalidades

- Cadastro e autenticação de usuários com JWT (Bearer Token)
- Controle de acesso baseado em perfis: `CLIENTE`, `RESTAURANTE`, `ADMIN`, `ENTREGADOR`
- CRUD de clientes, restaurantes, produtos e pedidos
- Listagem de produtos por restaurante
- Criação de pedidos com itens e cálculo automático do total
- Atualização de status do pedido (fluxo de estados)
- Cache com Spring Cache (Simple no dev, Redis no prod)
- Rastreamento distribuído com Micrometer Tracing + Zipkin (Brave)
- Monitoramento e métricas via Spring Boot Actuator
- Testes automatizados com JUnit 5, Mockito e Spring Security Test
- Documentação interativa com Swagger / SpringDoc OpenAPI 3
- Banco H2 em memória para desenvolvimento e testes
- Containerização com Docker e orquestração com Docker Compose
- Pipeline CI/CD com GitHub Actions
- Múltiplos perfis de configuração: `dev`, `prod`, `test`

---

## 🧰 Tecnologias e Dependências

### ☕ Core

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 21 (LTS) | Linguagem principal com suporte a Virtual Threads |
| Spring Boot | 3.2.5 | Framework base, auto-configuração e servidor embutido |
| Spring Boot Maven Plugin | 3.2.5 | Empacotamento e execução via Maven |

### 🌐 Web & API

| Dependência | Finalidade |
|---|---|
| `spring-boot-starter-web` | Servidor HTTP embutido (Tomcat), REST controllers, serialização JSON (Jackson) |
| `spring-boot-starter-validation` | Validação declarativa de DTOs com Bean Validation (`@NotNull`, `@Email`, etc.) |
| `springdoc-openapi-starter-webmvc-ui` 2.3.0 | Geração automática de documentação OpenAPI 3 + Swagger UI |

### 🔐 Segurança

| Dependência | Finalidade |
|---|---|
| `spring-boot-starter-security` | Filtros de autenticação, autorização e configuração de segurança HTTP |
| `jjwt-api` 0.11.5 | API para criação e parsing de tokens JWT |
| `jjwt-impl` 0.11.5 | Implementação do JJWT (runtime) |
| `jjwt-jackson` 0.11.5 | Integração do JJWT com Jackson para serialização dos claims |

### 🗄️ Persistência

| Dependência | Finalidade |
|---|---|
| `spring-boot-starter-data-jpa` | ORM com Hibernate, repositórios Spring Data, gerenciamento de transações |
| `h2` (runtime) | Banco de dados relacional em memória para dev e testes |

> **Nota:** Em produção recomenda-se PostgreSQL. Adicione o driver
> `org.postgresql:postgresql` e configure via `application-prod.yml`.

### ⚡ Cache

| Dependência | Finalidade |
|---|---|
| `spring-boot-starter-cache` | Abstração de cache (`@Cacheable`, `@CacheEvict`, `@CachePut`) |
| `spring-data-redis` | Integração com Redis via Lettuce (usado no perfil `prod`) |

### 📊 Observabilidade & Monitoramento

| Dependência | Finalidade |
|---|---|
| `spring-boot-starter-actuator` | Endpoints de saúde, métricas e informações da aplicação |
| `micrometer-observation` | API de observabilidade (spans, métricas, logs correlacionados) |
| `micrometer-tracing-bridge-brave` | Bridge entre Micrometer Tracing e o tracer Brave (Zipkin) |
| `zipkin-reporter-brave` | Envio de spans de rastreamento para o servidor Zipkin |

### 🛠️ Utilitários

| Dependência | Versão | Finalidade |
|---|---|---|
| `lombok` | 1.18.30 | Redução de boilerplate: `@Data`, `@Builder`, `@Slf4j`, etc. |

### 🧪 Testes

| Dependência | Finalidade |
|---|---|
| `spring-boot-starter-test` | JUnit 5, AssertJ, Hamcrest, MockMvc, Spring Test Context |
| `mockito-core` | Criação de mocks e stubs para testes unitários |
| `spring-security-test` | Suporte a `@WithMockUser`, `SecurityMockMvcRequestPostProcessors` |

---

## 📁 Estrutura do Projeto

```
delivery-api/
├── .github/
│   └── workflows/
├── src/
│   ├── main/
│   │   ├── java/com/deliverytech/
│   │   │   ├── config/                       # Configurações globais
│   │   │   ├── controller/                   # Camada de entrada da API (REST)
│   │   │   ├── dto/                          # Objetos de transporte (sem lógica)
│   │   │   │   ├── request/                  # Payloads de entrada (ex.: LoginRequest)
│   │   │   │   └── response/                 # Payloads de saída (ex.: TokenResponse)
│   │   │   ├── exception/                    # Tratamento centralizado de erros
│   │   │   ├── model/                        # Entidades JPA (mapeamento ORM)
│   │   │   ├── repository/                   # Interfaces Spring Data JPA
│   │   │   ├── security/                     # Infraestrutura de segurança
│   │   │   └── service/                      # Regras de negócio e orquestração
│   │   └── resources/
│   └── test/
│       └── java/com/deliverytech/
│           ├── controller/                   # Testes de integração com MockMvc
│           ├── service/                      # Testes unitários com Mockito
│           └── repository/                   # Testes de repositório com @DataJpaTest
├── docker-compose.yml                        # Sobe API + Redis + Zipkin
├── Dockerfile                                # Build da imagem da aplicação
├── pom.xml
└── README.md
```

---

## ⚙️ Como Rodar o Projeto

### 🔧 Pré-requisitos

| Ferramenta | Versão mínima |
|---|---|
| Java JDK | 21 |
| Maven | 3.9+ (ou use o `./mvnw` incluso) |
| Docker + Docker Compose | Qualquer versão recente (opcional) |

---

### 🖥️ Via Maven (perfil dev — H2 em memória)



# Clone o repositório
```
git clone https://github.com/crydemonw/deliverytech.git
cd deliverytech
```
# Roda com o perfil dev (padrão)
```./mvnw spring-boot:run´```




---

### 🐳 Via Docker Compose (sobe API + Redis + Zipkin)



# Build e start de todos os serviços
```docker-compose up --build```

# Em segundo plano
```docker-compose up --build -d```

# Parar e remover containers
```docker-compose down```

# Parar, remover containers e volumes (limpa dados Redis)
```docker-compose down -v```




---

## 🛠️ Comandos Maven Úteis



## Build 
### Compila, testa e empacota o JAR
```./mvnw clean package```

### Empacota sem rodar os testes
```./mvnw clean package -DskipTests```

### Apenas compila (sem empacotar)
```./mvnw clean compile```

## Execução 
### Roda com perfil dev (padrão definido no application.yml)
```./mvnw spring-boot:run```

### Roda explicitamente com perfil dev
```./mvnw spring-boot:run -Dspring-boot.run.profiles=dev```

### Roda com perfil prod (requer variáveis de ambiente configuradas)
```./mvnw spring-boot:run -Dspring-boot.run.profiles=prod```

## Testes
### Roda todos os testes (usa application-test.yml automaticamente)
```./mvnw test```

### Roda uma classe de teste específica
```./mvnw test -Dtest=PedidoServiceTest```

### Roda um método específico
```./mvnw test -Dtest=PedidoServiceTest#deveCriarPedidoComSucesso```

# Gera relatório de testes em target/surefire-reports/
```./mvnw surefire-report:report```

## Dependências 
### Lista as dependências do projeto em árvore
```./mvnw dependency:tree```

### Verifica se há atualizações de dependências disponíveis
```./mvnw versions:display-dependency-updates```

## Utilitários
### Valida o pom.xml
```./mvnw validate```

### Gera o JAR e o instala no repositório local (~/.m2)
```./mvnw install```

### Exibe as propriedades efetivas do projeto
```./mvnw help:effective-pom```


---

## 📄 Documentação da API

Com a aplicação rodando, acesse:

| Interface | URL |
|---|---|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |
| H2 Console (dev) | http://localhost:8080/h2-console |
| Actuator Health | http://localhost:8080/actuator/health |
| Actuator Metrics | http://localhost:8080/actuator/metrics |
| Zipkin UI | http://localhost:9411 |

---

## 🔐 Autenticação

A API utiliza **JWT Bearer Token**. Fluxo básico:



### 1. Registrar usuário 
```
POST /api/auth/register
Content-Type: application/json

{
  "nome": "William",
  "email": "william@email.com",
  "senha": "senha123",
  "perfil": "CLIENTE"
}
```
### 2. Fazer login e obter o token
```
POST /api/auth/login
Content-Type: application/json

{
  "email": "william@email.com",
  "senha": "senha123"
}
```
#### Resposta:
```
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```
### 3. Usar o token nas requisições protegidas
```
GET /api/pedidos
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```



---

## 🧪 Endpoints Principais

| Método | Endpoint | Perfil necessário | Descrição |
|---|---|---|---|
| `POST` | `/api/auth/register` | Público | Registra novo usuário |
| `POST` | `/api/auth/login` | Público | Autentica e retorna JWT |
| `GET` | `/api/clientes` | `ADMIN` | Lista todos os clientes |
| `GET` | `/api/restaurantes` | Público | Lista restaurantes |
| `POST` | `/api/pedidos` | `CLIENTE` | Cria novo pedido |

---

## 🐳 Docker

### Dockerfile


```
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/delivery-api-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## 🔄 CI/CD com GitHub Actions

O pipeline (`.github/workflows/ci.yml`) executa automaticamente a cada `push` ou `pull_request`
na branch `main`:

1. Checkout do código
2. Configuração do JDK 21
3. Cache de dependências Maven
4. `./mvnw clean package` (build + testes)
5. (Opcional) Build e push da imagem Docker

---

## 🤝 Como Contribuir



# Fork o repositório e clone localmente
```git clone https://github.com/crydemonw/deliverytech.git```

# Crie uma branch para sua feature
```git checkout -b feature/nome-da-feature```

# Faça suas alterações e commit
```git commit -m "feat: descrição da funcionalidade"```

# Suba a branch
```git push origin feature/nome-da-feature```

# Abra um Pull Request no GitHub




> Siga o padrão [Conventional Commits](https://www.conventionalcommits.org/) nas mensagens.

---

## 📬 Contato

Desenvolvido por **William**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/williamoliveirasilva)
[![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/crydemonw)
[![Email](https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:william.olive.silva@gmail.com)

---

## 📝 Licença

Este projeto está sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.