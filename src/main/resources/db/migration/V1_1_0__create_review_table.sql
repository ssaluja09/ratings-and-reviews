CREATE SEQUENCE hibernate_sequence START 1;

CREATE TABLE Review (
  id INT PRIMARY KEY,
  author_id INT,
  product_id INT,
  title VARCHAR,
  review_text VARCHAR,
  rating INT,
  review_status VARCHAR,
  CONSTRAINT unique_review UNIQUE (author_id, product_id)
);