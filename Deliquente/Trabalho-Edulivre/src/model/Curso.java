package model;

import java.sql.Timestamp;
import java.util.UUID;

public class Curso {
    private UUID id;
    private String titulo;
    private String descricao;
    private Timestamp dataCriacao;
    private String avaliacaoJson;

    public Curso() {}

    public Curso(UUID id, String titulo, String descricao, Timestamp dataCriacao, String avaliacaoJson) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.avaliacaoJson = avaliacaoJson;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Timestamp getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(Timestamp dataCriacao) { this.dataCriacao = dataCriacao; }

    public String getAvaliacaoJson() { return avaliacaoJson; }
    public void setAvaliacaoJson(String avaliacaoJson) { this.avaliacaoJson = avaliacaoJson; }

    @Override
    public String toString() {
        return "Curso{id=" + id + ", titulo='" + titulo + "', dataCriacao=" + dataCriacao + "}";
    }
}
