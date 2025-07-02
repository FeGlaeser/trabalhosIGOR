# Plataforma EduLivre - Implementação em PostgreSQL e Java JDBC

![Logo EduLivre](imagens/logo.png) *(opcional: adicione uma imagem de logo se disponível)*

## 📚 Visão Geral do Projeto

EduLivre é uma plataforma de gestão de aprendizagem (LMS) desenvolvida em Java com JDBC, utilizando PostgreSQL como banco de dados. Permite:

# ✅ Gestão de usuários com diferentes perfis (aluno, professor, admin)

# ✅ Criação e matrícula em cursos

# ✅ Gerenciamento de conteúdos multimídia

# ✅ Sistema de avaliações com dados estruturados em JSONB

### 🧰 Tecnologias Utilizadas
    Java 11+
    PostgreSQL 12+
    JDBC
    Maven
    JSON (org.json)

Dependências:
    postgresql (driver JDBC)
    json (org.json)

### Modelagem do Banco de Dados
```sql
-- Criação do banco de dados
CREATE DATABASE edulivre;

-- Habilitar extensão para UUIDs (necessária para uuid_generate_v4)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Drop total do schema edulivre (caso já exista)
DROP SCHEMA IF EXISTS edulivre CASCADE;

-- Criar schema
CREATE SCHEMA IF NOT EXISTS edulivre;

-- Tabela de Usuários
CREATE TABLE edulivre.usuario (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    perfil TEXT NOT NULL CHECK (perfil IN ('aluno', 'professor', 'admin'))
);

-- Tabela de Cursos
CREATE TABLE edulivre.curso (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    titulo TEXT NOT NULL,
    descricao TEXT,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    avaliacao JSONB DEFAULT '{}'::jsonb
);

-- Tabela de Matrículas
CREATE TABLE edulivre.matricula (
    id SERIAL PRIMARY KEY,
    usuario_id UUID NOT NULL,
    curso_id UUID NOT NULL,
    data_matricula TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(usuario_id, curso_id),
    CONSTRAINT fk_matricula_usuario FOREIGN KEY (usuario_id) REFERENCES edulivre.usuario(id) ON DELETE CASCADE,
    CONSTRAINT fk_matricula_curso FOREIGN KEY (curso_id) REFERENCES edulivre.curso(id) ON DELETE CASCADE
);

-- Tabela de Conteúdos
CREATE TABLE edulivre.conteudo (
    id SERIAL PRIMARY KEY,
    curso_id UUID NOT NULL,
    titulo TEXT NOT NULL,
    descricao TEXT,
    tipo TEXT NOT NULL CHECK (tipo IN ('video', 'pdf', 'imagem', 'audio', 'quiz', 'slide')),
    arquivo BYTEA,
    CONSTRAINT fk_conteudo_curso FOREIGN KEY (curso_id) REFERENCES edulivre.curso(id) ON DELETE CASCADE
);
```

## 🛠️ Funcionalidades
### Gestão de Usuários
- Cadastro/edição de usuários (aluno, professor, admin)
- Validação de e-mail único
- Autenticação básica

### Sistema de Cursos
- Criação de cursos com:
  - Título e descrição
  - Sistema de avaliações (JSONB)
  - Controle de matrículas

### Conteúdos Multimídia
- Upload de arquivos (vídeos, PDFs, imagens)
- Organização por tipo (video, pdf, imagem, etc.)

## 🚀 Instalação (com Maven)
### Pré-requisitos
1. PostgreSQL 12+ instalado
2. Java JDK 11+
3. Driver JDBC PostgreSQL (postgresql-42.3.1.jar)


### 📥 Clonando o projeto
git clone https://github.com/jovana/edulivre.git
cd edulivre

### 🛠️ Configurando o banco de dados
### Acesse o PostgreSQL com um usuário que tenha permissão:

psql -U postgres
## Execute os scripts SQL:

\i src/main/resources/sql/create_schema.sql
\i src/main/resources/sql/insert_data.sql
Certifique-se de que a extensão uuid-ossp está habilitada (CREATE EXTENSION IF NOT EXISTS "uuid-ossp";)

### ⚙️ Compilando e executando

# Compila o projeto
mvn clean compile

# Executa a aplicação
mvn exec:java -Dexec.mainClass="br.edulivre.App"
Você pode configurar variáveis de ambiente com .env ou diretamente no EnvLoader.java.

### Configuração
1. Banco de Dados:
```bash
psql -U postgres -f sql/create_schema.sql
psql -U postgres -f sql/insert_data.sql

# Compilação (incluindo o JAR manualmente)
javac -cp ".;lib/postgresql-42.3.1.jar" src/*.java src/db/*.java src/dao/*.java src/model/*.java

# Execução
java -cp ".;lib/postgresql-42.3.1.jar" src/App.java

edulivre/
├── pom.xml                       # Arquivo de configuração do Maven
├── README.md                    # Este documento
├── docs/
│   └── imagens/                 # Imagens para documentação (logo, diagramas, etc.)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/edulivre/
│   │   │       ├── App.java                     # Classe principal
│   │   │       ├── db/                          # Conexão e carregamento de ambiente
│   │   │       │   ├── ConnectionFactory.java
│   │   │       │   └── EnvLoader.java
│   │   │       ├── dao/                         # Camada de acesso a dados (DAOs)
│   │   │       │   ├── UsuarioDAO.java
│   │   │       │   ├── CursoDAO.java
│   │   │       │   ├── ConteudoDAO.java
│   │   │       │   ├── MatriculaDAO.java
│   │   │       │   └── AvaliacaoDAO.java
│   │   │       └── model/                       # Entidades do domínio
│   │   │           ├── Usuario.java
│   │   │           ├── Curso.java
│   │   │           ├── Matricula.java
│   │   │           └── Conteudo.java
│   │   └── resources/
│   │       └── sql/
│   │           ├── create_schema.sql           # Script de criação do banco
│   │           └── insert_data.sql             # Script com dados iniciais
│   └── test/
│       └── java/
│           └── br/edulivre/                    # Testes (JUnit, etc.)
