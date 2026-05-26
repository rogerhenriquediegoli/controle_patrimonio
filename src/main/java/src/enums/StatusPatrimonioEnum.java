package src.enums;

public enum StatusPatrimonioEnum {

    NAO_APLICAVEL(0, "-"),
    DISPONIVEL(1, "Disponível"),
    EM_USO(2, "Em Uso"),
    EM_MANUTENCAO(3, "Em Manutenção"),
    EXTRAVIADO(4, "Extraviado"),
    BAIXADO(5, "Baixado");

    private final Integer codigo;
    private final String descricao;

    StatusPatrimonioEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusPatrimonioEnum fromCodigo(Integer codigo) {
        if (codigo == null) {
            return NAO_APLICAVEL;
        }

        for (StatusPatrimonioEnum status : values()) {
            if (status.codigo.equals(codigo)) {
                return status;
            }
        }

        return NAO_APLICAVEL;
    }
}