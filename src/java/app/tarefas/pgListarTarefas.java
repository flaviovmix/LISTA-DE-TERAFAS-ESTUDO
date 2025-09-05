package app.Tarefas;

import br.jasap.core.AppManager;

public class pgListarTarefas {
    
    AppManager manager;
    
    public pgListarTarefas(AppManager manager){
        this.manager = manager;
    }
    
    public pgListarTarefas(){
    }

    public String html(boolean ativoOuInativo) throws Exception {
        
        TarefaImplementacao implementacao = new TarefaImplementacao(manager);
        
        StringBuilder out = new StringBuilder();

        out.append(interfaces.head()); 
        out.append(interfaces.header()); 
        
        out.append(implementacao.listaDetarefas(ativoOuInativo)); 
                
        out.append(interfaces.modalDeletar()); 
        out.append(interfaces.modalConfig());
        out.append(interfaces.footerScript());

       return out.toString();
    }   
}