<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head><meta charset="UTF-8"><title>${empty managedUser ? 'Nuevo usuario' : 'Editar usuario'} | Diecast</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"></head>
<body>
<header class="topbar"><div><span class="eyebrow">Administración</span><h1>${empty managedUser ? 'Crear usuario' : 'Editar usuario'}</h1></div><a class="button ghost" href="${pageContext.request.contextPath}/usuarios">Volver</a></header>
<main class="container narrow"><form class="form-card" method="post" action="${pageContext.request.contextPath}/usuarios" autocomplete="off"><input id="user-id" type="hidden" name="id" value="${empty managedUser ? '' : managedUser.id}"><div class="form-grid"><label>Usuario<input id="username" name="usuario" value="${empty managedUser ? '' : managedUser.usuario}" maxlength="80" autocomplete="new-password" required></label><label>Clave<input id="password" type="password" name="clave" autocomplete="new-password" ${empty managedUser ? 'required' : ''}><c:if test="${not empty managedUser}"><span class="muted">Déjala vacía para conservar la clave actual.</span></c:if></label><label class="checkbox-label"><input type="checkbox" name="administrador" value="true" ${managedUser.administrador ? 'checked' : ''}> Administrador</label></div><button class="button primary" type="submit">Guardar usuario</button></form></main>
<script>window.addEventListener('pageshow',function(){if(document.getElementById('user-id').value === ''){document.getElementById('username').value='';document.getElementById('password').value='';}});</script>
</body>
</html>