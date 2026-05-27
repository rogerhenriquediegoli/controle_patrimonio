package src;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import src.views.Dashboard;
import src.repository.DbSession;
import src.service.PatrimonioService;
import src.service.ResponsavelService;
import src.dao.impl.PatrimonioDaoImpl;
import src.dao.impl.ResponsavelDaoImpl;
import src.service.impl.PatrimonioServiceImpl;
import src.service.impl.ResponsavelServiceImpl;
import src.service.MovimentacaoPatrimonioService;
import src.dao.impl.MovimentacaoPatrimonioDaoImpl;
import src.service.impl.MovimentacaoPatrimonioServiceImpl;

public class Main {
    
    private static final MovimentacaoPatrimonioService movimentacaoPatrimonioService = new MovimentacaoPatrimonioServiceImpl(new MovimentacaoPatrimonioDaoImpl());
    private static final PatrimonioService patrimonioService = new PatrimonioServiceImpl(new PatrimonioDaoImpl(), movimentacaoPatrimonioService);
    private static final ResponsavelService responsavelService = new ResponsavelServiceImpl(new ResponsavelDaoImpl(), patrimonioService, movimentacaoPatrimonioService);

    public static void main(String[] args) {
        if (Boolean.FALSE.equals(DbSession.initializeDatabaseEnvironment())) {
            final String mensagem = "Não foi possível conectar ao banco de dados." +
                    "\nVerifique se o MySQL está em execução e tente novamente.";

            JOptionPane.showMessageDialog(
                    null,
                    mensagem,
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );

            System.exit(0);
        }

        new Dashboard(responsavelService, patrimonioService, movimentacaoPatrimonioService);
    }
}