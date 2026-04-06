CREATE TABLE IF NOT EXISTS post_comments(
    post_id BIGINT NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
    comment_id BIGINT NOT NULL REFERENCES comments(id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, comment_id)
);