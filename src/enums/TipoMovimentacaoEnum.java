package src.enums;

public enum TipoMovimentacaoEnum {

    CADASTRO(1, "Cadastro"),
    DISPONIBILIZACAO(2, "Disponibilização"),
    ATRIBUICAO(3, "Atribuição"),
    TRANSFERENCIA(4, "Transferência"),
    MANUTENCAO(5, "Manutenção"),
    RETORNO(6, "Retorno da manutenção"),
    EXTRAVIO(7, "Extravio"),
    BAIXA(8, "Baixa");

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