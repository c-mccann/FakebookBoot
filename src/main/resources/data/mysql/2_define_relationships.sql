# FOREIGN KEY CONSTRAINTS

ALTER TABLE photos
  ADD CONSTRAINT photos_user_id_fk
FOREIGN KEY (user_id) REFERENCES users (user_id);


ALTER TABLE posts
  ADD CONSTRAINT posts_user_id_fk
FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE posts
  ADD CONSTRAINT posts_user_posted_to_id_fk
FOREIGN KEY (user_posted_to_id) REFERENCES users (user_id);

ALTER TABLE posts
  ADD CONSTRAINT posts_photo_id_fk
FOREIGN KEY (photo_id) REFERENCES photos (photo_id);


ALTER TABLE friends
  ADD CONSTRAINT friends_user_one_id_fk
FOREIGN KEY (user_one_id) REFERENCES users (user_id);

ALTER TABLE friends
  ADD CONSTRAINT friends_user_two_id_fk
FOREIGN KEY (user_two_id) REFERENCES users (user_id);

ALTER TABLE comments
  ADD CONSTRAINT comments_post_id_fk
FOREIGN KEY (post_id) REFERENCES posts (post_id);

ALTER TABLE comments
  ADD CONSTRAINT comments_user_id_fk
FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE likes
  ADD CONSTRAINT user_id_in_likes_fk
FOREIGN KEY (user_id) REFERENCES users (user_id);


ALTER TABLE likes
  ADD CONSTRAINT post_id_in_likes_fk
FOREIGN KEY (post_id) REFERENCES posts (post_id);

ALTER TABLE likes
  ADD CONSTRAINT comment_id_in_likes_fk
FOREIGN KEY (comment_id) REFERENCES comments (comment_id);
