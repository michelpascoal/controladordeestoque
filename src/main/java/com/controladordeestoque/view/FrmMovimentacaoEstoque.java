package com.controladordeestoque.view;
import com.controladordeestoque.controller.MovimentacaoEstoqueController;
import com.controladordeestoque.dao.MovimentoEstoqueDAO;
import com.controladordeestoque.dao.ProdutoDAO;         
import com.controladordeestoque.model.MovimentoEstoque; 
                                  import javax.swing.JOptionPane;                         
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
                   

/**
 *
 * @author juann
 */
public class FrmMovimentacaoEstoque extends javax.swing.JFrame {
    private MovimentoEstoqueDAO movimentoEstoqueDAO;
    private ProdutoDAO produtoDAO;
    private MovimentacaoEstoqueController movimentacaoEstoqueController;
    
    public FrmMovimentacaoEstoque() {
        initComponents();
        inicializarLogicaMovimentacao();
    }
private void inicializarLogicaMovimentacao() {
    movimentoEstoqueDAO = new MovimentoEstoqueDAO();
    produtoDAO = new ProdutoDAO();
    movimentacaoEstoqueController = new MovimentacaoEstoqueController();
    
    configurarAcoesBotoesMovimentacao();
    
    this.setTitle("Movimentação de Estoque");
    this.setLocationRelativeTo(null);  
}

private void configurarAcoesBotoesMovimentacao() {
    btnConfirmarMovimentacao.addActionListener((ActionEvent e) -> {
        acaoConfirmarMovimentacao();
    });

    btnVoltarMenu.addActionListener((ActionEvent e) -> {
        acaoVoltarMenu();
    });
}

private void acaoVoltarMenu() {
    this.dispose();  
}

 
private void acaoGerarRelatorio() {
    List<MovimentoEstoque> relatorio = movimentacaoEstoqueController.listarTodasMovimentacoes(); // Usa o controller
    if (relatorio.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nenhuma movimentação de estoque registrada para gerar o relatório.", "Relatório Vazio", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    StringBuilder relatorioCompleto = new StringBuilder("=== RELATÓRIO DE MOVIMENTAÇÃO DE ESTOQUE ===\n\n");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    for (MovimentoEstoque mov : relatorio) {
        String linha = String.format("ID: %d | Produto: %s | Qtd: %d | Tipo: %s | Data: %s\n",
                mov.getId(),
                mov.getProduto().getNome(),
                mov.getQuantidade(),
                mov.getTipo(),
                sdf.format(mov.getData()));
        relatorioCompleto.append(linha);
    }

    // Prepara a área de texto para ser exibida na janela
    javax.swing.JTextArea textArea = new javax.swing.JTextArea(relatorioCompleto.toString());
    javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(textArea);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setEditable(false);
    scrollPane.setPreferredSize(new java.awt.Dimension(550, 350));

    // Define os botões customizados para a janela de diálogo
    Object[] options = {"Salvar Relatório", "Fechar"};

    int escolha = JOptionPane.showOptionDialog(
        this,
        scrollPane,
        "Relatório de Movimentações",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null,
        options,
        options[1]
    );

    
    if (escolha == JOptionPane.YES_OPTION) { // YES_OPTION corresponde ao primeiro botão (índice 0)
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Relatório Como...");
        fileChooser.setSelectedFile(new File("relatorio_movimentacao.txt")); // Sugestão de nome de arquivo

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File arquivoParaSalvar = fileChooser.getSelectedFile();
            try (FileWriter fw = new FileWriter(arquivoParaSalvar);
                 BufferedWriter bw = new BufferedWriter(fw)) {

                bw.write(relatorioCompleto.toString());

                JOptionPane.showMessageDialog(this, 
                    "Relatório salvo com sucesso em:\n" + arquivoParaSalvar.getAbsolutePath(), 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, 
                    "Ocorreu um erro ao salvar o arquivo:\n" + e.getMessage(), 
                    "Erro de Gravação", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

private void acaoConfirmarMovimentacao() {
    String textoCodigoProduto = txtCodigoMov.getText().trim();
    String textoQuantidade = txtQuantidadeMov.getText().trim();
    String tipoMovimento = (String) cmbTipoMovimento.getSelectedItem();

    // Validações básicas de entrada (mantidas na View)
    if (textoCodigoProduto.isEmpty()) {
        JOptionPane.showMessageDialog(this, "O Código do Produto é obrigatório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        txtCodigoMov.requestFocus();
        return;
    }
    if (textoQuantidade.isEmpty()) {
        JOptionPane.showMessageDialog(this, "A Quantidade é obrigatória.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        txtQuantidadeMov.requestFocus();
        return;
    }
    if (tipoMovimento == null || tipoMovimento.isEmpty() || "Selecione o Tipo de Movimento".equals(tipoMovimento)) { // Adicionado verificação para item padrão
        JOptionPane.showMessageDialog(this, "Selecione o Tipo de Movimento.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        cmbTipoMovimento.requestFocus();
        return;
    }

    int idProduto;
    try {
        idProduto = Integer.parseInt(textoCodigoProduto);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Código do Produto inválido. Deve ser um número.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        txtCodigoMov.requestFocus();
        return;
    }

    int quantidadeMovimentada;
    try {
        quantidadeMovimentada = Integer.parseInt(textoQuantidade);
        if (quantidadeMovimentada <= 0) {
            JOptionPane.showMessageDialog(this, "Quantidade deve ser um número positivo.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            txtQuantidadeMov.requestFocus();
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Quantidade inválida. Deve ser um número.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        txtQuantidadeMov.requestFocus();
        return;
    }

    boolean sucesso = false;
    if ("Entrada".equals(tipoMovimento)) {
        sucesso = movimentacaoEstoqueController.registrarEntrada(idProduto, quantidadeMovimentada); // DELEGA AO CONTROLLER
    } else if ("Saida".equals(tipoMovimento)) {
        sucesso = movimentacaoEstoqueController.registrarSaida(idProduto, quantidadeMovimentada); // DELEGA AO CONTROLLER
    }

    if (sucesso) {
        JOptionPane.showMessageDialog(this, "Movimentação de '" + tipoMovimento + "' registrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        txtCodigoMov.setText("");
        txtQuantidadeMov.setText("");
        cmbTipoMovimento.setSelectedIndex(0); // Reseta o ComboBox para o primeiro item
        txtCodigoMov.requestFocus();
    } else {
        // As mensagens de erro específicas (produto não encontrado, estoque insuficiente, etc.)
        // já são tratadas DENTRO do MovimentacaoEstoqueController.
        // Aqui, apenas uma mensagem genérica de falha se o controller retornar false.
        JOptionPane.showMessageDialog(this, "Falha ao registrar a movimentação.", "Erro na Operação", JOptionPane.ERROR_MESSAGE);
    }
    
}
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtCodigoMov = new javax.swing.JTextField();
        txtQuantidadeMov = new javax.swing.JTextField();
        cmbTipoMovimento = new javax.swing.JComboBox<>();
        btnVoltarMenu = new javax.swing.JButton();
        btnConfirmarMovimentacao = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnGerarRelatorio = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cmbTipoMovimento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entrada", "Saida" }));

        btnVoltarMenu.setText("Voltar");

        btnConfirmarMovimentacao.setText("Confirmar");

        jLabel1.setText("Código do produto");

        jLabel2.setText("Quantidade");

        btnGerarRelatorio.setText("Gerar Relatório");
        btnGerarRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarRelatorioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCodigoMov)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtQuantidadeMov, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(cmbTipoMovimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
            .addGroup(layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(btnConfirmarMovimentacao)
                .addGap(47, 47, 47)
                .addComponent(btnVoltarMenu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGerarRelatorio)
                .addGap(143, 143, 143))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantidadeMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipoMovimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmarMovimentacao)
                    .addComponent(btnVoltarMenu))
                .addGap(18, 18, 18)
                .addComponent(btnGerarRelatorio)
                .addGap(58, 58, 58))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGerarRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarRelatorioActionPerformed
        acaoGerarRelatorio();
    }//GEN-LAST:event_btnGerarRelatorioActionPerformed
    

    
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMovimentacaoEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
     

        
        java.awt.EventQueue.invokeLater(() -> {
            new FrmMovimentacaoEstoque().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmarMovimentacao;
    private javax.swing.JButton btnGerarRelatorio;
    private javax.swing.JButton btnVoltarMenu;
    private javax.swing.JComboBox<String> cmbTipoMovimento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtCodigoMov;
    private javax.swing.JTextField txtQuantidadeMov;
    // End of variables declaration//GEN-END:variables
}