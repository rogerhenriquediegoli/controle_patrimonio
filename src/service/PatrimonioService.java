package src.service;

import src.model.Patrimonio;

public interface PatrimonioService {

    void saveOrUpdate(Patrimonio patrimonio);
    
    Long countByIdResponsavel(Long idResponsavel);

    void deleteById(Long id);
}