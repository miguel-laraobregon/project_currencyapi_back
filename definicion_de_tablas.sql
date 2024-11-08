Tablas

DROP TABLE currency_requests_values;
DROP TABLE currency_requests;
DROP TABLE currencies;

-- Tabla con catalogo de divisas
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

-- Tabla para almacenar los valores de las divisas por peticion al api
CREATE TABLE IF NOT EXISTS currency_requests_values (
    id BIGSERIAL PRIMARY KEY,
    request_id BIGINT NOT NULL,
    currency_id BIGINT NOT NULL,
    value DECIMAL(18, 10),
    FOREIGN KEY (request_id) REFERENCES currency_requests(id) ON DELETE CASCADE,
    FOREIGN KEY (currency_id) REFERENCES currencies(id) ON DELETE CASCADE
);


-- Comentarios de tabla con catalogo de divisas ( currencies )
COMMENT ON TABLE currencies IS 'Tabla que almacena catalogo de divisas';
COMMENT ON COLUMN currencies.code IS 'Código de la divisa (ejemplo: USD, EUR, MXN)';
COMMENT ON COLUMN currencies.name IS 'Nombre completo de la divisa (ejemplo: Dólar estadounidense)';

-- Comentarios de tabla para almacenar los registros de solicitudes ( currency_requests )
COMMENT ON TABLE currency_requests IS 'Tabla que almacena el registro de la solicitudes al API de currencyapi.com';
COMMENT ON COLUMN currency_requests.status IS 'Estado de la solicitud (ejemplo: PENDING, COMPLETED, FAILED)';
COMMENT ON COLUMN currency_requests.last_updated_at IS 'Fecha y hora devuelva por API indicando ultima actualizacion de divisas';
COMMENT ON COLUMN currency_requests.response_time IS 'Tiempo de respuesta de la llamada API en milisegundos';
COMMENT ON COLUMN currency_requests.created_at IS 'Fecha y hora de creacion del registro';
COMMENT ON COLUMN currency_requests.updated_at IS 'Fecha y hora de actualizacion del registro';

-- Comentarios de tabla para almacenar los valores de las divisas por peticion al api ( currency_requests_values )
COMMENT ON TABLE currency_requests_values IS 'Tabla que almacena el valor de las divisas por peticion al API';
COMMENT ON COLUMN currency_requests_values.request_id IS 'id de la peticion , relacion con currency_requests';
COMMENT ON COLUMN currency_requests_values.currency_id IS 'id de la divisa , relacion con currencies';
COMMENT ON COLUMN currency_requests_values.value IS 'Valor de la divisa al momento de la solicitud';

