
# https://dba.stackexchange.com/questions/43284/two-nullable-columns-one-required-to-have-value
# so post must have either text or a photo or both, likes need to be associated to either a comment or post

DELIMITER / /
CREATE TRIGGER InsertPostTextPhotoNotNull
BEFORE INSERT ON posts
FOR EACH ROW
  BEGIN
    IF (NEW.post_text IS NULL AND NEW.photo_id IS NULL)
    THEN
      SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = '\'post_text\' and \'photo_id\' cannot both be null';
    END IF;
  END / /
CREATE TRIGGER UpdatePostTextPhotoNotNull
BEFORE UPDATE ON posts
FOR EACH ROW
  BEGIN
    IF (NEW.post_text IS NULL AND NEW.photo_id IS NULL)
    THEN
      SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = '\'post_text\' and \'photo_id\' cannot both be null';
    END IF;
  END / /

CREATE TRIGGER InsertLikePostCommentNotNull
BEFORE INSERT ON likes
FOR EACH ROW
  BEGIN
    IF (NEW.post_id IS NULL AND NEW.comment_id IS NULL)
    THEN
      SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = '\'post_id\' and \'comment_id\' cannot both be null';
    END IF;
  END / /
CREATE TRIGGER UpdateLikePostCommentNotNull
BEFORE UPDATE ON likes
FOR EACH ROW
  BEGIN
    IF (NEW.post_id IS NULL AND NEW.comment_id IS NULL)
    THEN
      SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = '\'post_id\' and \'comment_id\' cannot both be null';
    END IF;
  END / /


CREATE TRIGGER InsertLikePostCommentNotNotNull
BEFORE INSERT ON likes
FOR EACH ROW
  BEGIN
    IF (NEW.post_id IS NOT NULL AND NEW.comment_id IS NOT NULL)
    THEN
      SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = '\'post_id\' and \'comment_id\' cannot both be not null';
    END IF;
  END / /
CREATE TRIGGER UpdateLikePostCommentNotNotNull
BEFORE UPDATE ON likes
FOR EACH ROW
  BEGIN
    IF (NEW.post_id IS NOT NULL AND NEW.comment_id IS NOT NULL)
    THEN
      SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = '\'post_id\' and \'comment_id\' cannot both be not null';
    END IF;
  END / /


DELIMITER ;

