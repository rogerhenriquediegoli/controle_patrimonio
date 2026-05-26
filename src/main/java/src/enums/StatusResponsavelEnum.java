package src.enums;

public enum StatusResponsavelEnum {

    ATIVO(1, "Ativo"),
    INATIVO(0, "Inativo");

    private final Integer codigo;
    private final String descricao;

    StatusResponsavelEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusResponsavelEnum fromCodigo(Integer codigo) {
        for (StatusResponsavelEnum s : values()) {
            if (s.codigo == codigo) return s;
        }
        return null;
    }
}