# Plataforma EduLivre - Implementação em PostgreSQL e Java JDBC

![Logo EduLivre](imagens/logo.png) *(opcional: adicione uma imagem de logo se disponível)*

## 📚 Visão Geral do Projeto

Sistema de gestão de aprendizagem com backend PostgreSQL e interface Java JDBC, contendo:
- Gestão de usuários com diferentes perfis
- Criação de cursos e matrículas
- Gerenciamento de conteúdos multimídia
- Sistema de avaliações com armazenamento JSONB

## 🛠️ Especificações Técnicas

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

## 🚀 Instalação (Sem Maven)
### Pré-requisitos
1. PostgreSQL 12+ instalado
2. Java JDK 11+
3. Driver JDBC PostgreSQL (postgresql-42.3.1.jar)

### Configuração
1. Banco de Dados:
```bash
psql -U postgres -f sql/create_schema.sql
psql -U postgres -f sql/insert_data.sql

# Compilação (incluindo o JAR manualmente)
javac -cp ".;lib/postgresql-42.3.1.jar" src/*.java src/db/*.java src/dao/*.java src/model/*.java

# Execução
java -cp ".;lib/postgresql-42.3.1.jar" src/App.java

Trabalho-EDULIVRE/
├── lib/
│   └── postgresql-42.3.1.jar 
│   └── json-20240303.jar
├── sql/
│   ├── create_schema.sql        # Criação das tabelas
│   └── insert_data.sql          # Dados iniciais
├── src/
│   ├── db/                      # Conexão com banco
│   │   └── ConnectionFactory.java
│   │   └── EnvLoader.java
│   ├── dao/                     # Operações com:
│   │   ├── CursoDAO.java
│   │   ├── AvaliacaoDAO.java
│   │   ├── ConteudoDAO.java        # - Cursos
│   │   ├── UsuarioDAO.java      # - Usuários
│   │   └── MatriculaDAO.java    # - Matrículas
│   ├── model/                   # Classes:
│   │   ├── Curso.java           # - Curso
│   │   ├── Usuario.java         # - Usuário
│   │   ├── Matricula.java       # - Matrícula
│   │   └── Conteudo.java        # - Conteúdo
│   └── App.java                 # Classe principal
└── README.md                    # Este arquivo