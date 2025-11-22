package model;

public class Autores {
    private Long id;
    private String nombre;
    private String nacionalidad;

    /* Constructor para crear un usuario */
    public Autores(String nombre, String nacionalidad) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    /* Constructor para traer un usuario */
    public Autores(Long id, String nombre, String nacionalidad) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }
}
