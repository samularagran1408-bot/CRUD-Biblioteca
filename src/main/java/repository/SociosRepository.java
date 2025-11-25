package repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.Conexion;
import model.Socios;

public class SociosRepository {
    public void insertarSocio(Socios socio) {
        String sql = "INSERT INTO SOCIOS (NOMBRE, APELLIDO, DNI, TELEFONO) VALUES (?, ?, ?, ?)";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, socio.getNombre());
            preparedStatement.setString(2, socio.getApellido());
            preparedStatement.setString(3, socio.getDni());
            preparedStatement.setString(4, socio.getTelefono());

            preparedStatement.executeUpdate();

            System.out.println("Socio insertado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Socios> listarSocios() {
        List<Socios> listaSocios = new ArrayList<>();
        String sql = "SELECT * FROM SOCIOS";
        try (Connection connection = Conexion.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                listaSocios.add(new Socios(
                    resultSet.getLong("ID"),
                    resultSet.getString("NOMBRE"),
                    resultSet.getString("APELLIDO"),
                    resultSet.getString("DNI"),
                    resultSet.getString("TELEFONO")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaSocios;
    }

    public void consultarSocioPorId(Long id) {
        String sql = "SELECT * FROM SOCIOS WHERE ID = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Socios socio = new Socios(
                    resultSet.getLong("ID"),
                    resultSet.getString("NOMBRE"),
                    resultSet.getString("APELLIDO"),
                    resultSet.getString("DNI"),
                    resultSet.getString("TELEFONO")
                );
                System.out.println("Socio encontrado: " + socio.getNombre() + " " + socio.getApellido());
                System.out.println("DNI: " + socio.getDni());
                System.out.println("Teléfono: " + socio.getTelefono());
                System.out.println("ID: " + socio.getId() + "\n");
            } else {
                System.out.println("Socio no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarSocioPorId(Long id, String nuevoNombre, String nuevoApellido, String nuevoDni, String nuevoTelefono) {
        String sql = "UPDATE SOCIOS SET NOMBRE = ?, APELLIDO = ?, DNI = ?, TELEFONO = ? WHERE ID = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nuevoNombre);
            preparedStatement.setString(2, nuevoApellido);
            preparedStatement.setString(3, nuevoDni);
            preparedStatement.setString(4, nuevoTelefono);
            preparedStatement.setLong(5, id);
            
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Socio actualizado correctamente con ID: " + id);
            } else {
                System.out.println("No se encontró ningún socio con ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean eliminarSocioSiNoTienePrestamos(Long id) {
        // Se usan PreparedStatement y ResultSet porque aparte de que son consultas fijas por el conteo de las columnas de prestamo,
        // se necesita consultar el ID del socio a eliminar
        // Verificación de si tiene préstamos activos
        String sqlCheck = "SELECT COUNT(*) FROM PRESTAMOS WHERE SOCIO_ID = ?"; // Cuenta las columnas donde el ID del ingresado en el main aparezca en la tabla de prestamos
        String sqlDelete = "DELETE FROM SOCIOS WHERE ID = ?"; // Borra el socio respecto a su id ingresado en el main
        
        try (Connection connection = Conexion.getConnection()) {
            // Verificar si hay prestamos (Si tiene prestado o de vuelto significa que si tiene un historial)
            PreparedStatement checkStmt = connection.prepareStatement(sqlCheck);
            checkStmt.setLong(1, id);      // Estas 3 líneas conectan el comando sql entre el id para comprobar si ya estuvo ese id en la tabla de prestamos
            ResultSet resultSet = checkStmt.executeQuery();
            
            if (resultSet.next() && resultSet.getInt(1) > 0) { 
                System.out.println("No se puede eliminar el socio. Tiene préstamos en su historial.");
                return false;
            }
            
            // Si no tiene ningún historial de préstamos de ningún tipo, eliminar
            PreparedStatement deleteStmt = connection.prepareStatement(sqlDelete);
            deleteStmt.setLong(1, id);
            int rowsAffected = deleteStmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Socio eliminado correctamente.");
                return true;
            } else {
                System.out.println("No se encontró el socio con ID: " + id);
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}