import java.time.LocalDate;

public class Projeto {

    private long id = 0;
    private String nome = "";
    private String descricao = "";
    private LocalDate dataInit = LocalDate.now();
    private LocalDate dataTermino = LocalDate.now();

    public Projeto() { 
    }

    public Projeto(
        long id,
     String nome,
     String descricao,
     LocalDate dataInit,
     LocalDate dataTermino
    ) { 
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInit = dataInit;
        this.dataTermino = dataTermino;        
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInit() {
        return dataInit;
    }

    public void setDataInit(LocalDate dataInit) {
        this.dataInit = dataInit;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }


}