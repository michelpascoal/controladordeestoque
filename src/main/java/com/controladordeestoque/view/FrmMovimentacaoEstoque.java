/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.controladordeestoque.view;
import com.controladordeestoque.dao.MovimentoEstoqueDAO;
import com.controladordeestoque.dao.ProdutoDAO;         
import com.controladordeestoque.model.MovimentoEstoque; 
import com.controladordeestoque.model.Produto;          
import java.util.Date;                                  
import javax.swing.JOptionPane;                         
import java.awt.event.ActionEvent;                      
import java.awt.event.ActionListener;                   

/**
 *
 * @author juann
 */
public class FrmMovimentacaoEstoque extends javax.swing.JFrame {
    private MovimentoEstoqueDAO movimentoEstoqueDAO;
    private ProdutoDAO produtoDAO;

    /**
     * Creates new form FrmMovimentacaoEstoque
     */
    public FrmMovimentacaoEstoque() {
        initComponents();
        inicializarLogicaMovimentacao();
    }
private void inicializarLogicaMovimentacao() {
    movimentoEstoqueDAO = new MovimentoEstoqueDAO();
    produtoDAO = new ProdutoDAO();
    
    configurarAcoesBotoesMovimentacao();
    
    this.setTitle("Movimentação de Estoque");
    this.setLocationRelativeTo(null);  
}

private void configurarAcoesBotoesMovimentacao() {
    btnConfirmarMovimentacao.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            acaoConfirmarMovimentacao();
        }
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

    // Validações básicas de entrada
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
    if (tipoMovimento == null) {
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

    
    Produto produtoAlvo = produtoDAO.buscarPorId(idProduto);
    if (produtoAlvo == null) {
        JOptionPane.showMessageDialog(this, "Produto com o código " + idProduto + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        txtCodigoMov.requestFocus();
        return;
    }

    
    int quantidadeAntiga = produtoAlvo.getQuantidade();
    int novaQuantidade = quantidadeAntiga;

    if ("Entrada".equals(tipoMovimento)) {
        novaQuantidade = quantidadeAntiga + quantidadeMovimentada;
    } else if ("Saida".equals(tipoMovimento)) {  
        if (quantidadeAntiga < quantidadeMovimentada) {
            JOptionPane.showMessageDialog(this, "Estoque insuficiente para a saída de " + quantidadeMovimentada + " unidades do produto " + produtoAlvo.getNome() + ".\nEstoque atual: " + quantidadeAntiga, "Estoque Insuficiente", JOptionPane.ERROR_MESSAGE);
            txtQuantidadeMov.requestFocus();
            return;
        }
        novaQuantidade = quantidadeAntiga - quantidadeMovimentada;
    }
    
    produtoAlvo.setQuantidade(novaQuantidade);
    boolean produtoAtualizado = produtoDAO.atualizar(produtoAlvo);

    if (produtoAtualizado) {
         
        int idMovimento = movimentoEstoqueDAO.listarTodos().size() + 1; 
        
        MovimentoEstoque novoMovimento = new MovimentoEstoque(idMovimento, produtoAlvo, quantidadeMovimentada, tipoMovimento.toUpperCase(), new Date());
        boolean movimentoRegistrado = movimentoEstoqueDAO.registrarMovimento(novoMovimento);

        if (movimentoRegistrado) {
            JOptionPane.showMessageDialog(this, "Movimentação de '" + tipoMovimento + "' de " + quantidadeMovimentada + " unidade(s) para o produto '" + produtoAlvo.getNome() + "' registrada com sucesso!\nNovo estoque: " + novaQuantidade, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            // Limpar campos
            txtCodigoMov.setText("");
            txtQuantidadeMov.setText("");
            cmbTipoMovimento.setSelectedIndex(0);  
            txtCodigoMov.requestFocus();
        } else {
            
            produtoAlvo.setQuantidade(quantidadeAntiga);  
            produtoDAO.atualizar(produtoAlvo);
            JOptionPane.showMessageDialog(this, "Produto atualizado, mas houve um erro ao registrar o histórico da movimentação. A alteração no estoque foi desfeita.", "Erro no Registro", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Erro ao atualizar a quantidade do produto no estoque.", "Erro na Atualização", JOptionPane.ERROR_MESSAGE);
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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

        txtCodigoMov.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodigoMovFocusGained(evt);
            }
        });

        cmbTipoMovimento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entrada", "Saida" }));

        btnVoltarMenu.setText("Voltar");

        btnConfirmarMovimentacao.setText("Confirmar");

        jLabel1.setText("Código do produto");

        jLabel2.setText("Quantidade");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(btnConfirmarMovimentacao)
                .addGap(18, 18, 18)
                .addComponent(btnVoltarMenu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void txtCodigoMovFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoMovFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoMovFocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMovimentacaoEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMovimentacaoEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMovimentacaoEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMovimentacaoEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMovimentacaoEstoque().setVisible(true);
            }
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
