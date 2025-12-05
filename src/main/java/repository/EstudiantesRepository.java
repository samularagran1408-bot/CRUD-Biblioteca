package repository;import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.Conexion;
import model.Estudiantes;

public class EstudiantesRepository {
    public boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) as count FROM Estudiantes WHERE LOWER(email) = LOWER(?)";
        
        try (Connection conn = Conexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email.trim());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar email: " + e.getMessage());
        }
        return false;
    }

    public boolean InsertarEstudiante(Estudiantes estudiante) {
        // VALIDAR NOMBRE
        if (estudiante.getNom_cur() == null || estudiante.getNom_cur().trim().isEmpty()) { // La segunda condición elimina espacios en blanco al inicio y final y verifica si el String resultante tiene longitud 0
            System.err.println("Error: El nombre del estudiante es obligatorio.");
            return false;
        }
        
        // VALIDAR EMAIL
        if (estudiante.getEmail() == null || estudiante.getEmail().trim().isEmpty()) {
            System.err.println("Error: El email del estudiante es obligatorio.");
            return false;
        }
        
        // VALIDAR FORMATO DE EMAIL (opcional pero recomendado)
        String email = estudiante.getEmail().trim();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) { // 
            System.err.println("Error: El formato del email no es válido.");
            return false;
        }

        // VALIDAR EMAIL DUPLICADO
        if (existeEmail(estudiante.getEmail())) {
            System.err.println("Error: El email '" + estudiante.getEmail() + "' ya está registrado.");
            return false;
        }
        String sql = "INSERT INTO Estudiantes (nom_cur, email) VALUES (?, ?)";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, estudiante.getNom_cur());
            preparedStatement.setString(2, estudiante.getEmail());

            preparedStatement.executeUpdate();

            System.out.println("Estudiante insertado con éxito");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Estudiantes> ListarEstudiantes() {
        String sql = "SELECT * FROM Estudiantes";
        List<Estudiantes> estudiantes = new ArrayList<>();
        try (Connection connection = Conexion.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                estudiantes.add(new Estudiantes(
                        resultSet.getLong("ID_est"),
                        resultSet.getString("nom_cur"),
                        resultSet.getString("email")
                    )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return estudiantes;
    }

    public void ConsultarEstudiantePorID(Long ID) throws Exception {
        String sql = "SELECT * FROM Estudiantes WHERE ID_est = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Estudiantes estudiante = new Estudiantes(
                        resultSet.getLong("ID_est"),
                        resultSet.getString("nom_cur"),
                        resultSet.getString("email")
                    );
                System.out.println("ID: " + estudiante.getID());
                System.out.println("Curso: " + estudiante.getNom_cur());
                System.out.println("Email: " + estudiante.getEmail());
            } else {
                System.out.println("Estudiante no encontrado");
            }
        } catch (Exception e) { 
            e.printStackTrace();
        }
    }

    public void EliminarEstudiantePorID(Long ID) throws Exception {
        String sql = "DELETE FROM Estudiantes WHERE ID_est = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, ID);
            int filasAfectadas = preparedStatement.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Estudiante eliminado con éxito");
            } else {
                System.out.println("No se encontró ningún estudiante con ID: " + ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void EditarEstudiantePorID(Long ID_est, String Curso, String email) throws Exception {
        String sql = "UPDATE Estudiantes SET Curso = ?, email = ? WHERE ID_est = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Curso);
            preparedStatement.setString(2, email);
            preparedStatement.setLong(3, ID_est);
            
            int filasAfectadas = preparedStatement.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Estudiante editado con éxito");
            } else {
                System.out.println("No se encontró ningún estudiante con ID: " + ID_est);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
