<%@page import="app.subtarefa.SubtarefaDAO"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>

<%
    int id_detalhe = Integer.parseInt(request.getParameter("id_detalhe"));
    int id_tarefa = Integer.parseInt(request.getParameter("id_tarefa"));

    boolean estadoAtual = Boolean.parseBoolean(request.getParameter("estado_atual"));

    boolean novoEstado = !estadoAtual;

    SubtarefaDAO dao = new SubtarefaDAO();
    dao.alterarAtivoInativo(id_detalhe, novoEstado);

    dao.fecharConexao();

    response.sendRedirect("tarefaMasterDetail.jsp?id_tarefa=" + id_tarefa +"&novoOuEditar=1");
%>
