package src.enums;

public enum SexoEnum {

    MASCULINO('M', "Masculino"),
    FEMININO('F', "Feminino"),
    OUTRO('O', "Outro"),
    NAO_INFORMADO('N', "Não informado");

    private final Character codigo;
    private final String descricao;

    SexoEnum(Character codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Character getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static SexoEnum fromCodigo(Character codigo) {
        for (SexoEnum s : values()) {
            if (s.codigo == codigo) return s;
        }
        throw new IllegalArgumentException("Sexo inválido: " + codigo);
    }
}