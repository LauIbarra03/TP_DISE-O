# Usar una imagen base que tenga Maven y OpenJDK
FROM ubuntu:20.04 

RUN apt-get update && apt-get install -y openjdk-17-jdk maven

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el cÃ³digo fuente al contenedor
COPY . .

# Ejecutar Maven para limpiar y empaquetar el proyecto
RUN mvn clean install -DskipTests
RUN mvn clean package -DskipTests



EXPOSE 8081


# Comando para ejecutar el archivo JAR
CMD ["java", "-jar", "target/aplicacion-1.0.jar"]
