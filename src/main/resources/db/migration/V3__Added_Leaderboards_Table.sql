CREATE TABLE IF NOT EXISTS games (
     id BIGSERIAL PRIMARY KEY,
     name VARCHAR(50) UNIQUE NOT NULL,
     description TEXT,
     release_date DATE DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS leaderboard (
    id BIGSERIAL PRIMARY KEY,
    score FLOAT NOT NULL,
    user_id BIGINT NOT NULL,
    game_id BIGINT NOT NULL,
    date Date NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (game_id) REFERENCES games(id)
);

