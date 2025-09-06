package app.Paginas;

import app.DetalhesTarefa.detalhesTarefasActions;
import static br.jasap.core.AppManager.url;

public class home {
    
    public static String html() throws Exception{
        
        String check_ativa = "";
        String check_inativa = "";

        StringBuilder out = new StringBuilder();

            out.append("<!DOCTYPE html>");
            out.append("<html lang='pt-BR'>");

            out.append("<head>");
            out.append("  <meta charset='UTF-8'>");
            out.append("  <title>To-Do List</title>");
            out.append("  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css'>");
            out.append("  <link rel='stylesheet' href='./assets/css/guias.css'>");
            out.append("  <link rel='stylesheet' href='./assets/css/index-claro.css'>");
            out.append("  <link rel='stylesheet' href='./assets/css/modal-claro.css'>");
            out.append("</head>");

            out.append("<body>");
            out.append("  <header>");
            out.append("    <div class='container'>");
            out.append("      <button class='btn-add' onclick=\"window.location.href='")
               .append(         url(detalhesTarefasActions.Listar.class)).append("'\">Nova Tarefa</button>");
            out.append("      <a href='#' class='config-icon' onclick='openModalConfig(); return false;'>");
            out.append("        <i class='fas fa-cog'></i>");
            out.append("      </a>");
            out.append("    </div>");
            out.append("  </header>");
            
            
            
                out.append("  <div class='task-list'>");
                out.append("    <div class='tabs'>");            
            
                        out.append("<input type='radio' name='tabs' id='tab-inativas'>");
                        out.append("<input type='radio' name='tabs' id='tab-ativas' checked>");

                        out.append("<div class='tabs'>");
                        out.append("  <label class='" + check_ativa + "' onclick=\"window.location.href='")
                           //.append(url(TarefasActions.ListarAtivas.class))
                           .append("'\">Ativas</label>");

                        out.append("  <label class='" + check_inativa + "' onclick=\"window.location.href='")
                           //.append(url(TarefasActions.ListarInativas.class))
                           .append("'\">Inativas</label>");
                        out.append("</div>");

                        out.append("<div class='tab-content content-ativas'>");
                        out.append("  <div class='task-list'>");
                        
                        
           
//MONTAR UMA TAREFA    
//out.append(    implementacao.listarTarefa(ativoOuInativo));

//ATIVA
out.append("          <div class=\"task baixa\">");

out.append("            <div class=\"task-content\">");
out.append("              <div class=\"task-title \">");
out.append("                <a href=\"novaTarefa.html\" class=\"link-sem-estilo\">TAREFA ATIVA</a>");
out.append("              </div>");
out.append("              <div class=\"task-meta\">");
out.append("                <a href=\"novaTarefa.html\" class=\"link-sem-estilo\">");
out.append("                  <span><i class=\"fas fa-layer-group\"></i>0 subtarefas</span>");
out.append("                </a>");
out.append("                <span><i class=\"fas fa-calendar-alt\"></i>14 ago - 2025</span>");
out.append("                <span><i class=\"fas fa-user-alt\"></i>Pedro Henrique</span>");
out.append("              </div>");
out.append("              <span class=\"descricao\">AQUI VAI A DESCRICAO DA TAREFA</span>");
out.append("            </div>");

out.append("            <div class=\"task-actions\">");
out.append("              <div>");
out.append("                <label class=\"checkbox-container\">");
out.append("                  <div class=\"usuario_concluir\">");
out.append("                    <div class=\"assigned\"><strong>Quinta-feira, 14 ago - 2025</strong></div>");
out.append("                    <form action=\"#\" method=\"get\" style=\"display:inline;\">");
out.append("                      <input type=\"hidden\" name=\"estado_atual\" value=\"true\">");
out.append("                      <input type=\"hidden\" name=\"ativa\" value=\"0\">");
out.append("                      <input type=\"hidden\" name=\"id_tarefa\" value=\"118\">");
out.append("                      <input type=\"checkbox\" name=\"ativo\">");
out.append("                    </form>");
out.append("                  </div>");
out.append("                </label>");
out.append("              </div>");
out.append("              <a href=\"#\" class=\"deletar-link\">");
out.append("                <i class=\"fas fa-trash\"></i>");
out.append("              </a>");
out.append("            </div>");

out.append("          </div>");



//INATIVA
out.append("          <div class=\"task baixa\">");

out.append("            <div class=\"task-content\">");
out.append("              <div class=\"task-title opaco\">");
out.append("                <a href=\"novaTarefa.html\" class=\"link-sem-estilo\">TAREFA INATIVA</a>");
out.append("              </div>");
out.append("              <div class=\"task-meta opaco\">");
out.append("                <a href=\"novaTarefa.html\" class=\"link-sem-estilo\">");
out.append("                  <span><i class=\"fas fa-layer-group\"></i>0 subtarefas</span>");
out.append("                </a>");
out.append("                <span><i class=\"fas fa-calendar-alt\"></i>14 ago - 2025</span>");
out.append("                <span><i class=\"fas fa-user-alt\"></i>Pedro Henrique</span>");
out.append("              </div>");
out.append("              <span class=\"descricao opaco\">AQUI VAI A DESCRICAO DA TAREFA</span>");
out.append("            </div>");

out.append("            <div class=\"task-actions\">");
out.append("              <div>");
out.append("                <label class=\"checkbox-container\">");
out.append("                  <div class=\"usuario_concluir\">");
out.append("                    <div class=\"assigned\"><strong>Quinta-feira, 14 ago - 2025</strong></div>");
out.append("                    <form action=\"#\" method=\"get\" style=\"display:inline;\">");
out.append("                      <input type=\"hidden\" name=\"estado_atual\" value=\"true\">");
out.append("                      <input type=\"hidden\" name=\"inativo\" value=\"0\">");
out.append("                      <input type=\"hidden\" name=\"id_tarefa\" value=\"118\">");
out.append("                      <input type=\"checkbox\" name=\"inativo\" checked>");
out.append("                    </form>");
out.append("                  </div>");
out.append("                </label>");
out.append("              </div>");
out.append("              <a href=\"#\" class=\"deletar-link\">");
out.append("                <i class=\"fas fa-trash\"></i>");
out.append("              </a>");
out.append("            </div>");

out.append("          </div>");


                        
                        
                        
                        out.append("  </div>");
                        out.append("</div>");
        
        
                out.append("    </div>");
                out.append("  </div>");              
            
            
            
            
            out.append("  <script src='./assets/js/index.js'></script>");
            out.append("  <script src='./assets/js/Utilidades.js'></script>");

            out.append("</body>");
            out.append("</html>");
        
        return out.toString();
    }
}
