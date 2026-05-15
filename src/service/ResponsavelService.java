package src.service;

import src.model.Responsavel;

public interface ResponsavelService {

    void saveOrUpdate(Responsavel responsavel);
    
    void deleteById(Long id, Boolean throwException);
}