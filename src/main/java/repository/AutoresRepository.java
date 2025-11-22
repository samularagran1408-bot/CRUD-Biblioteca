package repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.Conexion;
import model.Autores;

// PreparedStatement es una interfaz que representa una sentencia SQL precompilada

public class AutoresRepository {
    public void insertarAutor(Autores autor) {
        String sql = "INSERT INTO AUTORES (NOMBRE, NACIONALIDAD) VALUES (?, ?)";

        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, autor.getNombre());
            preparedStatement.setString(2, autor.getNacionalidad());

            preparedStatement.executeUpdate();

            System.out.println("Autor insertado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Poner a las entidades con sus atributos ordenados en listas
    // Mostrar
    public List<Autores> listarAutores() {
        String sql = "SELECT * FROM AUTORES";
        List<Autores> autores = new ArrayList<>();

        try (Connection connection = Conexion.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(sql);
            
            while (resultSet.next()) {
                autores.add(new Autores(
                    resultSet.getLong("ID"),
                    resultSet.getString("NOMBRE"),
                    resultSet.getString("NACIONALIDAD")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }    
        return autores;
    }

    // Mostrar debido su ID ingresado
    public void consultarAutorPorId(Long id) {
        String sql = "SELECT * FROM AUTORES WHERE ID = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql); // Crear PreparedStatement y conecta con la base de datos
            preparedStatement.setLong(1, id); // Establecer el valor del parámetro ID y el uno es para
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Autores autor = new Autores(
                    resultSet.getLong("ID"),
                    resultSet.getString("NOMBRE"),
                    resultSet.getString("NACIONALIDAD")
                );
                System.out.println("autor encontrado: " + autor.getNombre());
                System.out.println("Nacionalidad: " + autor.getNacionalidad());
                System.out.println("ID: " + autor.getId() + "\n");
            } else {
                System.out.println("autor no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Eliminar debido su ID ingresado
    public void eliminarAutorPorId(Long id) {
        String sql = "DELETE FROM AUTORES WHERE ID = ?"; // Comando SQL correspondiente
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql); // Crear PreparedStatement y conecta con la base de datos
            preparedStatement.setLong(1, id); // Establecer el valor del parámetro ID y el uno es para
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Usuario eliminado correctamente con ID: " + id);
            } else {
                System.out.println("No se encontró ningún usuario con ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Editar 
    public void editarAutorPorId(Long id, String nuevoNombre, String nuevaNacionalidad) {
        String sql = "UPDATE AUTORES SET NOMBRE = ?, NACIONALIDAD = ? WHERE ID = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nuevoNombre);
            preparedStatement.setString(2, nuevaNacionalidad);
            preparedStatement.setLong(3, id);
            
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Usuario actualizado correctamente con ID: " + id);
            } else {
                System.out.println("No se encontró ningún usuario con ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
