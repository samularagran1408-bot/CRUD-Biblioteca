package model;

public class Estudiantes_Cursos {
    private Long Estudiante;
    private Long Curso;
    
    // Constructor
    public Estudiantes_Cursos(Long Estudiante, Long Curso) {
        this.Estudiante = Estudiante;
        this.Curso = Curso;
    }
    
    // Getters
    public Long getEstudiante() { 
        return Estudiante; 
    }

    public Long getCurso() { 
        return Curso; 
    }
    
    @Override
    public String toString() {
        return "Estudiante: " + Estudiante + ", Curso: " + Curso;
    }
}
