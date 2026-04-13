-- Tabela base para todas as pessoas do sistema
CREATE TABLE Pessoa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_de_nascimento DATE,
    matricula INT UNIQUE
);

-- Dados de autenticação (Relacionamento 1:1 com Pessoa)
CREATE TABLE Usuario_Autenticacao (
    pessoa_id INT PRIMARY KEY,
    senha VARCHAR(255) NOT NULL,
    data_ultimo_login DATETIME,
    FOREIGN KEY (pessoa_id) REFERENCES Pessoa(id)
);

-- Definição de perfis (Ex: Aluno, Professor, Gestor)
CREATE TABLE Perfil (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

-- Relacionamento N:N entre Pessoa e Perfil
CREATE TABLE Pessoa_Perfil (
    pessoa_id INT,
    perfil_id INT,
    PRIMARY KEY (pessoa_id, perfil_id),
    FOREIGN KEY (pessoa_id) REFERENCES Pessoa(id),
    FOREIGN KEY (perfil_id) REFERENCES Perfil(id)
);

CREATE TABLE Turma (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    limite_de_idade INT
);

-- Tabelas de associação para os papéis dentro da turma
CREATE TABLE Aluno_Turma (
    aluno_id INT,
    turma_id INT,
    data_matricula DATE,
    PRIMARY KEY (aluno_id, turma_id),
    FOREIGN KEY (aluno_id) REFERENCES Pessoa(id),
    FOREIGN KEY (turma_id) REFERENCES Turma(id)
);

CREATE TABLE Professor_Turma (
    professor_id INT,
    turma_id INT,
    PRIMARY KEY (professor_id, turma_id),
    FOREIGN KEY (professor_id) REFERENCES Pessoa(id),
    FOREIGN KEY (turma_id) REFERENCES Turma(id)
);

CREATE TABLE Gestor_Turma (
    gestor_id INT,
    turma_id INT,
    PRIMARY KEY (gestor_id, turma_id),
    FOREIGN KEY (gestor_id) REFERENCES Pessoa(id),
    FOREIGN KEY (turma_id) REFERENCES Turma(id)
);

-- Registro de chamadas realizadas
CREATE TABLE Chamada (
    id INT AUTO_INCREMENT PRIMARY KEY,
    turma_id INT,
    data_chamada DATE,
    status_chamada VARCHAR(20),
    valor_oferta DECIMAL(10, 2),
    qtd_visitantes INT,
    FOREIGN KEY (turma_id) REFERENCES Turma(id)
);

-- Registro de presença individual por chamada
CREATE TABLE Presenca (
    chamada_id INT,
    aluno_id INT,
    presente BOOLEAN,
    PRIMARY KEY (chamada_id, aluno_id),
    FOREIGN KEY (chamada_id) REFERENCES Chamada(id),
    FOREIGN KEY (aluno_id) REFERENCES Pessoa(id)
);

-- Tabela de Atividades (Modificada: professor_id agora permite NULL)
-- Alterado id para BIGINT para compatibilidade com atividade_link
CREATE TABLE Atividade (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    turma_id INT NOT NULL,
    professor_id INT NULL, 
    titulo VARCHAR(100) NOT NULL,
    data_publicacao DATETIME NOT NULL,
    descricao TEXT,
    FOREIGN KEY (turma_id) REFERENCES Turma(id),
    FOREIGN KEY (professor_id) REFERENCES Pessoa(id)
);

-- Nova tabela para links de apoio às atividades
CREATE TABLE atividade_link (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    atividade_id BIGINT NOT NULL,
    url VARCHAR(500) NOT NULL,
    descricao VARCHAR(255),
    FOREIGN KEY (atividade_id) REFERENCES Atividade(id)
);