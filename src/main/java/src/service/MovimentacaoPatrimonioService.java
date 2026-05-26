package src.service;

import java.util.List;

import src.model.MovimentacaoPatrimonio;

public interface MovimentacaoPatrimonioService {

    List<MovimentacaoPatrimonio> findAll();

    void save(MovimentacaoPatrimonio movimentacao);

    Long countByResponsavelAnteriorOrAtual(Long idResponsavel);

    void deleteByResponsavelAnteriorOrAtual(Long idResponsavel);

    Long countByIdPatrimonio(Long idPatrimonio);

    void deleteByIdPatrimonio(Long idPatrimonio);
}