<%@ page import="javax.naming.*, javax.sql.*, java.sql.*" %>
<%
try {
    // Obter o contexto JNDI
    InitialContext ic = new InitialContext();

    // Localizar o DataSource configurado no context.xml
    DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/MeuDB");

    // Tentar abrir a conex�o
    try (Connection conn = ds.getConnection()) {
        if (conn != null && !conn.isClosed()) {
            out.println("<p style='color:green;font-weight:bold'> Conex�o JNDI bem-sucedida!</p>");
        } else {
            out.println("<p style='color:red;font-weight:bold'> Conex�o JNDI retornou null ou est� fechada.</p>");
        }
    }
} catch (Exception e) {
    out.println("<p style='color:red;font-weight:bold'> Erro ao obter conex�o via JNDI:</p>");
    out.println("<pre>");
    e.printStackTrace(new java.io.PrintWriter(out));
    out.println("</pre>");
}
%>
