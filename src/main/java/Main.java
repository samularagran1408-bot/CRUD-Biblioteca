import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import model.Categoría;
import model.Cursos;
import model.Estudiantes;
import model.Estudiantes_Cursos;
import repository.CursosRepository;
import repository.EstudiantesRepository;
import repository.Estudiantes_CursosRepository;
import repository.CategoríaRepository;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        CategoríaRepository categoríaRepository = new CategoríaRepository();
        EstudiantesRepository estudiantesRepository = new EstudiantesRepository();
        Estudiantes_CursosRepository estudiantes_CursosRepository = new Estudiantes_CursosRepository();
        CursosRepository cursosRepository = new CursosRepository();
        Estudiantes_CursosRepository ecRepository = new Estudiantes_CursosRepository();

        int opcion;
        do {
            System.out.println("\n SISTEMA DE CURSOS ");
            System.out.println("1. Gestionar Categorías");
            System.out.println("2. Gestionar Cursos");
            System.out.println("3. Gestionar Estudiantes");
            System.out.println("4. Gestionar Inscripciones");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = sc.nextInt();
            sc.nextLine(); 
            switch (opcion) {
                case 1:
                    gestionarCategorías(sc, categoríaRepository);
                    break;
                case 2:
                    gestionarCursos(sc, cursosRepository);
                    break;
                case 3:
                    gestionarEstudiantes(sc, estudiantesRepository, estudiantes_CursosRepository);
                    break;
                case 4:
                    gestionarInscripciones(sc, ecRepository, estudiantesRepository, cursosRepository);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (opcion != 0);
        sc.close();
    }

    public static void gestionarCategorías(Scanner sc, CategoríaRepository categoríaRepository) throws SQLException {
        int opcionCategoría;
        do {
            System.out.println("\n GESTIÓN DE CATEGORÍAS");
            System.out.println("1. Insertar Categorías");
            System.out.println("2. Listar Categoría");
            System.out.println("3. Consultar Categoría por ID");
            System.out.println("4. Eliminar Categoría por ID");
            System.out.println("5. Editar Categoría por ID");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcionCategoría = sc.nextInt();
            sc.nextLine();

            switch (opcionCategoría) {
                case 1:
                    System.out.println("Insertando Categoría");
                    System.out.println("Introduzca el Nombre de la Categoría: ");
                    String nombre = sc.nextLine();
                    System.out.println("Introduzca el Email de la Categoría: ");
                    String email = sc.nextLine();
                    System.out.println("Introduzca el Estado de la Categoría: ");
                    String Estado = sc.nextLine();
                    Categoría categoría = new Categoría(nombre, email, Estado);
                    categoríaRepository.InsertarCategoría(categoría);
                    break;
                case 2:
                    System.out.println("Listando Categorías");
                    List<Categoría> lista = categoríaRepository.ListarCategorías();
                    for (int i = 0; i < lista.size(); i++) {
                        System.out.println("ID: " + lista.get(i).getID());
                        System.out.println("Nombre: " + lista.get(i).getNombre());
                        System.out.println("Email: " + lista.get(i).getEmail());
                        System.out.println("Estado: " + lista.get(i).getEstado());
                    }
                    break;
                case 3:
                    System.out.println("Consultando Categoría por ID");
                    Long IDConsultar = sc.nextLong();
                    categoríaRepository.ConsultarCategoríaPorID(IDConsultar);
                    break;
                case 4:
                    System.out.println("Eliminando Categoría por ID");
                    Long IDEliminar = sc.nextLong();
                    categoríaRepository.EliminarCategoríaPorID(IDEliminar);
                    break;
                case 5:
                    System.out.println("Editando Categoría por ID");
                    System.out.println("Ingrese el IDConsultar de la Categoría que desea editar: ");
                    Long IDEditar = sc.nextLong();
                    sc.nextLine();
                    System.out.println("Introduzca el nuevo Nombre de la Categoría: ");
                    String nombre2 = sc.nextLine();
                    System.out.println("Introduzca el nuevo Email de la Categoría: ");
                    String email2 = sc.nextLine();
                    System.out.println("Introduzca el nuevo Estado de la Categoría: ");
                    String Estado2 = sc.nextLine();
                    categoríaRepository.EditarCategoríaPorID(IDEditar, nombre2, email2, Estado2);
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (opcionCategoría != 0);
    }

    public static void gestionarCursos(Scanner sc, CursosRepository cursosRepository) throws SQLException {
        int opcionCursos;
        do {
            System.out.println("\n GESTIÓN DE CURSOS");
            System.out.println("1. Insertar Cursos");    
            System.out.println("2. Listar Cursos");
            System.out.println("3. Consultar Curso por ID");
            System.out.println("4. Eliminar Curso por ID");
            System.out.println("5. Editar Curso por ID");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcionCursos = sc.nextInt();
            sc.nextLine();

            switch (opcionCursos) {
                case 1:
                    System.out.println("Insertando Curso");
                    System.out.println("Introduzca el nombre del surso: ");
                    String nombre = sc.nextLine();
                    System.out.println("Introduzca el ID de la Categoría del Curso: ");
                    Long Categoria = sc.nextLong();
                    Cursos curso = new Cursos(nombre, Categoria);
                    cursosRepository.InsertarCurso(curso, null);
                    break;
                case 2:
                    System.out.println("Listando Cursos");
                    List<Cursos> lista = cursosRepository.ListarCursos();
                    for (int i = 0; i < lista.size(); i++) {
                        System.out.println("ID: " + lista.get(i).getID());
                        System.out.println("Nombre: " + lista.get(i).getNom_cur());
                        System.out.println("Categoría: " + lista.get(i).getCategoria());
                    }
                    break;
                case 3:
                    System.out.println("Ingresa el ID del Curso que desea consultar: ");
                    Long IDConsultar = sc.nextLong();
                    sc.nextLine();
                    cursosRepository.ConsultarCursoPorID(IDConsultar);
                    break;
                case 4:
                    System.out.println("Ingrese el ID del Curso que desea eliminar: ");
                    Long IDEliminar = sc.nextLong();
                    sc.nextLine();
                    cursosRepository.EliminarCursoPorID(IDEliminar);
                    break;
                case 5:
                    System.out.println("Ingrese el ID del Curso que desea editar: ");
                    Long IDEditar = sc.nextLong();
                    System.out.println("Introduzca el nuevo Título del Curso: ");
                    String Nombre2 = sc.nextLine();
                    System.out.println("Introduzca el nuevo ID de la Categoría del Curso: ");
                    Long Categoria2 = sc.nextLong();
                    cursosRepository.EditarCursoPorID(IDEditar, Nombre2, Categoria2);;
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (opcionCursos != 0);
    }

    public static void gestionarEstudiantes(Scanner sc, EstudiantesRepository estudiantesRepository, Estudiantes_CursosRepository estudiantes_CursosRepository) throws Exception {
        int opcionEstudiantes;
        do {
            System.out.println("\n GESTIÓN DE ESTUDIANTES");
            System.out.println("1. Insertar Estudiantes");
            System.out.println("2. Listar Estudiantes");
            System.out.println("3. Consultar Estudiante por ID");
            System.out.println("4. Eliminar Estudiante por ID");
            System.out.println("5. Editar Estudiante por ID");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcionEstudiantes = sc.nextInt();
            sc.nextLine();

            switch (opcionEstudiantes) {
                case 1:
                    System.out.println("Insertando Estudiante");
                    System.out.println("Introduzca el nombre del Estudiante: ");
                    String Curso = sc.nextLine();
                    System.out.println("Introduzca el Email del Estudiante: ");
                    String email = sc.nextLine();
                    Estudiantes estudiante = new Estudiantes(Curso, email);
                    estudiantesRepository.InsertarEstudiante(estudiante);
                    break;
                case 2:
                    System.out.println("Listando Estudiantes");
                    List<Estudiantes> lista = estudiantesRepository.ListarEstudiantes();
                    for (int i = 0; i < lista.size(); i++) {
                        System.out.println("ID: " + lista.get(i).getID());
                        System.out.println("Curso: " + lista.get(i).getNom_cur());
                        System.out.println("Email: " + lista.get(i).getEmail());
                    }
                    break;
                case 3:
                    System.out.println("Ingresa el ID del Estudiante que desea consultar: ");
                    Long IDConsultar = sc.nextLong();
                    sc.nextLine();
                    estudiantesRepository.ConsultarEstudiantePorID(IDConsultar);;
                    break;
                case 4:
                    System.out.println("Ingrese el ID del Estudiante que desea eliminar: ");
                    Long IDEliminar = sc.nextLong();
                    sc.nextLine();
                    estudiantesRepository.EliminarEstudiantePorID(IDEliminar);
                    break;
                case 5:
                    System.out.println("Ingrese el ID del Estudiante que desea editar: ");
                    Long IDEditar = sc.nextLong();
                    System.out.println("Introduzca el nuevo Curso del Estudiante: ");
                    String Curso2 = sc.nextLine();
                    System.out.println("Introduzca el nuevo Email del Estudiante: ");
                    String email2 = sc.nextLine();
                    estudiantesRepository.EditarEstudiantePorID(IDEditar, Curso2, email2);
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (opcionEstudiantes != 0);
    }

    public static void gestionarInscripciones(Scanner sc, Estudiantes_CursosRepository ecRepository, EstudiantesRepository estudiantesRepository, CursosRepository cursosRepository) {
        int opcionInscripciones;
        do {
            System.out.println("\n GESTIÓN DE INSCRIPCIONES");
            System.out.println("1. Inscribir Estudiante a Curso");
            System.out.println("2. Listar Cursos de un Estudiante");
            System.out.println("3. Dar de baja Curso de un Estudiante");
            System.out.println("4. Listar Todas las Inscripciones");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcionInscripciones = sc.nextInt();
            sc.nextLine();

            switch (opcionInscripciones) {
                case 1:
                    System.out.println("Inscribiendo Estudiante a Curso");
                    System.out.print("Ingrese el ID del Estudiante: ");
                    Long estudianteId = sc.nextLong();
                    System.out.print("Ingrese el ID del Curso: ");
                    Long cursoId = sc.nextLong();
                    ecRepository.inscribirEstudianteACurso(estudianteId, cursoId);
                    break;
                case 2:
                    System.out.println("Listando Cursos de un Estudiante");
                    System.out.print("Ingrese el ID del Estudiante: ");
                    Long estId = sc.nextLong();
                    List<String> cursos = ecRepository.listarCursosDelEstudiante(estId);
                    for (String cursoInfo : cursos) {
                        System.out.println(cursoInfo);
                    }
                    break;
                case 3:
                    System.out.println("Dar de baja Curso de un Estudiante");
                    System.out.print("Ingrese el ID del Estudiante: ");
                    Long estId2 = sc.nextLong();
                    System.out.print("Ingrese el ID del Curso: ");
                    Long curId2 = sc.nextLong();
                    ecRepository.darDeBajaEstudiante(estId2, curId2);
                    break;
                case 4:
                    System.out.println("Listando Estudiantes del Curso");
                    List<Estudiantes_Cursos> estudiantes2 = ecRepository.listarTodasInscripciones();
                    for (Estudiantes_Cursos estudianteInfo : estudiantes2) {
                        System.out.println(estudianteInfo.getEstudiante() + " | " + estudianteInfo.getCurso());
                    }
                    System.out.println("Estudiantes inscritos en cursos:");
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (opcionInscripciones != 0);
    }
}
