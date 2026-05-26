CREATE DATABASE IF NOT EXISTS controle_patrimonio
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_unicode_ci;

USE controle_patrimonio;
   
CREATE TABLE IF NOT EXISTS responsavel (
	id INT AUTO_INCREMENT PRIMARY KEY,
	nome_completo VARCHAR(100) NOT NULL,
	cpf VARCHAR(11) NOT NULL UNIQUE,
	email VARCHAR(100),
	telefone VARCHAR(20),
	setor VARCHAR(50),
	cargo VARCHAR(50),
	sexo ENUM('M','F', 'O', 'N') NOT NULL,
	data_cadastro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	status TINYINT NOT NULL
);

CREATE TABLE IF NOT EXISTS patrimonio (
	id INT AUTO_INCREMENT PRIMARY KEY,

	descricao VARCHAR(200) NOT NULL,
	numero_patrimonio VARCHAR(50) UNIQUE,

	status TINYINT NOT NULL, 

	id_responsavel INT,

	CONSTRAINT fk_patrimonio_responsavel
	FOREIGN KEY (id_responsavel) REFERENCES responsavel(id)
);

CREATE TABLE IF NOT EXISTS movimentacao_patrimonio (
	id INT AUTO_INCREMENT PRIMARY KEY,

	id_patrimonio INT NOT NULL,

	tipo_movimentacao TINYINT NOT NULL,

	status_anterior TINYINT,
	status_atual TINYINT NOT NULL,

	id_responsavel_anterior INT,
	id_responsavel_atual INT,

	data_movimentacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

	observacao VARCHAR(400),

	CONSTRAINT fk_mov_patrimonio
	FOREIGN KEY (id_patrimonio) REFERENCES patrimonio(id),

	CONSTRAINT fk_resp_ant
	FOREIGN KEY (id_responsavel_anterior) REFERENCES responsavel(id),

	CONSTRAINT fk_resp_atual
	FOREIGN KEY (id_responsavel_atual) REFERENCES responsavel(id)
);