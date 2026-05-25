# Fase 1: Build - Usa Maven com Java 21 para compilar
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Fase 2: Execução - Usa uma imagem leve do JRE 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copia o jar gerado na fase de build
COPY --from=build /app/target/*.jar app.jar
# Porta padrão do Spring Boot
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]