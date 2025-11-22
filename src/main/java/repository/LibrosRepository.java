package repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.Conexion;
import model.Libros;

public class LibrosRepository {
    public void insertarLibro(Libros libro) {
        String sql = "INSERT INTO LIBROS (ISBN, TITULO, AUTOR_ID, AÑO_PUBLICACION, CANTIDAD_TOTAL, CANTIDAD_DISPONIBLE) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, libro.getIsbn());
            preparedStatement.setString(2, libro.getTitulo());
            preparedStatement.setLong(3, libro.getAutor_id());
            preparedStatement.setLong(4, libro.getAño_publicacion());  // Cambiado a setLong
            preparedStatement.setLong(5, libro.getCantidad_total());
            preparedStatement.setLong(6, libro.getCantidad_disponible());

            preparedStatement.executeUpdate();

            System.out.println("Libro insertado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Listar todos los libros
    public List<Libros> listarLibros() {
        String sql = "SELECT * FROM LIBROS";
        List<Libros> libros = new ArrayList<>();

        try (Connection connection = Conexion.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                libros.add(new Libros(
                    resultSet.getLong("ISBN"),
                    resultSet.getString("TITULO"),
                    resultSet.getLong("AUTOR_ID"),
                    resultSet.getLong("AÑO_PUBLICACION"),  // Cambiado a getLong
                    resultSet.getLong("CANTIDAD_TOTAL"),
                    resultSet.getLong("CANTIDAD_DISPONIBLE")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return libros;
    }

    // Consultar por ISBN (no por ID, ya que tu tabla usa ISBN como PK)
    public void consultarLibroPorIsbn(Long isbn) throws SQLException {
        String sql = "SELECT * FROM LIBROS WHERE ISBN = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Libros libro = new Libros(
                    resultSet.getLong("ISBN"),
                    resultSet.getString("TITULO"),
                    resultSet.getLong("AUTOR_ID"),
                    resultSet.getLong("AÑO_PUBLICACION"),  // Cambiado a getLong
                    resultSet.getLong("CANTIDAD_TOTAL"),
                    resultSet.getLong("CANTIDAD_DISPONIBLE")
                );
                System.out.println("Libro encontrado: " + libro.getTitulo());
                System.out.println("Autor ID: " + libro.getAutor_id());
                System.out.println("Año de publicación: " + libro.getAño_publicacion());
                System.out.println("Cantidad total: " + libro.getCantidad_total());
                System.out.println("Cantidad disponible: " + libro.getCantidad_disponible());
                System.out.println("ISBN: " + libro.getIsbn() + "\n");
            } else {
                System.out.println("Libro no encontrado con ISBN: " + isbn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Editar por ISBN
    public void editarLibroPorIsbn(Long isbn, String nuevoTitulo, Long nuevoAutor_id, Long nuevoAño_publicacion, Long nuevoCantidad_total, Long nuevoCantidad_disponible) {
        String sql = "UPDATE LIBROS SET TITULO = ?, AUTOR_ID = ?, AÑO_PUBLICACION = ?, CANTIDAD_TOTAL = ?, CANTIDAD_DISPONIBLE = ? WHERE ISBN = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nuevoTitulo);
            preparedStatement.setLong(2, nuevoAutor_id);
            preparedStatement.setLong(3, nuevoAño_publicacion);
            preparedStatement.setLong(4, nuevoCantidad_total);
            preparedStatement.setLong(5, nuevoCantidad_disponible);
            preparedStatement.setLong(6, isbn);
            
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Libro actualizado correctamente con ISBN: " + isbn);
            } else {
                System.out.println("No se encontró ningún libro con ISBN: " + isbn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean eliminarLibroSiNoTienePrestamos(Long isbn) {
        // Verificación de si tiene préstamos activos
        String sqlCheck = "SELECT COUNT(*) FROM PRESTAMOS WHERE LIBRO_ISBN = ?";
        String sqlDelete = "DELETE FROM LIBROS WHERE ISBN = ?";
        
        try (Connection connection = Conexion.getConnection()) {
            // Verificar préstamos activos
            PreparedStatement checkStmt = connection.prepareStatement(sqlCheck);
            checkStmt.setLong(1, isbn);      // Estas 3 línes conectan el comando sql entre el isbn para comprobar si ya estuvo ese isbn en la tabla de prestamos
            ResultSet resultSet = checkStmt.executeQuery();
            
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("No se puede eliminar el libro. Tiene préstamos activos.");
                return false;
            }
            
            // Si no tiene préstamos, eliminar
            PreparedStatement deleteStmt = connection.prepareStatement(sqlDelete);
            deleteStmt.setLong(1, isbn);
            int rowsAffected = deleteStmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Libro eliminado correctamente.");
                return true;
            } else {
                System.out.println("No se encontró el libro con ISBN: " + isbn);
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}