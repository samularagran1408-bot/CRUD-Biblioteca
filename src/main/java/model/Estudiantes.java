package model;

public class Estudiantes {
    private Long ID_est;
    private String nom_cur;
    private String email;

    public Estudiantes(String nom_cur, String email) {
        this.nom_cur = nom_cur;
        this.email = email;
    }

    public Estudiantes(Long ID_est, String nom_cur, String email) {
        this.ID_est = ID_est;
        this.nom_cur = nom_cur;
        this.email = email;
    }

    public Long getID() {
        return ID_est;
    }

    public String getNom_cur() {
        return nom_cur;
    }

    public String getEmail() {
        return email;
    }
}
