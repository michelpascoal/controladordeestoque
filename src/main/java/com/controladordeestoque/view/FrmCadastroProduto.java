package com.controladordeestoque.view;

import com.controladordeestoque.controller.ProdutoController;
import com.controladordeestoque.dao.CategoriaDAO;
import com.controladordeestoque.dao.ProdutoDAO;
import com.controladordeestoque.model.Categoria;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import com.controladordeestoque.model.Produto;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author juann
 */

public class FrmCadastroProduto extends javax.swing.JFrame {
private ProdutoController produtoController = new ProdutoController();
   
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

private void acaoVoltarJanela() {
    this.dispose();
}


public Produto buscarProdutoPorId(int id) {
    return produtoDAO.buscarPorId(id);

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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProdutos = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        cbCategoria = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Categoria");

        txtNome.setMinimumSize(new java.awt.Dimension(66, 22));
        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        txtCodigo.setMinimumSize(new java.awt.Dimension(66, 22));
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        txtQuantidade.setMinimumSize(new java.awt.Dimension(66, 22));
        txtQuantidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantidadeActionPerformed(evt);
            }
        });

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnVoltar.setText("Voltar");

        txtDescrição.setMinimumSize(new java.awt.Dimension(66, 22));

        txtValidade.setMinimumSize(new java.awt.Dimension(66, 22));

        jLabel1.setText("Nome");

        jLabel2.setText("Codigo");

        jLabel3.setText("Descrição");

        jLabel4.setText("Validade");

        jLabel5.setText("Quantidade");

        jLabel6.setText("Preço Unitário");

        txtPrecoUnitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecoUnitarioActionPerformed(evt);
            }
        });

        jLabel7.setText("Quantidade Mínima");

        txtQuantidadeMinima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantidadeMinimaActionPerformed(evt);
            }
        });

        jLabel8.setText("Quantidade Maxima");

        txtQuantidadeMaxima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantidadeMaximaActionPerformed(evt);
            }
        });

        tblProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nome", "Preço Unitario", "Quantidade", "Validade", "Categoria"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblProdutos.setToolTipText("");
        tblProdutos.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tblProdutosAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(tblProdutos);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        cbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Categoria" }));
        cbCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoriaActionPerformed(evt);
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
                                        .addComponent(txtDescrição, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnExcluir)
                                .addGap(28, 28, 28)
                                .addComponent(btnEditar)
                                .addGap(26, 26, 26)
                                .addComponent(btnSalvar)
                                .addGap(26, 26, 26)
                                .addComponent(btnLimpar)
                                .addGap(28, 28, 28)
                                .addComponent(btnVoltar)
                                .addGap(8, 8, 8)))
                        .addGap(64, 64, 64))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtQuantidade, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
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
                            .addComponent(txtQuantidadeMaxima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnLimpar)
                    .addComponent(btnVoltar)
                    .addComponent(btnEditar)
                    .addComponent(btnExcluir))
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed

    private void txtQuantidadeMinimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantidadeMinimaActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantidadeMinimaActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
try {
    Produto produto = new Produto();
    produto.setId(Integer.parseInt(txtCodigo.getText())); // ou gere o ID automaticamente
    produto.setNome(txtNome.getText());
    produto.setDescricao(txtDescrição.getText());
    produto.setPreco(Double.parseDouble(txtPrecoUnitario.getText()));
    produto.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
    produto.setQuantidadeMinima(Integer.parseInt(txtQuantidadeMinima.getText()));
    produto.setQuantidadeMaxima(Integer.parseInt(txtQuantidadeMaxima.getText()));


    // Pegando a data de validade
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date validade = sdf.parse(txtValidade.getText());
    produto.setValidade(validade);

    // Pegando a categoria selecionada
    Categoria categoria = (Categoria) cbCategoria.getSelectedItem();
    produto.setCategoria(categoria);

   if (produtoController.cadastrarProduto(produto)) {


        JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
        btnLimpar();
        listarProdutos();

    } else {
        JOptionPane.showMessageDialog(this, "Erro ao cadastrar produto.");
    }
} catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
}
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimparActionPerformed

    private void txtQuantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantidadeActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantidadeActionPerformed

    private void txtPrecoUnitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecoUnitarioActionPerformed
try {
    double preco = Double.parseDouble(txtPrecoUnitario.getText());
    ProdutoController.setPrecoUnitario(preco);
} catch (NumberFormatException ex) {
    JOptionPane.showMessageDialog(this, "Preço inválido. Digite um número.");
    return;
}
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoUnitarioActionPerformed

    private void txtQuantidadeMaximaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantidadeMaximaActionPerformed
try {
    int qtd = Integer.parseInt(txtQuantidade.getText());
    ProdutoController.setQuantidadeMaxima(qtd);
} catch (NumberFormatException ex) {
    JOptionPane.showMessageDialog(this, "Quantidade inválida. Digite um número inteiro.");
    return;
}
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantidadeMaximaActionPerformed

    private void tblProdutosAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblProdutosAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProdutosAncestorAdded

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

    int linhaSelecionada = tblProdutos.getSelectedRow();
    if (linhaSelecionada == -1) {
        JOptionPane.showMessageDialog(this, "Selecione um produto para editar.");
        return;
    }

    int id = (int) tblProdutos.getValueAt(linhaSelecionada, 0);
    Produto p = produtoController.buscarProdutoPorId(id);

    if (p != null) {
        txtCodigo.setText(String.valueOf(p.getId()));
        txtNome.setText(p.getNome());
        txtDescrição.setText(p.getDescricao());
        txtPrecoUnitario.setText(String.valueOf(p.getPrecoUnitario()));
        txtQuantidade.setText(String.valueOf(p.getQuantidade()));
        txtQuantidadeMinima.setText(String.valueOf(p.getQuantidadeMinima()));
        txtQuantidadeMaxima.setText(String.valueOf(p.getQuantidadeMaxima()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtValidade.setText(sdf.format(p.getValidade()));
        cbCategoria.setSelectedItem(p.getCategoria());
    } else {
        JOptionPane.showMessageDialog(this, "Produto não encontrado.");
    }
    // TODO add your handling code here:
}//GEN-LAST:event_btnEditarActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed

    int linhaSelecionada = tblProdutos.getSelectedRow();
    if (linhaSelecionada == -1) {
        JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        int id = (int) tblProdutos.getValueAt(linhaSelecionada, 0); // coluna 0 = ID
        boolean removido = produtoController.removerProduto(id);
        if (removido) {
            JOptionPane.showMessageDialog(this, "Produto excluído com sucesso.");
            listarProdutos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir produto.");
        }
    }
    // TODO add your handling code here:
}//GEN-LAST:event_btnExcluirActionPerformed

    private void cbCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCategoriaActionPerformed
private void btnLimpar() {
    txtCodigo.setText("");
    txtNome.setText("");
    txtDescrição.setText("");
    txtPrecoUnitario.setText("");
    txtQuantidade.setText("");
    txtQuantidadeMinima.setText("");
    txtQuantidadeMaxima.setText("");
    txtValidade.setText("");
    cbCategoria.setSelectedIndex(0);
}

private void acaoLimparCampos() {
    btnLimpar();
}

   
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
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProdutos;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescrição;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPrecoUnitario;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JTextField txtQuantidadeMaxima;
    private javax.swing.JTextField txtQuantidadeMinima;
    private javax.swing.JTextField txtValidade;
    // End of variables declaration//GEN-END:variables

private void listarProdutos() {
    DefaultTableModel modelo = (DefaultTableModel) tblProdutos.getModel();
    modelo.setRowCount(0);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    for (Produto p : produtoController.listarProdutos()) {
        modelo.addRow(new Object[]{
            p.getId(),
            p.getNome(),
            p.getPrecoUnitario(),
            p.getQuantidade(),
            sdf.format(p.getValidade()),
            p.getCategoria().getNome()
        });
    }
}

}
