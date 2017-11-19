USE `fakebook`;

INSERT INTO users (first_name, last_name, email, password, account_created)
VALUES ('Carl', 'McCann', 'carlmccann2@gmail.com', 'to be encrypted', NOW());

INSERT INTO users (first_name, last_name, email, password, account_created)
VALUES ('John', 'Doe', 'johndoe@gmail.com', 'to be encrypted', NOW());

INSERT INTO users (first_name, last_name, email, password, account_created)
VALUES ('Jane', 'Doe', 'janedoe@gmail.com', 'to be encrypted', NOW());


INSERT INTO friends (user_one_id, user_two_id, friends_since, relationship)
VALUES (2, 3, NOW(), 'married');

# friend request: friends_since = NULL
INSERT INTO friends (user_one_id, user_two_id, friends_since, relationship)
VALUES (1, 2, NULL, 'married');


INSERT INTO posts (user_id, user_posted_to_id, post_text, photo_id, post_created)
VALUES (2, 3, "Honey, I'm home", NULL, NOW());

INSERT INTO comments (post_id, user_id, comment_text, comment_created)
VALUES (1, 3, "How was your day?", NOW());

INSERT INTO likes(like_id, user_id, post_id, comment_id)
VALUES (1,3,1,NULL);

INSERT INTO likes(like_id, user_id, post_id, comment_id)
VALUES (2,2,NULL,1);



