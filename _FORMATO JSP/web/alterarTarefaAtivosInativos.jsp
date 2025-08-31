<%@page import="app.tarefas.TarefaDAO"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String ativa = (request.getParameter("ativa"));
    int id_detalhe = Integer.parseInt(request.getParameter("id_tarefa"));
    boolean estadoAtual = Boolean.parseBoolean(request.getParameter("estado_atual"));
    boolean novoEstado = !estadoAtual;

    TarefaDAO tarefaDao = new TarefaDAO();
    tarefaDao.alterarAtivoInativo(id_detalhe, novoEstado);

    tarefaDao.fecharConexao();

    response.sendRedirect("index.jsp?ativa=" + ativa);
%>
