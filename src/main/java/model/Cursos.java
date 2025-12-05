package model;

public class Cursos {
    private Long ID_cur;
    private String nom_cur;
    private Long categoria;

    public Cursos(String nom_cur, Long categoria) {
        this.nom_cur = nom_cur;
        this.categoria = categoria;
    }

    public Cursos(Long ID_cur, String nom_cur, Long categoria) {
        this.ID_cur = ID_cur;
        this.nom_cur = nom_cur;
        this.categoria = categoria;
    }

    public Long getID() {
        return ID_cur;
    }

    public String getNom_cur() {
        return nom_cur;
    }

    public Long getCategoria() {
        return categoria;
    }
}