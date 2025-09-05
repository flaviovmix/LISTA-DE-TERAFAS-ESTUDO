package app.DetalhesTarefa;

import br.jasap.core.Effect;
import br.jasap.core.JasapAct;
import br.jasap.effect.Response;

public class detalhesTarefasActions extends JasapAct{

    @Override
    public Effect execute() throws Exception {
            return new Response();
    }

    public static class Listar extends detalhesTarefasActions{
        @Override
        public Effect execute() throws Exception {      
            
            pgDetalhesTarefas listaTabela = new pgDetalhesTarefas();
            getOutput().write(listaTabela.html());
            
            return new Response();
        }
    }
    
    public static class testeListar extends detalhesTarefasActions{
        @Override
        public Effect execute() throws Exception {      
            
            pgDetalhesTarefas listaTabela = new pgDetalhesTarefas();
            getOutput().write(listaTabela.html());
            
            return new Response();
        }
    }
    
}
