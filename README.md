# Proyecto CurrencyAPI Backend

Este proyecto es una API backend construida con **Spring Boot** que obtiene y gestiona información sobre divisas. Utiliza una base de datos PostgreSQL para almacenar la información relacionada con las divisas y las solicitudes a una API externa de cambio de divisas.

## Requisitos previos

Antes de comenzar, asegúrate de tener instalados los siguientes programas en tu máquina:

- Java 21
- Maven
- PostgreSQL
- API Key de CurrencyAPI

## Clonar el Proyecto

Primero, clona el repositorio en tu máquina local usando Git:

```bash
git clone https://github.com/miguel-laraobregon/project_currencyapi_back.git
cd project_currencyapi_back
```

## Configuración de la Base de Datos

1. **Crear la base de datos**  
   Crea una base de datos en tu servidor PostgreSQL, por ejemplo `currency_db`.

   ```sql
   CREATE DATABASE currency_db;
   ```

2. **Configurar las credenciales de la base de datos**  
   Abre el archivo `src/main/resources/application.properties` y configura los siguientes parámetros:

   ```properties

   # Configuración de la base de datos
   spring.datasource.url=jdbc:postgresql://localhost:5432/YOUR-DB
   spring.datasource.username=YOUR-USERNAME
   spring.datasource.password=YOUR-PASSWORD
   ```

   Reemplaza `YOUR-DB`, `YOUR-USERNAME` y `YOUR-PASSWORD` con los valores correspondientes para tu base de datos PostgreSQL.

3. **Crear las tablas en la base de datos**  
   Ejecuta los siguientes scripts SQL para crear las tablas necesarias en la base de datos:

   ```sql
   -- Tabla con catálogo de divisas
   CREATE TABLE IF NOT EXISTS currencies (
       id BIGSERIAL PRIMARY KEY,
       code VARCHAR(3) UNIQUE NOT NULL,
       name VARCHAR(100) DEFAULT NULL
   );

   -- Tabla para almacenar los registros de solicitudes
   CREATE TABLE IF NOT EXISTS currency_requests (
       id BIGSERIAL PRIMARY KEY,
       status VARCHAR(50),
       last_updated_at TIMESTAMP DEFAULT NULL,
       response_time FLOAT,
       created_at TIMESTAMP NOT NULL,
       updated_at TIMESTAMP DEFAULT NULL
   );

   -- Tabla para almacenar los valores de las divisas por petición al API
   CREATE TABLE IF NOT EXISTS currency_requests_values (
       id BIGSERIAL PRIMARY KEY,
       request_id BIGINT NOT NULL,
       currency_id BIGINT NOT NULL,
       value DECIMAL(18, 10),
       FOREIGN KEY (request_id) REFERENCES currency_requests(id) ON DELETE CASCADE,
       FOREIGN KEY (currency_id) REFERENCES currencies(id) ON DELETE CASCADE
   );
   ```

## Configuración de la API Externa

Este proyecto hace uso de la API de [CurrencyAPI](https://currencyapi.com/) para obtener las tasas de cambio de divisas. 

1. **Obtener tu API Key**  
   Regístrate en [CurrencyAPI](https://currencyapi.com/) y obtén una clave de API.

2. **Configurar la URL y la clave de API**  
   Abre el archivo `src/main/resources/application.properties` y configura los siguientes parámetros:

   ```properties
   # Configuración de la URL y clave de la API
   currencyapi.url=https://api.currencyapi.com/v3/latest
   currencyapi.apikey=YOUR-API-KEY

   # Intervalo de tiempo en minutos entre cada solicitud a la API
   currencyapi.intervalMinutes=20

   # Tiempo de espera máximo en segundos para la solicitud a la API
   currencyapi.timeoutSeconds=5
   ```

   Reemplaza `YOUR-API-KEY` con la clave de API que obtuviste en el paso anterior.

## Ejecutar el Proyecto

Una vez que hayas configurado la base de datos y la API externa, puedes ejecutar el proyecto con el siguiente comando:

```bash
mvn spring-boot:run
```

La API estará disponible en [http://localhost:8080](http://localhost:8080).

## Endpoints de la API

La API proporciona los siguientes endpoints:

- **GET /currencies**  
  Obtiene una lista del catalogo de divisas almacenado en la base de datos

- **GET /currencies/{currencyCode}**  
  Obtiene una lista paginada de los registros de divisas con su valor y fecha de actualizacion
  Parametros opcionales: 
    finit = fecha en formato “YYYY-MM-DDThh:mm:ss”
    fend = fecha en formato “YYYY-MM-DDThh:mm:ss”

## Notas

- **Base de datos**: El proyecto está configurado para usar PostgreSQL como base de datos, pero se puede modificar fácilmente para trabajar con otras bases de datos.
- **Manejo de errores**: La aplicación maneja los errores de la API y los errores de la base de datos de manera que no interrumpan el flujo principal.
- **Intervalos de actualización**: El proyecto realiza peticiones periódicas a la API para mantener actualizadas las tasas de cambio. Puedes configurar el intervalo en minutos y el tiempo de espera en segundos en el archivo `application.properties`.

