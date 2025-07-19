-- Datenbank erstellen
CREATE DATABASE IF NOT EXISTS bookbuddy;
USE bookbuddy;

-- Tabelle "category" erstellen
CREATE TABLE IF NOT EXISTS category (
  id INT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

-- Tabelle "book" erstellen
CREATE TABLE IF NOT EXISTS book (
  id BIGINT PRIMARY KEY,
  author VARCHAR(255),
  title VARCHAR(255),
  category_id INT,
  FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Initialdaten für Kategorien einfügen
INSERT INTO category (id, name) VALUES (1, 'Roman');
INSERT INTO category (id, name) VALUES (2, 'Thriller');
INSERT INTO category (id, name) VALUES (3, 'Krimi');
INSERT INTO category (id, name) VALUES (4, 'Fantasy');
INSERT INTO category (id, name) VALUES (5, 'Science Fiction');
INSERT INTO category (id, name) VALUES (6, 'Biografie');
INSERT INTO category (id, name) VALUES (7, 'Autobiografie');
INSERT INTO category (id, name) VALUES (8, 'Sachbuch');
INSERT INTO category (id, name) VALUES (9, 'Ratgeber');
INSERT INTO category (id, name) VALUES (10, 'Psychologie');
INSERT INTO category (id, name) VALUES (11, 'Philosophie');
INSERT INTO category (id, name) VALUES (12, 'Kinderbuch');
INSERT INTO category (id, name) VALUES (13, 'Märchen');
INSERT INTO category (id, name) VALUES (14, 'Jugendbuch');
INSERT INTO category (id, name) VALUES (15, 'Lyrik');
INSERT INTO category (id, name) VALUES (16, 'Manga');
INSERT INTO category (id, name) VALUES (17, 'Historisch');
INSERT INTO category (id, name) VALUES (18, 'Horror');
INSERT INTO category (id, name) VALUES (19, 'Abenteuer');
INSERT INTO category (id, name) VALUES (20, 'Liebesroman');
INSERT INTO category (id, name) VALUES (21, 'Reise');
INSERT INTO category (id, name) VALUES (22, 'Wissenschaft');
INSERT INTO category (id, name) VALUES (23, 'Kunst');
INSERT INTO category (id, name) VALUES (24, 'Kochbuch');
INSERT INTO category (id, name) VALUES (25, 'Gelesen');
INSERT INTO category (id, name) VALUES (26, 'Am Lesen');
INSERT INTO category (id, name) VALUES (27, 'Lyrik');

-- Initialdaten für Bücher einfügen (fiktiv)
INSERT INTO book (id, author, title, category_id) VALUES (1001, 'Jules Verne', 'Reise zum Mittelpunkt der Erde', 5);
INSERT INTO book (id, author, title, category_id) VALUES (1002, 'Jane Austen', 'Stolz und Vorurteil', 1);
INSERT INTO book (id, author, title, category_id) VALUES (1003, 'George Orwell', '1984', 2);
INSERT INTO book (id, author, title, category_id) VALUES (1004, 'J.K. Rowling', 'Harry Potter und der Feuerkelch', 4);
INSERT INTO book (id, author, title, category_id) VALUES (1005, 'Stephen Hawking', 'Eine kurze Geschichte der Zeit', 22);
INSERT INTO book (id, author, title, category_id) VALUES (1006, 'Paulo Coelho', 'Der Alchimist', 9);
INSERT INTO book (id, author, title, category_id) VALUES (1007, 'Albert Einstein', 'Mein Weltbild', 6);
INSERT INTO book (id, author, title, category_id) VALUES (1008, 'Max Frisch', 'Stiller', 1);
INSERT INTO book (id, author, title, category_id) VALUES (1009, 'Agatha Christie', 'Mord im Orientexpress', 3);
INSERT INTO book (id, author, title, category_id) VALUES (1010, 'Franz Kafka', 'Die Verwandlung', 17);
