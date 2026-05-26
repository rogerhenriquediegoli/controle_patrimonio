package src.service.impl;

import java.util.List;
import java.time.LocalDateTime;

import src.model.Patrimonio;
import src.dao.PatrimonioDao;
import src.model.Responsavel;
import src.utils.JOptionPaneUtils;
import src.service.PatrimonioService;
import src.enums.StatusPatrimonioEnum;
import src.enums.TipoMovimentacaoEnum;
import src.model.MovimentacaoPatrimonio;
import src.service.MovimentacaoPatrimonioService;

public class PatrimonioServiceImpl implements PatrimonioService {

        private final PatrimonioDao patrimonioDao;
        private final MovimentacaoPatrimonioService movimentacaoPatrimonioService;

        public PatrimonioServiceImpl(PatrimonioDao patrimonioDao,
                                     MovimentacaoPatrimonioService movimentacaoPatrimonioService) {
                this.patrimonioDao = patrimonioDao;
                this.movimentacaoPatrimonioService = movimentacaoPatrimonioService;
        }

        @Override
        public List<Patrimonio> findAll() {
                return patrimonioDao.findAll();
        }

        @Override
        public Patrimonio findById(Long id) {
                return patrimonioDao.findById(id);
        }

        @Override
        public void saveOrUpdate(Patrimonio patrimonio) {

                // CADASTRO

                if (patrimonio.getId() == null) {

                        patrimonio.setStatus(
                                        StatusPatrimonioEnum.DISPONIVEL);

                        Long idPatrimonio = patrimonioDao.save(patrimonio);
                        patrimonio.setId(idPatrimonio);

                        MovimentacaoPatrimonio movimentacao = new MovimentacaoPatrimonio();

                        movimentacao.setIdPatrimonio(
                                        patrimonio.getId());

                        movimentacao.setTipoMovimentacao(
                                        TipoMovimentacaoEnum.CADASTRO);

                        movimentacao.setStatusAnterior(
                                        null);

                        movimentacao.setStatusAtual(
                                        patrimonio.getStatus());

                        movimentacao.setIdResponsavelAnterior(
                                        null);

                        movimentacao.setIdResponsavelAtual(
                                        patrimonio.getIdResponsavel());

                        movimentacao.setDataMovimentacao(
                                        LocalDateTime.now());

                        movimentacao.setObservacao(
                                        "Cadastro inicial do patrimônio");

                        movimentacaoPatrimonioService.save(
                                        movimentacao);

                }

                // ALTERAÇÃO

                else {

                        Patrimonio anterior = patrimonioDao.findById(
                                        patrimonio.getId());

                        patrimonioDao.update(
                                        patrimonio);

                        boolean houveAlteracaoStatus = anterior.getStatus() != patrimonio.getStatus();

                        boolean houveAlteracaoResponsavel = !java.util.Objects.equals(
                                        anterior.getIdResponsavel(),
                                        patrimonio.getIdResponsavel());

                        if (houveAlteracaoStatus
                                        || houveAlteracaoResponsavel) {

                                TipoMovimentacaoEnum tipo = determinarTipoMovimentacao(
                                                anterior,
                                                patrimonio);

                                MovimentacaoPatrimonio movimentacao = new MovimentacaoPatrimonio();

                                movimentacao.setIdPatrimonio(
                                                patrimonio.getId());

                                movimentacao.setTipoMovimentacao(
                                                tipo);

                                movimentacao.setStatusAnterior(
                                                anterior.getStatus());

                                movimentacao.setStatusAtual(
                                                patrimonio.getStatus());

                                movimentacao.setIdResponsavelAnterior(
                                                anterior.getIdResponsavel());

                                movimentacao.setIdResponsavelAtual(
                                                patrimonio.getIdResponsavel());

                                movimentacao.setDataMovimentacao(
                                                LocalDateTime.now());

                                movimentacaoPatrimonioService.save(
                                                movimentacao);
                        }
                }

                JOptionPaneUtils.showOkDialog(
                                "Patrimônio salvo com sucesso.");
        }

        private TipoMovimentacaoEnum determinarTipoMovimentacao(
                        Patrimonio anterior,
                        Patrimonio atual) {

                Long responsavelAnterior = anterior.getIdResponsavel();

                Long responsavelAtual = atual.getIdResponsavel();

                StatusPatrimonioEnum statusAnterior = anterior.getStatus();

                StatusPatrimonioEnum statusAtual = atual.getStatus();

                if (statusAtual == StatusPatrimonioEnum.BAIXADO) {
                        return TipoMovimentacaoEnum.BAIXA;
                }

                if (statusAtual == StatusPatrimonioEnum.EXTRAVIADO) {
                        return TipoMovimentacaoEnum.EXTRAVIO;
                }

                if (statusAnterior != StatusPatrimonioEnum.EM_MANUTENCAO
                                && statusAtual == StatusPatrimonioEnum.EM_MANUTENCAO) {
                        return TipoMovimentacaoEnum.MANUTENCAO;
                }

                if (statusAnterior == StatusPatrimonioEnum.EM_MANUTENCAO
                                && statusAtual == StatusPatrimonioEnum.DISPONIVEL) {
                        return TipoMovimentacaoEnum.RETORNO;
                }

                if (responsavelAnterior == null
                                && responsavelAtual != null) {
                        return TipoMovimentacaoEnum.ATRIBUICAO;
                }

                if (responsavelAnterior != null
                                && responsavelAtual != null
                                && !responsavelAnterior.equals(
                                                responsavelAtual)) {
                        return TipoMovimentacaoEnum.TRANSFERENCIA;
                }

                if (responsavelAnterior != null
                                && responsavelAtual == null) {
                        return TipoMovimentacaoEnum.DISPONIBILIZACAO;
                }

                return TipoMovimentacaoEnum.DISPONIBILIZACAO;
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
                        if (!JOptionPaneUtils.showConfirmDialog(mensagem2))
                                return;
                }

                if (movimentacaoPatrimonioService.countByIdPatrimonio(patrimonio.getId()) > 0) {
                        final String mensagem3 = "Este ativo possui movimentações/logs vinculados, que também serão excluídos. Deseja continuar?";
                        if (!JOptionPaneUtils.showConfirmDialog(mensagem3))
                                return;
                        movimentacaoPatrimonioService.deleteByIdPatrimonio(patrimonio.getId());
                }

                patrimonioDao.deleteById(patrimonio.getId());
                JOptionPaneUtils.showOkDialog("Patrimônio excluído com sucesso.");
        }
}