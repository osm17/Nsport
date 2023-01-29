USE notes_app;

INSERT INTO notes (note_title, note_content) VALUES ('Grocery List', 'Eggs, milk, bread, apples');
INSERT INTO notes (note_title, note_content) VALUES ('Meeting Notes', 'Discussed project progress, assigned tasks');
INSERT INTO notes (note_title, note_content) VALUES ('Personal Journal', 'Reflecting on the past week, set goals for the future');

INSERT INTO tags (tag_content) VALUES ('shopping');
INSERT INTO tags (tag_content) VALUES ('food');
INSERT INTO tags (tag_content) VALUES ('work');
INSERT INTO tags (tag_content) VALUES ('project');
INSERT INTO tags (tag_content) VALUES ('personal');
INSERT INTO tags (tag_content) VALUES ('journaling');

INSERT INTO notes_tags (note_id, tag_id) VALUES (1, 1);
INSERT INTO notes_tags (note_id, tag_id) VALUES (1, 2);
INSERT INTO notes_tags (note_id, tag_id) VALUES (2, 3);
INSERT INTO notes_tags (note_id, tag_id) VALUES (2, 4);
INSERT INTO notes_tags (note_id, tag_id) VALUES (3, 5);
INSERT INTO notes_tags (note_id, tag_id) VALUES (3, 6);