<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es"><head><meta charset="UTF-8"><title>Acceso | Diecast</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"></head>
<body class="login-page"><main class="login-card"><div class="brand-mark">D</div><p class="eyebrow">Colección privada</p><h1>Inventario Diecast</h1><p class="muted">Ingresa para administrar tus autos.</p>
<% if (request.getAttribute("error") != null) { %><div class="alert"><%= request.getAttribute("error") %></div><% } %>
<form method="post" action="${pageContext.request.contextPath}/login"><label>Usuario<input name="usuario" required autocomplete="username"></label><label>Clave<input type="password" name="clave" required autocomplete="current-password"></label><button class="button primary" type="submit">Entrar</button></form></main></body></html>
