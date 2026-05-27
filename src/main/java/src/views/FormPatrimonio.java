package src.views;

import java.awt.Font;
import java.util.List;
import java.awt.Color;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;

import src.model.Patrimonio;
import src.model.Responsavel;
import src.utils.JOptionPaneUtils;
import src.service.PatrimonioService;
import src.enums.StatusPatrimonioEnum;
import src.service.ResponsavelService;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class FormPatrimonio extends JFrame {

        private final PatrimonioService patrimonioService;
        private final ResponsavelService responsavelService;

        private JLabel lblStatusAtual;
        private JLabel lblResponsavel;
        private JLabel lblSemRegistros;

        private JTextField txtDescricao;
        private JTextField txtNumeroPatrimonio;

        private JButton btnSalvar;
        private JButton btnBaixar;
        private JButton btnExcluir;
        private JButton btnExtraviar;
        private JButton btnManutencao;
        private JButton btnDisponibilizar;
        private JButton btnSelecionarResponsavel;

        private JTable tabela;
        private DefaultTableModel modelo;

        private Long idSelecionado = null;
        private Long idResponsavelSelecionado = null;

        private StatusPatrimonioEnum statusAtual = StatusPatrimonioEnum.DISPONIVEL;

        public FormPatrimonio(ResponsavelService responsavelService,
                        PatrimonioService patrimonioService) {

                this.responsavelService = responsavelService;
                this.patrimonioService = patrimonioService;

                setTitle("Cadastro de Patrimônio");
                setSize(1300, 700);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setLayout(new BorderLayout(10, 10));

                JPanel painelFormulario = new JPanel(new GridBagLayout());
                painelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Patrimônio"));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.anchor = GridBagConstraints.WEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 0;

                painelFormulario.add(new JLabel("Descrição:"), gbc);

                gbc.gridx = 1;
                txtDescricao = new JTextField(30);

                painelFormulario.add(txtDescricao, gbc);
                gbc.gridx = 0;
                gbc.gridy++;

                painelFormulario.add(new JLabel("Número Patrimônio:"), gbc);

                gbc.gridx = 1;
                txtNumeroPatrimonio = new JTextField(20);

                painelFormulario.add(txtNumeroPatrimonio, gbc);
                gbc.gridx = 0;
                gbc.gridy++;

                painelFormulario.add(new JLabel("Status Atual:"), gbc);

                gbc.gridx = 1;
                lblStatusAtual = new JLabel(statusAtual.getDescricao());

                lblStatusAtual.setFont(new Font("Arial", Font.BOLD, 14));
                painelFormulario.add(lblStatusAtual, gbc);
                gbc.gridx = 0;
                gbc.gridy++;

                painelFormulario.add(new JLabel("Responsável:"), gbc);

                gbc.gridx = 1;

                JPanel painelResponsavel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
                lblResponsavel = new JLabel("Nenhum responsável");
                btnSelecionarResponsavel = new JButton("Selecionar Responsável");
                painelResponsavel.add(lblResponsavel);
                painelResponsavel.add(btnSelecionarResponsavel);

                painelFormulario.add(painelResponsavel, gbc);
                gbc.gridx = 0;
                gbc.gridy++;

                painelFormulario.add(new JLabel("Alterar Status:"), gbc);

                gbc.gridx = 1;

                JPanel painelStatus = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

                btnDisponibilizar = new JButton("Disponibilizar");
                btnManutencao = new JButton("Manutenção");
                btnExtraviar = new JButton("Extraviar");
                btnBaixar = new JButton("Baixar");

                painelStatus.add(btnDisponibilizar);
                painelStatus.add(btnManutencao);
                painelStatus.add(btnExtraviar);
                painelStatus.add(btnBaixar);

                painelFormulario.add(painelStatus, gbc);

                JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

                btnSalvar = new JButton("Salvar");
                btnExcluir = new JButton("Excluir");

                painelBotoes.add(btnSalvar);
                painelBotoes.add(btnExcluir);

                JPanel painelTopo = new JPanel(new BorderLayout());
                painelTopo.add(painelFormulario, BorderLayout.CENTER);
                painelTopo.add(painelBotoes, BorderLayout.SOUTH);

                add(painelTopo, BorderLayout.NORTH);

                modelo = new DefaultTableModel() {

                        @Override
                        public boolean isCellEditable(int row, int column) {
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

                tabela = new JTable(modelo);
                tabela.setRowHeight(25);
                tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                JScrollPane scrollTabela = new JScrollPane(tabela);

                lblSemRegistros = new JLabel("Nenhum patrimônio cadastrado.", SwingConstants.CENTER);
                lblSemRegistros.setFont(new Font("Arial", Font.BOLD, 16));
                lblSemRegistros.setForeground(Color.GRAY);

                JPanel painelTabela = new JPanel(new BorderLayout());
                painelTabela.setBorder(BorderFactory.createTitledBorder("Lista de Patrimônios"));
                painelTabela.add(scrollTabela, BorderLayout.CENTER);
                painelTabela.add(lblSemRegistros, BorderLayout.SOUTH);

                add(painelTabela, BorderLayout.CENTER);

                tabela.getColumnModel().getColumn(0).setMinWidth(0);
                tabela.getColumnModel().getColumn(0).setMaxWidth(0);
                tabela.getColumnModel().getColumn(0).setWidth(0);
                tabela.getColumnModel().getColumn(6).setMinWidth(0);
                tabela.getColumnModel().getColumn(6).setMaxWidth(0);
                tabela.getColumnModel().getColumn(6).setWidth(0);

                tabela.addMouseListener(new EventoSelecionarLinha());

                btnSalvar.addActionListener(new EventoSalvar());
                btnExcluir.addActionListener(new EventoExcluir());
                btnDisponibilizar.addActionListener(e -> disponibilizarPatrimonio());
                btnManutencao.addActionListener(e -> enviarParaManutencao());
                btnExtraviar.addActionListener(e -> extraviarPatrimonio());
                btnBaixar.addActionListener(e -> baixarPatrimonio());

                btnSelecionarResponsavel.addActionListener(e -> {
                        if (idSelecionado == null) {
                                JOptionPaneUtils.showOkDialog("Salve o patrimônio antes de atribuir um responsável.");
                                return;
                        }

                        SelecaoResponsavel tela = new SelecaoResponsavel(this, responsavelService);

                        Responsavel responsavel = tela.getResponsavelSelecionado();
                        if (responsavel == null)
                                return;

                        Patrimonio patrimonio = new Patrimonio();
                        patrimonio.setId(idSelecionado);
                        patrimonio.setDescricao(txtDescricao.getText());
                        patrimonio.setNumeroPatrimonio(txtNumeroPatrimonio.getText());
                        patrimonio.setStatus(StatusPatrimonioEnum.EM_USO);
                        patrimonio.setIdResponsavel(responsavel.getId());

                        patrimonioService.saveOrUpdate(patrimonio, false);

                        idResponsavelSelecionado = responsavel.getId();

                        statusAtual = StatusPatrimonioEnum.EM_USO;

                        lblStatusAtual.setText(statusAtual.getDescricao());
                        lblResponsavel.setText(responsavel.getNomeCompleto());

                        carregarTabela();
                        atualizarEstadoBotoes();
                        limparCampos();
                });
                carregarTabela();
                atualizarEstadoBotoes();
                setVisible(true);
        }

        private void carregarTabela() {
                modelo.setRowCount(0);

                List<Patrimonio> lista = patrimonioService.findAll();
                if (lista.isEmpty()) {
                        lblSemRegistros.setVisible(true);
                } else {
                        lblSemRegistros.setVisible(false);

                        for (Patrimonio p : lista) {
                                String nomeResponsavel = "Nenhum responsável";
                                Long codigoResponsavel = null;

                                if (p.getIdResponsavel() != null) {
                                        Responsavel responsavel = responsavelService.findById(p.getIdResponsavel());
                                        if (responsavel != null) {
                                                codigoResponsavel = responsavel.getId();
                                                nomeResponsavel = responsavel.getNomeCompleto();
                                        }
                                }

                                modelo.addRow(
                                                new Object[] {
                                                                p.getId(),
                                                                p.getDescricao(),
                                                                p.getNumeroPatrimonio(),
                                                                p.getStatus().getDescricao(),
                                                                codigoResponsavel != null ? codigoResponsavel : "-",
                                                                nomeResponsavel,
                                                                p.getIdResponsavel()
                                                });
                        }
                }
        }

        private void atualizarEstadoBotoes() {
                if (idSelecionado == null) {
                        btnDisponibilizar.setEnabled(false);
                        btnSelecionarResponsavel.setEnabled(false);
                        btnManutencao.setEnabled(false);
                        btnExtraviar.setEnabled(false);
                        btnBaixar.setEnabled(false);
                        btnExcluir.setEnabled(false);
                        return;
                }

                if (statusAtual == StatusPatrimonioEnum.BAIXADO) {
                        btnSalvar.setEnabled(false);
                        btnDisponibilizar.setEnabled(false);
                        btnSelecionarResponsavel.setEnabled(false);
                        btnManutencao.setEnabled(false);
                        btnExtraviar.setEnabled(false);
                        btnBaixar.setEnabled(false);
                        btnExcluir.setEnabled(true);
                        return;
                }

                btnDisponibilizar.setEnabled(statusAtual != StatusPatrimonioEnum.DISPONIVEL);

                btnSelecionarResponsavel.setEnabled(
                                statusAtual != StatusPatrimonioEnum.BAIXADO
                                                && statusAtual != StatusPatrimonioEnum.EXTRAVIADO
                                                && statusAtual != StatusPatrimonioEnum.EM_MANUTENCAO);

                btnManutencao.setEnabled(statusAtual != StatusPatrimonioEnum.EM_MANUTENCAO);
                btnExtraviar.setEnabled(statusAtual != StatusPatrimonioEnum.EXTRAVIADO);
                btnBaixar.setEnabled(statusAtual != StatusPatrimonioEnum.BAIXADO);
                btnExcluir.setEnabled(true);
        }

        private void limparCampos() {
                idSelecionado = null;
                txtDescricao.setText("");
                txtNumeroPatrimonio.setText("");
                statusAtual = StatusPatrimonioEnum.DISPONIVEL;
                lblStatusAtual.setText(statusAtual.getDescricao());
                idResponsavelSelecionado = null;
                lblResponsavel.setText("Nenhum responsável");
                tabela.clearSelection();
                atualizarEstadoBotoes();
        }

        private void disponibilizarPatrimonio() {
                if (idSelecionado == null) {
                        return;
                }

                Patrimonio patrimonio = new Patrimonio();
                patrimonio.setId(idSelecionado);
                patrimonio.setDescricao(txtDescricao.getText());
                patrimonio.setNumeroPatrimonio(txtNumeroPatrimonio.getText());
                patrimonio.setStatus(StatusPatrimonioEnum.DISPONIVEL);
                patrimonio.setIdResponsavel(null);

                patrimonioService.saveOrUpdate(patrimonio, false);

                statusAtual = StatusPatrimonioEnum.DISPONIVEL;

                idResponsavelSelecionado = null;
                lblStatusAtual.setText(statusAtual.getDescricao());
                lblResponsavel.setText("Nenhum responsável");

                carregarTabela();
                limparCampos();
        }

        private void enviarParaManutencao() {
                if (idSelecionado == null) {
                        return;
                }

                Patrimonio patrimonio = new Patrimonio();
                patrimonio.setId(idSelecionado);
                patrimonio.setDescricao(txtDescricao.getText());
                patrimonio.setNumeroPatrimonio(txtNumeroPatrimonio.getText());
                patrimonio.setStatus(StatusPatrimonioEnum.EM_MANUTENCAO);
                patrimonio.setIdResponsavel(null);

                patrimonioService.saveOrUpdate(patrimonio, false);

                statusAtual = StatusPatrimonioEnum.EM_MANUTENCAO;

                idResponsavelSelecionado = null;
                lblStatusAtual.setText(statusAtual.getDescricao());
                lblResponsavel.setText("Nenhum responsável");

                carregarTabela();
                limparCampos();
        }

        private void extraviarPatrimonio() {
                if (idSelecionado == null) {
                        return;
                }

                Patrimonio patrimonio = new Patrimonio();
                patrimonio.setId(idSelecionado);

                patrimonio.setDescricao(txtDescricao.getText());
                patrimonio.setNumeroPatrimonio(txtNumeroPatrimonio.getText());
                patrimonio.setStatus(StatusPatrimonioEnum.EXTRAVIADO);
                patrimonio.setIdResponsavel(null);

                patrimonioService.saveOrUpdate(patrimonio, false);

                statusAtual = StatusPatrimonioEnum.EXTRAVIADO;

                idResponsavelSelecionado = null;
                lblStatusAtual.setText(statusAtual.getDescricao());
                lblResponsavel.setText("Nenhum responsável");

                carregarTabela();
                limparCampos();
        }

        private void baixarPatrimonio() {
                if (idSelecionado == null) {
                        return;
                }

                Patrimonio patrimonio = new Patrimonio();

                patrimonio.setId(idSelecionado);
                patrimonio.setDescricao(txtDescricao.getText());
                patrimonio.setNumeroPatrimonio(txtNumeroPatrimonio.getText());
                patrimonio.setStatus(StatusPatrimonioEnum.BAIXADO);
                patrimonio.setIdResponsavel(null);
                patrimonio.setNumeroPatrimonio("");

                patrimonioService.saveOrUpdate(patrimonio, false);

                statusAtual = StatusPatrimonioEnum.BAIXADO;

                idResponsavelSelecionado = null;
                lblStatusAtual.setText(statusAtual.getDescricao());
                lblResponsavel.setText("Nenhum responsável");

                carregarTabela();
                limparCampos();
        }

        private class EventoSalvar implements ActionListener {

                @Override
                public void actionPerformed(ActionEvent e) {

                        String descricao = txtDescricao.getText().trim();
                        String numero = txtNumeroPatrimonio.getText().trim();

                        if (descricao.isBlank() || numero.isBlank()) {
                                JOptionPaneUtils.showOkDialog("Todos os campos são obrigatórios!");
                                return;
                        }

                        Patrimonio patrimonio = new Patrimonio();
                        patrimonio.setId(idSelecionado);
                        patrimonio.setDescricao(descricao);
                        patrimonio.setNumeroPatrimonio(numero);
                        patrimonio.setStatus(statusAtual);
                        patrimonio.setIdResponsavel(idResponsavelSelecionado);

                        patrimonioService.saveOrUpdate(patrimonio, false);

                        limparCampos();
                        carregarTabela();
                }
        }

        private class EventoExcluir implements ActionListener {

                @Override
                public void actionPerformed(ActionEvent e) {
                        if (idSelecionado == null) {
                                JOptionPaneUtils.showOkDialog("Selecione um registro.");
                                return;
                        }

                        if (JOptionPaneUtils.showConfirmDialog("Deseja realmente excluir?")) {
                                patrimonioService.deleteById(idSelecionado);
                                limparCampos();
                                carregarTabela();
                        }
                }
        }

        private class EventoSelecionarLinha extends MouseAdapter {

                @Override
                public void mouseClicked(MouseEvent e) {
                        int linha = tabela.getSelectedRow();
                        if (linha == -1)
                                return;

                        idSelecionado = Long.parseLong(tabela.getValueAt(linha, 0).toString());
                        txtDescricao.setText(tabela.getValueAt(linha, 1).toString());
                        txtNumeroPatrimonio.setText(tabela.getValueAt(linha, 2).toString());

                        String status = tabela.getValueAt(linha, 3).toString();
                        for (StatusPatrimonioEnum s : StatusPatrimonioEnum.values()) {
                                if (s.getDescricao().equals(status)) {
                                        statusAtual = s;
                                        break;
                                }
                        }

                        lblStatusAtual.setText(statusAtual.getDescricao());

                        Object nomeResponsavel = tabela.getValueAt(linha, 5);
                        Object idResponsavel = tabela.getValueAt(linha, 6);

                        if (idResponsavel != null) {
                                idResponsavelSelecionado = Long.parseLong(idResponsavel.toString());
                                lblResponsavel.setText(nomeResponsavel.toString());
                        } else {
                                idResponsavelSelecionado = null;
                                lblResponsavel.setText("Nenhum responsável");
                        }
                        atualizarEstadoBotoes();
                }
        }
}