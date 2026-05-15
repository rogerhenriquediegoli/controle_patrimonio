package src.model;

import src.enums.StatusPatrimonioEnum;

public class Patrimonio {

    private Long id;
    private String descricao;
    private String numeroPatrimonio;
    private StatusPatrimonioEnum status;
    private Long idResponsavel;

    public Patrimonio() {}

    public Patrimonio(
            Long id,
            String descricao,
            String numeroPatrimonio,
            StatusPatrimonioEnum status,
            Long idResponsavel) {
        this.id = id;
        this.descricao = descricao;
        this.numeroPatrimonio = numeroPatrimonio;
        this.status = status;
        this.idResponsavel = idResponsavel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNumeroPatrimonio() {
        return numeroPatrimonio;
    }

    public void setNumeroPatrimonio(String numeroPatrimonio) {
        this.numeroPatrimonio = numeroPatrimonio;
    }

    public StatusPatrimonioEnum getStatus() {
        return status;
    }

    public void setStatus(StatusPatrimonioEnum status) {
        this.status = status;
    }

    public Long getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Long idResponsavel) {
        this.idResponsavel = idResponsavel;
    }
}