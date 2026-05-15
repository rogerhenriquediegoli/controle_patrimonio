package src.service.impl;

import src.dao.PatrimonioDao;
import src.model.Patrimonio;
import src.service.PatrimonioService;

public class PatrimonioServiceImpl implements PatrimonioService {

    private final PatrimonioDao patrimonioDao;

    public PatrimonioServiceImpl(PatrimonioDao patrimonioDao) {
        this.patrimonioDao = patrimonioDao;
    }

    @Override
    public void saveOrUpdate(Patrimonio patrimonio) {
        if (patrimonio.getId() != null) {
            patrimonioDao.update(patrimonio);
        } else {
            patrimonioDao.save(patrimonio);
        }
    }
}