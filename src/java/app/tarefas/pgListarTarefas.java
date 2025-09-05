package app.Tarefas;

import app.interfaces;
import br.jasap.core.AppManager;
import static br.jasap.core.AppManager.url;

public class pgListarTarefas {
    
    AppManager manager;
    
    public pgListarTarefas(AppManager manager){
        this.manager = manager;
    }
    
    public pgListarTarefas(){
    }

    public String html(boolean ativoOuInativo) throws Exception {
        
        
        TarefaImplementacao implementacao = new TarefaImplementacao(manager);
        
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
            out.append(    implementacao.listaTarefa(ativoOuInativo));
            out.append("  </div>");
            out.append("</div>");

        out.append(interfaces.taskList("fecha")); 
        out.append(interfaces.modalDeletar()); 
        out.append(interfaces.modalConfig());
        out.append(interfaces.footerScript());

       return out.toString();
    }
    
    
    
}

