# Inventario Diecast

Aplicación web para registrar y consultar una colección de autos diecast. Ofrece búsqueda, ordenamiento y paginación del inventario, además de herramientas de administración para mantener tanto los autos como los usuarios del sistema.

La aplicación se construye como un archivo WAR y está preparada para desplegarse en Apache Tomcat.

## Características

- Consulta de autos con búsqueda por marca, modelo, código, año, color y otros datos.
- Ordenamiento y paginación del inventario.
- Registro, edición y eliminación de autos para administradores.
- Inicio y cierre de sesión.
- Administración de usuarios: crear, editar y eliminar usuarios.
- Acceso restringido a las tareas administrativas según el rol del usuario.
- API REST para el recurso de autos.

## Tecnologías

| Tecnología | Uso |
| --- | --- |
| Java 17 | Lenguaje de la aplicación |
| Spring Boot 3.3.5 | Configuración, MVC y ejecución |
| Spring Security | Seguridad de endpoints y API |
| JSP y JSTL | Vistas del sitio web |
| MariaDB | Persistencia de datos |
| Maven | Compilación y empaquetado |
| Apache Tomcat 11 | Despliegue del archivo WAR |

## Requisitos

- JDK 17 o superior.
- Apache Maven 3.9 o superior.
- MariaDB en ejecución.
- Apache Tomcat 11 para despliegue externo.

## Base de datos

Ejecuta el script [database.sql](database.sql) en MariaDB. El script crea la base de datos `diecast_db`, las tablas necesarias y una cuenta administrativa inicial:

| Usuario | Clave | Rol |
| --- | --- | --- |
| `admin` | `admin123` | Administrador |

La conexión se puede configurar mediante variables de entorno:

| Variable | Valor predeterminado |
| --- | --- |
| `DB_URL` | `jdbc:mariadb://localhost:3306/diecast_db` |
| `DB_USER` | `Su-Usuario` |
| `DB_PASSWORD` | `Su-Clave` |

## Ejecutar localmente

Desde la raíz del proyecto:

```powershell
mvn spring-boot:run
```

Abre `http://localhost:8080/login` e inicia sesión con las credenciales configuradas.

## Construir el WAR

```powershell
mvn clean package -DskipTests
```

El artefacto generado estará en `target/inventario-diecast.war`.

## Desplegar en Tomcat

1. Detén Tomcat si ya está en ejecución.
2. Copia `target/inventario-diecast.war` a la carpeta `webapps` de Tomcat.
3. Inicia Tomcat.
4. Abre `http://localhost:8080/inventario-diecast/login`.

Tomcat expandirá automáticamente el WAR al iniciar o al detectar el archivo en `webapps`.

## Roles y accesos

| Función | Usuario | Administrador |
| --- | :---: | :---: |
| Consultar inventario | Sí | Sí |
| Buscar, ordenar y paginar | Sí | Sí |
| Crear, editar o eliminar autos | No | Sí |
| Administrar usuarios | No | Sí |

La autorización se valida en el servidor; ocultar los botones no sustituye el control de acceso.

## Rutas principales

| Ruta | Descripción |
| --- | --- |
| `/login` | Inicio de sesión |
| `/autos` | Inventario de autos |
| `/usuarios` | Gestión de usuarios, solo administradores |
| `/api/autos` | API REST de autos |
| `/logout` | Cierre de sesión |

## Estructura

```text
src/main/java/com/trabajo6/
  controller/  Controladores MVC y servlets
  dao/         Acceso JDBC a MariaDB
  jpa/         Entidades y repositorios JPA
  rest/        API REST
  security/    Configuración de seguridad
src/main/webapp/
  WEB-INF/views/  Vistas JSP
  assets/css/     Estilos de la aplicación
database.sql      Esquema y datos iniciales
```