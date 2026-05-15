package src.service;

public interface MovimentacaoPatrimonioService {

    Long countByResponsavelAnteriorOrAtual(Long idResponsavel);

    void deleteByResponsavelAnteriorOrAtual(Long idResponsavel);
}