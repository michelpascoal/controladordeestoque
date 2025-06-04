package com.controladordeestoque.view;

import com.controladordeestoque.dao.CategoriaDAO;
import com.controladordeestoque.dao.ProdutoDAO;
import com.controladordeestoque.model.Categoria;
import com.controladordeestoque.model.Produto;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.ZoneId;


/**
 *
 * @author juann
 */
public class FrmCadastroProduto extends javax.swing.JFrame {
    private ProdutoDAO produtoDAO;
    private CategoriaDAO categoriaDAO;

 
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
        btnSalvar.addActionListener((ActionEvent e) -> {
            acaoSalvarProduto();
        });
        btnVoltar.addActionListener((ActionEvent e) -> {
            acaoVoltarJanela();
        });
        btnLimpar.addActionListener((ActionEvent e) -> {
            acaoLimparCampos();
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
    // 1. Leitura de todos os campos da tela
    String textoCodigo = txtCodigo.getText().trim();
    String nomeProduto = txtNome.getText().trim();
    String descricaoProduto = txtDescrição.getText().trim();
    String textoQuantidade = txtQuantidade.getText().trim();
    String textoValidade = txtValidade.getText().trim();
    String textoPreco = txtPrecoUnitario.getText().trim().replace(",", ".");
    String textoQuantidadeMinima = txtQuantidadeMinima.getText().trim();
    String textoQuantidadeMaxima = txtQuantidadeMaxima.getText().trim();

    // 2. Validações de campos obrigatórios
    if (textoCodigo.isEmpty() || nomeProduto.isEmpty() || textoQuantidade.isEmpty() || textoPreco.isEmpty() || cbCategoria.getSelectedIndex() <= 0) {
        JOptionPane.showMessageDialog(this, "Os campos de Código, Nome, Quantidade, Preço e Categoria são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 3. Declaração das variáveis que serão preenchidas
    int idProduto, quantidadeProduto, quantidadeMinima, quantidadeMaxima;
    double precoUnitario;
    Date dataValidade = null;

    // 4. Conversão e Validação dos tipos de dados (tudo dentro de um único try-catch)
    try {
        idProduto = Integer.parseInt(textoCodigo);
        quantidadeProduto = Integer.parseInt(textoQuantidade);
        precoUnitario = Double.parseDouble(textoPreco);
        
        // Converte valores que podem ser vazios (mínimo e máximo), tratando como 0 se vazios
        quantidadeMinima = textoQuantidadeMinima.isEmpty() ? 0 : Integer.parseInt(textoQuantidadeMinima);
        quantidadeMaxima = textoQuantidadeMaxima.isEmpty() ? 0 : Integer.parseInt(textoQuantidadeMaxima);

        // Converte a data, somente se o campo não estiver vazio
        if (!textoValidade.isEmpty()) {
            SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
            formatadorData.setLenient(false);
            dataValidade = formatadorData.parse(textoValidade);
            LocalDate dataValidadeProduto = dataValidade.toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate();
LocalDate hoje = LocalDate.now();

// Verifica se a data do produto é anterior à data de hoje
    if (dataValidadeProduto.isBefore(hoje)) {
        JOptionPane.showMessageDialog(this, 
            "Não é possível cadastrar um produto com data de validade vencida.", 
            "Produto Vencido", 
            JOptionPane.ERROR_MESSAGE);
        txtValidade.requestFocus(); // Devolve o foco ao campo de validade
        return; // Interrompe a execução, impedindo o salvamento
    }
        }
    } catch (HeadlessException | NumberFormatException | ParseException e) {
        JOptionPane.showMessageDialog(this, "Erro de formato em um dos campos numéricos ou de data.\nUse números para campos de quantidade e dd/mm/aaaa para data.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (quantidadeMinima > 0 && quantidadeProduto < quantidadeMinima) { // Verifica se uma qtde. mínima foi definida
        JOptionPane.showMessageDialog(this, 
            "A Quantidade em estoque (" + quantidadeProduto + ") não pode ser menor que a Quantidade Mínima definida (" + quantidadeMinima + ").", 
            "Erro de Validação de Estoque", 
            JOptionPane.ERROR_MESSAGE);
        txtQuantidade.requestFocus(); // Coloca o foco no campo de quantidade
        return; // Impede o salvamento
    }

    // 5. Busca da Categoria selecionada
    String nomeCategoriaSelecionada = (String) cbCategoria.getSelectedItem();
    Categoria categoriaDoProduto = null;
    for (Categoria cat : categoriaDAO.listarTodas()) {
        if (cat.getNome().equals(nomeCategoriaSelecionada)) {
            categoriaDoProduto = cat;
            break;
        }
    }
     if (categoriaDoProduto == null) {
        JOptionPane.showMessageDialog(this, "Erro interno ao encontrar a categoria selecionada.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 6. Lógica para Salvar (novo) ou Atualizar (existente)
    Produto produtoJaExiste = produtoDAO.buscarPorId(idProduto);
    boolean foiSalvoComSucesso;

    if (produtoJaExiste != null) {
        // ATUALIZA um produto que já existe
        produtoJaExiste.setNome(nomeProduto);
        produtoJaExiste.setDescricao(descricaoProduto);
        produtoJaExiste.setQuantidade(quantidadeProduto);
        produtoJaExiste.setValidade(dataValidade);
        produtoJaExiste.setPrecoUnitario(precoUnitario);
        produtoJaExiste.setCategoria(categoriaDoProduto);
        produtoJaExiste.setQuantidadeMinima(quantidadeMinima);
        produtoJaExiste.setQuantidadeMaxima(quantidadeMaxima);
        foiSalvoComSucesso = produtoDAO.atualizar(produtoJaExiste);
    } else {
        // SALVA um produto novo
        Produto produtoParaSalvar = new Produto(idProduto, nomeProduto, descricaoProduto, quantidadeProduto, dataValidade, categoriaDoProduto, precoUnitario, quantidadeMinima, quantidadeMaxima);
        foiSalvoComSucesso = produtoDAO.salvar(produtoParaSalvar);
    }

    // 7. Mensagem final para o usuário
    if (foiSalvoComSucesso) {
        JOptionPane.showMessageDialog(this, "Produto salvo/atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        acaoLimparCampos();
    } else {
        JOptionPane.showMessageDialog(this, "Ocorreu um erro ao salvar/atualizar o produto.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    
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
        jLabel8 = new javax.swing.JLabel();
        txtQuantidadeMaxima = new javax.swing.JTextField();

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

        jLabel8.setText("Quantidade Maxima");

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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(txtQuantidadeMinima, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtQuantidadeMaxima, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(91, Short.MAX_VALUE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantidadeMaxima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCadastroProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(() -> {
            new FrmCadastroProduto().setVisible(true);
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescrição;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPrecoUnitario;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JTextField txtQuantidadeMaxima;
    private javax.swing.JTextField txtQuantidadeMinima;
    private javax.swing.JTextField txtValidade;
    // End of variables declaration//GEN-END:variables
}
