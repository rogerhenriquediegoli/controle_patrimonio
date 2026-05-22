package src.views;

import src.enums.StatusPatrimonioEnum;
import src.model.Patrimonio;
import src.model.Responsavel;
import src.service.PatrimonioService;
import src.service.ResponsavelService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FormPatrimonio extends JFrame {

    private final PatrimonioService patrimonioService;
    private final ResponsavelService responsavelService;

    private JTextField txtDescricao;
    private JTextField txtNumeroPatrimonio;

    private JLabel lblStatusAtual;
    private JLabel lblResponsavel;

    private JButton btnSalvar;
    private JButton btnExcluir;

    private JButton btnDisponibilizar;
    private JButton btnEmUso;
    private JButton btnManutencao;
    private JButton btnExtraviar;
    private JButton btnBaixar;

    private JButton btnSelecionarResponsavel;

    private JTable tabela;
    private DefaultTableModel modelo;

    private JLabel lblSemRegistros;

    private Long idSelecionado = null;

    private StatusPatrimonioEnum statusAtual =
            StatusPatrimonioEnum.DISPONIVEL;

    private Long idResponsavelSelecionado = null;

    public FormPatrimonio(
            ResponsavelService responsavelService,
            PatrimonioService patrimonioService
    ) {

        this.responsavelService =
                responsavelService;

        this.patrimonioService =
                patrimonioService;

        setTitle("Cadastro de Patrimônio");

        setSize(1300, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLayout(
                new BorderLayout(10, 10)
        );

        JPanel painelFormulario =
                new JPanel(new GridBagLayout());

        painelFormulario.setBorder(
                BorderFactory.createTitledBorder(
                        "Dados do Patrimônio"
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(5, 5, 5, 5);

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;

        painelFormulario.add(
                new JLabel("Descrição:"),
                gbc
        );

        gbc.gridx = 1;

        txtDescricao =
                new JTextField(30);

        painelFormulario.add(
                txtDescricao,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy++;

        painelFormulario.add(
                new JLabel("Número Patrimônio:"),
                gbc
        );

        gbc.gridx = 1;

        txtNumeroPatrimonio =
                new JTextField(20);

        painelFormulario.add(
                txtNumeroPatrimonio,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy++;

        painelFormulario.add(
                new JLabel("Status Atual:"),
                gbc
        );

        gbc.gridx = 1;

        lblStatusAtual =
                new JLabel(
                        statusAtual.getDescricao()
                );

        lblStatusAtual.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        painelFormulario.add(
                lblStatusAtual,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy++;

        painelFormulario.add(
                new JLabel("Responsável:"),
                gbc
        );

        gbc.gridx = 1;

        JPanel painelResponsavel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                5,
                                0
                        )
                );

        lblResponsavel =
                new JLabel(
                        "Nenhum responsável"
                );

        btnSelecionarResponsavel =
                new JButton(
                        "Selecionar Responsável"
                );

        painelResponsavel.add(
                lblResponsavel
        );

        painelResponsavel.add(
                btnSelecionarResponsavel
        );

        painelFormulario.add(
                painelResponsavel,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy++;

        painelFormulario.add(
                new JLabel("Alterar Status:"),
                gbc
        );

        gbc.gridx = 1;

        JPanel painelStatus =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                5,
                                0
                        )
                );

        btnDisponibilizar =
                new JButton(
                        "Disponibilizar"
                );

        btnEmUso =
                new JButton(
                        "Em Uso"
                );

        btnManutencao =
                new JButton(
                        "Manutenção"
                );

        btnExtraviar =
                new JButton(
                        "Extraviar"
                );

        btnBaixar =
                new JButton(
                        "Baixar"
                );

        painelStatus.add(
                btnDisponibilizar
        );

        painelStatus.add(
                btnEmUso
        );

        painelStatus.add(
                btnManutencao
        );

        painelStatus.add(
                btnExtraviar
        );

        painelStatus.add(
                btnBaixar
        );

        painelFormulario.add(
                painelStatus,
                gbc
        );

        JPanel painelBotoes =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        btnSalvar =
                new JButton("Salvar");

        btnExcluir =
                new JButton("Excluir");

        painelBotoes.add(btnSalvar);

        painelBotoes.add(btnExcluir);

        JPanel painelTopo =
                new JPanel(
                        new BorderLayout()
                );

        painelTopo.add(
                painelFormulario,
                BorderLayout.CENTER
        );

        painelTopo.add(
                painelBotoes,
                BorderLayout.SOUTH
        );

        add(
                painelTopo,
                BorderLayout.NORTH
        );

        modelo = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {

                return false;
            }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Descrição");
        modelo.addColumn("Número");
        modelo.addColumn("Status");
        modelo.addColumn("Código Responsável");
        modelo.addColumn("Responsável");
        modelo.addColumn("ID Responsável");

        tabela =
                new JTable(modelo);

        tabela.setRowHeight(25);

        tabela.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollTabela =
                new JScrollPane(tabela);

        lblSemRegistros =
                new JLabel(
                        "Nenhum patrimônio cadastrado.",
                        SwingConstants.CENTER
                );

        lblSemRegistros.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        lblSemRegistros.setForeground(
                Color.GRAY
        );

        JPanel painelTabela =
                new JPanel(
                        new BorderLayout()
                );

        painelTabela.setBorder(
                BorderFactory.createTitledBorder(
                        "Lista de Patrimônios"
                )
        );

        painelTabela.add(
                scrollTabela,
                BorderLayout.CENTER
        );

        painelTabela.add(
                lblSemRegistros,
                BorderLayout.SOUTH
        );

        add(
                painelTabela,
                BorderLayout.CENTER
        );

        tabela.getColumnModel()
                .getColumn(0)
                .setMinWidth(0);

        tabela.getColumnModel()
                .getColumn(0)
                .setMaxWidth(0);

        tabela.getColumnModel()
                .getColumn(0)
                .setWidth(0);

        tabela.getColumnModel()
                .getColumn(6)
                .setMinWidth(0);

        tabela.getColumnModel()
                .getColumn(6)
                .setMaxWidth(0);

        tabela.getColumnModel()
                .getColumn(6)
                .setWidth(0);

        btnSalvar.addActionListener(
                new EventoSalvar()
        );

        btnExcluir.addActionListener(
                new EventoExcluir()
        );

        tabela.addMouseListener(
                new EventoSelecionarLinha()
        );

        btnDisponibilizar.addActionListener(
                e -> alterarStatus(
                        StatusPatrimonioEnum.DISPONIVEL
                )
        );

        btnEmUso.addActionListener(
                e -> alterarStatus(
                        StatusPatrimonioEnum.EM_USO
                )
        );

        btnManutencao.addActionListener(
                e -> alterarStatus(
                        StatusPatrimonioEnum.EM_MANUTENCAO
                )
        );

        btnExtraviar.addActionListener(
                e -> alterarStatus(
                        StatusPatrimonioEnum.EXTRAVIADO
                )
        );

        btnBaixar.addActionListener(
                e -> alterarStatus(
                        StatusPatrimonioEnum.BAIXADO
                )
        );

        btnSelecionarResponsavel
                .addActionListener(e -> {

                    SelecaoResponsavel tela =
                            new SelecaoResponsavel(
                                    this,
                                    responsavelService
                            );

                    Responsavel responsavel =
                            tela.getResponsavelSelecionado();

                    if (responsavel != null) {

                        idResponsavelSelecionado =
                                responsavel.getId();

                        lblResponsavel.setText(
                                responsavel.getNomeCompleto()
                        );
                    }
                });

        carregarTabela();

        setVisible(true);
    }

    private void alterarStatus(
            StatusPatrimonioEnum status
    ) {

        statusAtual = status;

        lblStatusAtual.setText(
                statusAtual.getDescricao()
        );
    }

    private void carregarTabela() {

        modelo.setRowCount(0);

        List<Patrimonio> lista =
                patrimonioService.findAll();

        if (lista.isEmpty()) {

            lblSemRegistros.setVisible(true);

        } else {

            lblSemRegistros.setVisible(false);

            for (Patrimonio p : lista) {

                String nomeResponsavel =
                        "Nenhum responsável";

                Long codigoResponsavel = null;

                if (p.getIdResponsavel() != null) {

                    Responsavel responsavel =
                            responsavelService.findById(
                                    p.getIdResponsavel()
                            );

                    if (responsavel != null) {

                        codigoResponsavel =
                                responsavel.getId();

                        nomeResponsavel =
                                responsavel.getNomeCompleto();
                    }
                }

                modelo.addRow(
                        new Object[]{
                                p.getId(),
                                p.getDescricao(),
                                p.getNumeroPatrimonio(),
                                p.getStatus().getDescricao(),

                                codigoResponsavel != null
                                        ? codigoResponsavel
                                        : "-",

                                nomeResponsavel,

                                p.getIdResponsavel()
                        }
                );
            }
        }
    }

    private class EventoSalvar
            implements ActionListener {

        @Override
        public void actionPerformed(
                ActionEvent e
        ) {

            String descricao =
                    txtDescricao.getText().trim();

            String numero =
                    txtNumeroPatrimonio.getText().trim();

            if (
                    descricao.isBlank()
                            || numero.isBlank()
            ) {

                JOptionPane.showMessageDialog(
                        null,
                        "Todos os campos são obrigatórios!"
                );

                return;
            }

            Patrimonio patrimonio =
                    new Patrimonio();

            patrimonio.setId(idSelecionado);

            patrimonio.setDescricao(descricao);

            patrimonio.setNumeroPatrimonio(numero);

            patrimonio.setStatus(statusAtual);

            patrimonio.setIdResponsavel(
                    idResponsavelSelecionado
            );

            patrimonioService.saveOrUpdate(
                    patrimonio
            );

            JOptionPane.showMessageDialog(
                    null,
                    idSelecionado == null
                            ? "Patrimônio cadastrado!"
                            : "Patrimônio atualizado!"
            );

            limparCampos();

            carregarTabela();
        }
    }

    private class EventoExcluir
            implements ActionListener {

        @Override
        public void actionPerformed(
                ActionEvent e
        ) {

            if (idSelecionado == null) {

                JOptionPane.showMessageDialog(
                        null,
                        "Selecione um registro."
                );

                return;
            }

            int resposta =
                    JOptionPane.showConfirmDialog(
                            null,
                            "Deseja realmente excluir?",
                            "Confirmação",
                            JOptionPane.YES_NO_OPTION
                    );

            if (
                    resposta
                            == JOptionPane.YES_OPTION
            ) {

                patrimonioService.deleteById(
                        idSelecionado
                );

                JOptionPane.showMessageDialog(
                        null,
                        "Registro excluído!"
                );

                limparCampos();

                carregarTabela();
            }
        }
    }

    private class EventoSelecionarLinha
            extends MouseAdapter {

        @Override
        public void mouseClicked(
                MouseEvent e
        ) {

            int linha =
                    tabela.getSelectedRow();

            if (linha == -1) {
                return;
            }

            idSelecionado =
                    Long.parseLong(
                            tabela.getValueAt(
                                    linha,
                                    0
                            ).toString()
                    );

            txtDescricao.setText(
                    tabela.getValueAt(
                            linha,
                            1
                    ).toString()
            );

            txtNumeroPatrimonio.setText(
                    tabela.getValueAt(
                            linha,
                            2
                    ).toString()
            );

            String status =
                    tabela.getValueAt(
                            linha,
                            3
                    ).toString();

            for (
                    StatusPatrimonioEnum s
                            : StatusPatrimonioEnum.values()
            ) {

                if (
                        s.getDescricao()
                                .equals(status)
                ) {

                    statusAtual = s;

                    break;
                }
            }

            lblStatusAtual.setText(
                    statusAtual.getDescricao()
            );

            Object nomeResponsavel =
                    tabela.getValueAt(
                            linha,
                            5
                    );

            Object idResponsavel =
                    tabela.getValueAt(
                            linha,
                            6
                    );

            if (idResponsavel != null) {

                idResponsavelSelecionado =
                        Long.parseLong(
                                idResponsavel.toString()
                        );

                lblResponsavel.setText(
                        nomeResponsavel.toString()
                );

            } else {

                idResponsavelSelecionado = null;

                lblResponsavel.setText(
                        "Nenhum responsável"
                );
            }
        }
    }

    private void limparCampos() {

        idSelecionado = null;

        txtDescricao.setText("");

        txtNumeroPatrimonio.setText("");

        statusAtual =
                StatusPatrimonioEnum.DISPONIVEL;

        lblStatusAtual.setText(
                statusAtual.getDescricao()
        );

        idResponsavelSelecionado = null;

        lblResponsavel.setText(
                "Nenhum responsável"
        );

        tabela.clearSelection();
    }
}