package src.dao;

import java.util.List;

import src.model.MovimentacaoPatrimonio;

public interface MovimentacaoPatrimonioDao {

    List<MovimentacaoPatrimonio> findAll();

    MovimentacaoPatrimonio findById(Long id);

    void save(MovimentacaoPatrimonio movimentacao);

    void update(MovimentacaoPatrimonio movimentacao);

    void deleteById(Long id);
    
    Long countByResponsavelAnteriorOrAtual(Long idResponsavel);

    void deleteByResponsavelAnteriorOrAtual(Long idResponsavel);
    
    Long countByIdPatrimonio(Long idPatrimonio);

    void deleteByIdPatrimonio(Long idPatrimonio);
}