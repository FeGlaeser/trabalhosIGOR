package model;

import java.util.UUID;

public class Conteudo {
    private int id;
    private UUID cursoId;
    private String titulo;
    private String descricao;
    private String tipo;
    private byte[] arquivo;

    public Conteudo() {}

    public Conteudo(int id, UUID cursoId, String titulo, String descricao, String tipo, byte[] arquivo) {
        this.id = id;
        this.cursoId = cursoId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.arquivo = arquivo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public UUID getCursoId() { return cursoId; }
    public void setCursoId(UUID cursoId) { this.cursoId = cursoId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public byte[] getArquivo() { return arquivo; }
    public void setArquivo(byte[] arquivo) { this.arquivo = arquivo; }

    @Override
    public String toString() {
        return "Conteudo{id=" + id + ", titulo='" + titulo + "', tipo='" + tipo + "'}";
    }
}
