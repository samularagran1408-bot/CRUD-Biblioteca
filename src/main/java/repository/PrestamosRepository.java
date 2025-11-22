package repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.Conexion;
import model.Prestamos;

public class PrestamosRepository {
    public void insertarPrestamo(Prestamos prestamo) {
    String sql = "INSERT INTO PRESTAMOS (LIBRO_ISBN, SOCIO_ID, FECHA_PRESTAMO, FECHA_DEVOLUCION_PREVISTA, FECHA_DEVOLUCION_REAL, ESTADO) VALUES (?, ?, ?, ?, ?, ?)";

    try(Connection connection = Conexion.getConnection()) {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, prestamo.getLibroIsbn());
        preparedStatement.setLong(2, prestamo.getSocioId());
        preparedStatement.setString(3, prestamo.getFechaPrestamo());
        preparedStatement.setString(4, prestamo.getFechaDevolucionPrevista());
        preparedStatement.setString(5, prestamo.getFechaDevolucionReal());
        preparedStatement.setString(6, prestamo.getEstado());

        preparedStatement.executeUpdate();
        System.out.println("Préstamo insertado correctamente");
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public List<Prestamos> listarPrestamos() {
        List<Prestamos> listaPrestamos = new ArrayList<>();
        String sql = "SELECT * FROM PRESTAMOS";
        try (Connection connection = Conexion.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                listaPrestamos.add(new Prestamos(
                    resultSet.getLong("ID"),
                    resultSet.getString("LIBRO_ISBN"),
                    resultSet.getLong("SOCIO_ID"),
                    resultSet.getString("FECHA_PRESTAMO"),
                    resultSet.getString("FECHA_DEVOLUCION_PREVISTA"),
                    resultSet.getString("FECHA_DEVOLUCION_REAL"),
                    resultSet.getString("ESTADO")

                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPrestamos;
    }

    public void consultarPrestamoPorId(Long id) {
        String sql = "SELECT * FROM PRESTAMOS WHERE ID = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Prestamos prestamos = new Prestamos(
                    resultSet.getLong("ID"),
                    resultSet.getString("LIBRO_ISBN"),
                    resultSet.getLong("SOCIO_ID"),
                    resultSet.getString("FECHA_PRESTAMO"),
                    resultSet.getString("FECHA_DEVOLUCION_PREVISTA"),
                    resultSet.getString("FECHA_DEVOLUCION_REAL"),
                    resultSet.getString("ESTADO")
                );
                System.out.println("Prestamo encontrado: " + prestamos.getFechaPrestamo());
                System.out.println("Libro: " + prestamos.getLibroIsbn());
                System.out.println("Socio: " + prestamos.getSocioId());
                System.out.println("Estado: " + prestamos.getEstado());
                System.out.println("ID: " + prestamos.getId() + "\n"); 
            } else {
                System.out.println("No se encontró ningún prestamo con ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarPrestamoPorId(Long id) {
        String sql = "DELETE FROM PRESTAMOS WHERE ID = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Prestamo eliminado correctamente con ID: " + id);
            } else {
                System.out.println("No se encontró ningún prestamo con ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarPrestamoPorId(Long id, String nuevoFechaPrestamo, String nuevoFechaDevolucionPrevista, String nuevoFechaDevolucionReal, String nuevoEstado) {
        String sql = "UPDATE PRESTAMOS SET FECHA_PRESTAMO = ?, FECHA_DEVOLUCION_PREVISTA = ?, FECHA_DEVOLUCION_REAL = ?, ESTADO = ? WHERE ID = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, nuevoFechaPrestamo);
            preparedStatement.setString(2, nuevoFechaDevolucionPrevista);
            preparedStatement.setString(3, nuevoFechaDevolucionReal);
            preparedStatement.setString(4, nuevoEstado);
            preparedStatement.setLong(5, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Prestamo actualizado correctamente con ID: " + id);
            } else {
                System.out.println("No se encontró ningún prestamo con ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // REALIZAR PRÉSTAMO CON CONTROL DE DISPONIBILIDAD
    public boolean realizarPrestamo(String libroIsbn, Long socioId, String fechaDevolucionPrevista) {
        // Verificar disponibilidad del libro
        String sqlCheckLibro = "SELECT CANTIDAD_DISPONIBLE FROM LIBROS WHERE ISBN = ?"; // Comando que comprueba la cantidad de libros disponibles
        String sqlInsertPrestamo = "INSERT INTO PRESTAMOS (LIBRO_ISBN, SOCIO_ID, FECHA_PRESTAMO, FECHA_DEVOLUCION_PREVISTA, ESTADO) VALUES (?, ?, CURDATE(), ?, 'PRESTADO')"; // Comando que inserta el préstamo
        String sqlUpdateLibro = "UPDATE LIBROS SET CANTIDAD_DISPONIBLE = CANTIDAD_DISPONIBLE - 1 WHERE ISBN = ?"; // Comando que actualiza la cantidad de libros disponibles
        
        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false); // Iniciar 
            
            // 1. Verificar disponibilidad
            PreparedStatement checkStmt = connection.prepareStatement(sqlCheckLibro);
            checkStmt.setString(1, libroIsbn);   // Estas 3 líneas conectan el comando sqlCheckLibro entre el isbn para comprobar la cantidad de libros disponibles
            ResultSet resultSet = checkStmt.executeQuery();
            
            if (resultSet.next()) {
                int cantidadDisponible = resultSet.getInt("CANTIDAD_DISPONIBLE");
                if (cantidadDisponible <= 0) {
                    System.out.println("El libro no está disponible para préstamo.");
                    connection.rollback();
                    return false;
                }
            } else {
                System.out.println("Libro no encontrado.");
                connection.rollback();
                return false;
            }
            
            // 2. Insertar préstamo
            PreparedStatement prestamoStmt = connection.prepareStatement(sqlInsertPrestamo);
            prestamoStmt.setString(1, libroIsbn);
            prestamoStmt.setLong(2, socioId);
            prestamoStmt.setString(3, fechaDevolucionPrevista);
            prestamoStmt.executeUpdate();
            
            // 3. Actualizar cantidad disponible
            PreparedStatement updateStmt = connection.prepareStatement(sqlUpdateLibro);
            updateStmt.setString(1, libroIsbn);
            updateStmt.executeUpdate();
            
            connection.commit();
            System.out.println("Préstamo realizado correctamente.");
            return true; // Operación exitosa
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Devolver libro controlando disponibilidad
    public boolean devolverLibro(Long prestamoId) {
        String sqlUpdatePrestamo = "UPDATE PRESTAMOS SET FECHA_DEVOLUCION_REAL = CURDATE(), ESTADO = 'DEVUELTO' WHERE ID = ? AND ESTADO = 'PRESTADO'"; // Comando que comprueba el estado del prestamo mediante los atributos ID y Estado
        String sqlGetLibroIsbn = "SELECT LIBRO_ISBN FROM PRESTAMOS WHERE ID = ?"; // Comando del libro de su prestamo correspondiente
        String sqlUpdateLibro = "UPDATE LIBROS SET CANTIDAD_DISPONIBLE = CANTIDAD_DISPONIBLE + 1 WHERE ISBN = ?";
        
        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);
            
            // 1. Obtener ISBN del libro
            // Estas 3 líneas conectan el comando sqlGetLibroIsbn entre el id para comprobar si ya estuvo ese id en la tabla de prestamos
            PreparedStatement getIsbnStmt = connection.prepareStatement(sqlGetLibroIsbn);
            getIsbnStmt.setLong(1, prestamoId);  // Asigna el ISBN del libro al parámetro de la consulta
            ResultSet resultSet = getIsbnStmt.executeQuery(); // Ejecuta la consulta y obtiene resultado
            
            if (!resultSet.next()) {
                System.out.println("Préstamo no encontrado.");
                connection.rollback();
                return false;
            }
            
            String libroIsbn = resultSet.getString("LIBRO_ISBN"); // Obtiene el ISBN del libro
            
            // 2. Actualizar préstamo
            PreparedStatement prestamoStmt = connection.prepareStatement(sqlUpdatePrestamo);
            prestamoStmt.setLong(1, prestamoId);
            int rowsAffected = prestamoStmt.executeUpdate(); // Ejecuta los datos insertados
            
            if (rowsAffected == 0) {
                System.out.println("El préstamo ya estaba devuelto o no existe.");
                connection.rollback();
                return false;
            }
            
            // 3. Actualizar disponibilidad del libro
            PreparedStatement libroStmt = connection.prepareStatement(sqlUpdateLibro);
            libroStmt.setString(1, libroIsbn);//Asigna el ISBN para identificar el libro
            libroStmt.executeUpdate();// Ejecuta la actualización
            
            connection.commit(); // La operención se realiza correctamente en la base de datos principal
            System.out.println("Devolución registrada correctamente.");
            return true; // Operación exitosa
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LISTAR PRÉSTAMOS ACTIVOS
    // No trabaja con parametros porque la consulta fija dice que lista los prestamos 
    // esten activos, y no se necesita que el usuario ingrese un parametro para trabajarlo
    public List<Prestamos> listarPrestamosActivos() {
        String sql = "SELECT * FROM PRESTAMOS WHERE ESTADO = 'PRESTADO'"; // Comando que lista los préstamos activos
        List<Prestamos> prestamos = new ArrayList<>();
        
        try (Connection connection = Conexion.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            while (resultSet.next()) {
                prestamos.add(new Prestamos(
                    resultSet.getLong("ID"),
                    resultSet.getString("LIBRO_ISBN"),
                    resultSet.getLong("SOCIO_ID"),
                    resultSet.getString("FECHA_PRESTAMO"),
                    resultSet.getString("FECHA_DEVOLUCION_PREVISTA"),
                    resultSet.getString("FECHA_DEVOLUCION_REAL"),
                    resultSet.getString("ESTADO")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    // Listar orestamos
    // Se necesita el ID del socio como parametro para listar los prestamos hacia un socio
    public List<Prestamos> listarPrestamosPorSocio(Long socioId) {
        String sql = "SELECT * FROM PRESTAMOS WHERE SOCIO_ID = ?"; // Comando que lista los préstamos de cualquier tipo que pertenezcan al socio
        List<Prestamos> prestamos = new ArrayList<>();
        
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, socioId);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                prestamos.add(new Prestamos(
                    resultSet.getLong("ID"),
                    resultSet.getString("LIBRO_ISBN"),
                    resultSet.getLong("SOCIO_ID"),
                    resultSet.getString("FECHA_PRESTAMO"),
                    resultSet.getString("FECHA_DEVOLUCION_PREVISTA"),
                    resultSet.getString("FECHA_DEVOLUCION_REAL"),
                    resultSet.getString("ESTADO")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    // Ver libros y quien los tiene
    public void listarLibrosPrestadosConSocios() {
        String sql = "SELECT l.TITULO, l.ISBN, s.NOMBRE, s.APELLIDO, p.FECHA_DEVOLUCION_PREVISTA " + // Selección de datos de libros y prestamos
                     "FROM PRESTAMOS p " + // Tabla de prestamos
                     "JOIN LIBROS l ON p.LIBRO_ISBN = l.ISBN " + // Unión de tablas de libros y prestamos
                     "JOIN SOCIOS s ON p.SOCIO_ID = s.ID " + // Unión de tablas de socios y prestamos
                     "WHERE p.ESTADO = 'PRESTADO' " + // Condición de que el prestamo esté en estado prestado
                     "ORDER BY l.TITULO";
        
        try (Connection connection = Conexion.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            System.out.println("\n LIBROS PRESTADOS ACTUALMENTE ");
            boolean hayPrestamos = false;
            while (resultSet.next()) {
                hayPrestamos = true;
                System.out.println("Libro: " + resultSet.getString("TITULO"));
                System.out.println("ISBN: " + resultSet.getString("ISBN"));
                System.out.println("Prestado a: " + resultSet.getString("NOMBRE") + " " + resultSet.getString("APELLIDO"));
                System.out.println("Fecha devolución: " + resultSet.getString("FECHA_DEVOLUCION_PREVISTA"));
                System.out.println("------------------------");
            }
            if (!hayPrestamos) {
                System.out.println("No hay libros prestados actualmente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}