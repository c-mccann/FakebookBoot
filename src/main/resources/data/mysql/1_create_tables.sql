DROP DATABASE `fakebook`;

CREATE SCHEMA IF NOT EXISTS `fakebook`
  DEFAULT CHARACTER SET utf8;

USE `fakebook`;


CREATE TABLE IF NOT EXISTS `fakebook`.`users` (
  user_id         INT         NOT NULL AUTO_INCREMENT,
  first_name      VARCHAR(30) NOT NULL,
  last_name       VARCHAR(30) NOT NULL,
  email           VARCHAR(40) NOT NULL UNIQUE,
  password        VARCHAR(30) NOT NULL,
  account_created DATETIME    NULL,

  PRIMARY KEY (user_id)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `fakebook`.`photos` (
  photo_id    INT      NOT NULL,
  user_id     INT      NOT NULL,
  photo_added DATETIME NOT NULL,
  photo       BLOB     NOT NULL,
  profile_pic DATETIME NULL,    # most recent date will be current profile picture

  PRIMARY KEY (photo_id)
)
  ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `fakebook`.`posts` (
  post_id           INT      NOT NULL AUTO_INCREMENT,
  user_id           INT      NOT NULL,
  user_posted_to_id INT      NOT NULL,
  post_text         TEXT     NULL,
  photo_id          INT      NULL,
  post_created      DATETIME NOT NULL,

  PRIMARY KEY (post_id)
)
  ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `fakebook`.`friends` (
  friendship_id INT         NOT NULL AUTO_INCREMENT,
  user_one_id   INT         NOT NULL,
  user_two_id   INT         NOT NULL,
  friends_since DATETIME    NULL,
  relationship  VARCHAR(30) NULL,

  PRIMARY KEY (friendship_id)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `fakebook`.`comments` (
  comment_id      INT          NOT NULL AUTO_INCREMENT,
  post_id         INT          NOT NULL,
  user_id         INT          NOT NULL,
  comment_text    VARCHAR(250) NOT NULL,
  comment_created DATETIME     NOT NULL,

  PRIMARY KEY (comment_id)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `fakebook`.`likes` (
  like_id    INT NOT NULL AUTO_INCREMENT,
  user_id    INT NOT NULL,
  post_id    INT NULL,
  comment_id INT NULL,

  PRIMARY KEY (like_id)
)
  ENGINE = InnoDB;
