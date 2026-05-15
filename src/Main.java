package src;

import javax.swing.JOptionPane;

import src.repository.DbSession;

public class Main {
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
    }
}