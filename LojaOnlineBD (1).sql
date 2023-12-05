create table usuario(
	cpf char(11) primary key not null check (length(cpf) = 11),
	nome varchar(100) not null, 
	email varchar(100) not null unique,
	senha varchar(100) not null,
	prime boolean default false
);

create table loja(
	id serial primary key,
	nome_vendedor varchar(100) not null,
	cpf_vendedor char(11) not null check (length(cpf_vendedor) = 11),
	nome_loja varchar(100) not null,
	endereco varchar(100),
	email varchar(100) unique not null,
	cnpj char(14) unique not null check (length(cnpj) = 11),
	data_criacao date
);

create table produto(
	id serial primary key,
    nome varchar(100) not null,
    preco decimal(10, 2) not null,
    categoria varchar(50) not null,
    loja_id int references loja(id) on delete cascade
);

-- Tabela de Carrinho de Compras
CREATE TABLE carrinho (
    id SERIAL PRIMARY KEY,
    usuario_cpf CHAR(11) REFERENCES usuario(cpf) ON DELETE CASCADE
);

-- Tabela de Itens no Carrinho
CREATE TABLE itens_carrinho (
    carrinho_id INT REFERENCES carrinho(id) ON DELETE CASCADE,
    produto_id INT REFERENCES produto(id) ON DELETE CASCADE,
    quantidade INT NOT NULL,
    PRIMARY KEY (carrinho_id, produto_id)
);

-- Tabela de Compras
CREATE TABLE compras (
    id SERIAL PRIMARY KEY,
    usuario_cpf CHAR(11) REFERENCES usuario(cpf) ON DELETE CASCADE,
    data_compra DATE DEFAULT CURRENT_DATE,
    valor_total DECIMAL(10, 2) NOT NULL
);

-- Tabela de Detalhes da Compra
CREATE TABLE detalhes_compra (
    compra_id INT REFERENCES compras(id) ON DELETE CASCADE,
    produto_id INT REFERENCES produto(id) ON DELETE CASCADE,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (compra_id, produto_id)
);


