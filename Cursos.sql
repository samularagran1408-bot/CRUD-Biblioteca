create database Cursos;
use Cursos;

create table Categoría (
	ID INT PRIMARY KEY AUTO_INCREMENT,
	nom_cat varchar(12),
    email varchar(30),
    estado varchar(12)
);

create table Cursos (
	ID_cur INT PRIMARY KEY AUTO_INCREMENT,
    nom_cur varchar(12),
    categoría INT,
    foreign key (categoría) references Categoría (ID)
);

create table Estudiantes (
	ID_est INT PRIMARY KEY AUTO_INCREMENT,
    nom_cur varchar(20),
    email varchar(20)
);

create table Estudiantes_Cursos (
	Estudiante INT,
    Curso INT,
    foreign key (Estudiante) references Estudiantes (ID_est),
    foreign key (Curso) references Cursos (ID_cur)
);

select * from Categoría;
select * from Cursos;
select * from Estudiantes;

drop table Categoría;
drop table Cursos;
drop table Estudiantes_Cursos;

