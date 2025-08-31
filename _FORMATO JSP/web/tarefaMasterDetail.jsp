<%@page import="app.configuracao.ConfiguracaoBean"%>
<%@page import="app.configuracao.ConfiguracaoDAO"%>
<%@page import="app.Utilidades"%>
<%@page import="app.subtarefa.SubtarefaDAO"%>
<%@page import="app.subtarefa.SubtarefaBean"%>
<%@page import="java.util.List"%>
<%@page import="app.tarefas.TarefaDAO"%>
<%@page import="app.tarefas.TarefaBean"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%

    ConfiguracaoDAO configDAO = new ConfiguracaoDAO();
    ConfiguracaoBean configBean = new ConfiguracaoBean();
    configDAO.selecionarTema(configBean); 
    int temaAtual = configBean.getTema();

    
    TarefaBean tarefa = new TarefaBean();
    TarefaDAO tarefaDAO = new TarefaDAO();
    SubtarefaDAO subtarefaDAO = new SubtarefaDAO();
    
    Integer novoOuEditar = 0;
    if (request.getParameterMap().containsKey("novoOuEditar")) {
        novoOuEditar = Integer.parseInt(request.getParameter("novoOuEditar"));
    }
    
    if (request.getParameterMap().containsKey("id_tarefa")) {
        tarefa.setId_tarefa(Integer.parseInt(request.getParameter("id_tarefa")));
        tarefaDAO.selectUnico(tarefa);
    }
    
    List<SubtarefaBean> ativas = subtarefaDAO.listaTarefasAtivas(tarefa.getId_tarefa());
  //List<SubtarefaBean> inativas = subtarefaDAO.listaTarefasInativas(tarefa.getId_tarefa());

%>

<!DOCTYPE html>
<html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <title>Master-Detail de Tarefas</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" href="./css/radio.css">
        
        <% if (temaAtual == 1) { %>
            <link rel="stylesheet" href="./css/tarefaMasterDetail-claro.css">
            <link rel="stylesheet" href="./css/modal-claro.css">
        <% } else if (temaAtual == 2) { %>
            <link rel="stylesheet" href="./css/tarefaMasterDetail-escuro.css">
            <link rel="stylesheet" href="./css/modal-escuro.css">   
        <% } else { %>
            <script>
                const temaEscuro = window.matchMedia('(prefers-color-scheme: dark)').matches;
                if (temaEscuro) {
                    document.write('<link rel="stylesheet" href="./css/tarefaMasterDetail-escuro.css">');
                    document.write('<link rel="stylesheet" href="./css/modal-claro.css">');
                } else {
                    document.write('<link rel="stylesheet" href="./css/tarefaMasterDetail-claro.css">');
                    document.write('<link rel="stylesheet" href="./css/modal-claro.css">');
                }
            </script>
        <% } %>

    </head>

    <body>
        <div class="container">
            
            <!-- MASTER -->
            <div class="master">
                
                <% if (novoOuEditar != null && novoOuEditar.equals(1)) { %>
                    <button type="reset" class="voltar"  onclick="window.location.href = 'index.jsp'">Voltar</button>
                    <button class="btn-add" onclick="window.location.href='tarefaMasterDetail.jsp?novoOuEditar=2'">Nova Tarefa</button>
                <% } %>   
                
                <h2>Tarefa</h2>

                <form id="formTarefa" method="get" action="salvarTarefa.jsp">

                    <div id="formTarefa" 
                        class="  
                            <% if (novoOuEditar != null && novoOuEditar.equals(1)) { %>
                                opaco
                            <% } %>     
                        ">
                        <input type="hidden" name="id_tarefa" id="id_tarefa" value="<%= Utilidades.nullTrim(tarefa.getId_tarefa()) %>" />
                        <input type="text" name="titulo" id="titulo" placeholder="Título da tarefa" value="<%= Utilidades.nullTrim(tarefa.getTitulo()) %>" required>
                        <input type="text" name="responsavel" id="responsavel" placeholder="Responsável da tarefa" value="<%= Utilidades.nullTrim(tarefa.getResponsavel()) %>" required>

                        <textarea  name="descricao" id="descricao" placeholder="Descrição da tarefa"><%= Utilidades.nullTrim(tarefa.getDescricao()) %></textarea>

                       
                            
                        <div class="area-radio">
                           

                            <div class="radio-group">
                                <span class="texto-prioridade">Prioridade</span>
                                <label class="radio radio--baixa">
                                  <input type="radio" name="prioridade" value="baixa" required
                                    <%= "baixa".equals(tarefa.getPrioridade()) ? "checked" : "" %> />
                                  <span class="dot"></span>
                                  <span>Baixa</span>
                                </label>

                                <label class="radio radio--media">
                                  <input type="radio" name="prioridade" value="media"
                                    <%= "media".equals(tarefa.getPrioridade()) ? "checked" : "" %> />
                                  <span class="dot"></span>
                                  <span>Média</span>
                                </label>

                                <label class="radio radio--alta">
                                  <input type="radio" name="prioridade" value="alta"
                                    <%= "alta".equals(tarefa.getPrioridade()) ? "checked" : "" %> />
                                  <span class="dot"></span>
                                  <span>Alta</span>
                                </label>
                            </div>
                        </div>
                                  
                        

                        <label for="data" class="label-data-prevista">Data prevista para conclusão</label>
                        <input type="date" name="data_conclusao" id="data_conclusao" value="<%= Utilidades.nullTrim(tarefa.getData_conclusao()) %>">
                    </div>
                    
                    <div class="botoes">
                        
                        <% if (novoOuEditar != null && novoOuEditar.equals(0)) { %>
                            <button type="submit" class="salvar">Salvar</button>
                            <button type="button" class="fechar" onclick="link('tarefaMasterDetail.jsp?id_tarefa=<%= tarefa.getId_tarefa() %>&novoOuEditar=1')">Cancelar</button>
                        <% } %>     
                        
                        <% if (novoOuEditar != null && novoOuEditar.equals(1)) { %>
                            <button id="btn-editar" type="reset" class="editar"  onclick="link('tarefaMasterDetail.jsp?id_tarefa=<%= tarefa.getId_tarefa() %>&novoOuEditar=0')">Editar</button>
                        <% }  %>
                        
                        <% if (novoOuEditar != null && novoOuEditar.equals(2)) { %>
                           <button type="submit" class="salvar">Salvar</button>
                           <button type="button" class="fechar" onclick="link('index.jsp')">Cancelar</button>
                        <% }  %>          
                        
                    </div>
                    
                </form>

                <hr>
                
                    <h2>Detalhes da Tarefa</h2>
                    <form id="form-subtarefa" class="form" action="salvarSubtarefa.jsp" method="post">
                        <div class=" <% if (novoOuEditar==0 || novoOuEditar==2) { %> opaco <% } %>">
                        <input type="hidden" name="fk_tarefa" id="fk_tarefa" value="<%= tarefa.getId_tarefa() %>">

                        <div class="campo">
                            <textarea name="descricao" id="descricaoDetail"
                            placeholder="Digite a descrição..." required></textarea>
                        </div>

                        <button type="submit" class="salvar">Adicionar subtarefa</button>
                    </div>
                    </form>
                
            </div>

            <!-- DETAIL -->
            <div id="area-detail" class="detail <% if (novoOuEditar==0 || novoOuEditar==2) { %> opaco <% } %>">

                <h3>Lista de detalhes</h3>
                <ul id="lista-tarefas">
                    <% for (SubtarefaBean sub : ativas) { %>
                    <li>
                        <div>
                        <small> <%= Utilidades.dateToString(sub.getData_conclusao(), "dd MMM - yyyy") %> </small><br>
                        <!--<small> <%= tarefa.getData_criacao() %> </small><br>-->
                        <small><%= sub.getDescricao() %></small>
                        </div>
                        <a class="icone-lixeira" href="#"
                           onclick="openModalDeletar(<%= sub.getId_detalhe() %>, <%= sub.getFk_tarefa() %>, '<%= sub.getDescricao()%>'); return false;">
                           <i class="fas fa-trash"></i>
                        </a>
                    </li>
                    <% } %>
                </ul>

            </div>

        </div>
       
        <!-- Modal de Deletar oculto -->
        <div class="modal-overlay" id="modalDeletar" style="display:none;">
            <div class="modal">
                <h2>Confirmar Exclusão</h2>
                <div class="confirmacao">
                    <p id="texto-descricao" class="area-info"></p>
                    <form id="formDeletar" method="post" action="deletarSubtarefa.jsp">
                        <input type="hidden" name="id-detalhe" id="id-detalhe" value="0" />
                        <input type="hidden" name="id-tarefa" id="id-tarefa" value="0" />
                        <div class="modal-buttons">
                            <button type="submit" class="btn-deletar-confirmar" onclick="confirmarDelete()">Sim, deletar</button>
                            <button type="button" class="btn-cancelar" onclick="closeModalDeletar()">Cancelar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="./js/tarefaMasterDetail.js"></script>
        <script src="./js/Utilidades.js"></script>

        <% if (novoOuEditar==0 || novoOuEditar==2) { %>
            <script>selecionarAddTarefa()</script>
        <% } %>    
        
        <% if (novoOuEditar==1) { %>
            <script>selecionarAddSubTarefa()</script>
        <% } %>
            
    </body>
    <% tarefaDAO.fecharConexao(); %>
</html>