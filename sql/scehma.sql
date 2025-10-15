CREATE DATABASE Tomas_Loaiza_LibroNOVA;
USE Tomas_Loaiza_LibroNOVA;

CREATE TABLE books (
	isbn INT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    total_copies INT,
    copies_available INT,
    stock INT,
    price_ref FLOAT
);

CREATE TABLE users (
	id_user SERIAL PRIMARY KEY,
    name_user VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_user VARCHAR(255) NOT NULL,
    rol ENUM('admin', 'client')
);

CREATE TABLE loans (
	id_loan SERIAL PRIMARY KEY,
    isbn INT,
    id_user BIGINT UNSIGNED,
    start_date DATE,
    return_date DATE,
    status_loan ENUM('active','returned'),
    
    FOREIGN KEY (isbn) REFERENCES books(isbn),
    FOREIGN KEY (id_user) REFERENCES users(id_user)
    
);