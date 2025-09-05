package app.Tarefas;

import app.DetalhesTarefa.detalhesTarefasActions;
import app.interfaces;
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

        out.append(interfaces.head()); 
        out.append(interfaces.header()); 

        out.append(interfaces.taskList("abre")); 
        
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

        out.append(interfaces.taskList("fecha")); 
        out.append(interfaces.modalDeletar()); 
        out.append(interfaces.modalConfig());
        out.append(interfaces.footerScript());

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
    
}

