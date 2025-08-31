package app;

public class RenderizarModalConfig {

    public static String renderizar(int temaAtual) {
        String checked1 = (temaAtual == 1) ? " checked" : "";
        String checked2 = (temaAtual == 2) ? " checked" : "";
        String checked3 = (temaAtual == 3) ? " checked" : "";

        StringBuilder aux = new StringBuilder();

        aux.append("<div class='modal-overlay' id='modalConfig' style='display:none;'>\n")
           .append("  <div class='modal'>\n")
           .append("    <h2>Configurações</h2>\n")

           .append("    <table class='tabela-configuracao'>\n")
           .append("      <tr>\n")
           .append("        <td><i class='fas fa-adjust'></i> <strong>Tema:</strong></td>\n")
           .append("        <td></td>\n")
           .append("      </tr>\n")
           .append("      <tr>\n")
           .append("        <td>\n")
           .append("          <form id='formTema' action='alterarTema.jsp' method='post'>\n")
           .append("            <input type='hidden' name='id_configuracao' value='1'>\n")
           .append("            <div class='bolinhas-wrapper'>\n")

           .append("              <div class='bolinhas-selector'>\n")
           .append("                <input type='radio' id='bolinha1' name='tema' value='1' onchange=\"document.getElementById('formTema').submit()\"")
           .append(checked1).append(">\n")
           .append("                <label for='bolinha1'>Claro</label>\n")
           .append("              </div>\n")

           .append("              <div class='bolinhas-selector'>\n")
           .append("                <input type='radio' id='bolinha2' name='tema' value='2' onchange=\"document.getElementById('formTema').submit()\"")
           .append(checked2).append(">\n")
           .append("                <label for='bolinha2'>Escuro</label>\n")
           .append("              </div>\n")

           .append("              <div class='bolinhas-selector'>\n")
           .append("                <input type='radio' id='bolinha3' name='tema' value='3' onchange=\"document.getElementById('formTema').submit()\"")
           .append(checked3).append(">\n")
           .append("                <label for='bolinha3'>Base no Sistema</label>\n")
           .append("              </div>\n")

           .append("            </div>\n")
           .append("          </form>\n")
           .append("        </td>\n")
           .append("      </tr>\n")
           .append("    </table>\n")

           .append("    <table class='tabela-configuracao'>\n")
           .append("      <tr>\n")
           .append("        <td><i class='fas fa-adjust'></i> <strong>Nada:</strong></td>\n")
           .append("        <td></td>\n")
           .append("      </tr>\n")
           .append("      <tr>\n")
           .append("        <td><i class='fas fa-thumbtack'></i>vazio</td>\n")
           .append("        <td></td>\n")
           .append("      </tr>\n")
           .append("    </table>\n")

           .append("    <div class='modal-buttons'>\n")
           .append("      <button type='button' class='btn-cancelar' onclick='closeModalConfig()'>Fechar</button>\n")
           .append("    </div>\n")

           .append("  </div>\n")
           .append("</div>\n");

        return aux.toString();
    }
}
