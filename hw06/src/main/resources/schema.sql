DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS AUTHORS;
DROP TABLE IF EXISTS GENRES;

CREATE TABLE BOOKS(ID BIGINT PRIMARY KEY AUTO_INCREMENT, TITLE VARCHAR(255) NOT NULL, DESCRIPTION TEXT);
CREATE TABLE AUTHORS(ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255) NOT NULL, SURNAME VARCHAR(255));
CREATE TABLE GENRES(ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255) NOT NULL);

CREATE TABLE GENRE_BOOK(
  GENRE_ID BIGINT NOT NULL,
  BOOK_ID BIGINT NOT NULL,
  FOREIGN KEY (GENRE_ID) REFERENCES GENRES(ID),
  FOREIGN KEY (BOOK_ID) REFERENCES BOOKS(ID)
);

CREATE TABLE AUTHOR_BOOK(
  AUTHOR_ID BIGINT NOT NULL,
  BOOK_ID BIGINT NOT NULL,
  FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHORS(ID),
  FOREIGN KEY (BOOK_ID) REFERENCES BOOKS(ID)
);