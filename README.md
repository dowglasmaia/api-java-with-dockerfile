# **API Java With Dockerfile**


### ** Detalhes do Dockerfile**

Este Dockerfile está dividido em duas fases principais: build e runtime.

#### **Fase 1: Build**

```dockerfile
# Fase 1: Build
FROM maven:3.9.7-amazoncorretto-17 AS build
WORKDIR /api-docker
COPY pom.xml .
RUN mvn dependency:resolve
COPY src ./src
RUN mvn clean install
```

1. **Base da Imagem de Build:**
    - `FROM maven:3.9.7-amazoncorretto-17 AS build`: Usa uma imagem base do Maven que inclui Amazon Corretto JDK 17. `AS build` nomeia esta etapa como "build", permitindo referenciá-la na próxima fase.

2. **Definir Diretório de Trabalho:**
    - `WORKDIR /api-docker`: Define o diretório de trabalho `/api-docker`, onde todos os comandos subsequentes serão executados.

3. **Copiar `pom.xml`:**
    - `COPY pom.xml .`: Copia o arquivo `pom.xml` para o diretório de trabalho no contêiner. Este arquivo define as dependências e configurações do projeto Maven.

4. **Resolver Dependências:**
    - `RUN mvn dependency:resolve`: Executa o Maven para resolver e baixar todas as dependências listadas no `pom.xml`. Isso usa a cache do Maven, agilizando a construção.

5. **Copiar Código Fonte:**
    - `COPY src ./src`: Copia todo o diretório `src` (contendo o código-fonte da aplicação) para `./src` no contêiner.

6. **Compilar e Empacotar:**
    - `RUN mvn clean install`: Compila o código-fonte e empacota a aplicação em um JAR, que é salvo no diretório `target` dentro de `/api-docker`.

#### **Fase 2: Runtime**

```dockerfile
# Fase 2: Runtime
FROM amazoncorretto:17-alpine3.17
LABEL MAINTAINER="DOWGLAS MAIA"
ENV PORT=8085
WORKDIR /usr/src/app
RUN rm -f /etc/localtime && ln -s /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime
COPY --from=build /api-docker/target/*.jar /usr/src/app/api.jar

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "/usr/src/app/api.jar", "--server.port=${PORT}"]

EXPOSE ${PORT}
```

1. **Base da Imagem de Runtime:**
    - `FROM amazoncorretto:17-alpine3.17`: Usa a imagem de Amazon Corretto JDK 17 baseada em Alpine Linux, que é leve e otimizada para a execução de aplicações Java. Diferente da fase de build, esta imagem não inclui ferramentas de construção como o Maven, apenas o runtime necessário para executar a aplicação.

2. **Informações de Manutenção:**
    - `LABEL MAINTAINER="DOWGLAS MAIA"`: Adiciona informações sobre o mantenedor do Dockerfile.

3. **Variável de Ambiente:**
    - `ENV PORT=8085`: Define a variável de ambiente `PORT` com o valor `8085`, que será usada para configurar a porta em que a aplicação irá ouvir.

4. **Definir Diretório de Trabalho:**
    - `WORKDIR /usr/src/app`: Define o diretório de trabalho `/usr/src/app` onde a aplicação será executada.

5. **Configurar Fuso Horário:**
    - `RUN rm -f /etc/localtime && ln -s /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime`: Ajusta o fuso horário do contêiner para São Paulo, Brasil, removendo o link de tempo local padrão e criando um novo link simbólico para o fuso horário de São Paulo.

6. **Copiar Artefato Compilado:**
    - `COPY --from=build /api-docker/target/*.jar /usr/src/app/api.jar`: Copia o JAR gerado na fase de build (`/api-docker/target/*.jar`) para o diretório de trabalho no contêiner de runtime (`/usr/src/app/api.jar`). A diretiva `--from=build` permite a transferência de artefatos da fase de build.

7. **Definir Ponto de Entrada:**
    - `ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "/usr/src/app/api.jar", "--server.port=${PORT}"]`: Define o comando padrão que será executado quando o contêiner iniciar. Isso executa a aplicação Java com o encoding UTF-8 e define a porta do servidor para a variável `${PORT}`.

8. **Expor Porta:**
    - `EXPOSE ${PORT}`: Informa ao Docker que a aplicação no contêiner estará escutando na porta definida pela variável `PORT`. Isso é utilizado para o mapeamento de portas quando o contêiner é iniciado.

### **Resumo**

1. **Fase de Build:**
    - **Construção:** Utiliza Maven para compilar e empacotar a aplicação Java. O Maven resolve as dependências a partir do `pom.xml` e gera um arquivo JAR no diretório `target`.

2. **Fase de Runtime:**
    - **Runtime Leve:** Utiliza uma imagem mínima de Amazon Corretto JDK 17 baseada em Alpine para executar a aplicação. Isso reduz o tamanho da imagem e melhora a performance em produção.
    - **Configuração:** Ajusta o fuso horário e define o comando de entrada que executa a aplicação Java. A porta de execução é configurada através de uma variável de ambiente.
   
### Execução
```bash
docker-compose up --build
```

