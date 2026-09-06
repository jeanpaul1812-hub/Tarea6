<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head><meta charset="UTF-8"><title>Usuarios | Diecast</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"></head>
<body>
<header class="topbar"><div><span class="eyebrow">Administración</span><h1>Usuarios</h1></div><div class="top-actions"><a class="button ghost" href="${pageContext.request.contextPath}/autos">Inventario</a><a class="button ghost" href="${pageContext.request.contextPath}/logout">Salir</a></div></header>
<main class="container"><div class="toolbar"><p class="muted">Administra los accesos al inventario.</p><a class="button primary" href="${pageContext.request.contextPath}/usuarios?action=new">+ Nuevo usuario</a></div>
<div class="table-wrap"><table><thead><tr><th>Usuario</th><th>Rol</th><th>Acciones</th></tr></thead><tbody>
<c:forEach var="managedUser" items="${users}"><tr><td><strong>${managedUser.usuario}</strong></td><td>${managedUser.administrador ? 'Administrador' : 'Usuario'}</td><td><a class="button ghost" href="${pageContext.request.contextPath}/usuarios?action=edit&amp;id=${managedUser.id}">Editar</a><c:if test="${managedUser.id ne sessionScope.usuario.id}"><a class="button ghost" href="${pageContext.request.contextPath}/usuarios?action=delete&amp;id=${managedUser.id}" onclick="return confirm('¿Eliminar este usuario?')">Eliminar</a></c:if></td></tr></c:forEach>
<c:if test="${empty users}"><tr><td colspan="3" class="empty">No hay usuarios registrados.</td></tr></c:if></tbody></table></div></main>
</body>
</html>