# `[Feature] Implementar Métricas com Actuator e Prometheus`

**Labels Sugeridas:** `feature`, `observability`, `monitoring`

### Descrição

O objetivo desta tarefa é configurar a aplicação para expor métricas de desempenho e saúde utilizando o **Spring Boot Actuator** e o **Micrometer**. As métricas serão expostas em um formato compatível com o **Prometheus**, um sistema de monitoramento padrão da indústria.

Além de configurar a aplicação, vamos adicionar o Prometheus e o **Grafana** (para visualização de dashboards) ao nosso `docker-compose.yml` para criar um ambiente de monitoramento local completo.

### Tarefa 1: Adicionar Dependências no `pom.xml`

- [x] Abra seu arquivo `pom.xml` e garanta que as dependências `spring-boot-starter-actuator` e `micrometer-registry-prometheus` estejam presentes. É uma boa prática declará-las no início da seção de dependências, logo após o `spring-boot-starter-web`.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Tarefa 2: Configurar a Aplicação (`application.properties`)

- [x] Abra seu arquivo `src/main/resources/application.properties` e adicione as seguintes linhas. Estas configurações são cruciais para que a aplicação exponha os endpoints corretamente e aceite conexões externas (do container do Prometheus).

```properties
# ===============================================================
# SERVER CONFIGURATION
# ===============================================================
# Permite que o servidor aceite conexões de qualquer interface de rede (essencial para o Docker)
server.address=0.0.0.0

# ===============================================================
# ACTUATOR CONFIGURATION
# ===============================================================
# Expõe os endpoints de 'health' e 'prometheus' via web. 
# SEM esta linha, eles ficam inacessíveis e retornam 404.
management.endpoints.web.exposure.include=health,prometheus

# Garante que as métricas do servidor web (Tomcat) sejam exportadas
server.tomcat.mbeanregistry.enabled=true
```

### Tarefa 3: Atualizar o `docker-compose.yml`

- [x] Adicione os serviços do Prometheus e do Grafana ao seu ambiente e inclua a diretiva `extra_hosts` para garantir a comunicação entre o container do Prometheus e a sua aplicação rodando na máquina host.

```yaml
version: '3.8'

services:
  postgres-db:
    # ... (configuração do postgres-db) ...
  
  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    ports:
      - "9090:9090"
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
    networks:
      - ecommerce-network
    command:
      - '--config.file=/etc/prometheus/prometheus.yml'
    # [IMPORTANTE] Adiciona uma entrada de DNS para garantir que o container
    # consiga resolver 'host.docker.internal' para o IP da máquina host.
    extra_hosts:
      - "host.docker.internal:host-gateway"

  grafana:
    # ... (configuração do grafana) ...

# ... (definições de volumes e networks) ...
```

### Tarefa 4: Criar o Arquivo de Configuração do Prometheus (`prometheus.yml`)

- [x] Na raiz do seu projeto, crie o arquivo `prometheus.yml`. A configuração `job_name` deve corresponder ao esperado pelo dashboard que vamos importar.

```yaml
global:
  scrape_interval: 15s

scrape_configs:
  # [IMPORTANTE] O 'job_name' deve ser 'spring-boot-application' para ser
  # compatível com dashboards populares do Grafana, como o ID 4701.
  - job_name: 'spring-boot-application'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['host.docker.internal:8080']
```

### Tarefa 5: Rodar e Verificar (Passos de Depuração)

- [x] Inicie todos os containers com `docker compose up -d`.
- [x] Inicie sua aplicação Spring Boot.
- [x] **Verificação 1 (Aplicação):** Acesse `http://localhost:8080/actuator/prometheus` no seu navegador. **Resultado esperado:** Uma página de texto com as métricas. Se retornar `404 Not Found`, verifique a **Tarefa 2**.
- [x] **Verificação 2 (Prometheus):** Acesse `http://localhost:9090/targets`. **Resultado esperado:** O alvo (`spring-boot-application`) deve estar com o `State` **UP (verde)**.
    - Se o erro for `connection refused`, verifique a propriedade `server.address=0.0.0.0` na **Tarefa 2**.
    - Se o erro for `404 Not Found`, verifique a propriedade `management.endpoints.web.exposure.include` na **Tarefa 2**.
    - Se houver outro erro de conexão, verifique a diretiva `extra_hosts` na **Tarefa 3**.
- [x] **Verificação 3 (Grafana):** Acesse `http://localhost:3000` (login: `admin`/`admin`).
    - Adicione o Prometheus como Data Source (URL: `http://prometheus:9090`).
    - Importe o dashboard de ID `4701` e associe-o à fonte de dados Prometheus.
    - Gere tráfego na sua API (`GET`, `POST` em `localhost:8080/api/v1/products`) e observe os painéis do dashboard serem populados.

### Critérios de Aceitação

* [x] A aplicação expõe as métricas com sucesso no endpoint `/actuator/prometheus`.
* [x] O Prometheus consegue coletar as métricas da aplicação, mostrando o alvo como `UP`.
* [x] O Grafana se conecta ao Prometheus e exibe as métricas da aplicação em um dashboard.