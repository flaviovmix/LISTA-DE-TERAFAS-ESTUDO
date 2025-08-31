  let tempo = 9;
  let timer;
  const alertBox = document.getElementById('alertBox');
  const countdownEl = document.getElementById('countdown');

  function mostrarAlerta() {
    tempo = 9;
    countdownEl.textContent = tempo;
    alertBox.style.display = 'flex';
    setTimeout(() => {
      alertBox.classList.add('show');
    }, 100);

    timer = setInterval(() => {
      tempo--;
      countdownEl.textContent = tempo;
      if (tempo <= 0) {
        clearInterval(timer);
        confirmarAcao();
      }
    }, 1000);
  }

  function confirmarAcao() {
    clearInterval(timer);
    fecharAlerta();
  }

  function cancelarAcao() {
    clearInterval(timer);
    fecharAlerta();
  }

  function fecharAlerta() {
    alertBox.classList.remove('show');
    setTimeout(() => {
      alertBox.style.display = 'none';
    }, 800);
  }

  // Simulação
  mostrarAlerta();