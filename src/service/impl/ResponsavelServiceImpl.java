package src.service.impl;

import src.dao.ResponsavelDao;
import src.exception.AppException;
import src.model.Responsavel;
import src.service.PatrimonioService;
import src.service.ResponsavelService;

public class ResponsavelServiceImpl implements ResponsavelService {

    private final ResponsavelDao responsavelDao;
    private final PatrimonioService patrimonioService;

    public ResponsavelServiceImpl(ResponsavelDao responsavelDao, PatrimonioService patrimonioService) {
        this.responsavelDao = responsavelDao;
        this.patrimonioService = patrimonioService;
    }

    @Override
    public void saveOrUpdate(Responsavel responsavel) {
        if (responsavel.getId() != null) {
            responsavelDao.update(responsavel);
        } else {
            responsavelDao.save(responsavel);
        }
    }

    @Override
    public void deleteById(Long id, Boolean throwException) {
        Responsavel responsavel = responsavelDao.findById(id);

        if(Boolean.TRUE.equals(throwException)){
            if (patrimonioService.countByIdResponsavel(responsavel.getId()) > 0) {
                throw new AppException("Este responsável possui ativos vinculados. Exclusão não permitida.");
            }
        }
    }
}