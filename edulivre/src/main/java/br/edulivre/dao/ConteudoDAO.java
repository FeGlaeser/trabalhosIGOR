package br.edulivre.dao;

import java.sql.*;
import java.util.UUID;

public class ConteudoDAO {
    private Connection conn;

    public ConteudoDAO(Connection conn) {
        this.conn = conn;
    }

    public void listarConteudosPorCurso(String cursoId) throws SQLException {
        String sql = """
            SELECT titulo, tipo, OCTET_LENGTH(arquivo) AS tamanho
            FROM edulivre.conteudo
            WHERE curso_id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, UUID.fromString(cursoId));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.printf("Título: %s | Tipo: %s | Tamanho: %d bytes%n",
                        rs.getString("titulo"),
                        rs.getString("tipo"),
                        rs.getInt("tamanho"));
            }
        }
    }
}
