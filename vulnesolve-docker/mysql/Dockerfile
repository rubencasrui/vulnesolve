# Usa la imagen oficial de MySQL 8.0
FROM mysql:8.0.36

# Configura las variables de entorno con un usuario y contraseña
ENV MYSQL_ROOT_PASSWORD=2hF!8vT@nLkP3@Wx5ZdQmA1#YxG7bV9t
ENV MYSQL_DATABASE=vulnesolve
ENV MYSQL_USER=ruben
ENV MYSQL_PASSWORD=8uJ#5dK^2nL@9bV%4ZwQ!1RxG7yM0@Wx

# Copia el script SQL de inicialización
COPY ./mysql-init.sql /docker-entrypoint-initdb.d/
