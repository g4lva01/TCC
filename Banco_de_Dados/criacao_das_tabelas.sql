-- Tabela base para todas as pessoas do sistema
CREATE TABLE pessoa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_de_nascimento DATE,
    matricula INT UNIQUE,
    ativo BOOLEAN DEFAULT TRUE -- Coluna adicionada aqui
);

-- Dados de autenticação (Relacionamento 1:1 com Pessoa)
CREATE TABLE usuario_autenticacao (
    pessoa_id INT PRIMARY KEY,
    senha VARCHAR(255) NOT NULL,
    data_ultimo_login DATETIME,
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
);

CREATE TABLE usuario_autenticacao_roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    usuario_autenticacao_pessoa_id BIGINT NOT NULL,
    roles VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- Definição de perfis (Ex: Aluno, Professor, Gestor)
CREATE TABLE perfil (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

-- Relacionamento N:N entre Pessoa e Perfil
CREATE TABLE pessoa_perfil (
    pessoa_id INT,
    perfil_id INT,
    PRIMARY KEY (pessoa_id, perfil_id),
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id),
    FOREIGN KEY (perfil_id) REFERENCES perfil(id)
);

CREATE TABLE turma (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    limite_de_idade INT
);

-- Tabelas de associação para os papéis dentro da turma
CREATE TABLE aluno_turma (
    aluno_id INT,
    turma_id INT,
    data_matricula DATE,
    PRIMARY KEY (aluno_id, turma_id),
    FOREIGN KEY (aluno_id) REFERENCES pessoa(id),
    FOREIGN KEY (turma_id) REFERENCES turma(id)
);

CREATE TABLE professor_turma (
    professor_id INT,
    turma_id INT,
    PRIMARY KEY (professor_id, turma_id),
    FOREIGN KEY (professor_id) REFERENCES pessoa(id),
    FOREIGN KEY (turma_id) REFERENCES turma(id)
);

CREATE TABLE gestor_turma (
    gestor_id INT,
    turma_id INT,
    PRIMARY KEY (gestor_id, turma_id),
    FOREIGN KEY (gestor_id) REFERENCES pessoa(id),
    FOREIGN KEY (turma_id) REFERENCES turma(id)
);

-- Registro de chamadas realizadas
CREATE TABLE chamada (
    id INT AUTO_INCREMENT PRIMARY KEY,
    turma_id INT,
    data_chamada DATE,
    status_chamada VARCHAR(20),
    valor_oferta DECIMAL(10, 2),
    qtd_visitantes INT,
    FOREIGN KEY (turma_id) REFERENCES turma(id)
);

CREATE TABLE chamada_log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    chamada_id INT NOT NULL,
    usuario_id INT NOT NULL, -- ID da Pessoa que fez a alteração
    data_alteracao DATETIME DEFAULT CURRENT_TIMESTAMP,
    acao VARCHAR(100), -- Ex: "Criação", "Atualização de Status", "Correção de Presenças"
    FOREIGN KEY (chamada_id) REFERENCES chamada(id),
    FOREIGN KEY (usuario_id) REFERENCES pessoa(id)
);

-- Registro de presença individual por chamada
CREATE TABLE presenca (
    chamada_id INT,
    aluno_id INT,
    presente BOOLEAN,
    PRIMARY KEY (chamada_id, aluno_id),
    FOREIGN KEY (chamada_id) REFERENCES chamada(id),
    FOREIGN KEY (aluno_id) REFERENCES pessoa(id)
);

-- Tabela de Atividades
CREATE TABLE atividade (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    turma_id INT NOT NULL,
    professor_id INT NULL, 
    titulo VARCHAR(100) NOT NULL,
    data_publicacao DATETIME NOT NULL,
    descricao TEXT,
    FOREIGN KEY (turma_id) REFERENCES turma(id),
    FOREIGN KEY (professor_id) REFERENCES pessoa(id)
);

-- Nova tabela para links de apoio às atividades
CREATE TABLE atividade_link (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    atividade_id BIGINT NOT NULL,
    url VARCHAR(500) NOT NULL,
    descricao VARCHAR(255),
    FOREIGN KEY (atividade_id) REFERENCES atividade(id)
);