<html>
  <head><title>First JSP</title></head>
  <body>
  <%
    double num = Math.random();
    if (num > 0.95) {
  %>
    <h2>You'll have a luck day!</h2><p>(<%=num%>)</p>
  <% } else { %>
    <h2>Well, life goes on ... </h2><p>(<%=num%>)</p>
  <% } %>
    <h3>You are listening to:</h3>
    <p>${Song}</p>
  </body>
</html>
