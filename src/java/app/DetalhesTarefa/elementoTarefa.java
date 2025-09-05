package app.DetalhesTarefa;

public class elementoTarefa {

    public String itemLista() {

        StringBuilder out = new StringBuilder();

        out.append("          <div class='task '>");

        out.append("            <div class='task-content'>");
        out.append("              <div class='task-title '>");
        out.append("                <a href=''</a>");
        out.append("              </div>");
        out.append("              <div class='task-meta '>");
        out.append("                <a href='novaTarefa.html' class='link-sem-estilo'>");
        out.append("                  <span><i class='fas fa-layer-group'></i>0 subtarefas</span>");
        out.append("                </a>");
        out.append("                <span><i class='fas fa-calendar-alt'></i>14 ago - 2025</span>");
        out.append("                <span><i class='fas fa-user-alt'></i></span>");
        out.append("              </div>");
        out.append("              <span class='descricao '></span>");
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

        out.append("          </div>");

        return out.toString();

    }
}
