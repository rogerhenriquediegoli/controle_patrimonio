package src.model;

import java.time.LocalDateTime;
import src.enums.StatusPatrimonioEnum;
import src.enums.TipoMovimentacaoEnum;

public class MovimentacaoPatrimonio {

    private Long id;
    private Long idPatrimonio;
    private TipoMovimentacaoEnum tipoMovimentacao;
    private StatusPatrimonioEnum statusAnterior;
    private StatusPatrimonioEnum statusAtual;
    private Long idResponsavelAnterior;
    private Long idResponsavelAtual;
    private LocalDateTime dataMovimentacao;
    private String observacao;

    public MovimentacaoPatrimonio() {}

    public MovimentacaoPatrimonio(
            Long id,
            Long idPatrimonio,
            TipoMovimentacaoEnum tipoMovimentacao,
            StatusPatrimonioEnum statusAnterior,
            StatusPatrimonioEnum statusAtual,
            Long idResponsavelAnterior,
            Long idResponsavelAtual,
            LocalDateTime dataMovimentacao,
            String observacao) {

        this.id = id;
        this.idPatrimonio = idPatrimonio;
        this.tipoMovimentacao = tipoMovimentacao;
        this.statusAnterior = statusAnterior;
        this.statusAtual = statusAtual;
        this.idResponsavelAnterior = idResponsavelAnterior;
        this.idResponsavelAtual = idResponsavelAtual;
        this.dataMovimentacao = dataMovimentacao;
        this.observacao = observacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPatrimonio() {
        return idPatrimonio;
    }

    public void setIdPatrimonio(Long idPatrimonio) {
        this.idPatrimonio = idPatrimonio;
    }

    public TipoMovimentacaoEnum getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(TipoMovimentacaoEnum tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public StatusPatrimonioEnum getStatusAnterior() {
        return statusAnterior;
    }

    public void setStatusAnterior(StatusPatrimonioEnum statusAnterior) {
        this.statusAnterior = statusAnterior;
    }

    public StatusPatrimonioEnum getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(StatusPatrimonioEnum statusAtual) {
        this.statusAtual = statusAtual;
    }

    public Long getIdResponsavelAnterior() {
        return idResponsavelAnterior;
    }

    public void setIdResponsavelAnterior(Long idResponsavelAnterior) {
        this.idResponsavelAnterior = idResponsavelAnterior;
    }

    public Long getIdResponsavelAtual() {
        return idResponsavelAtual;
    }

    public void setIdResponsavelAtual(Long idResponsavelAtual) {
        this.idResponsavelAtual = idResponsavelAtual;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}