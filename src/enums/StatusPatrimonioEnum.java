package src.enums;

public enum StatusPatrimonioEnum {

    DISPONIVEL(1, "Disponível"),
    EM_USO(2, "Em uso"),
    EM_MANUTENCAO(3, "Em manutenção"),
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
        for (StatusPatrimonioEnum s : values()) {
            if (s.codigo == codigo) return s;
        }
        throw new IllegalArgumentException("StatusPatrimonio inválido: " + codigo);
    }
}