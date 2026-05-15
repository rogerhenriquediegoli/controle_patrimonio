package src.dao;

import java.util.List;

import src.model.Responsavel;

public interface ResponsavelDao {

    List<Responsavel> findAll();

    Responsavel findById(Long id);

    void save(Responsavel responsavel);

    void update(Responsavel responsavel);

    void deleteById(Long id);
}