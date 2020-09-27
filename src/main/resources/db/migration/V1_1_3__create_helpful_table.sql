CREATE TABLE helpfulness (
  author_id INT,
  review_id INT,
  is_helpful BOOLEAN,
  PRIMARY KEY (author_id, review_id)
);