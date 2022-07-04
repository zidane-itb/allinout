CREATE TABLE IF NOT EXISTS secrets
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    secretString varchar(36) NOT NULL UNIQUE,
    username varchar(50) NOT NULL UNIQUE,
    `active` binary NOT NULL
);