CREATE TABLE IF NOT EXISTS user1 (
    id  INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    description VARCHAR(256) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user2 (
    id  INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    description VARCHAR(256) NOT NULL,
    PRIMARY KEY (id)
);