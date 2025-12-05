package repository;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.Conexion;
import model.Cursos;
public class CursosRepository {
    public boolean InsertarCurso(Cursos curso, CategoríaRepository catRepo) {
            // VALIDAR QUE LA CATEGORÍA EXISTA
        if (curso.getCategoria() == null) {
            System.err.println("Error: La categoría es obligatoria.");
            return false;
        }

        String sql = "INSERT INTO Cursos (nom_cur, categoría) VALUES (?, ?)";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curso.getNom_cur());
            preparedStatement.setLong(2, curso.getCategoria());

            preparedStatement.executeUpdate();

            System.out.println("Curso insertado con éxito");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Cursos> ListarCursos() {
        String sql = "SELECT * FROM Cursos";
        List<Cursos> Cursos = new ArrayList<>();
        try (Connection connection = Conexion.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next() ) {
                Cursos.add(new Cursos(
                        resultSet.getLong("ID_cur"),
                        resultSet.getString("nom_cur"),
                        resultSet.getLong("categoría")
                    )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Cursos;
    }

    public void ConsultarCursoPorID(Long ID) throws SQLException {
        String sql = "SELECT * FROM Cursos WHERE ID_cur = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Cursos curso = new Cursos(
                        resultSet.getLong("ID_cur"),
                        resultSet.getString("nom_cur"),
                        resultSet.getLong("categoría")
                    );
                System.out.println("ID: " + curso.getID());
                System.out.println("Nombre: " + curso.getNom_cur());
                System.out.println("Categoría ID: " + curso.getCategoria());
            } else {
                System.out.println("Curso no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void EliminarCursoPorID(Long ID) throws SQLException {
        String sql = "DELETE FROM Cursos WHERE ID_cur = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, ID);
            int filasAfectadas = preparedStatement.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Curso eliminado con éxito");
            } else {
                System.out.println("No se encontró ningún curso con ID: " + ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void EditarCursoPorID(Long ID_cur, String nom_cur, Long Categoria) throws SQLException {
        String sql = "UPDATE Cursos SET nom_cur = ?, categoría = ? WHERE ID_cur = ?";
        try (Connection connection = Conexion.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nom_cur);
            preparedStatement.setLong(2, Categoria);
            preparedStatement.setLong(3, ID_cur);
            
            int filasAfectadas = preparedStatement.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Curso editado con éxito");
            } else {
                System.out.println("No se encontró ningún curso con ID: " + ID_cur);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
