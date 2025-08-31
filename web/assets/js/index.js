function closeModalDeletar() {
    document.getElementById("modalDeletar").style.display = "none";
}

function openModalDeletar(id, titulo, responsavel, prioridade, status) {
    document.getElementById("id_tarefa_deletar").value = id;
    document.getElementById("tituloDeletar").innerHTML = `<strong>${titulo}</strong>`;
    document.getElementById("tituloResponsavel").textContent = responsavel;
    document.getElementById("tituloPrioridade").textContent = prioridade;
    document.getElementById("tituloStatus").textContent = status;
    
    document.getElementById("modalDeletar").style.display = "flex";
}

function openModalConfig() {
    document.getElementById("modalConfig").style.display = "flex";
}

function closeModalConfig() {
    document.getElementById("modalConfig").style.display = "none";
    self.location.href="index.jsp";
}