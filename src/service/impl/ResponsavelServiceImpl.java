package src.service.impl;

import java.util.List;

import src.dao.ResponsavelDao;
import src.model.Responsavel;
import src.service.MovimentacaoPatrimonioService;
import src.service.PatrimonioService;
import src.service.ResponsavelService;
import src.utils.JOptionPaneUtils;

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
    public List<Responsavel> findAll(){
        return responsavelDao.findAll();
    }
    
    @Override
    public Responsavel findById(Long id){
        return responsavelDao.findById(id);
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
    public void deleteById(Long id) {
        Responsavel responsavel = responsavelDao.findById(id);

        final String mensagem1 = "Este responsável possui ativos vinculados. Exclusão não permitida.";
        if (patrimonioService.countByIdResponsavel(responsavel.getId()) > 0) {
            JOptionPaneUtils.showOkDialog(mensagem1);
            return;
        }

        final String mensagem2 = "Este responsável possui movimentações vinculadas, que também serão excluídas. Deseja continuar?";
        if (movimentacaoPatrimonioService.countByResponsavelAnteriorOrAtual(responsavel.getId()) > 0
                && !JOptionPaneUtils.showConfirmDialog(mensagem2)) {
            return;
        }

        movimentacaoPatrimonioService.deleteByResponsavelAnteriorOrAtual(id);
        responsavelDao.deleteById(id);
    }
}