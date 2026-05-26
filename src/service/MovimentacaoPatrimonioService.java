package src.service;

import java.util.List;

import src.model.MovimentacaoPatrimonio;

public interface MovimentacaoPatrimonioService {

    List<MovimentacaoPatrimonio> findAll();

    Long countByResponsavelAnteriorOrAtual(Long idResponsavel);

    void deleteByResponsavelAnteriorOrAtual(Long idResponsavel);

    Long countByIdPatrimonio(Long idPatrimonio);

    void deleteByIdPatrimonio(Long idPatrimonio);

    void save(MovimentacaoPatrimonio movimentacao);
}