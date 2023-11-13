ALTER TABLE users
    ADD CONSTRAINT username_length CHECK (LENGTH(username) >= 4);

ALTER TABLE users
    ADD CONSTRAINT password_length CHECK (LENGTH(password) >= 6);

ALTER TABLE users
    ADD CONSTRAINT email_format CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$');
