DROP TABLE players;

CREATE TABLE IF NOT EXISTS players
(
    id UUID PRIMARY KEY,
    name VARCHAR NOT NULL,
    chat_id BIGINT NOT NULL UNIQUE,
    playing BOOLEAN,
    searching BOOLEAN
);