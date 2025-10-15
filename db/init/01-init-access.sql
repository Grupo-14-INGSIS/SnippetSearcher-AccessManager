CREATE TABLE IF NOT EXISTS permission (
    id_permission SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
);

CREATE TABLE IF NOT EXISTS manager (
    id_permission INT, --lo toma de otra tabla no se define aca
    id_snippet INT,
    id_user INT --no las defino como foreign keys porque habría que hacer conexiones con otras db's en otros repos
    --prefiero definirlo como parámetro y después se lo pasamos en el token
);