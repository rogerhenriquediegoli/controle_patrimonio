package src.service;

import java.util.List;

import src.model.Patrimonio;

public interface PatrimonioService {

    List<Patrimonio> findAll();

    Patrimonio findById(Long id);

    void saveOrUpdate(Patrimonio patrimonio);
    
    Long countByIdResponsavel(Long idResponsavel);

    void deleteById(Long id);
}