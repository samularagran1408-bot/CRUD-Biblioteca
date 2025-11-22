package model;

public class Prestamos {
    private Long id;
    private String libroIsbn;
    private Long socioId;
    private String fechaPrestamo;
    private String fechaDevolucionPrevista;
    private String fechaDevolucionReal;
    private String estado;

    /* Constructor para crear un préstamo (sin ID) */
    public Prestamos(String libroIsbn, Long socioId, String fechaPrestamo, 
                    String fechaDevolucionPrevista, String fechaDevolucionReal, String estado) {
        this.libroIsbn = libroIsbn;
        this.socioId = socioId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionPrevista = fechaDevolucionPrevista;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.estado = estado;
    }

    /* Constructor para traer un préstamo (con ID) */
    public Prestamos(Long id, String libroIsbn, Long socioId, String fechaPrestamo,
                    String fechaDevolucionPrevista, String fechaDevolucionReal, String estado) {
        this.id = id;
        this.libroIsbn = libroIsbn;
        this.socioId = socioId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionPrevista = fechaDevolucionPrevista;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getLibroIsbn() {
        return libroIsbn;
    }

    public Long getSocioId() {
        return socioId;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public String getFechaDevolucionPrevista() {
        return fechaDevolucionPrevista;
    }

    public String getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public String getEstado() {
        return estado;
    }
}