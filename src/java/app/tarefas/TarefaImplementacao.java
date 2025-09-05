package app.Tarefas;

import app.DetalhesTarefa.detalhesTarefasActions;
import br.jasap.core.AppManager;
import static br.jasap.core.AppManager.url;
import java.util.List;

public class TarefaImplementacao {
    
    public AppManager manager;
    
    public TarefaImplementacao(AppManager manager){
        this.manager = manager;
    }
    
    public String listaTarefa(boolean ativoOuInativo) throws Exception{

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
