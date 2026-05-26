package src.views;

import java.util.List;

import java.time.format.DateTimeFormatter;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableModel;

import src.model.Patrimonio;
import src.model.Responsavel;
import src.service.PatrimonioService;
import src.service.ResponsavelService;
import src.model.MovimentacaoPatrimonio;
import src.service.MovimentacaoPatrimonioService;

public class Dashboard extends JFrame {

        private final PatrimonioService patrimonioService;
        private final ResponsavelService responsavelService;
        private final MovimentacaoPatrimonioService movimentacaoService;

        private JTable tabela;
        private DefaultTableModel modelo;
        private JLabel lblSemMovimentacoes;

        private JButton btnPatrimonios;
        private JButton btnResponsaveis;

        public Dashboard(ResponsavelService responsavelService,
                         PatrimonioService patrimonioService,
                         MovimentacaoPatrimonioService movimentacaoService) {
                this.responsavelService = responsavelService;
                this.patrimonioService = patrimonioService;
                this.movimentacaoService = movimentacaoService;

                setTitle("Sistema de Controle Patrimonial");
                setSize(1700, 850);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLayout(new BorderLayout(10, 10));
                setExtendedState(JFrame.MAXIMIZED_BOTH);

                // =========================
                // CABEÇALHO
                // =========================

                JPanel painelCabecalho = new JPanel(new BorderLayout());

                JLabel lblTitulo = new JLabel(
                                "Dashboard de Movimentações Patrimoniais",
                                SwingConstants.CENTER);

                lblTitulo.setFont(
                                new Font("Arial", Font.BOLD, 26));

                painelCabecalho.add(
                                lblTitulo,
                                BorderLayout.CENTER);

                // =========================
                // AÇÕES
                // =========================

                JPanel painelAcoes = new JPanel(
                                new FlowLayout(FlowLayout.LEFT, 10, 10));

                painelAcoes.setBorder(
                                BorderFactory.createTitledBorder("Ações"));

                btnResponsaveis = new JButton("Gerenciar Responsáveis");

                btnResponsaveis.setPreferredSize(
                                new Dimension(220, 35));

                btnPatrimonios = new JButton("Gerenciar Patrimônios");

                btnPatrimonios.setPreferredSize(
                                new Dimension(220, 35));

                painelAcoes.add(btnResponsaveis);
                painelAcoes.add(btnPatrimonios);

                JPanel painelSuperior = new JPanel(
                                new BorderLayout());

                painelSuperior.add(
                                painelCabecalho,
                                BorderLayout.NORTH);

                painelSuperior.add(
                                painelAcoes,
                                BorderLayout.SOUTH);

                add(
                                painelSuperior,
                                BorderLayout.NORTH);

                // =========================
                // TABELA
                // =========================

                modelo = new DefaultTableModel() {

                        @Override
                        public boolean isCellEditable(
                                        int row,
                                        int column) {

                                return false;
                        }
                };

                modelo.addColumn("Data/Hora");
                modelo.addColumn("Movimentação");
                modelo.addColumn("Patrimônio");
                modelo.addColumn("Status Anterior");
                modelo.addColumn("Status Atual");
                modelo.addColumn("Responsável Anterior");
                modelo.addColumn("Responsável Atual");
                modelo.addColumn("Observação");

                tabela = new JTable(modelo);

                tabela.setRowHeight(28);

                tabela.setSelectionMode(
                                ListSelectionModel.SINGLE_SELECTION);

                JTableHeader header = tabela.getTableHeader();

                header.setFont(
                                new Font("Arial", Font.BOLD, 13));

                tabela.getColumnModel()
                                .getColumn(0)
                                .setPreferredWidth(130);

                tabela.getColumnModel()
                                .getColumn(1)
                                .setPreferredWidth(120);

                tabela.getColumnModel()
                                .getColumn(2)
                                .setPreferredWidth(300);

                tabela.getColumnModel()
                                .getColumn(3)
                                .setPreferredWidth(120);

                tabela.getColumnModel()
                                .getColumn(4)
                                .setPreferredWidth(120);

                tabela.getColumnModel()
                                .getColumn(5)
                                .setPreferredWidth(220);

                tabela.getColumnModel()
                                .getColumn(6)
                                .setPreferredWidth(220);

                tabela.getColumnModel()
                                .getColumn(7)
                                .setPreferredWidth(300);

                JScrollPane scrollPane = new JScrollPane(tabela);

                lblSemMovimentacoes = new JLabel(
                                "Nenhuma movimentação registrada.",
                                SwingConstants.CENTER);

                lblSemMovimentacoes.setFont(
                                new Font("Arial", Font.BOLD, 16));

                lblSemMovimentacoes.setForeground(
                                Color.GRAY);

                JPanel painelTabela = new JPanel(new BorderLayout());

                painelTabela.setBorder(
                                BorderFactory.createTitledBorder(
                                                "Histórico de Movimentações"));

                painelTabela.add(
                                scrollPane,
                                BorderLayout.CENTER);

                painelTabela.add(
                                lblSemMovimentacoes,
                                BorderLayout.SOUTH);

                add(
                                painelTabela,
                                BorderLayout.CENTER);

                // =========================
                // EVENTOS
                // =========================

                btnResponsaveis.addActionListener(e -> {

                        FormResponsavel tela = new FormResponsavel(
                                        responsavelService);

                        tela.setDefaultCloseOperation(
                                        JFrame.DISPOSE_ON_CLOSE);

                        tela.addWindowListener(
                                        new WindowAdapter() {

                                                @Override
                                                public void windowClosed(
                                                                WindowEvent e) {

                                                        carregarTabela();
                                                }
                                        });
                });

                btnPatrimonios.addActionListener(e -> {

                        FormPatrimonio tela = new FormPatrimonio(
                                        responsavelService,
                                        patrimonioService);

                        tela.setDefaultCloseOperation(
                                        JFrame.DISPOSE_ON_CLOSE);

                        tela.addWindowListener(
                                        new WindowAdapter() {

                                                @Override
                                                public void windowClosed(
                                                                WindowEvent e) {

                                                        carregarTabela();
                                                }
                                        });
                });

                carregarTabela();

                setVisible(true);
        }

        private void carregarTabela() {

                modelo.setRowCount(0);

                List<MovimentacaoPatrimonio> lista = movimentacaoService.findAll();

                if (lista.isEmpty()) {

                        lblSemMovimentacoes.setVisible(true);
                        return;
                }

                lblSemMovimentacoes.setVisible(false);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                                "dd/MM/yyyy HH:mm");

                for (MovimentacaoPatrimonio mov : lista) {

                        modelo.addRow(new Object[] {
                                        mov.getDataMovimentacao()
                                                        .format(formatter),

                                        mov.getTipoMovimentacao()
                                                        .getDescricao(),

                                        obterDescricaoPatrimonio(
                                                        mov.getIdPatrimonio()),

                                        mov.getStatusAnterior() != null
                                                        ? mov.getStatusAnterior()
                                                                        .getDescricao()
                                                        : "-",

                                        mov.getStatusAtual() != null
                                                        ? mov.getStatusAtual()
                                                                        .getDescricao()
                                                        : "-",

                                        obterNomeResponsavel(
                                                        mov.getIdResponsavelAnterior()),

                                        obterNomeResponsavel(
                                                        mov.getIdResponsavelAtual()),

                                        mov.getObservacao() != null
                                                        ? mov.getObservacao()
                                                        : "-"
                        });
                }
        }

        private String obterDescricaoPatrimonio(
                        Long idPatrimonio) {

                if (idPatrimonio == null) {
                        return "-";
                }

                Patrimonio patrimonio = patrimonioService.findById(
                                idPatrimonio);

                if (patrimonio == null) {
                        return "-";
                }

                return patrimonio.getDescricao()
                                + " ("
                                + patrimonio.getNumeroPatrimonio()
                                + ")";
        }

        private String obterNomeResponsavel(
                        Long idResponsavel) {

                if (idResponsavel == null) {
                        return "-";
                }

                Responsavel responsavel = responsavelService.findById(
                                idResponsavel);

                return responsavel != null
                                ? responsavel.getNomeCompleto()
                                : "-";
        }
}