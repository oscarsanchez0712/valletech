ValleTech — Sistema de Gestión de Productos
Aplicación Desktop desarrollada en Java Swing + MySQL siguiendo el patrón MVC.

Tecnologías

Java 11
MySQL 8.0
Maven 3.x
Docker


Estructura del Proyecto
valletech/
├── pom.xml
├── sql/
│   └── valletech.sql
└── src/main/java/valletech/
    ├── Main.java
    ├── database/
    │   └── Conexion.java
    ├── model/
    │   ├── Usuario.java
    │   └── Producto.java
    ├── controller/
    │   ├── UsuarioController.java
    │   └── ProductoController.java
    └── view/
        ├── LoginView.java
        ├── DashboardView.java
        └── ProductoView.java

Funcionalidades
Login

Validación de usuario y contraseña contra MySQL
Mensaje de acceso correcto o incorrecto

Dashboard

Gestión de Productos
Cerrar Sesión

CRUD de Productos

Registrar, modificar y eliminar productos
Búsqueda por nombre o categoría
Listado en JTable
Campos: Id, Nombre, Categoría, Precio, Stock


Configuración con Docker
bashdocker run -d --name valletech-mysql -e MYSQL_ROOT_PASSWORD=root123 -e MYSQL_DATABASE=valletech_db -e MYSQL_USER=admin -e MYSQL_PASSWORD=admin123 -p 3306:3306 mysql:8.0
Luego ejecutar sql/valletech.sql en MySQL Workbench.

Credenciales de prueba
UsuarioContraseñaadminadmin123oscaroscar123

Cómo ejecutar

Clonar el repositorio
Levantar el contenedor Docker
Ejecutar sql/valletech.sql en Workbench
Abrir en IntelliJ → clic derecho en pom.xml → Add as Maven Project
Ejecutar valletech.Main


Patrón MVC
CapaClasesModelUsuario, ProductoViewLoginView, DashboardView, ProductoViewControllerUsuarioController, ProductoControllerDatabaseConexion (Singleton) 

Autor del proyecto
Oscar heyton Sanchez Arias

ValleTech © 2025 — Evaluación Final CO1
