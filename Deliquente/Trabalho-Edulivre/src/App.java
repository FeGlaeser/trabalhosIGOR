import dao.*;
import db.ConnectionFactory;
import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            System.out.println("✅ Conectado ao banco com sucesso!");

            // ====== INSTANCIAR DAOs ======
            CursoDAO cursoDAO = new CursoDAO(conn);
            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
            MatriculaDAO matriculaDAO = new MatriculaDAO(conn);
            ConteudoDAO conteudoDAO = new ConteudoDAO(conn);
            AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(conn);

            // ====== EXEMPLO: LISTAR CURSOS COM MÉDIA E ALUNOS ======
            System.out.println("\n📚 Cursos disponíveis:");
            cursoDAO.listarCursosComMediaEAlunos();

            // ====== IDs DE TESTE (AGORA SÃO VÁLIDOS) ======
            String usuarioId = "11111111-1111-1111-1111-111111111111";  // João Silva
            String cursoId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";   // Java Básico

            // ====== EXEMPLO: INSERIR MATRÍCULA ======
            System.out.println("\n📌 Tentando matricular o usuário no curso...");
            matriculaDAO.inserirMatricula(usuarioId, cursoId);

            // ====== EXEMPLO: LISTAR CONTEÚDOS DO CURSO ======
            System.out.println("\n🎥 Conteúdos do curso:");
            conteudoDAO.listarConteudosPorCurso(cursoId);

            // ====== EXEMPLO: ADICIONAR COMENTÁRIO ======
            System.out.println("\n📝 Adicionando comentário no curso:");
            avaliacaoDAO.adicionarComentario(
                    cursoId,
                    usuarioId,
                    5,
                    "Excelente curso! Recomendo muito."
            );

        } catch (Exception e) {
            System.err.println("❌ Erro ao executar aplicação: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
