package dao;

import java.sql.*;
import java.util.*;

public class CursoDAO {
    private Connection conn;

    public CursoDAO(Connection conn) {
        this.conn = conn;
    }

    public void listarCursosComMediaEAlunos() throws SQLException {
        String sql = """
            SELECT 
                c.titulo,
                COALESCE(c.avaliacao->>'media', '0')::FLOAT AS media_avaliacao,
                COUNT(m.usuario_id) AS total_matriculados
            FROM edulivre.curso c
            LEFT JOIN edulivre.matricula m ON c.id = m.curso_id
            GROUP BY c.id
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String titulo = rs.getString("titulo");
                float media = rs.getFloat("media_avaliacao");
                int total = rs.getInt("total_matriculados");

                System.out.printf("Curso: %s | Média: %.2f | Alunos: %d%n", titulo, media, total);
            }
        }
    }

    public void adicionarComentario(String cursoId, String usuarioId, int nota, String comentario) throws SQLException {
        String sql = """
            UPDATE edulivre.curso
            SET avaliacao = jsonb_set(
                COALESCE(avaliacao, '{}'),
                '{comentarios}',
                (
                    CASE 
                        WHEN (avaliacao ? 'comentarios') THEN
                            (avaliacao->'comentarios') || 
                            to_jsonb(jsonb_build_object(
                                'usuario_id', ?,
                                'nota', ?,
                                'comentario', ?,
                                'data', ?
                            ))
                        ELSE
                            to_jsonb(ARRAY[
                                jsonb_build_object(
                                    'usuario_id', ?,
                                    'nota', ?,
                                    'comentario', ?,
                                    'data', ?
                                )
                            ])
                    END
                )
            )
            WHERE id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuarioId);
            stmt.setInt(2, nota);
            stmt.setString(3, comentario);
            stmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));

            stmt.setString(5, usuarioId);
            stmt.setInt(6, nota);
            stmt.setString(7, comentario);
            stmt.setDate(8, new java.sql.Date(System.currentTimeMillis()));

            stmt.setObject(9, UUID.fromString(cursoId));

            stmt.executeUpdate();
        }
    }
}
