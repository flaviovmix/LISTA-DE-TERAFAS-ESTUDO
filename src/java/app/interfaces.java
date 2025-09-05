package app;

import app.DetalhesTarefa.detalhesTarefasActions;
import static br.jasap.core.AppManager.url;

public class interfaces {


    public static String taskList(String posicao) throws Exception{

        StringBuilder out = new StringBuilder();
        
            if (posicao.equals("abre")) {
                out.append("  <div class='task-list'>");
                out.append("    <div class='tabs'>");            
            } else {
                out.append("    </div>");
                out.append("  </div>");              
            }
        
        return out.toString();
    }


    public static String head() throws Exception{

        StringBuilder out = new StringBuilder();

            out.append("<!DOCTYPE html>");
            out.append("<html lang='pt-BR'>");

            out.append("<head>");
            out.append("  <meta charset='UTF-8'>");
            out.append("  <title>To-Do List</title>");
            out.append("  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css'>");
            out.append("  <link rel='stylesheet' href='./assets/css/guias.css'>");
            out.append("  <link rel='stylesheet' href='./assets/css/index-claro.css'>");
            out.append("  <link rel='stylesheet' href='./assets/css/modal-claro.css'>");
            out.append("</head>");
        
        return out.toString();
    }
    
    
    public static String header() throws Exception{

        StringBuilder out = new StringBuilder();

            out.append("<body>");
            out.append("  <header>");
            out.append("    <div class='container'>");
            out.append("      <button class='btn-add' onclick=\"window.location.href='")
               .append(         url(detalhesTarefasActions.Listar.class)).append("'\">Nova Tarefa</button>");
            out.append("      <a href='#' class='config-icon' onclick='openModalConfig(); return false;'>");
            out.append("        <i class='fas fa-cog'></i>");
            out.append("      </a>");
            out.append("    </div>");
            out.append("  </header>");
        
        return out.toString();
    }
    
    
    public static String modalDeletar() throws Exception{

        StringBuilder out = new StringBuilder();

            out.append("  <div class='modal-overlay' id='modalDeletar' style='display:none;'>");
            out.append("    <div class='modal'>");
            out.append("      <h2>Confirmar Exclusão</h2>");
            out.append("      <table class='tabela-confirmacao'><tbody>");
            out.append("        <tr><td><i class='fas fa-pen'></i> Título:</td><td id='tituloDeletar'></td></tr>");
            out.append("        <tr><td><i class='fas fa-user'></i> Responsável:</td><td id='tituloResponsavel'></td></tr>");
            out.append("        <tr><td><i class='fas fa-bolt'></i> Prioridade:</td><td id='tituloPrioridade'></td></tr>");
            out.append("        <tr><td><i class='fas fa-thumbtack'></i> Status:</td><td id='tituloStatus'></td></tr>");
            out.append("      </tbody></table>");
            out.append("      <form id='formDeletar' method='post' action='#'>");
            out.append("        <input type='hidden' name='id_tarefa' id='id_tarefa_deletar' value='0'>");
            out.append("        <div class='modal-buttons'>");
            out.append("          <button type='submit' class='btn-deletar-confirmar'>Sim, deletar</button>");
            out.append("          <button type='button' class='btn-cancelar' onclick='closeModalDeletar()'>Cancelar</button>");
            out.append("        </div>");
            out.append("      </form>");
            out.append("    </div>");
            out.append("  </div>");
        
        return out.toString();
    }
    
    
    public static String modalConfig() throws Exception{

        StringBuilder out = new StringBuilder();

            out.append("  <div class='modal-overlay' id='modalConfig' style='display:none;'>");
            out.append("    <div class='modal'>");
            out.append("      <h2>Configurações</h2>");
            out.append("      <table class='tabela-configuracao'><tbody>");
            out.append("        <tr><td><i class='fas fa-adjust'></i> <strong>Tema:</strong></td><td></td></tr>");
            out.append("        <tr><td>");
            out.append("          <form id='formTema' action='#' method='post'>");
            out.append("            <input type='hidden' name='id_configuracao' value='1'>");
            out.append("            <div class='bolinhas-wrapper'>");
            out.append("              <div class='bolinhas-selector'>");
            out.append("                <input type='radio' id='bolinha1' name='tema' value='1' onchange=\"document.getElementById('formTema').submit()\" checked>");
            out.append("                <label for='bolinha1'>Claro</label>");
            out.append("              </div>");
            out.append("              <div class='bolinhas-selector'>");
            out.append("                <input type='radio' id='bolinha2' name='tema' value='2' onchange=\"document.getElementById('formTema').submit()\">");
            out.append("                <label for='bolinha2'>Escuro</label>");
            out.append("              </div>");
            out.append("              <div class='bolinhas-selector'>");
            out.append("                <input type='radio' id='bolinha3' name='tema' value='3' onchange=\"document.getElementById('formTema').submit()\">");
            out.append("                <label for='bolinha3'>Base no Sistema</label>");
            out.append("              </div>");
            out.append("            </div>");
            out.append("          </form>");
            out.append("        </td></tr>");
            out.append("      </tbody></table>");

            out.append("      <table class='tabela-configuracao'><tbody>");
            out.append("        <tr><td><i class='fas fa-adjust'></i> <strong>Nova área:</strong></td><td></td></tr>");
            out.append("        <tr><td><i class='fas fa-thumbtack'></i>vazio</td><td></td></tr>");
            out.append("      </tbody></table>");

            out.append("      <div class='modal-buttons'>");
            out.append("        <button type='button' class='btn-cancelar' onclick='closeModalConfig()'>Fechar</button>");
            out.append("      </div>");
            out.append("    </div>");
            out.append("  </div>");
        
        return out.toString();
    }
    
    
    public static String footerScript() throws Exception{

            StringBuilder out = new StringBuilder();

            out.append("  <script src='./assets/js/index.js'></script>");
            out.append("  <script src='./assets/js/Utilidades.js'></script>");

            out.append("</body>");
            out.append("</html>");

            return out.toString();
    }    
}
