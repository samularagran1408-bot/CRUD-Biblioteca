package model;

public class Categoría {
    private Long ID;
    private String nombre;
    private String email;
    private String estado;

    public Categoría(String nombre, String email, String estado) {
        this.nombre = nombre;
        this.email = email;
        this.estado = estado;
    }

    public Categoría(Long ID, String nombre, String email, String estado) {
        this.ID = ID;
        this.nombre = nombre;
        this.email = email;
        this.estado = estado;
    }

    public Long getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getEstado() {
        return estado;
    }
}