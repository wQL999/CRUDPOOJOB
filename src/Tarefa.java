import java.time.LocalDate;

public class Tarefa {
    private Long idTarefa = 0l;
    private String nome = "";
    private String descricaoTarefa = "";
    private String status = "";
    private LocalDate dataCriacao = LocalDate.now();
    private LocalDate dataConclusao = LocalDate.now();

    public Tarefa() {
    }

    public Tarefa(Long idTarefa, String nome, String descricaoTarefa, String status, LocalDate dataCriacao, LocalDate dataConclusao) {
        this.idTarefa = idTarefa;
        this.nome = nome;
        this.descricaoTarefa = descricaoTarefa;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
    }

    public Long getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(Long idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nomeTarefa) {
        this.nome = nomeTarefa;
    }

    public String getDescricaoTarefa() {
        return descricaoTarefa;
    }

    public void setDescricaoTarefa(String descricaoTarefa) {
        this.descricaoTarefa = descricaoTarefa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

}
