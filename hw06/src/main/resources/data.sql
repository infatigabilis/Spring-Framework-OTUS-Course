INSERT INTO BOOKS (`TITLE`, `DESCRIPTION`) VALUES ('Мастер и Маргарита', 'Обещание, содержащееся на страницах книги - "ваш роман вам принесет еще сюрпризы", - оправдалось вполне: написанный Мастером провидческий роман о дьяволе, пожалуй, явился одной из самых загадочных, удивительных и самых читаемых книг XX столетия! Многие слова и выражения из этого произведения вошли в современный лексикон, а персонажи своею реальностью затмили действительно существующих граждан.');
INSERT INTO BOOKS (`TITLE`, `DESCRIPTION`) VALUES ('Война и мир', 'Роман-эпопея, описывающий события войн против Наполеона: 1805 года и отечественной 1812 года. Признан критикой всего мира величайшим эпическим произведением литературы нового времени.');
INSERT INTO BOOKS (`TITLE`) VALUES ('Фейнмановские лекции по физике. Выпуск 9. Квантовая механика');
INSERT INTO BOOKS (`TITLE`, `DESCRIPTION`) VALUES ('Data Structures and Algorithms in Java', 'Now revised to reflect the innovations of Java 5.0, Goodrich and Tamassia''s Fourth Edition of Data Structures and Algorithms in Java continues to offer accessible coverage of fundamental data structures, using a consistent object-oriented framework. The authors provide intuition, description, and analysis of fundamental data structures and algorithms. Numerous illustrations, web-based animations, and simplified mathematical analyses justify important analytical concepts.');
INSERT INTO BOOKS (`TITLE`, `DESCRIPTION`) VALUES ('Spring Framework Documentation', 'The reference documentation is divided into several sections: Core (IoC container, Events, Resources, i18n, Validation, Data Binding, Type Conversion, SpEL, AOP) Testing (Mock objects, TestContext framework, Spring MVC Test, WebTestClient) Data Access (Transactions, DAO support, JDBC, ORM, Marshalling XML) Web Servlet (Spring MVC, WebSocket, SockJS, STOMP messaging) Web Reactive (Spring WebFlux, WebClient, WebSocket) Integration (Remoting, JMS, JCA, JMX, Email, Tasks, Scheduling, Cache) Languages (Kotlin, Groovy, Dynamic languages).');

INSERT INTO AUTHORS (`NAME`, `SURNAME`) VALUES ('Михаил', 'Булгаков');
INSERT INTO AUTHORS (`NAME`, `SURNAME`) VALUES ('Лев', 'Толстой');
INSERT INTO AUTHORS (`NAME`, `SURNAME`) VALUES ('Richard', 'Feynman');
INSERT INTO AUTHORS (`NAME`, `SURNAME`) VALUES ('Robert', 'Leighton');
INSERT INTO AUTHORS (`NAME`, `SURNAME`) VALUES ('Matthew', 'Sands');
INSERT INTO AUTHORS (`NAME`, `SURNAME`) VALUES ('Michael', 'Goodrich');
INSERT INTO AUTHORS (`NAME`, `SURNAME`) VALUES ('Roberto', 'Tamassia');

INSERT INTO GENRES (`NAME`) VALUES ('Роман');
INSERT INTO GENRES (`NAME`) VALUES ('Классика');
INSERT INTO GENRES (`NAME`) VALUES ('Мистика');
INSERT INTO GENRES (`NAME`) VALUES ('Сатира');
INSERT INTO GENRES (`NAME`) VALUES ('Java');
INSERT INTO GENRES (`NAME`) VALUES ('Учебник');
INSERT INTO GENRES (`NAME`) VALUES ('Documentation');
INSERT INTO GENRES (`NAME`) VALUES ('Физика');

INSERT INTO AUTHOR_BOOK (`AUTHOR_ID`, `BOOK_ID`) VALUES (1, 1);
INSERT INTO AUTHOR_BOOK (`AUTHOR_ID`, `BOOK_ID`) VALUES (2, 2);
INSERT INTO AUTHOR_BOOK (`AUTHOR_ID`, `BOOK_ID`) VALUES (3, 3);
INSERT INTO AUTHOR_BOOK (`AUTHOR_ID`, `BOOK_ID`) VALUES (4, 3);
INSERT INTO AUTHOR_BOOK (`AUTHOR_ID`, `BOOK_ID`) VALUES (5, 3);
INSERT INTO AUTHOR_BOOK (`AUTHOR_ID`, `BOOK_ID`) VALUES (6, 4);
INSERT INTO AUTHOR_BOOK (`AUTHOR_ID`, `BOOK_ID`) VALUES (7, 4);

INSERT INTO GENRE_BOOK (`GENRE_ID`, `BOOK_ID`) VALUES (1, 1);
INSERT INTO GENRE_BOOK (`GENRE_ID`, `BOOK_ID`) VALUES (2, 1);
INSERT INTO GENRE_BOOK (`GENRE_ID`, `BOOK_ID`) VALUES (3, 1);
INSERT INTO GENRE_BOOK (`GENRE_ID`, `BOOK_ID`) VALUES (4, 1);
INSERT INTO GENRE_BOOK (`GENRE_ID`, `BOOK_ID`) VALUES (1, 2);
INSERT INTO GENRE_BOOK (`GENRE_ID`, `BOOK_ID`) VALUES (2, 2);
INSERT INTO GENRE_BOOK (`GENRE_ID`, `BOOK_ID`) VALUES (6, 3);
INSERT INTO GENRE_BOOK (`GENRE_ID`, `BOOK_ID`) VALUES (8, 3);
INSERT INTO GENRE_BOOK (`GENRE_ID`, `BOOK_ID`) VALUES (5, 4);
INSERT INTO GENRE_BOOK (`GENRE_ID`, `BOOK_ID`) VALUES (6, 4);
INSERT INTO GENRE_BOOK (`GENRE_ID`, `BOOK_ID`) VALUES (7, 4);