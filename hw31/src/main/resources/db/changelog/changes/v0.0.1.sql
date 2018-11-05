CREATE SEQUENCE account_id_seq;
CREATE SEQUENCE acl_class_id_seq;
CREATE SEQUENCE acl_entry_id_seq;
CREATE SEQUENCE acl_object_identity_id_seq;
CREATE SEQUENCE acl_sid_id_seq;
CREATE SEQUENCE author_id_seq;
CREATE SEQUENCE book_info_id_seq;
CREATE SEQUENCE comment_id_seq;
CREATE SEQUENCE genre_id_seq;
CREATE SEQUENCE system_message_id_seq;



CREATE TABLE public.account
(
  id bigint DEFAULT nextval('account_id_seq'::regclass) PRIMARY KEY NOT NULL,
  nickname varchar(255) NOT NULL,
  password varchar(255),
  role integer
);
INSERT INTO public.account (nickname, password, role) VALUES ('admin', '$2a$10$yjXINdq4KwTikUqY0cyxVu0NZkRLhDblU4jFsvqokBGR9M4YslQUi', 0);
INSERT INTO public.account (nickname, password, role) VALUES ('user1', '$2a$10$9t0ERr3UbJsooL21rVfnrOePC06NUnFi3SagI4E.aW8ptFr8sZZpm', 1);
INSERT INTO public.account (nickname, password, role) VALUES ('user2', '$2a$10$g6Z78pXnnZZYqhcdrqChA.Jh.vNNBzis6W8YFF3glzTNsDWf121nG', 1);


CREATE TABLE public.acl_class
(
  id bigint DEFAULT nextval('acl_class_id_seq'::regclass) PRIMARY KEY NOT NULL,
  class varchar(255) NOT NULL
);
INSERT INTO public.acl_class (class) VALUES ('ru.otus.springframework.hw31.domain.BookInfo');


CREATE TABLE public.acl_sid
(
  id bigint DEFAULT nextval('acl_sid_id_seq'::regclass) PRIMARY KEY NOT NULL,
  principal boolean NOT NULL,
  sid varchar(255) NOT NULL
);
INSERT INTO public.acl_sid (principal, sid) VALUES (true, 'user1');
INSERT INTO public.acl_sid (principal, sid) VALUES (false, 'ROLE_ADMIN');
INSERT INTO public.acl_sid (principal, sid) VALUES (true, 'user2');


CREATE TABLE public.acl_object_identity
(
  id bigint DEFAULT nextval('acl_object_identity_id_seq'::regclass) PRIMARY KEY NOT NULL,
  object_id_class bigint NOT NULL,
  object_id_identity bigint NOT NULL,
  parent_object bigint,
  owner_sid bigint,
  entries_inheriting boolean NOT NULL,
  CONSTRAINT acl_object_identity_object_id_class_fkey FOREIGN KEY (object_id_class) REFERENCES public.acl_class (id),
  CONSTRAINT acl_object_identity_parent_object_fkey FOREIGN KEY (parent_object) REFERENCES public.acl_object_identity (id),
  CONSTRAINT acl_object_identity_owner_sid_fkey FOREIGN KEY (owner_sid) REFERENCES public.acl_sid (id)
);
INSERT INTO public.acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (1, 1, null, 1, true);
INSERT INTO public.acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (1, 2, null, 1, true);
INSERT INTO public.acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (1, 3, null, 1, true);
INSERT INTO public.acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (1, 4, null, 3, true);
INSERT INTO public.acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (1, 5, null, 3, true);


CREATE TABLE public.acl_entry
(
  id bigint DEFAULT nextval('acl_entry_id_seq'::regclass) PRIMARY KEY NOT NULL,
  acl_object_identity bigint NOT NULL,
  ace_order integer NOT NULL,
  sid bigint NOT NULL,
  mask integer NOT NULL,
  granting boolean NOT NULL,
  audit_success boolean NOT NULL,
  audit_failure boolean NOT NULL,
  CONSTRAINT acl_entry_acl_object_identity_fkey FOREIGN KEY (acl_object_identity) REFERENCES public.acl_object_identity (id),
  CONSTRAINT acl_entry_sid_fkey FOREIGN KEY (sid) REFERENCES public.acl_sid (id)
);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (1, 0, 1, 1, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (1, 1, 1, 2, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (1, 2, 1, 8, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (1, 3, 2, 1, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (1, 4, 2, 2, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (1, 5, 2, 8, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (1, 6, 2, 16, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (2, 0, 1, 1, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (2, 1, 1, 2, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (2, 2, 1, 8, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (2, 3, 2, 1, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (2, 4, 2, 2, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (2, 5, 2, 8, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (2, 6, 2, 16, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (3, 0, 1, 1, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (3, 1, 1, 2, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (3, 2, 1, 8, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (3, 3, 2, 1, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (3, 4, 2, 2, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (3, 5, 2, 8, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (3, 6, 2, 16, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (4, 0, 3, 1, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (4, 1, 3, 2, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (4, 2, 3, 8, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (4, 3, 2, 1, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (4, 4, 2, 2, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (4, 5, 2, 8, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (4, 6, 2, 16, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (5, 0, 3, 1, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (5, 1, 3, 2, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (5, 2, 3, 8, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (5, 3, 2, 1, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (5, 4, 2, 2, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (5, 5, 2, 8, true, false, false);
INSERT INTO public.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (5, 6, 2, 16, true, false, false);


CREATE TABLE public.author
(
  id bigint DEFAULT nextval('author_id_seq'::regclass) PRIMARY KEY NOT NULL,
  name varchar(255) NOT NULL,
  surname varchar(255)
);
INSERT INTO public.author (name, surname) VALUES ('Михаил', 'Булгаков');
INSERT INTO public.author (name, surname) VALUES ('Лев', 'Толстой');
INSERT INTO public.author (name, surname) VALUES ('Richard', 'Feynman');
INSERT INTO public.author (name, surname) VALUES ('Robert', 'Leighton');
INSERT INTO public.author (name, surname) VALUES ('Matthew', 'Sands');
INSERT INTO public.author (name, surname) VALUES ('Michael', 'Goodrich');
INSERT INTO public.author (name, surname) VALUES ('Roberto', 'Tamassia');


CREATE TABLE public.genre
(
  id bigint DEFAULT nextval('genre_id_seq'::regclass) PRIMARY KEY NOT NULL,
  name varchar(255) NOT NULL
);
INSERT INTO public.genre (name) VALUES ('Роман');
INSERT INTO public.genre (name) VALUES ('Классика');
INSERT INTO public.genre (name) VALUES ('Мистика');
INSERT INTO public.genre (name) VALUES ('Сатира');
INSERT INTO public.genre (name) VALUES ('Java');
INSERT INTO public.genre (name) VALUES ('Учебник');
INSERT INTO public.genre (name) VALUES ('Documentation');
INSERT INTO public.genre (name) VALUES ('Физика');


CREATE TABLE public.book_info
(
  id bigint DEFAULT nextval('book_info_id_seq'::regclass) PRIMARY KEY NOT NULL,
  description text,
  title varchar(255) NOT NULL
);
INSERT INTO public.book_info (description, title) VALUES ('Обещание, содержащееся на страницах книги - "ваш роман вам принесет еще сюрпризы", - оправдалось вполне: написанный Мастером провидческий роман о дьяволе, пожалуй, явился одной из самых загадочных, удивительных и самых читаемых книг XX столетия! Многие слова и выражения из этого произведения вошли в современный лексикон, а персонажи своею реальностью затмили действительно существующих граждан.', 'Мастер и Маргарита');
INSERT INTO public.book_info (description, title) VALUES ('Роман-эпопея, описывающий события войн против Наполеона: 1805 года и отечественной 1812 года. Признан критикой всего мира величайшим эпическим произведением литературы нового времени.', 'Война и мир');
INSERT INTO public.book_info (description, title) VALUES (null, 'Фейнмановские лекции по физике. Выпуск 9. Квантовая механика');
INSERT INTO public.book_info (description, title) VALUES ('Now revised to reflect the innovations of Java 5.0, Goodrich and Tamassia''''s Fourth Edition of Data Structures and Algorithms in Java continues to offer accessible coverage of fundamental data structures, using a consistent object-oriented framework. The authors provide intuition, description, and analysis of fundamental data structures and algorithms. Numerous illustrations, web-based animations, and simplified mathematical analyses justify important analytical concepts.', 'Data Structures and Algorithms in Java');
INSERT INTO public.book_info (description, title) VALUES ('The reference documentation is divided into several sections: Core (IoC container, Events, Resources, i18n, Validation, Data Binding, Type Conversion, SpEL, AOP) Testing (Mock objects, TestContext framework, Spring MVC Test, WebTestClient) Data Access (Transactions, DAO support, JDBC, ORM, Marshalling XML) Web Servlet (Spring MVC, WebSocket, SockJS, STOMP messaging) Web Reactive (Spring WebFlux, WebClient, WebSocket) Integration (Remoting, JMS, JCA, JMX, Email, Tasks, Scheduling, Cache) Languages (Kotlin, Groovy, Dynamic languages).', 'Spring Framework Documentation');


CREATE TABLE public.book_info_authors
(
  book_info_id bigint NOT NULL,
  authors_id bigint NOT NULL,
  CONSTRAINT book_info_authors_pkey PRIMARY KEY (book_info_id, authors_id),
  CONSTRAINT fk7oms7u4s5y1y99tpagynheaqc FOREIGN KEY (book_info_id) REFERENCES public.book_info (id),
  CONSTRAINT fkifrsudvx49b83lrrp399jn2hh FOREIGN KEY (authors_id) REFERENCES public.author (id)
);
INSERT INTO public.book_info_authors (book_info_id, authors_id) VALUES (1, 1);
INSERT INTO public.book_info_authors (book_info_id, authors_id) VALUES (2, 2);
INSERT INTO public.book_info_authors (book_info_id, authors_id) VALUES (3, 3);
INSERT INTO public.book_info_authors (book_info_id, authors_id) VALUES (3, 4);
INSERT INTO public.book_info_authors (book_info_id, authors_id) VALUES (3, 5);
INSERT INTO public.book_info_authors (book_info_id, authors_id) VALUES (4, 6);
INSERT INTO public.book_info_authors (book_info_id, authors_id) VALUES (4, 7);


CREATE TABLE public.book_info_genres
(
  book_info_id bigint NOT NULL,
  genres_id bigint NOT NULL,
  CONSTRAINT book_info_genres_pkey PRIMARY KEY (book_info_id, genres_id),
  CONSTRAINT fk9oo9rxtchw1tc64ja5b74rsd3 FOREIGN KEY (book_info_id) REFERENCES public.book_info (id),
  CONSTRAINT fkjoyujxrhs9c14m55kslmaapm6 FOREIGN KEY (genres_id) REFERENCES public.genre (id)
);
INSERT INTO public.book_info_genres (book_info_id, genres_id) VALUES (1, 1);
INSERT INTO public.book_info_genres (book_info_id, genres_id) VALUES (1, 2);
INSERT INTO public.book_info_genres (book_info_id, genres_id) VALUES (1, 3);
INSERT INTO public.book_info_genres (book_info_id, genres_id) VALUES (1, 4);
INSERT INTO public.book_info_genres (book_info_id, genres_id) VALUES (2, 1);
INSERT INTO public.book_info_genres (book_info_id, genres_id) VALUES (2, 2);
INSERT INTO public.book_info_genres (book_info_id, genres_id) VALUES (3, 6);
INSERT INTO public.book_info_genres (book_info_id, genres_id) VALUES (3, 8);
INSERT INTO public.book_info_genres (book_info_id, genres_id) VALUES (4, 5);
INSERT INTO public.book_info_genres (book_info_id, genres_id) VALUES (4, 6);
INSERT INTO public.book_info_genres (book_info_id, genres_id) VALUES (4, 7);


CREATE TABLE public.comment
(
  id bigint DEFAULT nextval('comment_id_seq'::regclass) PRIMARY KEY NOT NULL,
  create_at timestamp NOT NULL,
  text varchar(255) NOT NULL,
  book_info_id bigint,
  CONSTRAINT fkoqim745vqt7sr6jouwa2qfvlg FOREIGN KEY (book_info_id) REFERENCES public.book_info (id)
);
INSERT INTO public.comment (create_at, text, book_info_id) VALUES ('2018-10-26 23:22:15.093000', 'Aliquam eros leo, bibendum ac sem et', 1);
INSERT INTO public.comment (create_at, text, book_info_id) VALUES ('2018-10-26 23:22:15.126000', 'tempor cursus ante', 1);
INSERT INTO public.comment (create_at, text, book_info_id) VALUES ('2018-10-26 23:22:15.137000', 'Mauris erat quam, dapibus in massa vel, commodo pretium odio.', 1);
INSERT INTO public.comment (create_at, text, book_info_id) VALUES ('2018-10-26 23:22:15.148000', 'Praesent efficitur et arcu at scelerisque.', 2);
INSERT INTO public.comment (create_at, text, book_info_id) VALUES ('2018-10-26 23:22:15.159000', 'Nulla ac efficitur sapien', 3);
INSERT INTO public.comment (create_at, text, book_info_id) VALUES ('2018-10-26 23:22:15.171000', 'Mauris sit amet molestie est, et lacinia sem. In hac habitasse platea dictumst. Morbi ex metus', 5);
INSERT INTO public.comment (create_at, text, book_info_id) VALUES ('2018-10-26 23:22:15.182000', 'blandit vitae metus a, condimentum consectetur erat', 5);


CREATE TABLE public.system_message
(
  id bigint DEFAULT nextval('system_message_id_seq'::regclass) PRIMARY KEY NOT NULL,
  content varchar(255)
);