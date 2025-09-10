# `[Chore] Configurar Jacoco para Análise de Cobertura de Testes`

**Labels Sugeridas:** `test`, `quality`, `build`

### Descrição

Para garantir que nossa suíte de testes seja eficaz e cubra as partes críticas do código, vamos integrar o **Jacoco (Java Code Coverage)** ao nosso processo de build. O Jacoco irá gerar um relatório detalhado mostrando quais linhas de código, branches (if/else) e classes foram exercitadas pelos nossos testes automatizados.

O objetivo é ter uma métrica clara da qualidade dos nossos testes e identificar áreas do código que não estão sendo testadas.

### Tarefas a Serem Executadas

- [ ] Adicionar o `jacoco-maven-plugin` à seção `<plugins>` do arquivo `pom.xml`.
- [ ] Configurar o plugin para ser executado durante a fase `test` do ciclo de vida do Maven.
- [ ] Configurar a tarefa `report` do plugin para gerar um relatório de cobertura em formato HTML.
- [ ] (Opcional) Configurar a tarefa `check` do plugin para falhar o build caso a cobertura de código não atinja um limiar mínimo (ex: 80% de cobertura de linhas). Isso garante que a qualidade dos testes seja mantida ao longo do tempo.
- [ ] Adicionar o diretório de relatórios do Jacoco (`/target/site/jacoco/`) ao arquivo `.gitignore` para evitar que os relatórios gerados localmente sejam enviados para o repositório.

### Critérios de Aceitação

* Ao executar o comando `mvn clean verify` (ou `mvn clean install`), o build deve rodar os testes e gerar um relatório de cobertura do Jacoco.
* O relatório HTML deve estar acessível em `/target/site/jacoco/index.html` e deve mostrar as métricas de cobertura para as classes do projeto.