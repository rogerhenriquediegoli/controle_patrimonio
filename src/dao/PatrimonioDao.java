package src.dao;

import java.util.List;
import src.model.Patrimonio;

public interface PatrimonioDao {

    List<Patrimonio> findAll();

    Patrimonio findById(Long id);

    void save(Patrimonio patrimonio);

    void update(Patrimonio patrimonio);

    void deleteById(Long id);
}