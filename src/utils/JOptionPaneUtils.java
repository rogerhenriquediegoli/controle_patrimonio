package src.utils;

import javax.swing.JOptionPane;

public class JOptionPaneUtils {

    public static boolean showConfirmDialog(String mensagem) {
        int opcao = JOptionPane.showConfirmDialog(
                null,
                mensagem,
                "Confirmação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        return opcao == JOptionPane.YES_OPTION;
    }

    public static void showOkDialog(String mensagem) {
        JOptionPane.showMessageDialog(
                null,
                mensagem,
                "Aviso",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}