CREATE DATABASE notes_app;
USE notes_app;

CREATE TABLE notes
(
    note_id      INT PRIMARY KEY AUTO_INCREMENT,
    note_title   VARCHAR(255) NOT NULL,
    note_content VARCHAR(255) NOT NULL
);

CREATE TABLE tags
(
    tag_id      INT PRIMARY KEY AUTO_INCREMENT,
    tag_content VARCHAR(255) NOT NULL
);

CREATE TABLE notes_tags
(
    note_id INT,
    tag_id  INT,
    PRIMARY KEY (note_id, tag_id),
    FOREIGN KEY (note_id) REFERENCES notes (note_id),
    FOREIGN KEY (tag_id) REFERENCES tags (tag_id)
);
