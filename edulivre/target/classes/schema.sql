-- Criação do banco de dados
CREATE DATABASE edulivre;

-- Conecta ao banco (em linha de comando: \c edulivre)
-- No código Java a conexão é feita via JDBC

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
