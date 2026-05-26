package src.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import src.enums.SexoEnum;
import src.enums.StatusResponsavelEnum;
import src.model.Responsavel;
import src.service.ResponsavelService;

public class FormResponsavel extends JFrame {

    private final ResponsavelService responsavelService;

    private JTextField txtNomeCompleto;
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JTextField txtSetor;
    private JTextField txtCargo;

    private JRadioButton rbMasculino;
    private JRadioButton rbFeminino;
    private JRadioButton rbOutro;
    private JRadioButton rbNaoInformado;

    private JCheckBox cbAtivo;

    private JButton btnSalvar;
    private JButton btnExcluir;

    private JTable tabela;
    private DefaultTableModel modelo;

    private JLabel lblSemRegistros;

    private Long idSelecionado = null;

    public FormResponsavel(ResponsavelService responsavelService) {

        this.responsavelService = responsavelService;

        setTitle("Cadastro de Responsável");
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel painelFormulario = new JPanel(new GridBagLayout());

        painelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Responsável"));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;

        painelFormulario.add(new JLabel("Nome Completo:"), gbc);

        gbc.gridx = 1;

        txtNomeCompleto = new JTextField(25);

        painelFormulario.add(txtNomeCompleto, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        painelFormulario.add(new JLabel("CPF:"), gbc);

        gbc.gridx = 1;

        txtCpf = new JTextField(20);

        painelFormulario.add(txtCpf, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        painelFormulario.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;

        txtEmail = new JTextField(25);

        painelFormulario.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        painelFormulario.add(new JLabel("Telefone:"), gbc);

        gbc.gridx = 1;

        txtTelefone = new JTextField(20);

        painelFormulario.add(txtTelefone, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        painelFormulario.add(new JLabel("Setor:"), gbc);

        gbc.gridx = 1;

        txtSetor = new JTextField(20);

        painelFormulario.add(txtSetor, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        painelFormulario.add(new JLabel("Cargo:"), gbc);

        gbc.gridx = 1;

        txtCargo = new JTextField(20);

        painelFormulario.add(txtCargo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        painelFormulario.add(new JLabel("Sexo:"), gbc);

        JPanel painelSexo = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 5, 0)
        );

        rbMasculino = new JRadioButton("Masculino");
        rbFeminino = new JRadioButton("Feminino");
        rbOutro = new JRadioButton("Outro");
        rbNaoInformado = new JRadioButton("Não Informado");

        ButtonGroup grupoSexo = new ButtonGroup();

        grupoSexo.add(rbMasculino);
        grupoSexo.add(rbFeminino);
        grupoSexo.add(rbOutro);
        grupoSexo.add(rbNaoInformado);

        rbNaoInformado.setSelected(true);

        painelSexo.add(rbMasculino);
        painelSexo.add(rbFeminino);
        painelSexo.add(rbOutro);
        painelSexo.add(rbNaoInformado);

        gbc.gridx = 1;

        painelFormulario.add(painelSexo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        painelFormulario.add(new JLabel("Status:"), gbc);

        cbAtivo = new JCheckBox("Ativo");

        cbAtivo.setSelected(true);

        gbc.gridx = 1;

        painelFormulario.add(cbAtivo, gbc);

        JPanel painelBotoes = new JPanel(
                new FlowLayout(FlowLayout.CENTER)
        );

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
        modelo.addColumn("Nome");
        modelo.addColumn("CPF");
        modelo.addColumn("Email");
        modelo.addColumn("Telefone");
        modelo.addColumn("Setor");
        modelo.addColumn("Cargo");
        modelo.addColumn("Sexo");
        modelo.addColumn("Status");

        tabela = new JTable(modelo);

        tabela.setRowHeight(25);

        tabela.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollTabela = new JScrollPane(tabela);

        lblSemRegistros = new JLabel(
                "Nenhum responsável cadastrado.",
                SwingConstants.CENTER
        );

        lblSemRegistros.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        lblSemRegistros.setForeground(Color.GRAY);

        JPanel painelTabela = new JPanel(new BorderLayout());

        painelTabela.setBorder(
                BorderFactory.createTitledBorder("Lista de Responsáveis")
        );

        painelTabela.add(scrollTabela, BorderLayout.CENTER);
        painelTabela.add(lblSemRegistros, BorderLayout.SOUTH);

        add(painelTabela, BorderLayout.CENTER);

        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);
        tabela.getColumnModel().getColumn(0).setWidth(0);

        btnSalvar.addActionListener(new EventoSalvar());
        btnExcluir.addActionListener(new EventoExcluir());

        tabela.addMouseListener(new EventoSelecionarLinha());

        carregarTabela();

        setVisible(true);
    }

    private void carregarTabela() {

        modelo.setRowCount(0);

        List<Responsavel> lista = responsavelService.findAll();

        if (lista.isEmpty()) {

            lblSemRegistros.setVisible(true);

        } else {

            lblSemRegistros.setVisible(false);

            for (Responsavel r : lista) {

                modelo.addRow(new Object[]{
                        r.getId(),
                        r.getNomeCompleto(),
                        r.getCpf(),
                        r.getEmail(),
                        r.getTelefone(),
                        r.getSetor(),
                        r.getCargo(),
                        r.getSexo().getDescricao(),
                        r.getStatus().getDescricao()
                });
            }
        }
    }

    private boolean emailValido(String email) {

        String regex =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        return Pattern.matches(regex, email);
    }

    private boolean telefoneValido(String telefone) {

        String regex =
                "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$";

        return Pattern.matches(regex, telefone);
    }

    private boolean cpfValido(String cpf) {

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {

            int soma = 0;

            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }

            int dig1 = 11 - (soma % 11);

            if (dig1 >= 10) {
                dig1 = 0;
            }

            soma = 0;

            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }

            int dig2 = 11 - (soma % 11);

            if (dig2 >= 10) {
                dig2 = 0;
            }

            return dig1 == (cpf.charAt(9) - '0') && dig2 == (cpf.charAt(10) - '0');
        } catch (Exception e) {

            return false;
        }
    }

    private SexoEnum obterSexoSelecionado() {

        if (rbMasculino.isSelected()) {
            return SexoEnum.MASCULINO;
        }

        if (rbFeminino.isSelected()) {
            return SexoEnum.FEMININO;
        }

        if (rbOutro.isSelected()) {
            return SexoEnum.OUTRO;
        }

        return SexoEnum.NAO_INFORMADO;
    }

    private class EventoSalvar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String nome = txtNomeCompleto.getText().trim();
            String cpf = txtCpf.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String setor = txtSetor.getText().trim();
            String cargo = txtCargo.getText().trim();

            if (
                    nome.isBlank()
                            || cpf.isBlank()
                            || email.isBlank()
                            || telefone.isBlank()
                            || setor.isBlank()
                            || cargo.isBlank()
            ) {

                JOptionPane.showMessageDialog(
                        null,
                        "Todos os campos são obrigatórios!"
                );

                return;
            }

            if (!cpfValido(cpf)) {

                JOptionPane.showMessageDialog(
                        null,
                        "CPF inválido!"
                );

                return;
            }

            if (!emailValido(email)) {

                JOptionPane.showMessageDialog(
                        null,
                        "Email inválido!"
                );

                return;
            }

            if (!telefoneValido(telefone)) {

                JOptionPane.showMessageDialog(
                        null,
                        "Telefone inválido!"
                );

                return;
            }

            Responsavel responsavel = new Responsavel();

            responsavel.setId(idSelecionado);

            responsavel.setNomeCompleto(nome);
            responsavel.setCpf(cpf);
            responsavel.setEmail(email);
            responsavel.setTelefone(telefone);
            responsavel.setSetor(setor);
            responsavel.setCargo(cargo);

            responsavel.setSexo(obterSexoSelecionado());

            responsavel.setStatus(
                    cbAtivo.isSelected()
                            ? StatusResponsavelEnum.ATIVO
                            : StatusResponsavelEnum.INATIVO
            );

            responsavel.setDataCadastro(LocalDateTime.now());

            responsavelService.saveOrUpdate(responsavel);

            JOptionPane.showMessageDialog(
                    null,
                    idSelecionado == null
                            ? "Responsável cadastrado!"
                            : "Responsável atualizado!"
            );

            limparCampos();

            carregarTabela();
        }
    }

    private class EventoExcluir implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (idSelecionado == null) {

                JOptionPane.showMessageDialog(
                        null,
                        "Selecione um registro."
                );

                return;
            }

            int resposta = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja realmente excluir?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
            );

            if (resposta == JOptionPane.YES_OPTION) {

                responsavelService.deleteById(idSelecionado);

                JOptionPane.showMessageDialog(
                        null,
                        "Registro excluído!"
                );

                limparCampos();

                carregarTabela();
            }
        }
    }

    private class EventoSelecionarLinha extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int linha = tabela.getSelectedRow();

            if (linha == -1) {
                return;
            }

            idSelecionado = Long.parseLong(tabela.getValueAt(linha, 0).toString());
            txtNomeCompleto.setText(tabela.getValueAt(linha, 1).toString());
            txtCpf.setText(tabela.getValueAt(linha, 2).toString());
            txtEmail.setText(tabela.getValueAt(linha, 3).toString());
            txtTelefone.setText(tabela.getValueAt(linha, 4).toString());
            txtSetor.setText(tabela.getValueAt(linha, 5).toString());
            txtCargo.setText(tabela.getValueAt(linha, 6).toString());

            String sexo = tabela.getValueAt(linha, 7).toString();

            switch (sexo) {
                case "Masculino":
                    rbMasculino.setSelected(true);
                    break;

                case "Feminino":
                    rbFeminino.setSelected(true);
                    break;

                case "Outro":
                    rbOutro.setSelected(true);
                    break;

                default:
                    rbNaoInformado.setSelected(true);
            }

            String status = tabela.getValueAt(linha, 8).toString();
            cbAtivo.setSelected(status.equals("Ativo"));
        }
    }

    private void limparCampos() {
        idSelecionado = null;
        txtNomeCompleto.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtSetor.setText("");
        txtCargo.setText("");
        rbNaoInformado.setSelected(true);
        cbAtivo.setSelected(true);
        tabela.clearSelection();
    }
}