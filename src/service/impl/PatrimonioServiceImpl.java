package src.service.impl;

import java.util.List;

import src.dao.PatrimonioDao;
import src.enums.StatusPatrimonioEnum;
import src.model.Patrimonio;
import src.model.Responsavel;
import src.service.MovimentacaoPatrimonioService;
import src.service.PatrimonioService;
import src.utils.JOptionPaneUtils;

public class PatrimonioServiceImpl implements PatrimonioService {

    private final PatrimonioDao patrimonioDao;
    private final MovimentacaoPatrimonioService movimentacaoPatrimonioService;

    public PatrimonioServiceImpl(PatrimonioDao patrimonioDao, MovimentacaoPatrimonioService movimentacaoPatrimonioService) {
        this.patrimonioDao = patrimonioDao;
        this.movimentacaoPatrimonioService = movimentacaoPatrimonioService;
    }

    @Override
    public List<Patrimonio> findAll(){
        return patrimonioDao.findAll();
    }

    @Override
    public void saveOrUpdate(Patrimonio patrimonio) {
        
        if (patrimonio.getId() != null) {
            patrimonioDao.update(patrimonio);
        } else {
            patrimonioDao.save(patrimonio);
        }

        JOptionPaneUtils.showOkDialog("Patrimônio salvo com sucesso.");
    }

    @Override
    public Long countByIdResponsavel(Long idResponsavel) {
        return patrimonioDao.countByIdResponsavel(idResponsavel);
    }

    @Override
    public void deleteById(Long id) {
        Patrimonio patrimonio = patrimonioDao.findById(id);

        if (patrimonio.getStatus() == StatusPatrimonioEnum.EM_USO
                || patrimonio.getStatus() == StatusPatrimonioEnum.EM_MANUTENCAO) {
            final String mensagem1 = "Este ativo está em uso ou em manutenção. Exclusão não permitida.";
            JOptionPaneUtils.showOkDialog(mensagem1);
            return;
        }

        if (patrimonio.getStatus() == StatusPatrimonioEnum.EXTRAVIADO) {
            final String mensagem2 = "Este ativo está extraviado. Confirma a exclusão?";
            if (!JOptionPaneUtils.showConfirmDialog(mensagem2)) return;
        }

        if (movimentacaoPatrimonioService.countByIdPatrimonio(patrimonio.getId()) > 0) {
            final String mensagem3 = "Este ativo possui movimentações/logs vinculados, que também serão excluídos. Deseja continuar?";
            if (!JOptionPaneUtils.showConfirmDialog(mensagem3)) return;
            movimentacaoPatrimonioService.deleteByIdPatrimonio(patrimonio.getId());
        }

        patrimonioDao.deleteById(patrimonio.getId());

        JOptionPaneUtils.showOkDialog("Patrimônio excluído com sucesso.");
    }
}