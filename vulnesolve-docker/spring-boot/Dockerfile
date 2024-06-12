# Usa una imagen de OpenJDK 17
FROM openjdk:17

# Establece las variables de entorno para la conexión a la base de datos
ENV DATABASE_URL=jdbc:mysql://mysql:3306/vulnesolve
ENV DATABASE_USERNAME=ruben
ENV DATABASE_PASSWORD=8uJ#5dK^2nL@9bV%4ZwQ!1RxG7yM0@Wx
ENV DATABASE_DRIVER=com.mysql.cj.jdbc.Driver

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación Spring Boot
COPY ./vulnesolve-0.0.1.jar /app/

# Expone el puerto en el que la aplicación Spring Boot se ejecutará
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "vulnesolve-0.0.1.jar"]
