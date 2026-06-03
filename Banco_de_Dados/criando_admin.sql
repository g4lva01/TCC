INSERT INTO perfil (nome)
VALUES ('aluno');
INSERT INTO perfil (nome)
VALUES ('professor');
INSERT INTO perfil (nome)
VALUES ('gestor');

INSERT INTO pessoa (nome,matricula)
VALUES ('admin',999999);

INSERT INTO usuario_autenticacao (pessoa_id, senha, data_ultimo_login)
VALUES (1, SHA2('7654321', 256), NOW());

INSERT INTO pessoa_perfil (pessoa_id, perfil_id)
VALUES (1, 1); -- aluno
INSERT INTO pessoa_perfil (pessoa_id, perfil_id)
VALUES (1, 2); -- professor
INSERT INTO pessoa_perfil (pessoa_id, perfil_id)
VALUES (1, 3); -- gestor

INSERT INTO usuario_autenticacao_roles (usuario_autenticacao_pessoa_id, roles)
VALUES (1, 'ROLE_ALUNO');
INSERT INTO usuario_autenticacao_roles (usuario_autenticacao_pessoa_id, roles)
VALUES (1, 'ROLE_PROFESSOR');
INSERT INTO usuario_autenticacao_roles (usuario_autenticacao_pessoa_id, roles)
VALUES (1, 'ROLE_GESTOR');