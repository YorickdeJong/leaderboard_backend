CREATE TABLE IF NOT EXISTS leaderboard (
    id BIGSERIAL PRIMARY KEY,
    score FLOAT NOT NULL,
    player VARCHAR(72) NOT NULL,
    game VARCHAR(50) NOT NULL,
    date Date NOT NULL DEFAULT CURRENT_DATE
);