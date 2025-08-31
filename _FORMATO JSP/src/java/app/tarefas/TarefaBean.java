package app.tarefas;

import java.sql.Date;

public class TarefaBean {
    
    private int id_tarefa;
    private String titulo;
    private String descricao;
    private String status;
    private String prioridade;
    private String responsavel;
    private Date data_criacao;
    private Date data_conclusao;
    
    // Atributo que armazena a quantidade de subtarefas associadas a esta tarefa.
    // Pode ser nulo, por isso foi usado o tipo wrapper (Revestimento) Integer (em vez de int).
    private Integer quantidade_de_subtarefas;

    public int getId_tarefa() {
        return id_tarefa;
    }
    public void setId_tarefa(int id_tarefa) {
        this.id_tarefa = id_tarefa;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrioridade() {
        return prioridade;
    }
    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }
    
    public boolean isPrioridadeBaixa(){
        return ("baixa".equals(getPrioridade()));
    }
    public boolean isPrioridadeMedia(){
        return ("media".equals(getPrioridade()));
    }
    public boolean isPrioridadeAlta(){
        return ("alta".equals(getPrioridade()));
    }

    public String getResponsavel() {
        return responsavel;
    }
    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
    
    public Date getData_criacao() {
        return data_criacao;
    }
    public void setData_criacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }

    public Date getData_conclusao() {
        return data_conclusao;
    }
    public void setData_conclusao(Date data_conclusao) {
        this.data_conclusao = data_conclusao;
    }
   
    // Retorna a quantidade de subtarefas associadas a esta tarefa.
    public Integer getQuantidade_de_subtarefas() {
        return quantidade_de_subtarefas;
    }

    // Define a quantidade de subtarefas associadas a esta tarefa.
    public void setQuantidade_de_subtarefas(Integer valor) {
        this.quantidade_de_subtarefas = valor;
    }
}
