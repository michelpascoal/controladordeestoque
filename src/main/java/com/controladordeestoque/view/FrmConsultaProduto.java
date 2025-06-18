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
 * Representa a tela (JFrame) para consulta e visualização de produtos.
 * <p>
 * Esta interface permite aos usuários buscar, filtrar e visualizar a lista
 * de produtos cadastrados com diferentes níveis de detalhe e aplicar
* destaques visuais com base em regras de negócio (como estoque e validade).
 */
public class FrmConsultaProduto extends javax.swing.JFrame {
    /**
     * Objeto de acesso a dados para realizar operações de busca de produtos.
     */
    private ProdutoDAO produtoDAO;
    /**
     * Modelo da tabela usado para manipular os dados exibidos na JTable.
     */
    private DefaultTableModel tableModel;


    /**
     * Construtor da tela de consulta de produtos.
     * Inicia os componentes visuais gerados pela IDE e chama a configuração
     * da lógica customizada da tela.
     */
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
        tblProdutos.setMaximumSize(new java.awt.Dimension(500, 80));
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
    /**
     * Configura a lógica inicial e os componentes customizados da tela de consulta.
     * <p>
     * Este método é responsável por:
     * <ul>
     * <li>Inicializar o DAO.</li>
     * <li>Criar e configurar um {@link DefaultTableModel} customizado que não permite edição de células e define os tipos de dados das colunas para ordenação correta.</li>
     * <li>Criar uma {@link JTable} customizada que sobrescreve o método de renderização para colorir as linhas de produtos com base na proximidade da data de validade.</li>
     * <li>Aplicar um formatador de data para a coluna de validade.</li>
     * <li>Carregar os dados iniciais na tabela.</li>
     * <li>Configurar os listeners de eventos para os botões.</li>
     * </ul>
     */
    private void inicializarLogicaConsulta() {
        System.out.println("FrmConsultaProduto: Iniciando inicializarLogicaConsulta()...");
        produtoDAO = new ProdutoDAO();

        String[] nomeColunas = {"Codigo", "Nome", "Descrição", "Quantidade", "Validade", "Categoria"};
        this.tableModel = new DefaultTableModel(nomeColunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 0 -> Integer.class;
                    case 3 -> Integer.class;
                    case 4 -> Date.class;
                    default -> String.class;
                };
            }
        };

        tblProdutos = new JTable() {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);

                if (getModel().getRowCount() == 0) {
                    return component;
                }

                int modelRow = convertRowIndexToModel(row);
                Object value = getModel().getValueAt(modelRow, 4);

                if (value instanceof Date validade) {
                    
                    LocalDate dataValidade = new java.sql.Date(validade.getTime()).toLocalDate();
                    
                    LocalDate hoje = LocalDate.now();
                    long diasParaVencer = ChronoUnit.DAYS.between(hoje, dataValidade);

                    if (!isRowSelected(row)) {
                        if (diasParaVencer <= 2) {
                            component.setBackground(new Color(255, 180, 180)); // Vermelho claro
                            component.setForeground(Color.BLACK);
                        } else if (diasParaVencer <= 7) {
                            component.setBackground(new Color(255, 220, 160)); // Laranja claro
                            component.setForeground(Color.BLACK);
                        } else {
                            component.setBackground(super.getBackground());
                            component.setForeground(super.getForeground());
                        }
                    } else {
                        component.setBackground(super.getSelectionBackground());
                        component.setForeground(super.getSelectionForeground());
                    }
                } else {
                    if (!isRowSelected(row)) {
                        component.setBackground(super.getBackground());
                        component.setForeground(super.getForeground());
                    }
                }
                return component;
            }
        };

        tblProdutos.setModel(this.tableModel);
        tblProdutos.setAutoCreateRowSorter(true);

        tblProdutos.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
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

        jScrollPane1.setViewportView(tblProdutos);

        carregarTodosProdutosNaTabela();
        configurarAcaoBotoesConsulta();
        configurarAcoesRelatorios();
        this.setTitle("Consulta de Produtos");
        this.setLocationRelativeTo(null);
    }

    /**
     * Busca todos os produtos do banco de dados e os carrega na tabela da interface.
     */
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

    /**
     * Configura o listener de evento para o botão de busca.
     */
    private void configurarAcaoBotoesConsulta() {
        
        btnBuscarProduto.addActionListener((ActionEvent e) -> {
            acaoBuscarProduto();
        });

        
    }

    /**
     * Contém a lógica para realizar a busca de um produto por ID.
     * Se o campo de busca estiver vazio, recarrega todos os produtos. Se o termo
     * de busca não for um número, exibe uma mensagem de aviso.
     */
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

    /**
     * Configura os listeners de eventos para os botões de geração de relatório.
     */
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

    /**
     * Gera e exibe o relatório de lista de preços em uma caixa de diálogo.
     */
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

    /**
     * Gera e exibe o relatório de produtos abaixo do estoque mínimo.
     */
    private void gerarRelatorioAbaixoMinimo() {
        List<String> relatorio = produtoDAO.gerarRelatorioAbaixoMinimo();
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
    
    /**
     * Executa a ação de busca de produto. Serve como um invólucro para o método de busca.
     */
    private void acaoBuscarProduto() {
         executarBusca();

    }
    
    /**
     * Gera e exibe o relatório de produtos acima do estoque máximo.
     */
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
    
    /**
     * Método principal para executar esta tela (JFrame) de forma independente.
     *
     * @param args os argumentos de linha de comando (não utilizados).
     */
    public static void main(String args[]) {
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
