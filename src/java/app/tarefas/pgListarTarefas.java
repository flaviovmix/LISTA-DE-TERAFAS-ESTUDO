package app.Tarefas;

import app.DetalhesTarefa.detalhesTarefasActions;
import br.jasap.core.AppManager;
import static br.jasap.core.AppManager.url;
import java.util.List;

public class pgListarTarefas {
    
    public AppManager manager;
    
    public pgListarTarefas(){
    }
    
    public pgListarTarefas(AppManager manager){
        this.manager = manager;
    }

    public String html(boolean ativoOuInativo) throws Exception {
        
        String check_ativa = (ativoOuInativo ? " check" : "");
        String check_inativa = (!ativoOuInativo ? " check" : "");
        
        StringBuilder out = new StringBuilder();

        out.append(head()); 
        out.append(header()); 

        out.append(taskList("abre")); 
        
            out.append("<input type='radio' name='tabs' id='tab-inativas'>");
            out.append("<input type='radio' name='tabs' id='tab-ativas' checked>");

            out.append("<div class='tabs'>");
            out.append("  <label class='" + check_ativa + "' onclick=\"window.location.href='")
               .append(url(TarefasActions.ListarAtivas.class))
               .append("'\">Ativas</label>");

            out.append("  <label class='" + check_inativa + "' onclick=\"window.location.href='")
               .append(url(TarefasActions.ListarInativas.class))
               .append("'\">Inativas</label>");
            out.append("</div>");

            out.append("<div class='tab-content content-ativas'>");
            out.append("  <div class='task-list'>");
            out.append(    itemLista(ativoOuInativo));
            out.append("  </div>");
            out.append("</div>");

        out.append(taskList("fecha")); 
        out.append(modalDeletar()); 
        out.append(modalConfig());
        out.append(footerScript());

       return out.toString();
    }
    
    public String itemLista(boolean ativoOuInativo) throws Exception{

        TarefaDAO dao = new TarefaDAO(manager);

        StringBuilder out = new StringBuilder();

        List<TarefaBean> lista = dao.listarTarefas(ativoOuInativo);
        String opaco = "task-title" + (!ativoOuInativo ? " opaco" : "");

        for (TarefaBean tarefa : lista){

           out.append("          <div class='task "+tarefa.getPrioridade()+"'>");

           out.append("            <div class='task-content'>");
           out.append("              <div class='task-title "+ opaco +"'>");
           out.append("                <a href='" + url(detalhesTarefasActions.Listar.class) + "' class='link-sem-estilo'>"+tarefa.getTitulo()+"</a>");
           out.append("              </div>");
           out.append("              <div class='task-meta " + opaco + "'>");
           out.append("                <a href='novaTarefa.html' class='link-sem-estilo'>");
           out.append("                  <span><i class='fas fa-layer-group'></i>0 subtarefas</span>");
           out.append("                </a>");
           out.append("                <span><i class='fas fa-calendar-alt'></i>14 ago - 2025</span>");
           out.append("                <span><i class='fas fa-user-alt'></i>"+tarefa.getResponsavel()+"</span>");
           out.append("              </div>");
           out.append("              <span class='descricao " + opaco + "'>"+tarefa.getDescricao()+"</span>");
           out.append("            </div>");

           out.append("            <div class='task-actions'>");
           out.append("              <div>");
           out.append("                <label class='checkbox-container'>");
           out.append("                  <div class='usuario_concluir'>");
           out.append("                    <div class='assigned'><strong>Quinta-feira, 14 ago - 2025</strong></div>");
           out.append("                    <form action='#' method='get' style='display:inline;'>");
           out.append("                      <input type='hidden' name='estado_atual' value='true'>");
           out.append("                      <input type='hidden' name='ativa' value='0'>");
           out.append("                      <input type='hidden' name='id_tarefa' value='118'>");
           out.append("                      <input type='checkbox' name='ativo'>");
           out.append("                    </form>");
           out.append("                  </div>");
           out.append("                </label>");
           out.append("              </div>");
           out.append("              <a href='#' class='deletar-link'>");
           out.append("                <i class='fas fa-trash'></i>");
           out.append("              </a>");
           out.append("            </div>");

           out.append ("          </div>");

        }

        return out.toString();
     }






    public String taskList(String posicao) throws Exception{

        StringBuilder out = new StringBuilder();
        
            if (posicao.equals("abre")) {
                out.append("  <div class='task-list'>");
                out.append("    <div class='tabs'>");            
            } else {
                out.append("    </div>");
                out.append("  </div>");              
            }
        
        return out.toString();
    }




    
    public String head() throws Exception{

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
        
        return out.toString();
     }
    
    public String header() throws Exception{

        StringBuilder out = new StringBuilder();

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
        
        return out.toString();
     }
    
    public String modalDeletar() throws Exception{

        StringBuilder out = new StringBuilder();

            out.append("  <div class='modal-overlay' id='modalDeletar' style='display:none;'>");
            out.append("    <div class='modal'>");
            out.append("      <h2>Confirmar Exclusão</h2>");
            out.append("      <table class='tabela-confirmacao'><tbody>");
            out.append("        <tr><td><i class='fas fa-pen'></i> Título:</td><td id='tituloDeletar'></td></tr>");
            out.append("        <tr><td><i class='fas fa-user'></i> Responsável:</td><td id='tituloResponsavel'></td></tr>");
            out.append("        <tr><td><i class='fas fa-bolt'></i> Prioridade:</td><td id='tituloPrioridade'></td></tr>");
            out.append("        <tr><td><i class='fas fa-thumbtack'></i> Status:</td><td id='tituloStatus'></td></tr>");
            out.append("      </tbody></table>");
            out.append("      <form id='formDeletar' method='post' action='#'>");
            out.append("        <input type='hidden' name='id_tarefa' id='id_tarefa_deletar' value='0'>");
            out.append("        <div class='modal-buttons'>");
            out.append("          <button type='submit' class='btn-deletar-confirmar'>Sim, deletar</button>");
            out.append("          <button type='button' class='btn-cancelar' onclick='closeModalDeletar()'>Cancelar</button>");
            out.append("        </div>");
            out.append("      </form>");
            out.append("    </div>");
            out.append("  </div>");
        
        return out.toString();
     }
    
    public String modalConfig() throws Exception{

        StringBuilder out = new StringBuilder();

            out.append("  <div class='modal-overlay' id='modalConfig' style='display:none;'>");
            out.append("    <div class='modal'>");
            out.append("      <h2>Configurações</h2>");
            out.append("      <table class='tabela-configuracao'><tbody>");
            out.append("        <tr><td><i class='fas fa-adjust'></i> <strong>Tema:</strong></td><td></td></tr>");
            out.append("        <tr><td>");
            out.append("          <form id='formTema' action='#' method='post'>");
            out.append("            <input type='hidden' name='id_configuracao' value='1'>");
            out.append("            <div class='bolinhas-wrapper'>");
            out.append("              <div class='bolinhas-selector'>");
            out.append("                <input type='radio' id='bolinha1' name='tema' value='1' onchange=\"document.getElementById('formTema').submit()\" checked>");
            out.append("                <label for='bolinha1'>Claro</label>");
            out.append("              </div>");
            out.append("              <div class='bolinhas-selector'>");
            out.append("                <input type='radio' id='bolinha2' name='tema' value='2' onchange=\"document.getElementById('formTema').submit()\">");
            out.append("                <label for='bolinha2'>Escuro</label>");
            out.append("              </div>");
            out.append("              <div class='bolinhas-selector'>");
            out.append("                <input type='radio' id='bolinha3' name='tema' value='3' onchange=\"document.getElementById('formTema').submit()\">");
            out.append("                <label for='bolinha3'>Base no Sistema</label>");
            out.append("              </div>");
            out.append("            </div>");
            out.append("          </form>");
            out.append("        </td></tr>");
            out.append("      </tbody></table>");

            out.append("      <table class='tabela-configuracao'><tbody>");
            out.append("        <tr><td><i class='fas fa-adjust'></i> <strong>Nova área:</strong></td><td></td></tr>");
            out.append("        <tr><td><i class='fas fa-thumbtack'></i>vazio</td><td></td></tr>");
            out.append("      </tbody></table>");

            out.append("      <div class='modal-buttons'>");
            out.append("        <button type='button' class='btn-cancelar' onclick='closeModalConfig()'>Fechar</button>");
            out.append("      </div>");
            out.append("    </div>");
            out.append("  </div>");
        
        return out.toString();
     }
    
    public String footerScript() throws Exception{

            StringBuilder out = new StringBuilder();

            out.append("  <script src='./assets/js/index.js'></script>");
            out.append("  <script src='./assets/js/Utilidades.js'></script>");

            out.append("</body>");
            out.append("</html>");

            return out.toString();
    }
}

