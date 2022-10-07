drop database if exists Mercado;
CREATE DATABASE Mercado;
use Mercado;

create table user(
    cpf char(14) primary key not null ,
    nome varchar(100),
    senha varchar(30)
);

create table produto(
    cod int primary key not null ,
    valor double,
    estoque int,
    descri varchar(100)
);

create table carrinho(
    cod_user char(14),
    cod_produto int,
    quantidade int,
    foreign key (cod_user) references user(cpf),
    foreign key (cod_produto) references produto(cod),
    primary key (cod_user, cod_produto, quantidade)
);

create table registro(
    cpf char(14),
    valor double,
    dataHora datetime,
    cod_produto int,
    quantidade int,
    primary key (cpf,valor,dataHora,cod_produto,quantidade)
);

insert into  user(cpf, nome, senha) values ("admin", "admin", "admin");
insert into  user(cpf, nome, senha) values ("107.008.099-37", "André", "1234");

insert into produto(cod, valor, estoque, descri) values (1, 4.00,10,"Agua");
insert into produto(cod, valor, estoque, descri) values (2, 15.50,5,"Café");
insert into produto(cod, valor, estoque, descri) values (3, 8.10,60,"Óleo de soja");
insert into produto(cod, valor, estoque, descri) values (4, 4.00,37,"1kg Açúcar");
insert into produto(cod, valor, estoque, descri) values (5, 2.40,21,"Macarrão Instntâneo");
insert into produto(cod, valor, estoque, descri) values (6, 2.70,6,"Wafer");
insert into produto(cod, valor, estoque, descri) values (7, 4.80,56,"1kg Trigo");
insert into produto(cod, valor, estoque, descri) values (8, 5.00,33,"Macarrão Espaguete");
insert into produto(cod, valor, estoque, descri) values (9, 7.80,9,"Nescau");
insert into produto(cod, valor, estoque, descri) values (10, 2.26,60,"Molho de tomate");
insert into produto(cod, valor, estoque, descri) values (11, 1.60,14,"1kg Sal");
insert into produto(cod, valor, estoque, descri) values (12, 3.40,43,"Milho");
insert into produto(cod, valor, estoque, descri) values (13, 6.00,90,"Cebola");
insert into produto(cod, valor, estoque, descri) values (14, 5.50,35,"Banana");

INSERT INTO carrinho(COD_USER, COD_PRODUTO, QUANTIDADE) VALUES ("admin", 1, 5);
INSERT INTO carrinho(COD_USER, COD_PRODUTO, QUANTIDADE) VALUES ("admin", 3, 2);
INSERT INTO carrinho(COD_USER, COD_PRODUTO, QUANTIDADE) VALUES ("admin", 12, 4);
INSERT INTO carrinho(COD_USER, COD_PRODUTO, QUANTIDADE) VALUES ("107.008.099-37", 14, 4);
INSERT INTO carrinho(COD_USER, COD_PRODUTO, QUANTIDADE) VALUES ("107.008.099-37", 6, 2);


