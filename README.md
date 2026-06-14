# 🚀 ValleTech - Sistema de Gestión de Productos

Aplicación Desktop desarrollada en **Java Swing** y **MySQL**, implementando el patrón de arquitectura **MVC (Model - View - Controller)** para una mejor organización del código y separación de responsabilidades.

---

# 📌 Tecnologías Utilizadas

* ☕ Java 11
* 🗄️ MySQL 8.0
* 📦 Maven 3.x
* 🐳 Docker
* 🖥️ Java Swing

---

# 📂 Estructura del Proyecto

```text
valletech/
│
├── pom.xml
├── sql/
│   └── valletech.sql
│
└── src/
    └── main/
        └── java/
            └── valletech/
                ├── Main.java
                │
                ├── database/
                │   └── Conexion.java
                │
                ├── model/
                │   ├── Usuario.java
                │   └── Producto.java
                │
                ├── controller/
                │   ├── UsuarioController.java
                │   └── ProductoController.java
                │
                └── view/
                    ├── LoginView.java
                    ├── DashboardView.java
                    └── ProductoView.java
```

---

# ✨ Funcionalidades

## 🔐 Inicio de Sesión

* Validación de usuario y contraseña contra MySQL.
* Mensajes de acceso correcto o incorrecto.
* Acceso seguro al sistema.

---

## 🏠 Dashboard

* Panel principal del sistema.
* Acceso a la gestión de productos.
* Opción para cerrar sesión.

---

## 📦 Gestión de Productos (CRUD)

Permite administrar completamente los productos mediante las siguientes operaciones:

* ✅ Registrar productos
* ✏️ Modificar productos
* ❌ Eliminar productos
* 🔍 Buscar por nombre o categoría
* 📋 Mostrar listado en JTable

### Campos del Producto

| Campo     |
| --------- |
| ID        |
| Nombre    |
| Categoría |
| Precio    |
| Stock     |

---

# 🐳 Configuración con Docker

Crear el contenedor de MySQL ejecutando el siguiente comando:

```bash
docker run -d \
--name valletech-mysql \
-e MYSQL_ROOT_PASSWORD=root123 \
-e MYSQL_DATABASE=valletech_db \
-e MYSQL_USER=admin \
-e MYSQL_PASSWORD=admin123 \
-p 3306:3306 \
mysql:8.0
```

Una vez iniciado el contenedor:

1. Abrir MySQL Workbench.
2. Crear la conexión.
3. Ejecutar el script:

```text
sql/valletech.sql
```

---

# 👤 Credenciales de Prueba

| Usuario | Contraseña |
| ------- | ---------- |
| admin   | admin123   |
| oscar   | oscar123   |

---

# ▶️ Cómo Ejecutar el Proyecto

### 1. Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
```

### 2. Ingresar al proyecto

```bash
cd valletech
```

### 3. Levantar el contenedor Docker

```bash
docker start valletech-mysql
```

o, si no existe:

```bash
docker run -d --name valletech-mysql \
-e MYSQL_ROOT_PASSWORD=root123 \
-e MYSQL_DATABASE=valletech_db \
-e MYSQL_USER=admin \
-e MYSQL_PASSWORD=admin123 \
-p 3306:3306 mysql:8.0
```

### 4. Ejecutar el script SQL

Importar y ejecutar:

```text
sql/valletech.sql
```

### 5. Abrir el proyecto

* Abrir IntelliJ IDEA.
* Seleccionar **Open Project**.
* Hacer clic derecho sobre **pom.xml**.
* Elegir **Add as Maven Project**.

### 6. Ejecutar la aplicación

```text
valletech.Main
```

---

# 🏛️ Patrón MVC

| Capa           | Clases                                                |
| -------------- | ----------------------------------------------------- |
| **Model**      | Usuario.java, Producto.java                           |
| **View**       | LoginView.java, DashboardView.java, ProductoView.java |
| **Controller** | UsuarioController.java, ProductoController.java       |
| **Database**   | Conexion.java (Singleton)                             |

---

# 📸 Módulos del Sistema

* 🔐 Login
* 🏠 Dashboard
* 📦 Gestión de Productos
* 🔍 Búsqueda
* ✏️ Actualización
* ❌ Eliminación
* 🚪 Cierre de Sesión

---

# 👨‍💻 Autor

**Oscar Heyton Sánchez Arias**

Proyecto desarrollado como evaluación final del curso.

---

# 📄 Licencia

Proyecto con fines académicos.

---

<div align="center">

## 🚀 ValleTech

**Sistema de Gestión de Productos**

**Evaluación Final CO1**

© 2025 - Todos los derechos reservados.

</div>
