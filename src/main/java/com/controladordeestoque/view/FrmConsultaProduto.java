package com.controladordeestoque.view;

import com.controladordeestoque.dao.ProdutoDAO;
import com.controladordeestoque.model.Produto;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import javax.swing.JTable;

/**
 *
 * @author juann
 */
public class FrmConsultaProduto extends javax.swing.JFrame {
    private ProdutoDAO produtoDAO;
    private DefaultTableModel tableModel;

 
    public FrmConsultaProduto() {
        initComponents();
        inicializarLogicaConsulta();
        
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBuscaProduto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProdutos = new javax.swing.JTable();
        btnBuscarProduto = new javax.swing.JButton();
        btnListaPrecos = new javax.swing.JButton();
        btnAbaixoMinimo = new javax.swing.JButton();
        btnAcimaMaximo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtBuscaProduto.setAutoscrolls(false);
        txtBuscaProduto.setMinimumSize(new java.awt.Dimension(75, 23));
        txtBuscaProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscaProdutoActionPerformed(evt);
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
                "Codigo", "Nome", "Descrição", "Quantidade", "Validade", "Categoria"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Short.class, java.lang.String.class, java.lang.String.class, java.lang.Short.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProdutos.setOpaque(false);
        tblProdutos.setPreferredSize(new java.awt.Dimension(486, 267));
        tblProdutos.setShowGrid(false);
        jScrollPane1.setViewportView(tblProdutos);

        btnBuscarProduto.setText("Buscar");
        btnBuscarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProdutoActionPerformed(evt);
            }
        });

        btnListaPrecos.setText("Lista de Preços");

        btnAbaixoMinimo.setText("Abaixo do Mínimo");

        btnAcimaMaximo.setText("Acima do Maximo");
        btnAcimaMaximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcimaMaximoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(txtBuscaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscarProduto)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 112, Short.MAX_VALUE)
                .addComponent(btnAbaixoMinimo)
                .addGap(18, 18, 18)
                .addComponent(btnListaPrecos)
                .addGap(18, 18, 18)
                .addComponent(btnAcimaMaximo)
                .addGap(108, 108, 108))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarProduto)
                    .addComponent(txtBuscaProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAbaixoMinimo)
                    .addComponent(btnListaPrecos)
                    .addComponent(btnAcimaMaximo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscaProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscaProdutoActionPerformed
        
    }//GEN-LAST:event_txtBuscaProdutoActionPerformed

    private void btnBuscarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProdutoActionPerformed
        executarBusca();
    }//GEN-LAST:event_btnBuscarProdutoActionPerformed

    private void btnAcimaMaximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcimaMaximoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAcimaMaximoActionPerformed
    private void inicializarLogicaConsulta() {
    System.out.println("FrmConsultaProduto: Iniciando inicializarLogicaConsulta()...");
    produtoDAO = new ProdutoDAO();

    // 1. Define o modelo de dados com os tipos de coluna corretos para ordenação
    String[] nomeColunas = {"Codigo", "Nome", "Descrição", "Quantidade", "Validade", "Categoria"};
    this.tableModel = new DefaultTableModel(nomeColunas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return switch (columnIndex) {
                case 0 -> Integer.class; // Codigo
                case 3 -> Integer.class; // Quantidade
                case 4 -> Date.class; // Validade
                default -> String.class;
            }; 
            
            
        }
    };

    // 2. Cria uma nova tabela customizada com a lógica para pintar as linhas
    JTable tabelaCustomizada;
        tabelaCustomizada = new JTable() {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                
                if (getModel().getRowCount() == 0) {
                    return component;
                }
                
                int modelRow = convertRowIndexToModel(row);
                Object value = getModel().getValueAt(modelRow, 4); // Coluna 4 = Validade
                
                if (value instanceof Date validade) {
                    LocalDate dataValidade = validade.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate hoje = LocalDate.now();
                    long diasParaVencer = ChronoUnit.DAYS.between(hoje, dataValidade);
                    
                    if (!isRowSelected(row)) {
                        // Produtos já vencidos ou vencendo em até 2 dias
                        if (diasParaVencer <= 2) {
                            component.setBackground(new Color(255, 180, 180)); // Vermelho claro
                            component.setForeground(Color.BLACK);
                            // Produtos vencendo em até 7 dias
                        } else if (diasParaVencer <= 7) {
                            component.setBackground(new Color(255, 220, 160)); // Laranja claro
                            component.setForeground(Color.BLACK);
                        } else {
                            // Cor padrão para os demais
                            component.setBackground(super.getBackground());
                            component.setForeground(super.getForeground());
                        }
                    } else {
                        // Mantém as cores de seleção do sistema
                        component.setBackground(super.getSelectionBackground());
                        component.setForeground(super.getSelectionForeground());
                    }
                } else { // Caso não tenha data
                    if (!isRowSelected(row)) {
                        component.setBackground(super.getBackground());
                        component.setForeground(super.getForeground());
                    }
                }
                return component;
            }
        };

    // 3. Configura a nova tabela
    tabelaCustomizada.setModel(this.tableModel);
    tabelaCustomizada.setAutoCreateRowSorter(true); // Habilita a ordenação

    // Configura o renderizador para exibir a data no formato dd/MM/yyyy
    tabelaCustomizada.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
        private final SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        @Override
        public void setValue(Object value) {
            if (value instanceof Date date) {
                setText(formatador.format(date));
            } else {
                setText("");
            }
        }
    });

    // 4. Substitui a tabela do NetBeans pela tabela customizada (a tabela original não pode ser pintada, pelo menos não descobri como)
    jScrollPane1.setViewportView(tabelaCustomizada);
    this.tblProdutos = tabelaCustomizada; // A variável de instância agora aponta para a nova tabela

  
    carregarTodosProdutosNaTabela();
    configurarAcaoBotoesConsulta();
    configurarAcoesRelatorios();
    this.setTitle("Consulta de Produtos");
    this.setLocationRelativeTo(null);
}

   
    
private void carregarTodosProdutosNaTabela() {
    System.out.println("FrmConsultaProduto: Iniciando carregarTodosProdutosNaTabela()...");
    tableModel.setRowCount(0); 

    List<Produto> listaDeProdutos = produtoDAO.listarTodos();

    if (listaDeProdutos != null) {
        System.out.println("FrmConsultaProduto: Produtos encontrados no DAO: " + listaDeProdutos.size());
        for (Produto p : listaDeProdutos) {
            String nomeCategoria = (p.getCategoria() != null) ? p.getCategoria().getNome() : "";

            Object[] dadosDaLinha = {
                p.getId(),
                p.getNome(),
                p.getDescricao(),
                p.getQuantidade(),
                p.getValidade(), 
                nomeCategoria
            };
            tableModel.addRow(dadosDaLinha);
        }
    } else {
        System.out.println("FrmConsultaProduto: listaDeProdutos é null."); 
    }
    System.out.println("FrmConsultaProduto: Finalizou carregarTodosProdutosNaTabela().");
}

    private void configurarAcaoBotoesConsulta() {
        
        btnBuscarProduto.addActionListener((ActionEvent e) -> {
            acaoBuscarProduto();
        });

        
    }

    
    private void executarBusca() {
    String termoBusca = txtBuscaProduto.getText().trim();

    if (termoBusca.isEmpty()) {
        carregarTodosProdutosNaTabela();
        return;
    }

    try {
        int idParaBuscar = Integer.parseInt(termoBusca);
        Produto produtoEncontrado = produtoDAO.buscarPorId(idParaBuscar);

        tableModel.setRowCount(0); 

        if (produtoEncontrado != null) {
            String nomeCategoria = produtoEncontrado.getCategoria() != null ? produtoEncontrado.getCategoria().getNome() : "";

            Object[] dadosDaLinha = {
                produtoEncontrado.getId(),
                produtoEncontrado.getNome(),
                produtoEncontrado.getDescricao(),
                produtoEncontrado.getQuantidade(),
                produtoEncontrado.getValidade(), 
                nomeCategoria
            };
            tableModel.addRow(dadosDaLinha);
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum produto encontrado com o código: " + idParaBuscar, "Busca sem Resultados", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Para buscar por código, digite um número.", "Tipo de Busca", JOptionPane.WARNING_MESSAGE);
    }
}


    private void configurarAcoesRelatorios() {
        btnListaPrecos.addActionListener((ActionEvent e) -> {
            gerarRelatorioListaPrecos();
        });

      
        btnAbaixoMinimo.addActionListener((ActionEvent e) -> {
            gerarRelatorioAbaixoMinimo();
        });
        btnAcimaMaximo.addActionListener((ActionEvent e) -> {
            gerarRelatorioAcimaMaximo();
        });
    }

   
    private void gerarRelatorioListaPrecos() {
        List<String> relatorio = produtoDAO.gerarRelatorioListaPrecos(); //
        if (relatorio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum produto cadastrado para gerar o relatório.", "Relatório Vazio", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

     
        StringBuilder relatorioCompleto = new StringBuilder("=== LISTA DE PREÇOS ===\n\n");
        for (String linha : relatorio) {
            relatorioCompleto.append(linha).append("\n");
        }

     
        javax.swing.JTextArea textArea = new javax.swing.JTextArea(relatorioCompleto.toString());
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Relatório de Lista de Preços", JOptionPane.INFORMATION_MESSAGE);
    }

   
    private void gerarRelatorioAbaixoMinimo() {
        List<String> relatorio = produtoDAO.gerarRelatorioAbaixoMinimo(); //
        if (relatorio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum produto abaixo do estoque mínimo.", "Relatório", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder relatorioCompleto = new StringBuilder("=== PRODUTOS ABAIXO DO ESTOQUE MÍNIMO ===\n\n");
        for (String linha : relatorio) {
            relatorioCompleto.append(linha).append("\n");
        }

        javax.swing.JTextArea textArea = new javax.swing.JTextArea(relatorioCompleto.toString());
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Relatório de Estoque Mínimo", JOptionPane.WARNING_MESSAGE);
    }
   
    private void acaoBuscarProduto() {
         executarBusca();

}
    private void gerarRelatorioAcimaMaximo() {
    List<String> relatorio = produtoDAO.gerarRelatorioAcimaMaximo();
    if (relatorio.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nenhum produto acima do estoque máximo.", "Relatório", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    StringBuilder relatorioCompleto = new StringBuilder("=== PRODUTOS ACIMA DO ESTOQUE MÁXIMO ===\n\n");
    for (String linha : relatorio) {
        relatorioCompleto.append(linha).append("\n");
    }

   
    javax.swing.JTextArea textArea = new javax.swing.JTextArea(relatorioCompleto.toString());
    javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(textArea);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setEditable(false);
    scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
    JOptionPane.showMessageDialog(this, scrollPane, "Relatório de Estoque Máximo", JOptionPane.WARNING_MESSAGE);
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
            java.util.logging.Logger.getLogger(FrmConsultaProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FrmConsultaProduto().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbaixoMinimo;
    private javax.swing.JButton btnAcimaMaximo;
    private javax.swing.JButton btnBuscarProduto;
    private javax.swing.JButton btnListaPrecos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProdutos;
    private javax.swing.JTextField txtBuscaProduto;
    // End of variables declaration//GEN-END:variables
}
