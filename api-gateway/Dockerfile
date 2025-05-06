# Usa a imagem base do Zulu OpenJDK 21
FROM azul/zulu-openjdk:21

# Criar usuario e grupo
ARG USER=jfood
ARG GROUP=jfood
ARG UID=1000
ARG GID=1000

RUN groupadd -g ${GID} ${GROUP} \
    && useradd -m -u ${UID} -g ${GID} -s /bin/bash ${USER}

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo .jar para dentro do container
COPY --chown=${USER}:${GROUP} target/*.jar app.jar

# Alterar usuario
USER $USER

# Permite passar opções para a JVM via variável de ambiente JAVA_OPTS
ENV JAVA_OPTS=""

# Comando para executar o JAR com JAVA_OPTS
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]