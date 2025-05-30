/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.controladordeestoque.view;

import com.controladordeestoque.dao.CategoriaDAO;
import com.controladordeestoque.dao.ProdutoDAO;
import com.controladordeestoque.model.Categoria;
import com.controladordeestoque.model.Produto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane; 


/**
 *
 * @author juann
 */
public class FrmCadastroProduto extends javax.swing.JFrame {
    private ProdutoDAO produtoDAO;
    private CategoriaDAO categoriaDAO;

    /**
     * Creates new form FrmCadastroProduto
     */
    public FrmCadastroProduto() {
    initComponents();
    inicializarLogicaTela();
}
    private void inicializarLogicaTela() {
        produtoDAO = new ProdutoDAO();
        categoriaDAO = new CategoriaDAO();
        carregarCategoriasNaTela();
        configurarAcoesBotoes();
        this.setLocationRelativeTo(null);
}
    private void carregarCategoriasNaTela() {
        List<Categoria> listaDeCategorias = categoriaDAO.listarTodas();
        cbCategoria.removeAllItems(); 
        cbCategoria.addItem("Categoria"); 
        if (listaDeCategorias != null) {
            for (Categoria cat : listaDeCategorias) {
                cbCategoria.addItem(cat.getNome());
        }
    }
}
    private void configurarAcoesBotoes() {
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoSalvarProduto();
        }
    });
        btnVoltar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    acaoVoltarJanela();
                }
            });
        btnLimpar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            acaoLimparCampos();
        }
    });
}
    private void acaoLimparCampos() {
    txtCodigo.setText("");
    txtNome.setText(""); 
    txtQuantidade.setText("");
    txtDescrição.setText(""); 
    txtQuantidade.setText("");
    txtValidade.setText("");
    cbCategoria.setSelectedIndex(0); 
    txtCodigo.requestFocus();
}

private void acaoVoltarJanela() {
    this.dispose();
}

private void acaoSalvarProduto() {
    String textoCodigo = txtCodigo.getText().trim();
    String nomeProduto = txtNome.getText().trim();
    String descricaoProduto = txtDescrição.getText().trim();  
    String textoQuantidade = txtQuantidade.getText().trim();
    String textoValidade = txtValidade.getText().trim();
    String textoPreco = txtPrecoUnitario.getText().trim();
    String textoQuantidadeMinima = txtQuantidadeMinima.getText().trim();
    
    String nomeCategoriaSelecionada = null;
    if (textoPreco.isEmpty()) {
    JOptionPane.showMessageDialog(this, "O Preço Unitário é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
    txtPrecoUnitario.requestFocus();
    return;
    }
    if (cbCategoria.getSelectedIndex() > 0 && cbCategoria.getSelectedItem() != null) { 
         nomeCategoriaSelecionada = (String) cbCategoria.getSelectedItem();
    }

    if (textoCodigo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "O Código é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
        txtCodigo.requestFocus();
        return;
    }
    if (nomeProduto.isEmpty()) {
        JOptionPane.showMessageDialog(this, "O Nome é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
        txtNome.requestFocus();
        return;
    }
    if (textoQuantidade.isEmpty()) {
        JOptionPane.showMessageDialog(this, "A Quantidade é obrigatória.", "Erro", JOptionPane.ERROR_MESSAGE);
        txtQuantidade.requestFocus();
        return;
    }
    if (nomeCategoriaSelecionada == null) {
        JOptionPane.showMessageDialog(this, "Selecione uma Categoria válida.", "Erro", JOptionPane.ERROR_MESSAGE);
        cbCategoria.requestFocus();
        return;
    }

    Date dataValidade = null;
    if (!textoValidade.isEmpty()) {
        try {
            SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
            formatadorData.setLenient(false);
            dataValidade = formatadorData.parse(textoValidade);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de Validade inválido. Use dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
            txtValidade.requestFocus();
            return;
        }
    // Conversão do campo de preço unitário
    double precoUnitario = 0.0;
        try {
            precoUnitario = Double.parseDouble(textoPreco.replace(",", "."));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido. Use ponto para decimais (ex: 10.50).", "Erro", JOptionPane.ERROR_MESSAGE);
            txtPrecoUnitario.requestFocus();
            return;
        }
    }
    
    int quantidadeMinimaProduto = Integer.parseInt(textoQuantidadeMinima);

    int idProduto;
    try {
        idProduto = Integer.parseInt(textoCodigo);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Código inválido. Deve ser um número.", "Erro", JOptionPane.ERROR_MESSAGE);
        txtCodigo.requestFocus();
        return;
    }

    int quantidadeProduto;
    try {
        quantidadeProduto = Integer.parseInt(textoQuantidade);
        if (quantidadeProduto < 0) {
            JOptionPane.showMessageDialog(this, "Quantidade não pode ser negativa.", "Erro", JOptionPane.ERROR_MESSAGE);
            txtQuantidade.requestFocus();
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Quantidade inválida. Deve ser um número.", "Erro", JOptionPane.ERROR_MESSAGE);
        txtQuantidade.requestFocus();
        return;
    }
    
    int quantidadeMinima = 0;
    try {
        quantidadeMinima = Integer.parseInt(txtQuantidadeMinima.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Quantidade mínima inválida. Use apenas números inteiros.", "Erro", JOptionPane.ERROR_MESSAGE);
        txtQuantidadeMinima.requestFocus();
        return;   
}


    Categoria categoriaDoProduto = null;
    List<Categoria> todasCategorias = categoriaDAO.listarTodas();
    if (todasCategorias != null) {
        for (Categoria cat : todasCategorias) {
            if (cat.getNome().equals(nomeCategoriaSelecionada)) {
                categoriaDoProduto = cat;
                break;
            }
        }
    }

    if (categoriaDoProduto == null) {
        JOptionPane.showMessageDialog(this, "Categoria selecionada não encontrada na base de dados.", "Erro Interno", JOptionPane.ERROR_MESSAGE);
        return;
    }
    // Conversão do campo de preço unitário
    double precoUnitario = 0.0;
    try {
        precoUnitario = Double.parseDouble(txtPrecoUnitario.getText().trim().replace(",", "."));
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Preço inválido. Use ponto para decimais (ex: 10.50).", "Erro", JOptionPane.ERROR_MESSAGE);
        txtPrecoUnitario.requestFocus();
        return;
    }

    Produto produtoParaSalvar = new Produto(idProduto, nomeProduto, descricaoProduto, quantidadeProduto, dataValidade, categoriaDoProduto, precoUnitario, quantidadeMinima);
    Produto produtoJaExiste = produtoDAO.buscarPorId(idProduto);
    boolean foiSalvoComSucesso;

    if (produtoJaExiste != null) {
        produtoJaExiste.setNome(produtoParaSalvar.getNome());
        produtoJaExiste.setDescricao(produtoParaSalvar.getDescricao());
        produtoJaExiste.setQuantidade(produtoParaSalvar.getQuantidade());
        produtoJaExiste.setValidade(produtoParaSalvar.getValidade());
        produtoJaExiste.setCategoria(produtoParaSalvar.getCategoria());
        foiSalvoComSucesso = produtoDAO.atualizar(produtoJaExiste);
        if (foiSalvoComSucesso) {
            JOptionPane.showMessageDialog(this, "Produto atualizado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            acaoLimparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        foiSalvoComSucesso = produtoDAO.salvar(produtoParaSalvar);
        if (foiSalvoComSucesso) {
            JOptionPane.showMessageDialog(this, "Produto salvo!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            acaoLimparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
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

        txtNome = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        txtQuantidade = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        txtDescrição = new javax.swing.JTextField();
        txtValidade = new javax.swing.JTextField();
        cbCategoria = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPrecoUnitario = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtQuantidadeMinima = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Categoria");

        txtNome.setMinimumSize(new java.awt.Dimension(66, 22));
        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        txtCodigo.setMinimumSize(new java.awt.Dimension(66, 22));

        txtQuantidade.setMinimumSize(new java.awt.Dimension(66, 22));

        btnSalvar.setText("Salvar");

        btnLimpar.setText("Limpar");

        btnVoltar.setText("Voltar");

        txtDescrição.setMinimumSize(new java.awt.Dimension(66, 22));

        txtValidade.setMinimumSize(new java.awt.Dimension(66, 22));

        cbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Categoria", " " }));
        cbCategoria.setToolTipText("");
        cbCategoria.setName("Categoria"); // NOI18N
        cbCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoriaActionPerformed(evt);
            }
        });

        jLabel1.setText("Nome");

        jLabel2.setText("Codigo");

        jLabel3.setText("Descrição");

        jLabel4.setText("Validade");

        jLabel5.setText("Quantidade");

        jLabel6.setText("Preço Unitário");

        jLabel7.setText("Quantidade Mínima");

        txtQuantidadeMinima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantidadeMinimaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(47, 47, 47))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(39, 39, 39)
                                        .addComponent(jLabel3))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDescrição, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSalvar)
                                .addGap(26, 26, 26)
                                .addComponent(btnLimpar)
                                .addGap(28, 28, 28)
                                .addComponent(btnVoltar)
                                .addGap(8, 8, 8)))
                        .addGap(64, 64, 64))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtQuantidade, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtPrecoUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtQuantidadeMinima, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDescrição, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecoUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantidadeMinima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnLimpar)
                    .addComponent(btnVoltar))
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed

    private void cbCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCategoriaActionPerformed

    private void txtQuantidadeMinimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantidadeMinimaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantidadeMinimaActionPerformed

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
            java.util.logging.Logger.getLogger(FrmCadastroProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCadastroProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCadastroProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCadastroProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmCadastroProduto().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> cbCategoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescrição;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPrecoUnitario;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JTextField txtQuantidadeMinima;
    private javax.swing.JTextField txtValidade;
    // End of variables declaration//GEN-END:variables
}
