package src.service.impl;

import src.dao.MovimentacaoPatrimonioDao;
import src.service.MovimentacaoPatrimonioService;

public class MovimentacaoPatrimonioServiceImpl implements MovimentacaoPatrimonioService {

    private final MovimentacaoPatrimonioDao movimentacaoPatrimonioDao;

    public MovimentacaoPatrimonioServiceImpl(MovimentacaoPatrimonioDao movimentacaoPatrimonioDao) {
        this.movimentacaoPatrimonioDao = movimentacaoPatrimonioDao;
    }

    @Override
    public Long countByResponsavelAnteriorOrAtual(Long idResponsavel) {
        return movimentacaoPatrimonioDao.countByResponsavelAnteriorOrAtual(idResponsavel);
    }

    @Override
    public void deleteByResponsavelAnteriorOrAtual(Long idResponsavel) {
        movimentacaoPatrimonioDao.deleteByResponsavelAnteriorOrAtual(idResponsavel);
    }

    @Override
    public Long countByIdPatrimonio(Long idPatrimonio) {
        return movimentacaoPatrimonioDao.countByResponsavelAnteriorOrAtual(idPatrimonio);
    }

    @Override
    public void deleteByIdPatrimonio(Long idPatrimonio) {
        movimentacaoPatrimonioDao.deleteByResponsavelAnteriorOrAtual(idPatrimonio);
    }
}