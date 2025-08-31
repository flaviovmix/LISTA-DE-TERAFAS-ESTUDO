package app.Tarefas;

import br.jasap.core.JasapRootManager;

public class TarefasManager extends JasapRootManager {
    @Override
    public void configGlobalFilters() throws Exception {
    }

    @Override
    public void config() throws Exception {
        
        regAction(TarefasActions.class);
        
        regAction(TarefasActions.ListarAtivas.class);
        regAction(TarefasActions.ListarInativas.class);
    
    }
}
