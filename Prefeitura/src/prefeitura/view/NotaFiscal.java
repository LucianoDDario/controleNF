/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package prefeitura.view;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
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
public class NotaFiscal extends javax.swing.JFrame {

    EntityManagerFactory factory;
    ProtocoloJpaController protocoloController;
    NotafiscalJpaController notaFiscalController;
    ProcessoJpaController processoController;

    private void abrirConexao() {
        try {
            factory = Persistence.createEntityManagerFactory("PrefeituraFuncionandoPU");
            protocoloController = new ProtocoloJpaController(factory);
            notaFiscalController = new NotafiscalJpaController(factory);
            processoController = new ProcessoJpaController(factory);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    private void atualizarTabela() {

        ((DefaultTableModel) jTable1.getModel()).setRowCount(0);
        try {
            List<prefeitura.entities.Notafiscal> notasFiscais = notaFiscalController.findNotafiscalEntities();

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            for (prefeitura.entities.Notafiscal notaFiscal : notasFiscais) {

                prefeitura.entities.Protocolo protocolo = notaFiscal.getIdProtocolo();

                String dataProtocoloFormatada = formato.format(protocolo.getDataProtocolo());
                String dataNotaFormatada = formato.format(notaFiscal.getDataEmissao());
                String dataChegadaFormatada = formato.format(notaFiscal.getDataChegada());
                String valorNota = String.valueOf(notaFiscal.getValorNota());

                if (Integer.parseInt(valorNota) < 100) {
                    valorNota = "0," + valorNota;
                } else {
                    String valorTextoCentavos = valorNota.substring(valorNota.length() - 2);
                    String valorTextoReais = valorNota.substring(0, valorNota.length() - 2);

                    valorNota = valorTextoReais + "," + valorTextoCentavos;
                }

                String linha[] = {
                    String.valueOf(notaFiscal.getIdNotaFiscal()),
                    dataChegadaFormatada,
                    String.valueOf(protocolo.getNumeroProtocolo()),
                    dataProtocoloFormatada,
                    String.valueOf(notaFiscal.getNumeroNota()),
                    valorNota,
                    dataNotaFormatada,
                    String.valueOf(notaFiscal.getIdProcesso().getNumeroProcesso())};
                ((DefaultTableModel) jTable1.getModel()).addRow(linha);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    private void limpar() {
        jFormattedTextField1.setText("");
        jFormattedTextField2.setText("");
        jFormattedTextField3.setText("");
        jFormattedTextField4.setText("");
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");

        atualizarTabela();

        jButton3.setEnabled(true);
        jButton4.setEnabled(false);
        jButton6.setEnabled(true);
        jButton5.setEnabled(false);
        formatarCampo();

        jTextField1.requestFocus();
    }

    private void formatarCampo() {
        try {
            MaskFormatter formatter = new MaskFormatter("##/##/####");
            formatter.setPlaceholderCharacter('_');
            jFormattedTextField1.setFormatterFactory(new DefaultFormatterFactory(formatter));
            jFormattedTextField2.setFormatterFactory(new DefaultFormatterFactory(formatter));
            jFormattedTextField4.setFormatterFactory(new DefaultFormatterFactory(formatter));

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void colocarData() {
        jFormattedTextField4.setText(String.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

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
                    "ERRO4",
                    JOptionPane.ERROR_MESSAGE);
        }

        return null;
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
                    "ERRO3",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private void formatarDinheiro() {
        String valorTexto = jFormattedTextField3.getText().replaceAll("[^0-9]", "");

        while (valorTexto.length() < 2) {
            valorTexto = "0" + valorTexto;
        }

        if (valorTexto.length() >= 2) {
            valorTexto = valorTexto.replaceFirst("^0+(?!$)", "");

            String valorTextoCentavos = valorTexto.substring(valorTexto.length() - 2);
            String valorTextoReais = valorTexto.substring(0, valorTexto.length() - 2);

            String valorTextoFormatado = valorTextoReais + "," + valorTextoCentavos;
            jFormattedTextField3.setText(valorTextoFormatado);
        } else {

            jFormattedTextField3.setText("0," + valorTexto);
        }

    }

    public NotaFiscal() {
        initComponents();
        abrirConexao();
        limpar();
        colocarData();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane5 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jTextField2 = new javax.swing.JTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Data de Chegada", "Protocolo", "Data Protocolo", "Número da Nota", "Valor", "Data da Nota", "Processo"
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
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel1.setText("Protocolo");

        jLabel2.setText("Data do Protocolo");

        jLabel3.setText("Número da Nota");

        jLabel4.setText("Data da Nota");

        jLabel5.setText("Número do Processo");

        jButton3.setText("Inserir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

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

        jLabel6.setText("Valor da Nota");

        jFormattedTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField3KeyReleased(evt);
            }
        });

        jLabel7.setText("Data de Chegada");

        jTextField4.setEditable(false);
        jTextField4.setEnabled(false);

        jLabel8.setText("ID");

        jButton1.setText("Voltar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(44, 44, 44)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jFormattedTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                            .addComponent(jTextField3)
                                            .addComponent(jTextField4))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        if (evt.getClickCount() == 2) {

            int i = jTable1.getSelectedRow();
            jTextField4.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 0).toString());
            jTextField1.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 2).toString());
            jTextField2.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 4).toString());
            jTextField3.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 7).toString());
            jFormattedTextField1.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 3).toString());
            jFormattedTextField2.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 6).toString());
            jFormattedTextField3.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 5).toString().replaceAll("[^0-9]", ""));
            jFormattedTextField4.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 1).toString());
            formatarDinheiro();
            jButton4.setEnabled(true);
            jButton5.setEnabled(true);
            jButton3.setEnabled(false);

        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String numeroProtocolo = jTextField1.getText();
        String dataProtocolo = jFormattedTextField1.getText();
        String dataEmissao = jFormattedTextField2.getText();
        String dataChegada = jFormattedTextField4.getText();
        String numeroNota = jTextField2.getText();
        String numeroProcesso = jTextField3.getText();
        String valorNota = jFormattedTextField3.getText();

        valorNota = valorNota.replaceAll("[^0-9]", "");

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        try {
            if (numeroProtocolo.isEmpty()) {
                throw new Exception("Preencha o número de protocolo");

            }

            List<prefeitura.entities.Notafiscal> notasFiscais = notaFiscalController.findNotafiscalEntities();
            List<prefeitura.entities.Processo> processos = processoController.findProcessoEntities();
            for (prefeitura.entities.Notafiscal notaFiscal : notasFiscais) {
                for (prefeitura.entities.Processo processo : processos) {
                    if (Integer.valueOf(numeroNota).equals(notaFiscal.getNumeroNota()) && notaFiscal.getIdFornecedor().equals(processo.getIdFornecedor())) {
                        throw new Exception("Numero de nota já existe para este fornecedor");
                    }

                }

            }

            List<prefeitura.entities.Protocolo> protocolos = protocoloController.findProtocoloEntities();
            for (prefeitura.entities.Protocolo protocolo : protocolos) {
                if (Integer.valueOf(numeroProtocolo).equals(protocolo.getNumeroProtocolo())) {
                    throw new Exception("Protocolo já existente");
                }
            }

            prefeitura.entities.Protocolo protocolo = new prefeitura.entities.Protocolo(Integer.valueOf(numeroProtocolo), formato.parse(dataProtocolo));
            protocoloController.create(protocolo);

            if (dataProtocolo.isEmpty() || dataEmissao.isEmpty() || numeroNota.isEmpty() || numeroProcesso.isEmpty() || dataChegada.isEmpty() || valorNota.isEmpty()) {
                throw new Exception("Preencha todos os campos");
            }

            protocolo = acharProtocolo(Integer.valueOf(numeroProtocolo));
            prefeitura.entities.Processo processo = acharProcesso(Integer.valueOf(numeroProcesso));
            if (processo == null) {
                throw new Exception("Processo não encontrado");
            }

            prefeitura.entities.Notafiscal notaFiscal = new prefeitura.entities.Notafiscal(Integer.valueOf(numeroNota), formato.parse(dataEmissao), Integer.valueOf(valorNota), formato.parse(dataChegada), processo.getIdFornecedor(), processo, protocolo);
            notaFiscalController.create(notaFiscal);

            protocolo.setIdNota(notaFiscal);
            protocoloController.edit(protocolo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO1",
                    JOptionPane.ERROR_MESSAGE);

        }

        atualizarTabela();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Integer IdNotaFiscal = Integer.valueOf(jTextField4.getText());
        Integer numeroNota = notaFiscalController.findNotafiscal(IdNotaFiscal).getNumeroNota();
        try {
            if (JOptionPane.showConfirmDialog(this, "Remover a nota " + numeroNota + " ?") == JOptionPane.OK_OPTION) {

                Integer idProtocolo = notaFiscalController.findNotafiscal(IdNotaFiscal).getIdProtocolo().getIdProtocolo();
                notaFiscalController.destroy(IdNotaFiscal);
                protocoloController.destroy(idProtocolo);

                JOptionPane.showConfirmDialog(this,
                        "Ofício " + numeroNota + " removido com sucesso",
                        "SUCESSO",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            limpar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO25",
                    JOptionPane.ERROR_MESSAGE);

        }
        atualizarTabela();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String IdNota = jTextField4.getText();
        String numeroProtocolo = jTextField1.getText();
        String dataProtocoloStr = jFormattedTextField1.getText();
        String numeroNota = jTextField2.getText();
        String dataNotaStr = jFormattedTextField2.getText();
        String dataChegadaStr = jFormattedTextField4.getText();
        String valorNota = jFormattedTextField3.getText();
        String numeroProcesso = jTextField3.getText();

        valorNota = valorNota.replaceAll("[^0-9]", "");

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        try {

            if (dataProtocoloStr.isEmpty() || numeroProtocolo.isEmpty() || numeroNota.isEmpty()) {
                throw new Exception("Preencha todos os campos");
            }
            Date dataProtocolo = formato.parse(dataProtocoloStr);
            Date dataChegada = formato.parse(dataChegadaStr);
            Date dataNota = formato.parse(dataNotaStr);

            prefeitura.entities.Notafiscal notaFiscal = notaFiscalController.findNotafiscal(Integer.valueOf(IdNota));
            prefeitura.entities.Protocolo protocolo = acharProtocolo(notaFiscal.getIdProtocolo().getNumeroProtocolo());
            prefeitura.entities.Processo processo = acharProcesso(Integer.valueOf(numeroProcesso));
            if (processo == null) {
                throw new Exception("Processo não encontrado");
            }

            notaFiscal.setNumeroNota(Integer.valueOf(numeroNota));
            protocolo.setNumeroProtocolo(Integer.valueOf(numeroProtocolo));
            protocolo.setDataProtocolo(dataProtocolo);
            notaFiscal.setIdProtocolo(protocolo);
            notaFiscal.setDataChegada(dataChegada);
            notaFiscal.setDataEmissao(dataNota);
            notaFiscal.setValorNota(Integer.valueOf(valorNota));
            notaFiscal.setIdProcesso(processo);

            protocoloController.edit(protocolo);
            notaFiscalController.edit(notaFiscal);
            limpar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO26",
                    JOptionPane.ERROR_MESSAGE);

        }
        atualizarTabela();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        limpar();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        if (jFormattedTextField1.getText().isEmpty()) {
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

        if (jFormattedTextField2.getText().isEmpty()) {
            try {
                MaskFormatter formatter = new MaskFormatter("##/##/####");
                formatter.setPlaceholderCharacter('_');
                jFormattedTextField2.setFormatterFactory(new DefaultFormatterFactory(formatter));

            } catch (Exception e) {

                JOptionPane.showMessageDialog(this,
                        e.getMessage(),
                        "ERRO",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (jFormattedTextField4.getText().isEmpty()) {
            try {
                MaskFormatter formatter = new MaskFormatter("##/##/####");
                formatter.setPlaceholderCharacter('_');
                jFormattedTextField4.setFormatterFactory(new DefaultFormatterFactory(formatter));

            } catch (Exception e) {

                JOptionPane.showMessageDialog(this,
                        e.getMessage(),
                        "ERRO",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_formMouseMoved

    private void jFormattedTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField3KeyReleased

        if (jFormattedTextField3.getText().replaceAll("[^0-9]", "").length() > 1) {
            formatarDinheiro();
        }


    }//GEN-LAST:event_jFormattedTextField3KeyReleased

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
            java.util.logging.Logger.getLogger(NotaFiscal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NotaFiscal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NotaFiscal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NotaFiscal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NotaFiscal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
