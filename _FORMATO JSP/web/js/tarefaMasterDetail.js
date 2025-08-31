function selecionarAddSubTarefa() {
    document.getElementById("descricaoDetail").focus();
}

function selecionarAddTarefa() {
    document.getElementById("titulo").focus();
}

const descricaoDetail = document.getElementById("descricaoDetail");

if (descricaoDetail) {
    descricaoDetail.addEventListener("keydown", function (e) {
        if (e.key === "Enter" && !e.shiftKey) {
            e.preventDefault(); // Impede quebra de linha
            document.getElementById("form-subtarefa").submit(); // Envia o formulário
        }
    });
}

function openModalDeletar(id_detalhe, id_tarefa, descricao) {
    document.getElementById("id-detalhe").value = id_detalhe;
    document.getElementById("id-tarefa").value = id_tarefa;
    document.getElementById("texto-descricao").textContent = descricao;
    document.getElementById("modalDeletar").style.display = "flex";
}

function closeModalDeletar() {
    document.getElementById("modalDeletar").style.display = "none";
}
