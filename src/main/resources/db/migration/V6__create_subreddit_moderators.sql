CREATE TABLE IF NOT EXISTS subreddit_moderators (
    account_id BIGINT NOT NULL REFERENCES accounts(id) ON DELETE CASCADE,
    subreddit_id BIGINT NOT NULL REFERENCES subreddits(id) ON DELETE CASCADE,
    PRIMARY KEY (account_id, subreddit_id)
);