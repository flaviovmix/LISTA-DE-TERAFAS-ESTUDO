package app.DetalhesTarefa;

public class pgDetalhesTarefas {
    public String html() {

        StringBuilder out = new StringBuilder();

        out.append("<!DOCTYPE html>");
        out.append("<html lang='pt-br'>");

        out.append("<head>");
        out.append("  <meta charset='UTF-8'>");
        out.append("  <title>Master-Detail de Tarefas</title>");
        out.append("  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css'>");
        out.append("  <link rel='stylesheet' href='./assets/css/radio.css'>");
        out.append("  <link rel='stylesheet' href='./assets/css/tarefaMasterDetail-claro.css'>");
        out.append("  <link rel='stylesheet' href='./assets/css/modal-claro.css'>");
        out.append("</head>");

        out.append("<body>");
        out.append("  <div class='container'>");

        out.append("    <div class='master'>");
        out.append("      <button type='reset' class='voltar' onclick=\"window.location.href = 'index.html'\">Voltar</button>");
        out.append("      <button class='btn-add' onclick=\"window.location.href='#'\">Nova Tarefa</button>");
        out.append("      <h2>Tarefa</h2>");

        out.append("      <form id='formTarefa' method='get' action='#'>");
        out.append("        <div id='formTarefa'>");
        out.append("          <input type='hidden' name='id_tarefa' id='id_tarefa' value='118'>");
        out.append("          <input type='text' name='titulo' id='titulo' placeholder='Título da tarefa' required>");
        out.append("          <input type='text' name='responsavel' id='responsavel' placeholder='Responsável da tarefa' required>");
        out.append("          <textarea name='descricao' id='descricao' placeholder='Descrição da tarefa'></textarea>");

        out.append("          <div class='area-radio'>");
        out.append("            <div class='radio-group'>");
        out.append("              <span class='texto-prioridade'>Prioridade</span>");
        out.append("              <label class='radio radio--baixa'>");
        out.append("                <input type='radio' name='prioridade' value='baixa' checked>");
        out.append("                <span class='dot'></span><span>Baixa</span>");
        out.append("              </label>");
        out.append("              <label class='radio radio--media'>");
        out.append("                <input type='radio' name='prioridade' value='media'>");
        out.append("                <span class='dot'></span><span>Média</span>");
        out.append("              </label>");
        out.append("              <label class='radio radio--alta'>");
        out.append("                <input type='radio' name='prioridade' value='alta'>");
        out.append("                <span class='dot'></span><span>Alta</span>");
        out.append("              </label>");
        out.append("            </div>");
        out.append("          </div>");

        out.append("          <label for='data_conclusao' class='label-data-prevista'>Data prevista para conclusão</label>");
        out.append("          <input type='date' name='data_conclusao' id='data_conclusao'>");
        out.append("        </div>");

        out.append("        <div class='botoes'>");
        out.append("          <button id='btn-editar' type='reset' class='editar' onclick=\"link('#')\">Editar</button>");
        out.append("        </div>");
        out.append("      </form>");

        out.append("      <hr>");
        out.append("      <h2>Detalhes da Tarefa</h2>");

        out.append("      <form id='form-subtarefa' class='form' action='#' method='post'>");
        out.append("        <div>");
        out.append("          <input type='hidden' name='fk_tarefa' id='fk_tarefa' value='118'>");
        out.append("          <div class='campo'>");
        out.append("            <textarea name='descricao' id='descricaoDetail' placeholder='Digite a descrição...' required></textarea>");
        out.append("          </div>");
        out.append("          <button type='submit' class='salvar'>Adicionar subtarefa</button>");
        out.append("        </div>");
        out.append("      </form>");
        out.append("    </div>");

        out.append("    <div id='area-detail' class='detail'>");
        out.append("      <h3>Lista de detalhes</h3>");
        out.append("      <ul id='lista-tarefas'>");
        out.append("        <li>");
        out.append("          <div>");
        out.append("            <small>15 ago - 2025</small><br>");
        out.append("            <small>RESOLVER PENDÊNCIA 1</small>");
        out.append("          </div>");
        out.append("          <a class='icone-lixeira' href='#' onclick=\"openModalDeletar(247, 118, 'RESOLVER PENDENCIA 1'); return false;\">");
        out.append("            <i class='fas fa-trash'></i>");
        out.append("          </a>");
        out.append("        </li>");
        out.append("      </ul>");
        out.append("    </div>");
        out.append("  </div>");

        out.append("  <div class='modal-overlay' id='modalDeletar' style='display:none;'>");
        out.append("    <div class='modal'>");
        out.append("      <h2>Confirmar Exclusão</h2>");
        out.append("      <div class='confirmacao'>");
        out.append("        <p id='texto-descricao' class='area-info'></p>");
        out.append("        <form id='formDeletar' method='post' action='#'>");
        out.append("          <input type='hidden' name='id-detalhe' id='id-detalhe' value='0'>");
        out.append("          <input type='hidden' name='id-tarefa' id='id-tarefa' value='0'>");
        out.append("          <div class='modal-buttons'>");
        out.append("            <button type='submit' class='btn-deletar-confirmar' onclick='confirmarDelete()'>Sim, deletar</button>");
        out.append("            <button type='button' class='btn-cancelar' onclick='closeModalDeletar()'>Cancelar</button>");
        out.append("          </div>");
        out.append("        </form>");
        out.append("      </div>");
        out.append("    </div>");
        out.append("  </div>");

        out.append("  <script src='./assets/js/tarefaMasterDetail.js'></script>");
        out.append("  <script src='./assets/js/Utilidades.js'></script>");
        out.append("  <script>selecionarAddSubTarefa()</script>");

        out.append("</body>");
        out.append("</html>");

        return out.toString();

        
    }
}
