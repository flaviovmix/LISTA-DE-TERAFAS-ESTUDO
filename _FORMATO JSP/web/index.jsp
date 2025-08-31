<%@page import="app.RenderizarModalDeletarTarefa"%>
<%@page import="app.RenderizarModalConfig"%>
<%@page import="app.util.RenderizadorTarefas"%>
<%@page import="app.configuracao.ConfiguracaoBean"%>
<%@page import="app.configuracao.ConfiguracaoDAO"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="app.Utilidades" %>
<%@page import="app.tarefas.TarefaBean"%>
<%@page import="java.util.List"%>
<%@page import="app.tarefas.TarefaDAO"%>
<%@page import="app.ConexaoPostGres"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    
    Integer offset = 0;
    if (request.getParameterMap().containsKey("offset")) {
        offset = Integer.parseInt(request.getParameter("offset"));
    }
    
    ConfiguracaoDAO configDAO = new ConfiguracaoDAO();
    ConfiguracaoBean configBean = new ConfiguracaoBean();
    configDAO.selecionarTema(configBean); 
    int temaAtual = configBean.getTema();
    
    Integer configuracao = 0;
    if (request.getParameterMap().containsKey("configuracao")) {
        configuracao = Integer.parseInt(request.getParameter("configuracao"));
    }
    
    Integer ativa = 0;
    if (request.getParameterMap().containsKey("ativa")) {
        ativa = Integer.parseInt(request.getParameter("ativa"));
    }

    // Não foi necessário dar um new TarefaBean() aqui porque os objetos são criados 
    // dentro dos métodos listaTarefasAtivas() e listaTarefasInativas() do TarefaDAO.
    // Esses métodos instanciam TarefaBean internamente, preenchem seus dados e retornam 
    // uma lista já pronta com todos os objetos.
    TarefaDAO tarefaDAO = new TarefaDAO();
    List<TarefaBean> tarefasAtivas = tarefaDAO.listaTarefasAtivas(offset);
    List<TarefaBean> tarefasInativas = tarefaDAO.listaTarefasInativas(offset);
%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8" />
        <title>To-Do List</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" href="./css/guias.css">
        
        
        <% if (temaAtual == 1) { %>
            <link rel="stylesheet" href="./css/index-claro.css">
            <link rel="stylesheet" href="./css/modal-claro.css">
        <% } else if (temaAtual == 2) { %>
            <link rel="stylesheet" href="./css/index-escuro.css">
            <link rel="stylesheet" href="./css/modal-escuro.css">
        <% } else { %>
            <script>
                const temaEscuro = window.matchMedia('(prefers-color-scheme: dark)').matches;
                if (temaEscuro) {
                    document.write('<link rel="stylesheet" href="./css/index-escuro.css">');
                    document.write('<link rel="stylesheet" href="./css/modal-escuro.css">');
                } else {
                    document.write('<link rel="stylesheet" href="./css/index-claro.css">');
                    document.write('<link rel="stylesheet" href="./css/modal-claro.css">');
                }
            </script>
        <% } %>
        <link rel="stylesheet" href="./css/alert.css">
        <link rel="stylesheet" href="./css/paginacao.css"> 
        
    </head>
    
    <body>
        <header>
            <div class="container">
                <button class="btn-add" onclick="window.location.href='tarefaMasterDetail.jsp?novoOuEditar=2'">Nova Tarefa</button>
                <a href="#" class="config-icon" onclick="openModalConfig(); return false;">
                    <i class="fas fa-cog"></i>
                </a>
            </div>
        </header>
        

        
        <%  
            if (
                    (tarefasAtivas == null || tarefasAtivas.isEmpty()) && 
                    (tarefasInativas == null || tarefasInativas.isEmpty())
               ) 
            { %> 
                <div class="container-imagem">
                    <img src="./img/personagem.png">
                </div>
            <%} else { %>
                <div class='task-list '>
                    

                    <div class="alert alert-blue" id="alertBox">
                      <span class="alert-text">
                        <strong>ARRUMAR A SALA</strong> será movida para concluída em <span id="countdown">9</span>.
                      </span>
                      <button class="btn btn-cancel" onclick="cancelarAcao()">Cancelar</button>
                      <button class="btn btn-confirm" onclick="confirmarAcao()">Confirmar agora</button>
                    </div>
                    
                    <div class="tabs">
                       <!-- Radios controlam as abas -->
                       <input type="radio" name="tabs" id="tab-ativas" 
                            <% if (ativa != null && ativa.equals(0)) { %>
                                  checked       
                            <% } %>                              
                       >
                       <label for="tab-ativas">Ativas</label>                       
                       
                       <input type="radio" name="tabs" id="tab-inativas"
                            <% if (ativa != null && ativa.equals(1)) { %>
                                  checked       
                            <% } %>
                       >
                       <label for="tab-inativas">Concluídas</label>

                       <!-- Conteúdo da Aba: ATIVAS -->
                       <div class="tab-content content-ativas">
                           <% RenderizadorTarefas.renderizar(tarefasAtivas, true, out);%>


                           <%
    int limit = 5;
    int total = tarefaDAO.contarTarefas(true); // true = ativas (ajuste p/ inativas)
    int totalPaginas = Math.max(1, (int)Math.ceil((double) total / limit));

    int off = (request.getParameter("offset") != null)
                ? Integer.parseInt(request.getParameter("offset"))
                : 0;
    off = Math.max(0, Math.min(off, (totalPaginas - 1) * limit));

    int paginaAtual = (off / limit) + 1;

    String ativaParam = "0"; // 0=Ativas, 1=Concluídas (se você usa isso nas abas)
    if (request.getParameter("ativa") != null) {
        ativaParam = request.getParameter("ativa");
    }

    int offPrimeira  = 0;
    int offAnterior  = Math.max(off - limit, 0);
    int offMenos1    = Math.max(off - limit, 0);                 // botão “-1”
    int offMais1     = Math.min(off + limit, (totalPaginas-1)*limit); // botão “+1”
    int offProxima   = Math.min(off + limit, (totalPaginas-1)*limit);
    int offUltima    = (totalPaginas - 1) * limit;
%>

<nav class="pagination" aria-label="Paginação">
    <ul class="pagination__list">

        <!-- PRIMEIRA -->
        <li class="pagination__item">
            <a class="pagination__link <%= (paginaAtual == 1 ? "opaco" : "") %>"
               href="?ativa=<%= ativaParam %>&offset=0">««</a>
        </li>

        <!-- ANTERIOR -->
        <li class="pagination__item">
            <a class="pagination__link <%= (paginaAtual == 1 ? "opaco" : "") %>"
               href="?ativa=<%= ativaParam %>&offset=<%= offAnterior %>">«</a>
        </li>
        
        <% if (off == 0) { %>
        <li class="pagination__item">
            <a class="pagination__link opaco"
               href="?ativa=<%= ativaParam %>&offset=0">1</a>
        </li>
        <li class="pagination__item">
            <a class="pagination__link opaco"
               href="?ativa=<%= ativaParam %>&offset=0">2</a>
        </li>
        <% } %>
        
        <% if (off == 5) { %>
        <li class="pagination__item">
            <a class="pagination__link opaco"
               href="?ativa=<%= ativaParam %>&offset=0">2</a>
        </li>
        <% } %>
       
        
        <!-- 2 PÁGINAS ANTERIORES -->
        <%
            for (int i = Math.max(1, paginaAtual - 2); i < paginaAtual; i++) {
                int offCalc = (i - 1) * limit;
        %>
        
            <li class="pagination__item">
                <a class="pagination__link"
                   href="?ativa=<%= ativaParam %>&offset=<%= offCalc %>"><%= i %></a>
            </li>
        <% } %>

        <!-- DROPDOWN CENTRAL -->
        <li class="pagination__item">
            <form method="get" style="display:inline;">
                <input type="hidden" name="ativa" value="<%= ativaParam %>">
                <select name="offset" onchange="this.form.submit()" class="pagination__select">
                    <%
                        for (int i = 1; i <= totalPaginas; i++) {
                            int o = (i - 1) * limit;
                    %>
                        <option value="<%= o %>" <%= (i == paginaAtual ? "selected" : "") %>>
                            Página <%= i %> / <%= totalPaginas %>
                        </option>
                    <% } %>
                </select>
            </form>
        </li>

        <!-- 2 PÁGINAS SEGUINTES -->
        <%
            for (int i = paginaAtual + 1; i <= Math.min(totalPaginas, paginaAtual + 2); i++) {
                int offCalc = (i - 1) * limit;
        %>
            <li class="pagination__item">
                <a class="pagination__link"
                   href="?ativa=<%= ativaParam %>&offset=<%= offCalc %>"><%= i %></a>
            </li>
        <% } %>

        <!-- PRÓXIMA -->
        <li class="pagination__item">
            <a class="pagination__link <%= (paginaAtual == totalPaginas ? "opaco" : "") %>"
               href="?ativa=<%= ativaParam %>&offset=<%= offProxima %>">»</a>
        </li>

        <!-- ÚLTIMA -->
        <li class="pagination__item">
            <a class="pagination__link <%= (paginaAtual == totalPaginas ? "opaco" : "") %>"
               href="?ativa=<%= ativaParam %>&offset=<%= offUltima %>">»»</a>
        </li>

    </ul>
</nav>






                       </div>
                       <!-- Conteúdo da Aba: INATIVAS -->
                       <div class="tab-content content-inativas">
                           <% RenderizadorTarefas.renderizar(tarefasInativas, false, out); %>
                           
    
                       </div>

                   </div>
                       
                </div>
           <% } %>

        
        <%= RenderizarModalDeletarTarefa.renderizar() %>

        <%= RenderizarModalConfig.renderizar(temaAtual) %>

        <script src="./js/index.js"></script>  
        <script src="./js/Utilidades.js"></script>
        <script src="./js/alert.js"></script>

         <% if (configuracao != null && configuracao.equals(1)) { %>
            <script>openModalConfig();</script>
         <% } %>
         
         

    </body>
    
    <% tarefaDAO.fecharConexao(); %>

</html>