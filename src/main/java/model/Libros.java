package model;

public class Libros {
    private Long isbn;
    private String titulo;
    private Long autor_id;
    private Long año_publicacion;
    private Long cantidad_total;
    private Long cantidad_disponible;

    public Libros(String titulo, Long autor_id, Long año_publicacion, Long cantidad_total, Long cantidad_disponible) {
        this.titulo = titulo;
        this.autor_id = autor_id;
        this.año_publicacion = año_publicacion;
        this.cantidad_total = cantidad_total;
        this.cantidad_disponible = cantidad_disponible;
    }

    public Libros(Long isbn, String titulo, Long autor_id, Long año_publicacion, Long cantidad_total, Long cantidad_disponible) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor_id = autor_id;
        this.año_publicacion = año_publicacion;
        this.cantidad_total = cantidad_total;
        this.cantidad_disponible = cantidad_disponible;
    }

    public Long getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public Long getAutor_id() {
        return autor_id;
    }

    public Long getAño_publicacion() {
        return año_publicacion;
    }

    public Long getCantidad_total() {
        return cantidad_total;
    }

    public Long getCantidad_disponible() {
        return cantidad_disponible;
    }
}
