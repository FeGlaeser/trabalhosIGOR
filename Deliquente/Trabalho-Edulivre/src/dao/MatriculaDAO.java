package dao;

import java.sql.*;
import java.util.UUID;

public class MatriculaDAO {
    private Connection conn;

    public MatriculaDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean inserirMatricula(String usuarioId, String cursoId) throws SQLException {
        String checkSql = "SELECT 1 FROM edulivre.matricula WHERE usuario_id = ? AND curso_id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setObject(1, UUID.fromString(usuarioId));
            checkStmt.setObject(2, UUID.fromString(cursoId));
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Usuário já matriculado nesse curso.");
                return false;
            }
        }

        String sql = "INSERT INTO edulivre.matricula (usuario_id, curso_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, UUID.fromString(usuarioId));
            stmt.setObject(2, UUID.fromString(cursoId));
            stmt.executeUpdate();
            System.out.println("✅ Matrícula inserida.");
            return true;
        }
    }
}
