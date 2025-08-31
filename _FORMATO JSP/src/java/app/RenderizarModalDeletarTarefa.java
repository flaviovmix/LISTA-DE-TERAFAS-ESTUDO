package app;

public class RenderizarModalDeletarTarefa {
    
    public static String renderizar() {
        StringBuilder aux = new StringBuilder();

        aux.append("<div class='modal-overlay' id='modalDeletar' style='display:none;'>")
           .append("<div class='modal'>")
           .append("<h2>Confirmar Exclusão</h2>")
           .append("<table class='tabela-confirmacao'>")
           .append("<tr>")
               .append("<td><i class='fas fa-pen'></i> Título:</td>")
               .append("<td id='tituloDeletar'></td>")
           .append("</tr>")
           .append("<tr>")
               .append("<td><i class='fas fa-user'></i> Responsável:</td>")
               .append("<td id='tituloResponsavel'></td>")
           .append("</tr>")
           .append("<tr>")
               .append("<td><i class='fas fa-bolt'></i> Prioridade:</td>")
               .append("<td id='tituloPrioridade'></td>")
           .append("</tr>")
           .append("<tr>")
               .append("<td><i class='fas fa-thumbtack'></i> Status:</td>")
               .append("<td id='tituloStatus'></td>")
           .append("</tr>")
           .append("</table>")
           .append("<form id='formDeletar' method='post' action='deletarTarefa.jsp'>")
               .append("<input type='hidden' name='id_tarefa' id='id_tarefa_deletar' value='0' />")
               .append("<div class='modal-buttons'>")
                   .append("<button type='submit' class='btn-deletar-confirmar'>Sim, deletar</button>")
                   .append("<button type='button' class='btn-cancelar' onclick='closeModalDeletar()'>Cancelar</button>")
               .append("</div>")
           .append("</form>")
           .append("</div>")
           .append("</div>");

        return aux.toString();
    }
}
