package src.service.impl;

import java.util.List;

import src.model.MovimentacaoPatrimonio;
import src.dao.MovimentacaoPatrimonioDao;
import src.service.MovimentacaoPatrimonioService;

public class MovimentacaoPatrimonioServiceImpl implements MovimentacaoPatrimonioService {

    private final MovimentacaoPatrimonioDao movimentacaoPatrimonioDao;

    public MovimentacaoPatrimonioServiceImpl(MovimentacaoPatrimonioDao movimentacaoPatrimonioDao) {
        this.movimentacaoPatrimonioDao = movimentacaoPatrimonioDao;
    }

    @Override
    public List<MovimentacaoPatrimonio> findAll(){
        return movimentacaoPatrimonioDao.findAll();
    }

    @Override
    public void save(MovimentacaoPatrimonio movimentacao){
        movimentacaoPatrimonioDao.save(movimentacao);
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
        return movimentacaoPatrimonioDao.countByIdPatrimonio(idPatrimonio);
    }

    @Override
    public void deleteByIdPatrimonio(Long idPatrimonio) {
        movimentacaoPatrimonioDao.deleteByIdPatrimonio(idPatrimonio);
    }
}