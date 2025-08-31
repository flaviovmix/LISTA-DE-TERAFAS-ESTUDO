package app;

import app.Tarefas.TarefasActions;
import app.Tarefas.TarefasManager;
import app.DetalhesTarefa.detalhesTarefasManager;
import br.jasap.core.JasapRootManager; 
import br.jasap.dao.DataBase;
import br.root.filters.DataBaseFilter;
import br.root.filters.ErrorFilter;

public class RootManager extends JasapRootManager {
    
    @Override
    public void config() throws Exception {
        regAction(TarefasActions.class, "home.jsap");  
        
        new TarefasManager().config();
        new detalhesTarefasManager().config();
                
    }
    
    @Override
    public void sessionConfig() {
    }

    @Override
    public void configGlobalFilters() throws Exception {
        getGlobalFilters().add(new ErrorFilter());
        getGlobalFilters().add(new DataBaseFilter("TAREFAS", DataBase.POSTGRES, "localhost", 5432, "LISTA_TAREFAS", "postgres", "masterkey"));
    }

}
