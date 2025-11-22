import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import model.Autores;
import model.Libros;
import model.Prestamos;
import model.Socios;
import repository.AutoresRepository;
import repository.LibrosRepository;
import repository.SociosRepository;
import repository.PrestamosRepository;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        AutoresRepository autoresRepository = new AutoresRepository();
        LibrosRepository librosRepository = new LibrosRepository();
        SociosRepository sociosRepository = new SociosRepository();
        PrestamosRepository prestamosRepository = new PrestamosRepository();
        
        int opcion;

        do {
            System.out.println("\n SISTEMA DE BIBLIOTECA ");
            System.out.println("1. Gestionar Autores");
            System.out.println("2. Gestionar Libros");
            System.out.println("3. Gestionar Socios");
            System.out.println("4. Gestionar Prestamos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    gestionarAutores(sc, autoresRepository);
                    break;
                    
                case 2:
                    gestionarLibros(sc, librosRepository);
                    break;
                    
                case 3:
                    gestionarSocios(sc, sociosRepository);
                    break;

                case 4:
                    gestionarPrestamos(sc, prestamosRepository);
                    break;
                    
                case 0:
                    System.out.println("¡Hasta pronto!");
                    break;
                    
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 0);

        sc.close();
    }

    // Método para gestionar Autores
    private static void gestionarAutores(Scanner sc, AutoresRepository autoresRepository) {
        int opcionAutor;

        do {
            System.out.println("\n GESTIÓN DE AUTORES ");
            System.out.println("1. Insertar Autor");
            System.out.println("2. Listar Autores");
            System.out.println("3. Consultar Autor por ID");
            System.out.println("4. Editar Autor");
            System.out.println("5. Eliminar Autor");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            
            opcionAutor = sc.nextInt();
            sc.nextLine(); 

            switch (opcionAutor) {
                case 1:
                    System.out.print("Ingrese el nombre del autor: ");
                    String nombre = sc.nextLine();
                    System.out.print("Ingrese la nacionalidad del autor: ");
                    String nacionalidad = sc.nextLine();
                    
                    Autores autor = new Autores(nombre, nacionalidad);
                    autoresRepository.insertarAutor(autor);
                    break;
                    
                case 2:
                    System.out.println("\n LISTA DE AUTORES ");
                    for (Autores autores : autoresRepository.listarAutores()) {
                        System.out.println("ID: " + autores.getId());
                        System.out.println("Autor: " + autores.getNombre());
                        System.out.println("Nacionalidad: " + autores.getNacionalidad());
                        System.out.println("------------------------");
                    }
                    break;
                    
                case 3:
                    System.out.print("Ingrese el ID del autor que desea consultar: ");
                    Long idConsulta = sc.nextLong();
                    sc.nextLine(); 
                    autoresRepository.consultarAutorPorId(idConsulta);
                    break;
                    
                case 4:
                    System.out.print("Ingrese el ID del autor que desea editar: ");
                    Long idEditar = sc.nextLong();
                    sc.nextLine(); 
                    System.out.print("Ingrese el nuevo nombre del autor: ");
                    String nuevoNombre = sc.nextLine();
                    System.out.print("Ingrese la nueva nacionalidad del autor: ");
                    String nuevaNacionalidad = sc.nextLine();
                    autoresRepository.editarAutorPorId(idEditar, nuevoNombre, nuevaNacionalidad);
                    break;
                    
                case 5:
                    System.out.print("Ingrese el ID del autor que desea eliminar: ");
                    Long idEliminar = sc.nextLong();
                    sc.nextLine(); 
                    autoresRepository.eliminarAutorPorId(idEliminar);
                    break;

                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                    
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcionAutor != 0);
    }

    // Método para gestionar Libros
    private static void gestionarLibros(Scanner sc, LibrosRepository librosRepository) throws SQLException {
        int opcionLibro;

        do {
            System.out.println("\n GESTIÓN DE LIBROS ");
            System.out.println("1. Insertar Libro");
            System.out.println("2. Listar Libros");
            System.out.println("3. Consultar Libro por ISBN");
            System.out.println("4. Editar Libro");
            System.out.println("5. Eliminar Libro");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            
            opcionLibro = sc.nextInt();
            sc.nextLine(); 

            switch (opcionLibro) {
                case 1:
                    System.out.print("Ingrese el ISBN del libro: ");
                    Long isbn = sc.nextLong();
                    sc.nextLine(); 
                    System.out.print("Ingrese el título del libro: ");
                    String titulo = sc.nextLine();
                    System.out.print("Ingrese el autor ID del libro: ");
                    Long autor_id = sc.nextLong();
                    System.out.print("Ingrese el año de publicación del libro: ");
                    Long año_publicacion = sc.nextLong();
                    System.out.print("Ingrese la cantidad total del libro: ");
                    Long cantidad_total = sc.nextLong();
                    System.out.print("Ingrese la cantidad disponible del libro: ");
                    Long cantidad_disponible = sc.nextLong();
                    sc.nextLine(); 
                    
                    Libros libro = new Libros(isbn, titulo, autor_id, año_publicacion, cantidad_total, cantidad_disponible);
                    librosRepository.insertarLibro(libro);
                    break;
                    
                case 2:
                    System.out.println("\n LISTA DE LIBROS ");
                    for (Libros libros : librosRepository.listarLibros()) {
                        System.out.println("ISBN: " + libros.getIsbn());
                        System.out.println("Título: " + libros.getTitulo());
                        System.out.println("Autor ID: " + libros.getAutor_id());
                        System.out.println("Año: " + libros.getAño_publicacion());
                        System.out.println("Total: " + libros.getCantidad_total());
                        System.out.println("Disponible: " + libros.getCantidad_disponible());
                        System.out.println("------------------------");
                    }
                    break;
                    
                case 3:
                    System.out.print("Ingrese el ISBN del libro que desea consultar: ");
                    Long isbnConsulta = sc.nextLong();
                    sc.nextLine(); 
                    librosRepository.consultarLibroPorIsbn(isbnConsulta);;
                    break;
                    
                case 4:
                    System.out.print("Ingrese el ISBN del libro que desea editar: ");
                    Long isbnEditar = sc.nextLong();
                    sc.nextLine(); 
                    System.out.print("Ingrese el nuevo título del libro: ");
                    String nuevoTitulo = sc.nextLine();
                    System.out.print("Ingrese el nuevo autor ID del libro: ");
                    Long nuevoAutor_id = sc.nextLong();
                    System.out.print("Ingrese el nuevo año de publicación del libro: ");
                    Long nuevoAño_publicacion = sc.nextLong();
                    System.out.print("Ingrese la nueva cantidad total del libro: ");
                    Long nuevoCantidad_total = sc.nextLong();
                    System.out.print("Ingrese la nueva cantidad disponible del libro: ");
                    Long nuevoCantidad_disponible = sc.nextLong();
                    sc.nextLine(); 
                    
                    librosRepository.editarLibroPorIsbn(isbnEditar, nuevoTitulo, nuevoAutor_id, nuevoAño_publicacion, nuevoCantidad_total, nuevoCantidad_disponible);
                    break;
                    
                case 5:
                    System.out.print("Ingrese el ISBN del libro que desea eliminar: ");
                    Long isbnEliminar = sc.nextLong();
                    sc.nextLine(); 
                    librosRepository.eliminarLibroSiNoTienePrestamos(isbnEliminar);
                    break;
                    
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                    
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcionLibro != 0);
    }

    // Método para gestionar Socios
    private static void gestionarSocios(Scanner sc, SociosRepository sociosRepository) {
        int opcionSocio;

        do {
            System.out.println("\n GESTIÓN DE SOCIOS ");
            System.out.println("1. Insertar Socio");
            System.out.println("2. Listar Socios");
            System.out.println("3. Consultar Socio por ID");
            System.out.println("4. Editar Socio");
            System.out.println("5. Eliminar Socio");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            
            opcionSocio = sc.nextInt();
            sc.nextLine(); 

            switch (opcionSocio) {
                case 1:
                    System.out.print("Ingrese el nombre del socio: ");
                    String nombreSocio = sc.nextLine();
                    System.out.print("Ingrese el apellido del socio: ");
                    String apellido = sc.nextLine();
                    System.out.print("Ingrese el DNI del socio: ");
                    String dni = sc.nextLine();
                    System.out.print("Ingrese el teléfono del socio: ");
                    String telefono = sc.nextLine();
                    
                    Socios socio = new Socios(nombreSocio, apellido, dni, telefono);
                    sociosRepository.insertarSocio(socio);
                    break;
                    
                case 2:
                    System.out.println("\n LISTA DE SOCIOS ");
                    for (Socios socios : sociosRepository.listarSocios()) {
                        System.out.println("ID: " + socios.getId());
                        System.out.println("Nombre: " + socios.getNombre());
                        System.out.println("Apellido: " + socios.getApellido());
                        System.out.println("DNI: " + socios.getDni());
                        System.out.println("Teléfono: " + socios.getTelefono());
                        System.out.println("------------------------");
                    }
                    break;
                    
                case 3:
                    System.out.print("Ingrese el ID del socio que desea consultar: ");
                    Long idConsulta = sc.nextLong();
                    sc.nextLine(); 
                    sociosRepository.consultarSocioPorId(idConsulta);
                    break;
                    
                case 4:
                    System.out.print("Ingrese el ID del socio que desea editar: ");
                    Long idEditar = sc.nextLong();
                    sc.nextLine(); 
                    System.out.print("Ingrese el nuevo nombre del socio: ");
                    String nuevoNombreSocio = sc.nextLine();
                    System.out.print("Ingrese el nuevo apellido del socio: ");
                    String nuevoApellido = sc.nextLine();
                    System.out.print("Ingrese el nuevo DNI del socio: ");
                    String nuevoDni = sc.nextLine();
                    System.out.print("Ingrese el nuevo teléfono del socio: ");
                    String nuevoTelefono = sc.nextLine();
                    
                    sociosRepository.editarSocioPorId(idEditar, nuevoNombreSocio, nuevoApellido, nuevoDni, nuevoTelefono);
                    break;
                    
                case 5:
                    System.out.print("Ingrese el ID del socio que desea eliminar: ");
                    Long idEliminar = sc.nextLong();
                    sc.nextLine(); 
                    sociosRepository.eliminarSocioSiNoTienePrestamos(idEliminar);
                    break;
                    
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                    
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcionSocio != 0);
    }

    // Método para gestionar Prestamos
private static void gestionarPrestamos(Scanner sc, PrestamosRepository prestamosRepository) {
    int opcionPrestamo;

    do {
        System.out.println("\n GESTIÓN DE PRESTAMOS ");
        System.out.println("1. Insertar Prestamo");
        System.out.println("2. Listar Prestamos");
        System.out.println("3. Consultar Prestamo por ID");
        System.out.println("4. Editar Prestamo");
        System.out.println("5. Eliminar Prestamo");
        System.out.println("6. Realizar préstamo de un libro");
        System.out.println("7. Devolver un libro");
        System.out.println("8. Listar préstamos activos");
        System.out.println("9. Listar préstamos de un socio específico");
        System.out.println("10. Ver libros prestados actualmente y quién los tiene");
        System.out.println("0. Volver al Menú Principal");
        System.out.print("Seleccione una opción: ");
        
        opcionPrestamo = sc.nextInt();
        sc.nextLine(); 

        switch (opcionPrestamo) {
            case 1:
                System.out.print("Ingrese el ISBN del libro: ");
                String isbn = sc.nextLine();
                System.out.print("Ingrese el ID del socio: ");
                Long socioId = sc.nextLong();
                sc.nextLine(); // Salto de línea
                System.out.print("Ingrese la fecha de prestamo (YYYY-MM-DD): ");
                String fechaPrestamo = sc.nextLine();
                System.out.print("Ingrese la fecha de devolución prevista (YYYY-MM-DD): ");
                String fechaDevolucionPrevista = sc.nextLine();
                System.out.print("Ingrese la fecha de devolución real (YYYY-MM-DD o dejar vacío): ");
                String fechaDevolucionReal = sc.nextLine();
                System.out.print("Ingrese el estado del prestamo (PRESTADO/DEVUELTO): ");
                String estado = sc.nextLine();
                
                Prestamos prestamo = new Prestamos(isbn, socioId, fechaPrestamo, fechaDevolucionPrevista, fechaDevolucionReal, estado);
                prestamosRepository.insertarPrestamo(prestamo);
                break;

            case 2:
                System.out.println("\n LISTA DE PRÉSTAMOS ");
                for (Prestamos p : prestamosRepository.listarPrestamos()) {
                    System.out.println("ID: " + p.getId());
                    System.out.println("Libro ISBN: " + p.getLibroIsbn());
                    System.out.println("Socio ID: " + p.getSocioId());
                    System.out.println("Fecha préstamo: " + p.getFechaPrestamo());
                    System.out.println("Fecha devolución prevista: " + p.getFechaDevolucionPrevista());
                    System.out.println("Fecha devolución real: " + 
                        (p.getFechaDevolucionReal() != null ? p.getFechaDevolucionReal() : "Pendiente"));
                    System.out.println("Estado: " + p.getEstado());
                    System.out.println("------------------------");
                }
                break;

            case 3:
                System.out.print("Ingrese el ID del préstamo a consultar: ");
                Long idConsulta = sc.nextLong();
                sc.nextLine(); // Salto de línea
                prestamosRepository.consultarPrestamoPorId(idConsulta);
                break;

            case 4:
                System.out.print("Ingrese el ID del préstamo a editar: ");
                Long idEditar = sc.nextLong();
                sc.nextLine(); // Salto de línea
                System.out.print("Ingrese la nueva fecha de préstamo (YYYY-MM-DD): ");
                String nuevoFechaPrestamo = sc.nextLine();
                System.out.print("Ingrese la nueva fecha de devolución prevista (YYYY-MM-DD): ");
                String nuevoFechaDevolucionPrevista = sc.nextLine();
                System.out.print("Ingrese la nueva fecha de devolución real (YYYY-MM-DD): ");
                String nuevoFechaDevolucionReal = sc.nextLine();
                System.out.print("Ingrese el nuevo estado (PRESTADO/DEVUELTO): ");
                String nuevoEstado = sc.nextLine();
                
                prestamosRepository.editarPrestamoPorId(idEditar, nuevoFechaPrestamo, nuevoFechaDevolucionPrevista, nuevoFechaDevolucionReal, nuevoEstado);
                break;

            case 5:
                System.out.print("Ingrese el ID del préstamo a eliminar: ");
                Long idEliminar = sc.nextLong();
                sc.nextLine(); // Salto de línea
                prestamosRepository.eliminarPrestamoPorId(idEliminar);
                break;

            // Algunas se trabajan con booleanos para comprobar si se ha realizado correctamente
            // o no la acción crítica que se espera
            case 6:
                System.out.println("\n REALIZAR PRÉSTAMO CONTROLADO ");
                System.out.print("Ingrese el ISBN del libro: ");
                String isbnControlado = sc.nextLine();
                System.out.print("Ingrese el ID del socio: ");
                Long socioIdControlado = sc.nextLong();
                sc.nextLine(); // Salto de línea
                System.out.print("Ingrese la fecha de devolución prevista (YYYY-MM-DD): ");
                String fechaDevolucionControlada = sc.nextLine();
                
                boolean prestamoExitoso = prestamosRepository.realizarPrestamo(isbnControlado, socioIdControlado, fechaDevolucionControlada);
                
                if (prestamoExitoso) {
                    System.out.println("Préstamo realizado exitosamente");
                } else {
                    System.out.println("No se pudo realizar el préstamo");
                }
                break;

            case 7:
                System.out.println("\n DEVOLVER LIBRO ");
                System.out.print("Ingrese el ID del préstamo a devolver: ");
                Long prestamoIdDevolver = sc.nextLong();
                sc.nextLine(); // Salto de línea
                
                System.out.print("¿Confirmar devolución? (s/n): ");
                String confirmarDevolucion = sc.nextLine();
                
                if (confirmarDevolucion.equalsIgnoreCase("s")) {
                    boolean devolucionExitosa = prestamosRepository.devolverLibro(prestamoIdDevolver);
                    
                    if (devolucionExitosa) {
                        System.out.println("Devolución registrada exitosamente");
                    } else {
                        System.out.println("No se pudo registrar la devolución");
                    }
                } else {
                    System.out.println("Devolución cancelada.");
                }
                break;

            case 8:
                System.out.println("\n PRÉSTAMOS ACTIVOS ");
                List<Prestamos> prestamosActivos = prestamosRepository.listarPrestamosActivos();
                
                if (prestamosActivos.isEmpty()) {
                    System.out.println("No hay préstamos activos.");
                } else {
                    for (Prestamos prestamoActivo : prestamosActivos) {
                        System.out.println("ID: " + prestamoActivo.getId());
                        System.out.println("Libro ISBN: " + prestamoActivo.getLibroIsbn());
                        System.out.println("Socio ID: " + prestamoActivo.getSocioId());
                        System.out.println("Fecha préstamo: " + prestamoActivo.getFechaPrestamo());
                        System.out.println("Fecha devolución prevista: " + prestamoActivo.getFechaDevolucionPrevista());
                        System.out.println("Estado: " + prestamoActivo.getEstado());
                        System.out.println("------------------------");
                    }
                }
                break;

            case 9:
                System.out.println("\n PRÉSTAMOS POR SOCIO ");
                System.out.print("Ingrese el ID del socio: ");
                Long idSocio = sc.nextLong();
                sc.nextLine(); // Salto de línea
                
                List<Prestamos> prestamosSocio = prestamosRepository.listarPrestamosPorSocio(idSocio);
                
                if (prestamosSocio.isEmpty()) {
                    System.out.println("El socio no tiene préstamos registrados.");
                } else {
                    System.out.println("Préstamos del socio ID: " + idSocio);
                    for (Prestamos prestamoSocio : prestamosSocio) {
                        System.out.println("ID Préstamo: " + prestamoSocio.getId());
                        System.out.println("Libro ISBN: " + prestamoSocio.getLibroIsbn());
                        System.out.println("Fecha préstamo: " + prestamoSocio.getFechaPrestamo());
                        System.out.println("Fecha devolución prevista: " + prestamoSocio.getFechaDevolucionPrevista());
                        System.out.println("Fecha devolución real: " + 
                            (prestamoSocio.getFechaDevolucionReal() != null ? prestamoSocio.getFechaDevolucionReal() : "Pendiente"));
                        System.out.println("Estado: " + prestamoSocio.getEstado());
                        System.out.println("------------------------");
                    }
                }
                break;

            case 10:
                prestamosRepository.listarLibrosPrestadosConSocios();
                break;

            case 0:
                System.out.println("Volviendo al menú principal...");
                break;

            default:
                System.out.println("Opción no válida");
        }
    } while (opcionPrestamo != 0);
}
}