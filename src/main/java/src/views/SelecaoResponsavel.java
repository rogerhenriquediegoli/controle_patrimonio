package src.views;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import src.enums.StatusResponsavelEnum;
import src.model.Responsavel;
import src.utils.JOptionPaneUtils;
import src.service.ResponsavelService;

public class SelecaoResponsavel extends JDialog {

    private JTable tabela;
    private JButton btnSelecionar;
    private DefaultTableModel modelo;
    private Responsavel responsavelSelecionado;

    public SelecaoResponsavel(JFrame parent, ResponsavelService responsavelService) {
        super(parent, "Selecionar Responsável", true);
        setSize(800, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("CPF");
        modelo.addColumn("Setor");
        modelo.addColumn("Cargo");

        tabela = new JTable(modelo);
        tabela.setRowHeight(25);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        btnSelecionar = new JButton("Selecionar");

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotao.add(btnSelecionar);
        add(painelBotao, BorderLayout.SOUTH);

        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);
        tabela.getColumnModel().getColumn(0).setWidth(0);

        List<Responsavel> lista = responsavelService.findAll();
        for (Responsavel r : lista) {
            if (r.getStatus().equals(StatusResponsavelEnum.INATIVO)) {
                continue;
            }

            modelo.addRow(
                    new Object[] {
                            r.getId(),
                            r.getNomeCompleto(),
                            r.getCpf(),
                            r.getSetor(),
                            r.getCargo()
                    });
        }

        btnSelecionar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();

            if (linha == -1) {
                JOptionPaneUtils.showOkDialog("Selecione um responsável.");
                return;
            }

            Responsavel responsavel = new Responsavel();
            responsavel.setId(Long.parseLong(tabela.getValueAt(linha, 0).toString()));
            responsavel.setNomeCompleto(tabela.getValueAt(linha, 1).toString());
            responsavelSelecionado = responsavel;

            dispose();
        });

        tabela.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            btnSelecionar.doClick();
                        }
                    }
                });

        setVisible(true);
    }

    public Responsavel getResponsavelSelecionado() {
        return responsavelSelecionado;
    }
}