package src.dao;

import java.util.List;

import src.model.Patrimonio;

public interface PatrimonioDao {

    List<Patrimonio> findAll();

    Patrimonio findById(Long id);

    Long save(Patrimonio patrimonio);

    void update(Patrimonio patrimonio);

    void deleteById(Long id);
    
    Long countByIdResponsavel(Long idResponsavel);
}