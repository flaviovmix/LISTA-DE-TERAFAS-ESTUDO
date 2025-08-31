<%@ page import="static app.Utilidades.stringToDate" %>
<%@page import="app.subtarefa.SubtarefaDAO"%>
<%@page import="app.subtarefa.SubtarefaBean"%>
<%@page import="app.ConexaoPostGres"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    SubtarefaBean subtarefa = new SubtarefaBean();
    SubtarefaDAO dao = new SubtarefaDAO();

    subtarefa.setDescricao(request.getParameter("descricao"));
    subtarefa.setData_conclusao(stringToDate(request.getParameter("data_conclusao"), "yyyy-MM-dd"));
    subtarefa.setFk_tarefa(Integer.parseInt(request.getParameter("fk_tarefa")));

    if (subtarefa.getId_detalhe() == 0) {
        dao.adicionarSubtarefa(subtarefa);
        response.sendRedirect("tarefaMasterDetail.jsp?id_tarefa=" + subtarefa.getFk_tarefa()+"&novoOuEditar=1");
    } 

    dao.fecharConexao();
%>
