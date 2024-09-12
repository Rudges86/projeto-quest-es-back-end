insert into Usuarios (id, nome, email, password, role, status) values (101,'Rudge', 'rudges86@gmail.com','$2a$12$VQSVztr/M8tcwzcy9jJgFebpqybKpF4FVwM3zilhOAI4yL1iW3rJa' , 'ROLE_ADMIN', 'ATIVO');
insert into Usuarios (id, nome, email, password, role, status) values (102,'Valdinete', 'valdinete@gmail.com','$2a$12$VQSVztr/M8tcwzcy9jJgFebpqybKpF4FVwM3zilhOAI4yL1iW3rJa' , 'ROLE_CLIENTE', 'ATIVO');
insert into Usuarios (id, nome, email, password, role, status) values (103,'Juçânia', 'jucania@gmail.com','$2a$12$VQSVztr/M8tcwzcy9jJgFebpqybKpF4FVwM3zilhOAI4yL1iW3rJa' , 'ROLE_CLIENTE', 'ATIVO');
insert into Usuarios (id, nome, email, password, role, status) values (104,'Barbára', 'babi@gmail.com','$2a$12$VQSVztr/M8tcwzcy9jJgFebpqybKpF4FVwM3zilhOAI4yL1iW3rJa' , 'ROLE_CLIENTE', 'ATIVO');
insert into Usuarios (id, nome, email, password, role, status) values (105,'Raul', 'raul@gmail.com','$2a$12$VQSVztr/M8tcwzcy9jJgFebpqybKpF4FVwM3zilhOAI4yL1iW3rJa' , 'ROLE_CLIENTE', 'ATIVO');

insert into Disciplina(id, nome) VALUES(1,'Geografia');
insert into Banca(id, nome) VALUES(1, 'ChatGPT');

insert into Questoes (id,pergunta,ano ,resposta,dificuldade,banca_id, disciplina_id) VALUES (1,'Qual é a capital do Brasil?','2024', 'Brasília','fácil', 1, 1);
insert into Questoes_alternativas (questoes_id, alternativas) VALUES (1,'Rio de janeiro');
insert into Questoes_alternativas (questoes_id, alternativas) VALUES (1,'Aracaju');
insert into Questoes_alternativas (questoes_id, alternativas) VALUES (1,'Bahia');
insert into Questoes_alternativas (questoes_id, alternativas) VALUES (1,'Florianopolis');
insert into Questoes_alternativas (questoes_id, alternativas) VALUES (1,'Brasília');

