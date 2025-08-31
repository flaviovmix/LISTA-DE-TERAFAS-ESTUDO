package app.DetalhesTarefa;

import br.jasap.core.JasapRootManager;

public class detalhesTarefasManager extends JasapRootManager {
    @Override
    public void configGlobalFilters() throws Exception {
    }

    @Override
    public void config() throws Exception {
        
        regAction(detalhesTarefasActions.class);
        regAction(detalhesTarefasActions.Listar.class);
    
    }
}
