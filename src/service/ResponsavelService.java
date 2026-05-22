package src.service;

import java.util.List;
import src.model.Responsavel;

public interface ResponsavelService {

    List<Responsavel> findAll();

    Responsavel findById(Long id);

    void saveOrUpdate(Responsavel responsavel);
    
    void deleteById(Long id);
}