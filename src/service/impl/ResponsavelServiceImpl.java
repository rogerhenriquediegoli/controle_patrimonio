package src.service.impl;

import src.dao.ResponsavelDao;
import src.exception.AppException;
import src.model.Responsavel;
import src.service.MovimentacaoPatrimonioService;
import src.service.PatrimonioService;
import src.service.ResponsavelService;

public class ResponsavelServiceImpl implements ResponsavelService {

    private final ResponsavelDao responsavelDao;
    private final PatrimonioService patrimonioService;
    private final MovimentacaoPatrimonioService movimentacaoPatrimonioService;

    public ResponsavelServiceImpl(ResponsavelDao responsavelDao, PatrimonioService patrimonioService,
            MovimentacaoPatrimonioService movimentacaoPatrimonioService) {
        this.responsavelDao = responsavelDao;
        this.patrimonioService = patrimonioService;
        this.movimentacaoPatrimonioService = movimentacaoPatrimonioService;
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

        if (Boolean.TRUE.equals(throwException)) {
            if (patrimonioService.countByIdResponsavel(responsavel.getId()) > 0) {
                throw new AppException("Este responsável possui ativos vinculados. Exclusão não permitida.");
            }

            if (movimentacaoPatrimonioService.countByResponsavelAnteriorOrAtual(responsavel.getId()) > 0) {
                throw new AppException(
                        "Este responsável possui movimentações vinculadas, que também serão excluídas. Deseja continuar?",
                        true);
            }
        }

        movimentacaoPatrimonioService.deleteByResponsavelAnteriorOrAtual(id);

        responsavelDao.deleteById(id);
    }
}