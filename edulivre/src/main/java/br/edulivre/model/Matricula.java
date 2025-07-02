package br.edulivre.model;

import java.sql.Timestamp;
import java.util.UUID;

public class Matricula {
    private int id;
    private UUID usuarioId;
    private UUID cursoId;
    private Timestamp dataMatricula;

    public Matricula() {}

    public Matricula(int id, UUID usuarioId, UUID cursoId, Timestamp dataMatricula) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.cursoId = cursoId;
        this.dataMatricula = dataMatricula;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }

    public UUID getCursoId() { return cursoId; }
    public void setCursoId(UUID cursoId) { this.cursoId = cursoId; }

    public Timestamp getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(Timestamp dataMatricula) { this.dataMatricula = dataMatricula; }

    @Override
    public String toString() {
        return "Matricula{id=" + id + ", usuarioId=" + usuarioId + ", cursoId=" + cursoId + "}";
    }
}
