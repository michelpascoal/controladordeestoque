package com.controladordeestoque.view;

/**
 *
 * @author juann
 */
public class FrmMenuPrincipal extends javax.swing.JFrame {

   
    public FrmMenuPrincipal() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCadastroProduto = new javax.swing.JButton();
        btnConsultaProduto = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnMovimentacaoEstoque = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnCadastroProduto.setText("Cadastro de Produto");
        btnCadastroProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastroProdutoActionPerformed(evt);
            }
        });

        btnConsultaProduto.setText("Consulta de Produto");
        btnConsultaProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaProdutoActionPerformed(evt);
            }
        });

        jButton4.setText("Sair");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnMovimentacaoEstoque.setText("Movimentação de Estoque");
        btnMovimentacaoEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovimentacaoEstoqueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(btnCadastroProduto)
                        .addGap(5, 5, 5)
                        .addComponent(btnConsultaProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMovimentacaoEstoque))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(235, 235, 235)
                        .addComponent(jButton4)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCadastroProduto)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnConsultaProduto)
                        .addComponent(btnMovimentacaoEstoque)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 243, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadastroProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastroProdutoActionPerformed
        FrmCadastroProduto telaCadastro = new FrmCadastroProduto();
        telaCadastro.setVisible(true);
    }//GEN-LAST:event_btnCadastroProdutoActionPerformed

    private void btnConsultaProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaProdutoActionPerformed
        FrmConsultaProduto telaConsulta = new FrmConsultaProduto();
        telaConsulta.setVisible(true);
    }//GEN-LAST:event_btnConsultaProdutoActionPerformed

    private void btnMovimentacaoEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovimentacaoEstoqueActionPerformed
        FrmMovimentacaoEstoque telaMovimentacao = new FrmMovimentacaoEstoque();
        telaMovimentacao.setVisible(true);
    }//GEN-LAST:event_btnMovimentacaoEstoqueActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int resposta = javax.swing.JOptionPane.showConfirmDialog(this,
        "Deseja realmente sair do sistema?",
        "Confirmar Saída",
        javax.swing.JOptionPane.YES_NO_OPTION);
        if (resposta == javax.swing.JOptionPane.YES_OPTION) {
    System.exit(0);
   
    }//GEN-LAST:event_jButton4ActionPerformed

 
    } public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new FrmMenuPrincipal().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastroProduto;
    private javax.swing.JButton btnConsultaProduto;
    private javax.swing.JButton btnMovimentacaoEstoque;
    private javax.swing.JButton jButton4;
    // End of variables declaration//GEN-END:variables
}
