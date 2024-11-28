CREATE DATABASE gerenciamentoProjetos;

USE gerenciamentoProjetos;

CREATE TABLE projeto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    dataInit DATE,
    dataTermino DATE
);

CREATE TABLE tarefa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    statusT TEXT,
    dataCriacao DATE,
    dataConclusao DATE
);
