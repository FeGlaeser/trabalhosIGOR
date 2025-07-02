package br.edulivre.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.UUID;
import org.json.JSONObject;

public class AvaliacaoDAO {
    private final Connection conn;

    public AvaliacaoDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adiciona um novo comentário no campo JSONB de avaliação do curso.
     * Atualiza a lista de comentários e depois recalcula a média.
     * @param cursoId ID do curso
     * @param usuarioId ID do usuário que está comentando
     * @param nota Nota dada pelo usuário (1-5)
     * @param comentario Texto do comentário
     * @throws SQLException Em caso de erro no banco de dados
     */
    public void adicionarComentario(String cursoId, String usuarioId, int nota, String comentario) throws SQLException {
        // Validação dos parâmetros
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5");
        }
        
        // Construindo o objeto JSON do comentário
        JSONObject comentarioJson = new JSONObject();
        comentarioJson.put("usuario_id", usuarioId);
        comentarioJson.put("nota", nota);
        comentarioJson.put("comentario", comentario);
        comentarioJson.put("data", LocalDate.now().toString());
        
        String novoComentario = comentarioJson.toString();

        // Query SQL para adicionar o comentário
        String sql = """
            UPDATE edulivre.curso
            SET avaliacao = jsonb_set(
                COALESCE(avaliacao, '{"comentarios": []}'::jsonb),
                '{comentarios}',
                (
                    SELECT COALESCE(avaliacao->'comentarios', '[]'::jsonb) || ?::jsonb
                    FROM edulivre.curso
                    WHERE id = ?
                ),
                true
            )
            WHERE id = ?
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Definindo os parâmetros na ordem correta
            stmt.setString(1, novoComentario);  // Comentário a ser adicionado
            stmt.setObject(2, UUID.fromString(cursoId));  // ID para o subselect
            stmt.setObject(3, UUID.fromString(cursoId));  // ID para o WHERE

            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas == 0) {
                System.out.println("❌ Curso não encontrado para adicionar comentário.");
                return;
            }
            
            System.out.println("✅ Comentário adicionado com sucesso!");
            atualizarMedia(cursoId);
        }
    }

    /**
     * Recalcula a média das notas nos comentários e atualiza o campo "media" no JSONB.
     * @param cursoId ID do curso a ser atualizado
     * @throws SQLException Em caso de erro no banco de dados
     */
    public void atualizarMedia(String cursoId) throws SQLException {
        String sql = """
            UPDATE edulivre.curso
            SET avaliacao = 
                CASE 
                    WHEN avaliacao->'comentarios' IS NULL OR jsonb_array_length(avaliacao->'comentarios') = 0 THEN
                        jsonb_set(COALESCE(avaliacao, '{}'), '{media}', '0'::jsonb)
                    ELSE
                        jsonb_set(
                            avaliacao,
                            '{media}',
                            to_jsonb(
                                (
                                    SELECT AVG((elem->>'nota')::numeric)
                                    FROM jsonb_array_elements(avaliacao->'comentarios') AS elem
                                )
                            )
                        )
                END
            WHERE id = ?
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, UUID.fromString(cursoId));
            
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("✅ Média atualizada com sucesso!");
            } else {
                System.out.println("❌ Curso não encontrado para atualizar média.");
            }
        }
    }
}