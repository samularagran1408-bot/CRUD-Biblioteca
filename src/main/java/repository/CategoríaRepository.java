package repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.Conexion;
import model.Categoría;

public class CategoríaRepository {
    
    public boolean InsertarCategoría(Categoría categoría) {
        // VALIDAR NOMBRE
        if (categoría.getNombre() == null || categoría.getNombre().trim().isEmpty()) {
            System.err.println("Error: El nombre de la categoría es obligatorio.");
            return false;
        }
        
        // VALIDAR EMAIL 
        if (categoría.getEmail() == null || categoría.getEmail().trim().isEmpty()) {
            System.err.println("Error: El email de la categoría es obligatorio.");
            return false;
        }

        String sql = "INSERT INTO Categoría (nom_cat, email, estado) VALUES (?, ?, ?)";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, categoría.getNombre());
            preparedStatement.setString(2, categoría.getEmail());
            preparedStatement.setString(3, categoría.getEstado());

            int filasAfectadas = preparedStatement.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Categoría insertada con éxito");
            } else {
                System.out.println("No se pudo insertar la categoría");
            }
        } catch (Exception e) {
            System.err.println("Error al insertar categoría: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<Categoría> ListarCategorías() {
        String sql = "SELECT * FROM Categoría";
        List<Categoría> categorias = new ArrayList<>();
        
        try (Connection connection = Conexion.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                categorias.add(new Categoría(
                    resultSet.getLong("ID"),
                    resultSet.getString("nom_cat"),
                    resultSet.getString("email"),
                    resultSet.getString("estado")
                ));
            }
            
            if (categorias.isEmpty()) {
                System.out.println("No hay categorías registradas.");
            }
            
        } catch (Exception e) {
            System.err.println("Error al listar categorías: " + e.getMessage());
            e.printStackTrace();
        }
        return categorias;
    }

    public Categoría ConsultarCategoríaPorID(Long ID) {
        String sql = "SELECT * FROM Categoría WHERE ID = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Categoría categoría = new Categoría(
                    resultSet.getLong("ID"),
                    resultSet.getString("nom_cat"),
                    resultSet.getString("email"),
                    resultSet.getString("estado")
                );
                System.out.println("Categoría encontrada :" + categoría.getNombre());
                System.out.println("Email: " + categoría.getEmail());
                System.out.println("Estado: " + categoría.getEstado());

            } else {
                System.out.println("No se encontró ninguna categoría con ID: " + ID);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void EliminarCategoríaPorID(Long ID) {
        String sql = "DELETE FROM Categoría WHERE ID = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, ID);
            int filasAfectadas = preparedStatement.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Categoría eliminada con éxito");
            } else {
                System.out.println("No se encontró ninguna categoría con ID: " + ID);
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1451) { // Error de clave foránea
                System.err.println("No se puede eliminar la categoría porque tiene cursos asociados.");
            } else {
                System.err.println("Error al eliminar categoría: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar categoría: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void EditarCategoríaPorID(Long ID, String nombre, String email, String estado) {
        String sql = "UPDATE Categoría SET nom_cat = ?, email = ?, estado = ? WHERE ID = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, estado);
            preparedStatement.setLong(4, ID);
            
            int filasAfectadas = preparedStatement.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Categoría editada con éxito");
            } else {
                System.out.println("No se encontró ninguna categoría con ID: " + ID);
            }
        } catch (Exception e) {
            System.err.println("Error al editar categoría: " + e.getMessage());
            e.printStackTrace();
        }
    }
}