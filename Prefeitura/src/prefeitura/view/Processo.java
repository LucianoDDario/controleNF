/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package prefeitura.view;

import java.awt.FocusTraversalPolicy;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import prefeitura.controllers.*;
import prefeitura.entities.Protocolo;

/**
 *
 * @author lucia
 */
public class Processo extends javax.swing.JFrame {

    EntityManagerFactory factory;
    ProcessoJpaController processoController;
    FornecedorJpaController fornecedorController;
    ProtocoloJpaController protocoloController;
    
    

    public Processo() {
        initComponents();
        abrirConexao();
        atualizarTabela();
        limpar();
        atualizarTabelaFornecedor();
        
        
    }

    public void campoFornecedor() {

    }

    private void abrirConexao() {
        try {
            factory = Persistence.createEntityManagerFactory("PrefeituraFuncionandoPU");            
            processoController = new ProcessoJpaController(factory);
            fornecedorController = new FornecedorJpaController(factory);
            protocoloController = new ProtocoloJpaController(factory);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    private void limpar() {

        jTextField1.setText("");
        jTextField2.setText("");
        jFormattedTextField1.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        atualizarTabela();
        formatarCampo();
        colocarData();

        jButton3.setEnabled(true);
        jButton4.setEnabled(false);
        jButton6.setEnabled(true);
        jButton5.setEnabled(false);
        jTextField4.requestFocus();
        jTextField4.setEnabled(true);

        
        jTextField5.setEnabled(false);
        jTextField1.setEnabled(false);
        jTextField2.setEnabled(false);
        jFormattedTextField1.setEnabled(false);
        jTextField7.setEnabled(false);
        jButton2.setEnabled(false);
        jComboBox1.setEnabled(false);

    }

    private void colocarData() {
        jFormattedTextField1.setText(String.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
    }

    private void atualizarTabela() {

        ((DefaultTableModel) jTable1.getModel()).setRowCount(0);
        try {
            List<prefeitura.entities.Processo> processos = processoController.findProcessoEntities();

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            for (prefeitura.entities.Processo processo : processos) {
                prefeitura.entities.Oficio oficio = processo.getIdOficio();
                prefeitura.entities.Protocolo protocolo = processo.getIdProtocolo();

                String dataFormatada = formato.format(processo.getDataSaidaCompras());
                String linha[] = {
                    String.valueOf(protocolo.getNumeroProtocolo()),
                    String.valueOf(processo.getNumeroOficio()),
                    String.valueOf(processo.getNumeroProcesso()),
                    dataFormatada,
                    String.valueOf(processo.getTipoProcesso()),
                    String.valueOf(oficio.getIdSecretaria()),
                    oficio.getDescricao(),
                    String.valueOf(processo.getIdFornecedor())
                };
                ((DefaultTableModel) jTable1.getModel()).addRow(linha);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    private void atualizarTabelaFornecedor() {
        ((DefaultTableModel) jTable2.getModel()).setRowCount(0);
        try {
            List<prefeitura.entities.Fornecedor> fornecedores = fornecedorController.findFornecedorEntities();
            for (prefeitura.entities.Fornecedor fornecedor : fornecedores) {
                String linha[] = {
                    String.valueOf(fornecedor.getIdFornecedor()),
                    String.valueOf(fornecedor.getCnpj()),
                    String.valueOf(fornecedor.getNomeEmpresa())

                };
                ((DefaultTableModel) jTable2.getModel()).addRow(linha);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    private void procuraFornecedor() {
        String cnpjEmpresa = jTextField8.getText();

        ((DefaultTableModel) jTable2.getModel()).setRowCount(0);

        try {

            if (cnpjEmpresa.isEmpty()) {
                throw new Exception("Preencha o campo de CNPJ");
            }
            List<prefeitura.entities.Fornecedor> fornecedores = fornecedorController.findFornecedorEntities();
            for (prefeitura.entities.Fornecedor fornecedor : fornecedores) {
                if (cnpjEmpresa.equalsIgnoreCase(String.valueOf(fornecedor.getCnpj()))) {
                    String linha[] = {
                        String.valueOf(fornecedor.getIdFornecedor()),
                        String.valueOf(fornecedor.getCnpj()),
                        String.valueOf(fornecedor.getNomeEmpresa())

                    };
                    ((DefaultTableModel) jTable2.getModel()).addRow(linha);

                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    private Protocolo acharProtocolo(Integer numeroProtocolo) {

        try {

            List<Protocolo> protocolos = protocoloController.findProtocoloEntities();
            for (Protocolo protocolo : protocolos) {
                if (numeroProtocolo.equals(protocolo.getNumeroProtocolo())) {

                    return protocolo;
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private prefeitura.entities.Processo acharProcesso(Integer numeroProcesso) {

        try {
            List<prefeitura.entities.Processo> processos = processoController.findProcessoEntities();
            for (prefeitura.entities.Processo processo : processos) {
                if (numeroProcesso.equals(processo.getNumeroProcesso())) {
                    return processo;
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

        
     

    private void formatarCampo() {
        try {
            MaskFormatter formatter = new MaskFormatter("##/##/####");
            formatter.setPlaceholderCharacter('_');
            jFormattedTextField1.setFormatterFactory(new DefaultFormatterFactory(formatter));

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
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

        jDialog1 = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jTextField8 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jTextField3 = new javax.swing.JTextField();

        jDialog1.setTitle("Pesquisa fornecedor");
        jDialog1.setMinimumSize(new java.awt.Dimension(572, 244));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CNPJ", "Nome da Empresa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable2);

        jButton7.setText("jButton1");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(386, 386, 386))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog1Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Processo");
        setPreferredSize(new java.awt.Dimension(791, 516));

        jLabel2.setText("Número do Processo");

        jTextField1.setEnabled(false);

        jTextField2.setEnabled(false);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Data");

        jButton1.setText("Voltar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jButton3.setText("Inserir");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel5.setText("Protocolo Ofício");
        jLabel5.setRequestFocusEnabled(false);

        jButton4.setText("Deletar");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Alterar");
        jButton5.setEnabled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Limpar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Protocolo", "Oficio", "Número", "Data", "Tipo", "Secretaria", "Descrição", "Fornecedor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setToolTipText("");
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable1);

        jLabel1.setText("Ofício");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dispensa", "Inexibilidade" }));
        jComboBox1.setEnabled(false);

        jLabel6.setText("Tipo");

        jLabel8.setText("Secretaria");

        jTextField5.setEditable(false);
        jTextField5.setEnabled(false);

        jLabel9.setText("Descrição");

        jTextField6.setEnabled(false);

        jLabel10.setText("Fornecedor");

        jTextField7.setEnabled(false);
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jFormattedTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFormattedTextField1MouseClicked(evt);
            }
        });

        jTextField3.setText("jTextField3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                            .addComponent(jTextField2)
                                            .addComponent(jTextField5)
                                            .addComponent(jFormattedTextField1))
                                        .addGap(57, 57, 57)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(74, 74, 74)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jComboBox1, 0, 145, Short.MAX_VALUE)
                                            .addComponent(jTextField1)
                                            .addComponent(jTextField6)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 175, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap(28, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jButton4)
                                .addGap(12, 12, 12)
                                .addComponent(jButton5)
                                .addGap(12, 12, 12)
                                .addComponent(jButton6))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        dispose();
        new Inicio().setVisible(true);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String dataStr = jFormattedTextField1.getText();
        String numeroProcesso = jTextField2.getText();
        String numeroOficio = jTextField5.getText();
        String tipoProcesso = (String) jComboBox1.getSelectedItem();
        String numeroProtocolo = jTextField4.getText();

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        try {
            if (dataStr.isEmpty() || numeroProcesso.isEmpty()) {
                throw new Exception("Preencha todos os campos");
            }

            prefeitura.entities.Protocolo protocolo = acharProtocolo(Integer.valueOf(numeroProtocolo));

            prefeitura.entities.Fornecedor fornecedor = fornecedorController.findFornecedor(Integer.valueOf(jTextField7.getText()));
            prefeitura.entities.Oficio oficio = protocolo.getIdOficio();

            prefeitura.entities.Processo processo = new prefeitura.entities.Processo(Integer.parseInt(numeroProcesso), tipoProcesso, Integer.valueOf(numeroOficio), formato.parse(dataStr), fornecedor, oficio, protocolo);

            processoController.create(processo);
            limpar();
            atualizarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        limpar();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        String numeroProtocolo = jTextField4.getText();

        try {

            if (numeroProtocolo.isEmpty()) {
                throw new Exception("Preencha o campo número de protcolo");
            }

            prefeitura.entities.Protocolo protocolo = acharProtocolo(Integer.valueOf(numeroProtocolo));
            if (protocolo == null) {
                throw new Exception("Protocolo não encontrado");
            }
            prefeitura.entities.Oficio oficio = protocolo.getIdOficio();
            prefeitura.entities.Secretaria secretaria = oficio.getIdSecretaria();

            jTextField5.setText(String.valueOf(oficio.getNumeroOficio()));
            jTextField6.setText(oficio.getDescricao());
            jTextField1.setText(String.valueOf(secretaria.getNomeSecretaria()));
            jTextField4.setEnabled(false);
            jTextField5.setEnabled(false);
            jTextField2.setEnabled(true);
            jFormattedTextField1.setEnabled(true);
            jTextField7.setEnabled(true);
            jButton2.setEnabled(true);
            jComboBox1.setEnabled(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed

    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        if (evt.getClickCount() == 2) {
            int i = jTable2.getSelectedRow();
            jTextField7.setText(((DefaultTableModel) jTable2.getModel()).getValueAt(i, 0).toString());
            jDialog1.setVisible(false);

        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        procuraFornecedor();


    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jDialog1.setVisible(true);
        atualizarTabelaFornecedor();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        procuraFornecedor();


    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        try {
            prefeitura.entities.Processo processo = acharProcesso(Integer.valueOf(jTextField2.getText()));
            Integer numeroProcesso = processo.getNumeroProcesso();
            Integer idProcesso = processo.getIdProcesso();
            if (processo == null) {
                throw new Exception("Processo não encontrado");
            }

            if (JOptionPane.showConfirmDialog(this, "Remover o processo " + numeroProcesso + " ?") == JOptionPane.OK_OPTION) {
                processoController.destroy(idProcesso);
                JOptionPane.showConfirmDialog(this,
                        "Ofício " + numeroProcesso + " removido com sucesso",
                        "SUCESSO",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            limpar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);

        }
        atualizarTabela();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
//        try {
//            prefeitura.entities.Processo processo = acharProcesso(Integer.valueOf(jTextField2.getText()));
//            Integer numeroProcesso = processo.getNumeroProcesso();
//            Integer idProcesso = processo.getIdProcesso();
//            if (processo == null) {
//                throw new Exception("Processo não encontrado");
//            }
//            
//            prefeitura.entities.Oficio oficio = processo.getIdOficio();
//            prefeitura.entities.Secretaria secretaria = oficio.getIdSecretaria();
//            prefeitura.entities.Protocolo protocolo = oficio.getIdProtocolo();
//            prefeitura.entities.Fornecedor fornecedor = processo.getIdFornecedor();
//            
//            
//            jTextField4.setText(String.valueOf(protocolo.getNumeroProtocolo()));
//            jTextField5.setText(String.valueOf(oficio.getNumeroOficio()));
//            jTextField2.setText(String.valueOf(processo.getNumeroProcesso()));
//            jFormattedTextField1.setText(processo.getDataSaidaCompras().toString());
//            jTextField7.setText(fornecedor.getIdFornecedor().toString());
//            jTextField1.setText(secretaria.getIdSecretaria().toString());
//            jTextField6.setText(oficio.getDescricao());
//            
//            jTextField4.setEnabled(false);
//            
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this,
//                    e.getMessage(),
//                    "ERRO",
//                    JOptionPane.ERROR_MESSAGE);
//
//        }

    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        if (evt.getClickCount() == 2) {

            int i = jTable1.getSelectedRow();
            jTextField4.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 0).toString());
            jTextField5.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 1).toString());
            jTextField2.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 2).toString());
            jFormattedTextField1.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 3).toString());
            jTextField7.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 7).toString());
            jTextField1.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 5).toString());
            jTextField6.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 6).toString());
            jButton4.setEnabled(true);
            jButton5.setEnabled(true);
            jButton3.setEnabled(false);
            
            jTextField4.setEnabled(false);
            jTextField2.setEnabled(true);
            jTextField7.setEnabled(true);
            jFormattedTextField1.setEnabled(true);
            
            prefeitura.entities.Processo processo = acharProcesso(Integer.valueOf(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 2).toString()));
            jTextField3.setText(String.valueOf(processo.getNumeroProcesso()));
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        try {

            String dataStr = jFormattedTextField1.getText();
            String numeroProcesso = jTextField2.getText();
            String tipo = (String) jComboBox1.getSelectedItem();
            String numeroProtocolo = jTextField4.getText();
            String numeroProcessoAntigo = jTextField3.getText();
            
            Date data = formato.parse(dataStr);

            if (numeroProtocolo.isEmpty()) {
                throw new Exception("Preencha todos os campos");
            }

            prefeitura.entities.Processo processo = acharProcesso(Integer.valueOf(numeroProcessoAntigo));
            
            processo.setNumeroProcesso(Integer.parseInt(numeroProcesso));
            processo.setDataSaidaCompras(data);
            processo.setTipoProcesso(tipo);
                        

            processoController.edit(processo);
            limpar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);

        }
        atualizarTabela();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jFormattedTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFormattedTextField1MouseClicked
        if (evt.getClickCount() == 2) {
            formatarCampo();
            colocarData();
        }
    }//GEN-LAST:event_jFormattedTextField1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Processo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Processo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Processo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Processo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Processo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables

    public void persist(Object object) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
