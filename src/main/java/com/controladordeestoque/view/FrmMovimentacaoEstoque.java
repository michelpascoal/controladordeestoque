package com.controladordeestoque.view;
import com.controladordeestoque.controller.MovimentacaoEstoqueController;
import com.controladordeestoque.dao.MovimentoEstoqueDAO;
import com.controladordeestoque.dao.ProdutoDAO;         
import com.controladordeestoque.model.MovimentoEstoque; 
import com.controladordeestoque.model.Produto;          
import java.util.Date;                                  
import javax.swing.JOptionPane;                         
import java.awt.event.ActionEvent;                      
                   

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cmbTipoMovimento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entrada", "Saida" }));

        btnVoltarMenu.setText("Voltar");

        btnConfirmarMovimentacao.setText("Confirmar");

        jLabel1.setText("Código do produto");

        jLabel2.setText("Quantidade");

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
                .addGap(99, 99, 99))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    

    
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
    private javax.swing.JButton btnVoltarMenu;
    private javax.swing.JComboBox<String> cmbTipoMovimento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtCodigoMov;
    private javax.swing.JTextField txtQuantidadeMov;
    // End of variables declaration//GEN-END:variables
}