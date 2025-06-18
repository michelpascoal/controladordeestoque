package com.controladordeestoque.view;

/**
 * Representa a tela do menu principal da aplicação.
 * <p>
 * Este frame serve como o ponto central de navegação, a partir do qual o
 * usuário pode acessar as diferentes funcionalidades do sistema, como as telas
 * de cadastro, consulta e movimentação de estoque.
 */
public class FrmMenuPrincipal extends javax.swing.JFrame {


    /**
     * Construtor da tela de menu principal.
     * Inicia e configura todos os componentes visuais da tela.
     */
    public FrmMenuPrincipal() {
        initComponents();
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        menuItens = new javax.swing.JMenu();
        menuCadastros = new javax.swing.JMenu();
        itemCadastroProduto = new javax.swing.JMenuItem();
        menuConsultas = new javax.swing.JMenu();
        itemConsultaProduto = new javax.swing.JMenuItem();
        menuMovimentacao = new javax.swing.JMenu();
        itemMovimentacaoEstoque = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuSistema = new javax.swing.JMenu();
        itemSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menuItens.setText("Itens");

        menuCadastros.setText("Cadastros");

        itemCadastroProduto.setText("Produto");
        itemCadastroProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCadastroProdutoActionPerformed(evt);
            }
        });
        menuCadastros.add(itemCadastroProduto);

        menuItens.add(menuCadastros);

        menuConsultas.setText("Consultas");

        itemConsultaProduto.setText("Produto");
        itemConsultaProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemConsultaProdutoActionPerformed(evt);
            }
        });
        menuConsultas.add(itemConsultaProduto);

        menuItens.add(menuConsultas);

        menuMovimentacao.setText("Movimentação");

        itemMovimentacaoEstoque.setText("Estoque");
        itemMovimentacaoEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMovimentacaoEstoqueActionPerformed(evt);
            }
        });
        menuMovimentacao.add(itemMovimentacaoEstoque);

        menuItens.add(menuMovimentacao);
        menuItens.add(jSeparator1);

        menuSistema.setText("Sistema");

        itemSair.setText("Sair");
        itemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSairActionPerformed(evt);
            }
        });
        menuSistema.add(itemSair);

        menuItens.add(menuSistema);

        jMenuBar1.add(menuItens);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 546, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 119, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemCadastroProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCadastroProdutoActionPerformed
        FrmCadastroProduto telaCadastro = new FrmCadastroProduto();
        telaCadastro.setVisible(true);
    }//GEN-LAST:event_itemCadastroProdutoActionPerformed

    private void itemConsultaProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemConsultaProdutoActionPerformed
        FrmConsultaProduto telaConsulta = new FrmConsultaProduto();
        telaConsulta.setVisible(true);
    }//GEN-LAST:event_itemConsultaProdutoActionPerformed

    private void itemMovimentacaoEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMovimentacaoEstoqueActionPerformed
        FrmMovimentacaoEstoque telaMovimentacao = new FrmMovimentacaoEstoque();
        telaMovimentacao.setVisible(true);
    }//GEN-LAST:event_itemMovimentacaoEstoqueActionPerformed

    private void itemSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSairActionPerformed
        /*
 * Exibe uma caixa de diálogo para o usuário confirmar se deseja
 * realmente sair da aplicação. Se a resposta for "Sim", o programa
 * é encerrado.
 */
int resposta = javax.swing.JOptionPane.showConfirmDialog(this,
        "Deseja realmente sair do sistema?",
        "Confirmar Saída",
        javax.swing.JOptionPane.YES_NO_OPTION);
if (resposta == javax.swing.JOptionPane.YES_OPTION) {
    System.exit(0);
}
    }//GEN-LAST:event_itemSairActionPerformed

 /**
     * Método principal que serve como ponto de entrada para iniciar a aplicação.
     * <p>
     * Este método utiliza {@code java.awt.EventQueue.invokeLater} para garantir
     * que a criação e exibição da interface gráfica (a tela FrmMenuPrincipal)
     * ocorra na Event Dispatch Thread (EDT) do Swing. Esta é a prática
     * recomendada para garantir a segurança de threads em aplicações Swing.
     *
     * @param args os argumentos de linha de comando (não utilizados).
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new FrmMenuPrincipal().setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem itemCadastroProduto;
    private javax.swing.JMenuItem itemConsultaProduto;
    private javax.swing.JMenuItem itemMovimentacaoEstoque;
    private javax.swing.JMenuItem itemSair;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenu menuCadastros;
    private javax.swing.JMenu menuConsultas;
    private javax.swing.JMenu menuItens;
    private javax.swing.JMenu menuMovimentacao;
    private javax.swing.JMenu menuSistema;
    // End of variables declaration//GEN-END:variables
}
