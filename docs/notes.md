# Notes:

# Project scope:

I want to implement a Reddit clone using Spring Web flux. This will allow me to learn Spring Webflux.

## Functional Req:
- Users should be able to sign up and login
- Users are able to create sub-Reddits and set moderators and rules
- Users should be able to create posts in sub reddits
- Each post can be upvotes and downvotes
- Each post can have comments which have nesting. comments can be upvotes and downvotes as well.
- Users have a feed based on the sub reddits they join
- Users are able to manage account and perform password updates.
- Users have a karma which start at 1 and aggregates with post upvotes or downvotes.

## Backend Features:
- JWT and AuthO based auth
- Entities and Repositories
- Controllers and Services
- Logging

## Steps:
- define schema
- create required schemas in DB
- Initialize base app
- create health check endpoint
- connect application with existing postgres deployment on computer
- Start defining entities and repositories
  - Start with user as auth is first feature
  - Move on to post
- Create subreddit, Post and Comments resources moving forward
