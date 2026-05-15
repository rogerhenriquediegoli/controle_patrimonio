package src.service.impl;

import src.dao.ResponsavelDao;
import src.model.Responsavel;
import src.service.ResponsavelService;

public class ResponsavelServiceImpl implements ResponsavelService {

    private final ResponsavelDao responsavelDao;

    public ResponsavelServiceImpl(ResponsavelDao responsavelDao) {
        this.responsavelDao = responsavelDao;
    }

    @Override
    public void saveOrUpdate(Responsavel responsavel) {
        if (responsavel.getId() != null) {
            responsavelDao.update(responsavel);
        } else {
            responsavelDao.save(responsavel);
        }
    }
}