create table Pessoa (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_de_nascimento DATE,
    matricula INT UNIQUE
);

create table Usuario_Autenticacao (
	pessoa_id INT PRIMARY KEY,
    senha VARCHAR(255) NOT NULL,
    data_ultimo_login DATETIME,
    FOREIGN KEY (pessoa_id) REFERENCES Pessoa(id)
);

create table Perfil (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

create table Pessoa_Perfil (
	pessoa_id INT,
    perfil_id INT,
    PRIMARY KEY (pessoa_id,perfil_id),
    FOREIGN KEY (pessoa_id) REFERENCES Pessoa(id),
    FOREIGN KEY (perfil_id) REFERENCES Perfil(id)
);

create table Turma (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    limite_de_idade INT
);

create table Aluno_Turma (
	aluno_id INT,
    turma_id INT,
    data_matricula DATE,
    PRIMARY KEY (aluno_id,turma_id),
    FOREIGN KEY (aluno_id) REFERENCES Pessoa(id),
    FOREIGN KEY (turma_id) REFERENCES Turma(id)
);

create table Professor_Turma (
	professor_id INT,
    turma_id INT,
    PRIMARY KEY (professor_id,turma_id),
    FOREIGN KEY (professor_id) REFERENCES Pessoa(id),
    FOREIGN KEY (turma_id) REFERENCES Turma(id)
);

create table Gestor_Turma (
	gestor_id INT,
    turma_id INT,
    PRIMARY KEY (gestor_id,turma_id),
    FOREIGN KEY (gestor_id) REFERENCES Pessoa(id),
    FOREIGN KEY (turma_id) REFERENCES Turma(id)
);

create table Chamada (
	id INT AUTO_INCREMENT PRIMARY KEY,
    turma_id INT,
    data_chamada DATE,
    status_chamada VARCHAR(20),
    FOREIGN KEY (turma_id) REFERENCES Turma(id)
);

create table Presenca (
	chamada_id INT,
    aluno_id INT,
    presente BOOLEAN,
    PRIMARY KEY (chamada_id,aluno_id),
    FOREIGN KEY (chamada_id) REFERENCES Chamada(id),
    FOREIGN KEY (aluno_id) REFERENCES Pessoa(id)
);

create table Atividade (
	id INT AUTO_INCREMENT PRIMARY KEY,
    turma_id INT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT
);
