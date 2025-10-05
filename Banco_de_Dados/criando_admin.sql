INSERT INTO Perfil (nome)
VALUES ('admin');

INSERT INTO Pessoa (nome,matricula)
VALUES ('admin',999999);

INSERT INTO Usuario_Autenticacao (pessoa_id, senha, data_ultimo_login)
VALUES (1, SHA2('7654321', 256), NOW());

INSERT INTO Pessoa_Perfil (pessoa_id, perfil_id)
VALUES (1, 1);
