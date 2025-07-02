-- Inserção de usuários
INSERT INTO edulivre.usuario (id, nome, email, senha, perfil)
VALUES
  ('11111111-1111-1111-1111-111111111111', 'João Silva', 'joao@email.com', 'senha123', 'aluno'),
  ('22222222-2222-2222-2222-222222222222', 'Maria Lima', 'maria@email.com', 'senha123', 'aluno'),
  ('33333333-3333-3333-3333-333333333333', 'Carlos Souza', 'carlos@email.com', 'senha123', 'professor');

-- Inserção de cursos
INSERT INTO edulivre.curso (id, titulo, descricao, data_criacao, avaliacao)
VALUES 
  (
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    'Java Básico',
    'Curso introdutório de programação com Java.',
    CURRENT_TIMESTAMP,
    '{
      "media": 4.5,
      "comentarios": [
        {
          "usuario_id": "11111111-1111-1111-1111-111111111111",
          "nota": 5,
          "comentario": "Ótimo curso!",
          "data": "2025-05-20"
        },
        {
          "usuario_id": "22222222-2222-2222-2222-222222222222",
          "nota": 4,
          "comentario": "Gostei, mas poderia ter mais exemplos.",
          "data": "2025-05-21"
        }
      ]
    }'::jsonb
  ),
  (
    'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
    'Banco de Dados com PostgreSQL',
    'Curso sobre modelagem e comandos SQL.',
    CURRENT_TIMESTAMP,
    '{}'::jsonb
  );

-- Inserção de conteúdos
INSERT INTO edulivre.conteudo (curso_id, titulo, descricao, tipo, arquivo)
VALUES
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Introdução ao Java', 'Vídeo introdutório', 'video', decode('DEADBEEF', 'hex')),
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Slides da Aula 1', 'Apresentação em slides', 'slide', decode('CAFEBABE', 'hex')),
  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Modelo Entidade-Relacionamento', 'PDF com explicações', 'pdf', decode('CAFEBABE', 'hex'));

-- Inserção de matrículas
INSERT INTO edulivre.matricula (usuario_id, curso_id)
VALUES
  ('11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
  ('22222222-2222-2222-2222-222222222222', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
  ('11111111-1111-1111-1111-111111111111', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb');
