package src.enums;

public enum TipoMovimentacaoEnum {

    CADASTRO(1, "Cadastro"),
    ATRIBUICAO(2, "Atribuição"),
    TRANSFERENCIA(3, "Transferência"),
    MANUTENCAO(4, "Manutenção"),
    RETORNO(5, "Retorno da manutenção"),
    EXTRAVIO(6, "Extravio"),
    BAIXA(7, "Baixa");

    private final Integer codigo;
    private final String descricao;

    TipoMovimentacaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoMovimentacaoEnum fromCodigo(Integer codigo) {
        for (TipoMovimentacaoEnum t : values()) {
            if (t.codigo == codigo) return t;
        }
        throw new IllegalArgumentException("TipoMovimentacao inválido: " + codigo);
    }
}