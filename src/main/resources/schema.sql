CREATE TABLE users (
    id IDENTITY,             -- 自動で番号が振られる主キー
    name VARCHAR NOT NULL    -- ユーザ名（空にはできない）
);
CREATE TABLE matches (
    id IDENTITY,             -- 自動で番号が振られる主キー
    user1 INT NOT NULL,      -- 対戦したユーザ1のid（usersテーブルのid）
    user2 INT NOT NULL,      -- 対戦したユーザ2のid（usersテーブルのid）
    user1Hand VARCHAR NOT NULL, -- ユーザ1が出した手
    user2Hand VARCHAR NOT NULL  -- ユーザ2が出した手
);
