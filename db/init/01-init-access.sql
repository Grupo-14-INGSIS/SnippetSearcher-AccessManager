CREATE TABLE IF NOT EXISTS ownerships (
    snippet_id VARCHAR(255) PRIMARY KEY NOT NULL,
    owner_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS shares (
    snippet_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (snippet_id, user_id)
)