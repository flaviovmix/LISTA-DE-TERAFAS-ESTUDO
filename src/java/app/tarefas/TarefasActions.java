package app.Tarefas;

import br.jasap.core.Effect;
import br.jasap.core.JasapAct;
import br.jasap.effect.Response;

public class TarefasActions extends JasapAct{

    @Override
    public Effect execute() throws Exception {
            pgListarTarefas listaTarefa = new pgListarTarefas(getManager());
            getOutput().write(listaTarefa.html(true));
            return new Response();
    }

    public static class ListarAtivas extends TarefasActions{
        @Override
          public Effect execute() throws Exception {
            pgListarTarefas listaTarefa = new pgListarTarefas(getManager());
            getOutput().write(listaTarefa.html(true));
            return new Response();
          }  
    }
    
    public static class ListarInativas extends TarefasActions{
    @Override
      public Effect execute() throws Exception {
        pgListarTarefas listaTarefa = new pgListarTarefas(getManager());
        getOutput().write(listaTarefa.html(false));
        return new Response();
      }
    }
    
    
}
