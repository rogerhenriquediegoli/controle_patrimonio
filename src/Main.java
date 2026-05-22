package src;

import javax.swing.JOptionPane;

import src.dao.MovimentacaoPatrimonioDao;
import src.dao.PatrimonioDao;
import src.dao.ResponsavelDao;
import src.dao.impl.MovimentacaoPatrimonioDaoImpl;
import src.dao.impl.PatrimonioDaoImpl;
import src.dao.impl.ResponsavelDaoImpl;
import src.repository.DbSession;
import src.service.MovimentacaoPatrimonioService;
import src.service.PatrimonioService;
import src.service.ResponsavelService;
import src.service.impl.MovimentacaoPatrimonioServiceImpl;
import src.service.impl.PatrimonioServiceImpl;
import src.service.impl.ResponsavelServiceImpl;
import src.views.FormPatrimonio;
import src.views.FormResponsavel;

public class Main {
    private static final ResponsavelDao responsavelDao = new ResponsavelDaoImpl();
    private static final PatrimonioDao patrimonioDao = new PatrimonioDaoImpl();
    private static final MovimentacaoPatrimonioDao movimentacaoPatrimonioDao = new MovimentacaoPatrimonioDaoImpl();
    private static final MovimentacaoPatrimonioService movimentacaoPatrimonioService = new MovimentacaoPatrimonioServiceImpl(movimentacaoPatrimonioDao);
    private static final PatrimonioService patrimonioService = new PatrimonioServiceImpl(patrimonioDao, movimentacaoPatrimonioService);
    private static final ResponsavelService responsavelService = new ResponsavelServiceImpl(responsavelDao, patrimonioService, movimentacaoPatrimonioService);

    public static void main(String[] args) {
        if (Boolean.FALSE.equals(DbSession.initializeDatabaseEnvironment())) {
            final String mensagem = "Não foi possível conectar ao banco de dados." +
                    "\nVerifique se o MySQL está em execução e tente novamente.";

            JOptionPane.showMessageDialog(
                    null,
                    mensagem,
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);

            System.exit(0);
        }


        //new FormResponsavel(responsavelService);
        new FormPatrimonio(responsavelService, patrimonioService);
    }
}