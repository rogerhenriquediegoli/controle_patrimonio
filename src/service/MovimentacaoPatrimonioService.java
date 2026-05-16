package src.service;

public interface MovimentacaoPatrimonioService {

    Long countByResponsavelAnteriorOrAtual(Long idResponsavel);

    void deleteByResponsavelAnteriorOrAtual(Long idResponsavel);

    Long countByIdPatrimonio(Long idPatrimonio);

    void deleteByIdPatrimonio(Long idPatrimonio);
}