CREATE TABLE users (
user_id INT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(50) UNIQUE NOT NULL,
password VARCHAR(50) NOT NULL,
avatar VARCHAR(100),
introduction TEXT
);
CREATE TABLE article (
article_id INT PRIMARY KEY AUTO_INCREMENT,
title VARCHAR(100) NOT NULL,
content TEXT,
clicks INT DEFAULT 0,
publish_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
user_id INT,
FOREIGN KEY (user_id) REFERENCES user(user_id)
);
CREATE TABLE comment (
comment_id INT PRIMARY KEY AUTO_INCREMENT,
content TEXT,
article_id INT,
user_id INT,
parent_comment_id INT,
FOREIGN KEY (article_id) REFERENCES article(article_id),
FOREIGN KEY (user_id) REFERENCES user(user_id),
FOREIGN KEY (parent_comment_id) REFERENCES comment(comment_id)
);
CREATE TABLE likes (
like_id INT PRIMARY KEY AUTO_INCREMENT,
article_id INT,
user_id INT,
FOREIGN KEY (article_id) REFERENCES article(article_id),
FOREIGN KEY (user_id) REFERENCES user(user_id)
);
CREATE TABLE favorite (
favorite_id INT PRIMARY KEY AUTO_INCREMENT,
article_id INT,
user_id INT,
FOREIGN KEY (article_id) REFERENCES article(article_id),
FOREIGN KEY (user_id) REFERENCES users(user_id)
);