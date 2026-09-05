# Inventario Diecast

Aplicacion web para administrar una coleccion de autos diecast. La aplicacion incorpora Spring Boot, Spring MVC, Spring Data JPA, Spring Security y una API RESTful, y se despliega como WAR en Apache Tomcat.

El proyecto conserva las vistas JSP y los Servlets existentes para mantener el flujo web actual, mientras que los nuevos servicios REST usan la arquitectura Spring.

## Tecnologias en uso

- **Spring Boot 3.3.5**: punto de entrada de la aplicacion, autoconfiguracion y empaquetado WAR mediante Maven.
- **Spring MVC**: controladores web y REST basados en anotaciones.
- **Spring Data JPA**: entidades `CarEntity` y `UserEntity`, junto con `CarRepository` y `UserRepository`, para persistir en MariaDB.
- **Spring Security**: autenticacion HTTP Basic para la API y roles `ADMIN`/`USER` derivados del campo `usuarios.administrador`.
- **REST**: `CarRestController` expone operaciones CRUD JSON bajo `/api/autos`.
- **Maven**: gestiona dependencias, compilacion, pruebas y generacion del WAR.
- **MVC**: las vistas JSP/Servlet existentes siguen atendiendo la interfaz web; Spring MVC atiende la nueva capa REST.

## Funcionalidades

- Inicio de sesion mediante la tabla `usuarios`.
- Roles de usuario con el campo `administrador` (`TRUE`/`FALSE`).
- Solo los administradores pueden agregar, editar o eliminar piezas.
- Busqueda por cualquier dato de la pieza: marca, modelo, codigo, ano, numero, color, serie, T-Hunt u otro.
- Ordenamiento ascendente o descendente al seleccionar los encabezados de la tabla.
- Paginacion del inventario.
- Franja visual de marcas: Hot Wheels, Matchbox, M2 Machines, Majorette y GreenLight.

## API REST

La API requiere autenticacion HTTP Basic. Las consultas requieren cualquier usuario autenticado; las altas, modificaciones y eliminaciones requieren el rol administrador.

| Metodo | Ruta | Permiso | Funcion |
| --- | --- | --- | --- |
| `GET` | `/api/autos` | Usuario autenticado | Lista piezas |
| `GET` | `/api/autos/{id}` | Usuario autenticado | Consulta una pieza |
| `POST` | `/api/autos` | Administrador | Crea una pieza |
| `PUT` | `/api/autos/{id}` | Administrador | Actualiza una pieza |
| `DELETE` | `/api/autos/{id}` | Administrador | Elimina una pieza |

Ejemplo de consulta:

```powershell
curl.exe -u admin:admin123 http://localhost:8080/inventario-diecast/api/autos
```

## Requisitos

- JDK 17 o superior.
- Apache Maven 3.9 o superior.
- MariaDB.
- Apache Tomcat 11 (compatible con Jakarta Servlet).

## Configurar MariaDB

1. Ejecutar [`database.sql`](database.sql) en MariaDB. El script crea la base `diecast_db`, las tablas y el usuario administrador inicial.
2. Configurar estas variables de entorno para Tomcat:

   - `DB_URL`: por defecto `jdbc:mariadb://localhost:3306/diecast_db`
   - `DB_USER`: por defecto
   - `DB_PASSWORD`: por defecto

3. Usuario inicial:

   - Usuario: `admin`
   - Clave: `admin123`
   - Administrador: `TRUE`

Si la base ya existia, vuelve a ejecutar el script para aplicar las columnas `thunt` y `administrador`.

## Compilar

Desde la raiz del proyecto:

```powershell
mvn clean package -DskipTests
```

El archivo generado sera `target/inventario-diecast.war`.

## Desplegar en Tomcat

1. Copiar `target/inventario-diecast.war` en la carpeta `webapps` de Tomcat.
2. Reiniciar Tomcat.
3. Abrir:

```text
http://localhost:8080/inventario-diecast/
```

La aplicacion redirige al formulario de inicio de sesion.

## Estructura principal

```text
src/main/java/com/trabajo6/
  controller/  Servlets y flujo web heredado
  dao/         Acceso JDBC heredado
  jpa/         Entidades y repositorios Spring Data JPA
  rest/        Controladores Spring MVC REST
  security/    Configuracion Spring Security
  model/       Entidades del flujo web heredado
src/main/webapp/
  WEB-INF/views/  Vistas JSP
  assets/css/     Estilos
database.sql      Esquema y datos iniciales
pom.xml           Configuracion Maven
```
