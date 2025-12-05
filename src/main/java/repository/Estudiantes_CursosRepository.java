package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.Conexion;
import model.Estudiantes_Cursos;

public class Estudiantes_CursosRepository {
    
    // OPERACIONES BÁSICAS
    
    // Inscribir estudiante a curso
    public boolean inscribirEstudianteACurso(Long estudianteId, Long cursoId) {
        String sql = "INSERT INTO Estudiantes_Cursos (Estudiante, Curso) VALUES (?, ?)";
        
        try (Connection conn = Conexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, estudianteId);
            pstmt.setLong(2, cursoId);
            
            int filas = pstmt.executeUpdate();
            
            if (filas > 0) {
                System.out.println("Estudiante inscrito exitosamente al curso");
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Dar de baja estudiante de curso
    public boolean darDeBajaEstudiante(Long estudianteId, Long cursoId) {
        String sql = "DELETE FROM Estudiantes_Cursos WHERE Estudiante = ? AND Curso = ?";
        
        try (Connection conn = Conexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, estudianteId);
            pstmt.setLong(2, cursoId);
            
            int filas = pstmt.executeUpdate();
            
            if (filas > 0) {
                System.out.println("Estudiante dado de baja exitosamente");
                return true;
            } else {
                System.out.println("No se encontró la inscripción");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // CONSULTAS DE RELACIONES    

    // Listar cursos de un estudiante
    public List<String> listarCursosDelEstudiante(Long estudianteId) {
        List<String> cursos = new ArrayList<>();
        
        String sql = "SELECT c.ID_cur, c.nom_cur, cat.nom_cat " + // SELECT: selecciona columnas
                     "FROM Cursos c " + // FROM: selecciona tablas
                     "INNER JOIN Estudiantes_Cursos ec ON c.ID_cur = ec.Curso " + // INNER JOIN: unión de tablas
                     "INNER JOIN Categoría cat ON c.categoría = cat.ID " +
                     "WHERE ec.Estudiante = ? " + // WHERE: filtra y el nombre del estudiante por su ID ingresado
                     "ORDER BY c.nom_cur";
        
        try (Connection conn = Conexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, estudianteId);
            ResultSet rs = pstmt.executeQuery();
            
            int contador = 1; // cuenta el número de cursos inscritos para despues mostrarlos
            boolean tieneCursos = false;
            
            while (rs.next()) {
                tieneCursos = true;
                String info = String.format("%2d. [ID: %3d] %-30s | Categoría: %s", // formato
                                                                                    // %2d: inicia con 2 caracteres que sean decimales
                                                                                    // %3d: ancho mínimo de 3 caracteres
                                                                                    // %-30s: cadena de texto alineada a la izquierda con ancho mínimo de 30 caracteres
                    contador++,
                    rs.getLong("ID_cur"),
                    rs.getString("nom_cur"),
                    rs.getString("nom_cat")
                );
                cursos.add(info);
            }
            
            if (!tieneCursos) {
                cursos.add("El estudiante no está inscrito en ningún curso.");
            }
            
        } catch (SQLException e) {
            cursos.add("Error al consultar: " + e.getMessage());
            System.err.println("Error SQL: " + e.getMessage());
        }
        
        return cursos;
    }
    
    // Listar estudiantes de un curso
    public List<String> listarEstudiantesDelCurso(Long cursoId) {
        List<String> estudiantes = new ArrayList<>();
        
        String sql = "SELECT e.ID_est, e.nombre, e.email " +
                     "FROM Estudiantes e " +
                     "INNER JOIN Estudiantes_Cursos ec ON e.ID_est = ec.Estudiante " +
                     "WHERE ec.Curso = ? " +
                     "ORDER BY e.nombre";
        
        try (Connection conn = Conexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, cursoId);
            ResultSet rs = pstmt.executeQuery();
            
            int contador = 1;
            boolean tieneEstudiantes = false;
            
            while (rs.next()) { // recorre la tabla y muestra el nombre y email de cada estudiante
                tieneEstudiantes = true; // Si no hay estudiantes
                                         // Todo este while se vuelve false de una vez
                String info = String.format("%2d. [ID: %3d] %-25s | Email: %s",
                    contador++,
                    rs.getLong("ID_est"),
                    rs.getString("nombre"),
                    rs.getString("email")
                );
                estudiantes.add(info);
            }
            
            if (!tieneEstudiantes) { // Si no hay estudiantes
                estudiantes.add("No hay estudiantes inscritos en este curso.");
            }
            
        } catch (SQLException e) {
            estudiantes.add("Error al consultar: " + e.getMessage());
        }
        
        return estudiantes;
    }
    
    // 6. Obtener todos los registros de la tabla puente
    public List<Estudiantes_Cursos> listarTodasInscripciones() {
        List<Estudiantes_Cursos> inscripciones = new ArrayList<>();
        String sql = "SELECT * FROM Estudiantes_Cursos ORDER BY Estudiante, Curso";
        
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                inscripciones.add(new Estudiantes_Cursos(
                    rs.getLong("Estudiante"),
                    rs.getLong("Curso")
                ));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar inscripciones: " + e.getMessage());
        }
        
        return inscripciones;
    }
}