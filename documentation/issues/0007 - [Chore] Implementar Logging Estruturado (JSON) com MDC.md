# `[Chore] Implementar Logging Estruturado (JSON) com MDC`

**Labels Sugeridas:** `chore`, `enhancement`, `observability`, `logging`

### Descrição

Em uma arquitetura de microserviços, depurar problemas olhando logs de texto puro de múltiplos serviços é quase impossível. O **Logging Estruturado** resolve isso formatando as saídas de log como objetos JSON. Logs em JSON são facilmente processáveis por máquinas, permitindo que ferramentas de agregação (como a Stack ELK/EFK) os indexem, filtrem e pesquisem de forma eficiente.

Além disso, vamos implementar o **MDC (Mapped Diagnostic Context)** para adicionar um `traceId` (ID de rastreamento) único a cada requisição que chega na nossa API. Esse `traceId` será incluído em **todas as linhas de log** geradas durante o processamento daquela requisição, permitindo-nos agrupar e seguir o rastro de uma operação específica através de todo o sistema.

### Pré-requisito: Adicionar Dependência

Adicione a dependência do encoder do Logstash para o Logback no seu `pom.xml`:

```xml
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
    <version>7.4</version> </dependency>
```

### Tarefas a Serem Executadas

1.  **Configurar o Logback para Saída em JSON:**
    - [ ] Crie o arquivo `logback-spring.xml` no diretório `src/main/resources`.
    - [ ] Configure um `appender` para o console que utilize o `LogstashEncoder`. Este encoder irá formatar todas as saídas de log como JSON.
    - [ ] Garanta que o appender-padrão (`<root level="INFO">`) utilize a nova configuração.

    *Exemplo de `logback-spring.xml`*:
    ```xml
    <configuration>
        <springProperty scope="context" name="springAppName" source="spring.application.name"/>
        <appender name="CONSOLE_JSON" class="ch.qos.logback.core.ConsoleAppender">
            <encoder class="net.logstash.logback.encoder.LogstashEncoder">
                <fieldNames>
                    <timestamp>timestamp</timestamp>
                    <version>[ignore]</version>
                    <level>level</level>
                    <thread>thread</thread>
                    <logger>logger</logger>
                    <message>message</message>
                    <stackTrace>stack_trace</stackTrace>
                </fieldNames>
                <customFields>{"application_name":"${springAppName}"}</customFields>
            </encoder>
        </appender>
        <root level="INFO">
            <appender-ref ref="CONSOLE_JSON"/>
        </root>
    </configuration>
    ```

2.  **Implementar Interceptor para Adicionar o `traceId` via MDC:**
    - [ ] Crie a classe `MdcInterceptor.java` no pacote `...api.interceptor`.
    - [ ] Faça a classe implementar a interface `HandlerInterceptor`.
    - [ ] No método `preHandle`, gere um `UUID` único e adicione-o ao `MDC` com a chave `traceId`. Ex: `MDC.put("traceId", traceId);`
    - [ ] No método `afterCompletion`, limpe o MDC (`MDC.clear()`) para evitar vazamento de contexto entre as requisições.

3.  **Registrar o Interceptor na Aplicação:**
    - [ ] Crie uma classe de configuração (ex: `WebMvcConfig.java`) no pacote `...config`, anotada com `@Configuration`.
    - [ ] Faça a classe implementar `WebMvcConfigurer`.
    - [ ] Sobrescreva o método `addInterceptors` e registre o seu `MdcInterceptor`.

### Critérios de Aceitação

* Ao iniciar a aplicação, todos os logs no console devem aparecer no formato JSON.
* Ao fazer uma requisição para qualquer endpoint da API, cada linha de log gerada por aquela requisição deve conter um campo `"traceId"` com um valor de UUID, e esse valor deve ser o mesmo para todos os logs da mesma requisição.