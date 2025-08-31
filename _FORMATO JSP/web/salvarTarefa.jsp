<%@page import="app.Utilidades"%>
<%@ page import="static app.Utilidades.stringToDate" %>
<%@page import="java.sql.Date"%>
<%@page import="app.tarefas.TarefaDAO"%>
<%@page import="app.tarefas.TarefaBean"%>
<%
    TarefaBean tarefa = new TarefaBean();
    TarefaDAO dao = new TarefaDAO();

    tarefa.setId_tarefa(Integer.parseInt(request.getParameter("id_tarefa")));
    tarefa.setTitulo(request.getParameter("titulo"));
    tarefa.setResponsavel(request.getParameter("responsavel"));
    tarefa.setDescricao(request.getParameter("descricao"));
    tarefa.setStatus(request.getParameter("status"));
    tarefa.setPrioridade(request.getParameter("prioridade"));
    tarefa.setData_conclusao(stringToDate(request.getParameter("data_conclusao"), "yyyy-MM-dd"));
    
    if (tarefa.getId_tarefa()==0){
        dao.adicionarTarefa(tarefa);
    } else {
        dao.alterarTarefa(tarefa);
    }
    response.sendRedirect("tarefaMasterDetail.jsp?id_tarefa=" + tarefa.getId_tarefa() + "&novoOuEditar=1");
    
    dao.fecharConexao();
%>