# Controle de Patrimônio

Sistema de Gestão Patrimonial desenvolvido em Java, com interface gráfica em Swing e banco de dados MySQL. A aplicação oferece funcionalidades para cadastro e gerenciamento de patrimônios e responsáveis, além da gravação automática de movimentações, proporcionando maior controle, rastreabilidade e histórico das ações realizadas sobre os ativos.

## Visão geral do projeto

- Aplicação desktop em Java 17
- Interface gráfica construída com Swing
- Persistência de dados em MySQL
- JAR empacotado com dependências usando Maven Assembly
- Banco de dados criado automaticamente a partir de `src/main/resources/DB_SCRIPT.sql`

## Requisitos

1. Java 17 ou superior instalado
2. Maven instalado (versão atual compatível com Java 17)
3. MySQL rodando em `localhost:3306`
4. Usuário `root` sem senha (configuração atual do projeto)

> Se o MySQL estiver usando outra conta ou senha, será preciso alterar `src/main/java/src/repository/DbSession.java`.

## Banco de dados

A aplicação exige um banco MySQL local na porta `3306`. O projeto se conecta a:

- Host: `localhost`
- Porta: `3306`
- Banco: `controle_patrimonio`
- Usuário: `root`
- Senha: `""` (vazia)

O script de criação do banco está em `src/main/resources/DB_SCRIPT.sql` e contém as tabelas:

- `responsavel`
- `patrimonio`
- `movimentacao_patrimonio`

### Observações sobre criação do banco

Na primeira execução, a aplicação tenta se conectar ao banco `controle_patrimonio`. Se ele ainda não existir, usa `DB_SCRIPT.sql` para criar o banco e as tabelas.

## Como executar o projeto "na mão"

### 1. Clonar o projeto

Clone o repositório para sua máquina utilizando Git:

```powershell
git clone https://github.com/rogerhenriquediegoli/controle_patrimonio.git
```

### 1. Preparar o ambiente

- Instale Java 17+.
- Instale Maven.
- Verifique com:

```powershell
java -version
mvn -version
```

### 2. Verificar o MySQL

- Certifique-se de que o MySQL está em execução em `localhost:3306`.
- Crie o banco ou deixe a aplicação criar automaticamente.
- Se quiser testar a conexão manualmente:

```powershell
mysql -u root -p -h localhost -P 3306
```

> Não é obrigatório criar o banco manualmente, pois o projeto já contém o script SQL.

### 3. Compilar com Maven

Abra o terminal na pasta raiz do projeto e execute:

```powershell
mvn clean package
```

Isso gera o JAR empacotado com dependências em:

```text
target/controle-patrimonio-1.0.0-jar-with-dependencies.jar
```

### 4. Executar o JAR gerado

Após a compilação, execute:

```powershell
java -jar target\controle-patrimonio-1.0.0-jar-with-dependencies.jar
```

## Como executar apenas o JAR

Se você não pretende compilar o projeto, pode baixar o arquivo `controle-patrimonio.jar` diretamente na seção de Releases do repositório.

Após o download, basta ter o Java 17+ instalado e executar a aplicação com dois cliques no arquivo ou pelo terminal:

```powershell
java -jar controle-patrimonio.jar
```

## Estrutura do projeto

- `src/main/java/src/Main.java` - classe principal do aplicativo
- `src/main/java/src/repository/DbSession.java` - gerenciamento de conexão e inicialização do banco
- `src/main/java/src/dao/` - interfaces para operações de CRUD
- `src/main/java/src/dao/impl/` - implementações das operações de acesso a dados
- `src/main/java/src/service/` - contratos de negócio
- `src/main/java/src/service/impl/` - lógica de negócio e validações
- `src/main/java/src/views/` - telas Swing
- `src/main/resources/DB_SCRIPT.sql` - script de criação do banco e tabelas

## Dependências

- `mysql:mysql-connector-java:5.1.49`

## Customizações possíveis

Se o MySQL usar outro usuário ou senha, altere `USER` e `PASSWORD` em `src/main/java/src/repository/DbSession.java`.

Se quiser alterar o host ou a porta do banco, modifique `SERVER_PATH` e `DB_PATH` também em `DbSession.java`.

## Problemas comuns

- `Communications link failure`: o MySQL não está rodando em `localhost:3306` ou a conexão está bloqueada.
- `Unknown database 'controle_patrimonio'`: a aplicação tenta criar automaticamente, mas não consegue acessar o servidor MySQL. Verifique usuário/senha.
- `Access denied`: credenciais incorretas.