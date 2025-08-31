<%@page import="app.tarefas.TarefaDAO"%>
<%@page import="app.tarefas.TarefaBean"%>
<%
    int id_tarefa = Integer.parseInt(request.getParameter("id_tarefa"));

    TarefaBean tarefa = new TarefaBean();            
    TarefaDAO dao = new TarefaDAO();
    dao.excluirTarefa(id_tarefa);
    dao.fecharConexao();
    
    response.sendRedirect("index.jsp");
    dao.fecharConexao();
%>