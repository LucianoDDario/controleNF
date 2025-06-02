/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package prefeitura.view;

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
import prefeitura.entities.*;

/**
 *
 * @author lucia
 */
public class Oficio extends javax.swing.JFrame {

    EntityManagerFactory factory;
    OficioJpaController oficioController;
    SecretariaJpaController secretariaController;
    ProtocoloJpaController protocoloController;

    public Oficio() {
        initComponents();
        abrirConexao();
        limpar();
        colocarData();
        
    }

    private void abrirConexao() {
        try {
            factory = Persistence.createEntityManagerFactory("PrefeituraFuncionandoPU");
            oficioController = new OficioJpaController(factory);
            secretariaController = new SecretariaJpaController(factory);
            protocoloController = new ProtocoloJpaController(factory);

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
            List<prefeitura.entities.Oficio> oficios = oficioController.findOficioEntities();

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            for (prefeitura.entities.Oficio oficio : oficios) {

                String dataFormatada = formato.format(oficio.getDataOficio());
                String linha[] = {
                    String.valueOf(oficio.getIdOficio()),
                    String.valueOf(oficio.getIdProtocolo().getNumeroProtocolo()),
                    dataFormatada,
                    String.valueOf(oficio.getNumeroOficio()),
                    String.valueOf(oficio.getIdSecretaria()),
                    oficio.getDescricao()
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

    private void limpar() {
        jFormattedTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        atualizarTabela();
        atualizarCombo();
        jButton3.setEnabled(true);
        jButton4.setEnabled(false);
        jButton6.setEnabled(true);
        jButton5.setEnabled(false);
        formatarCampo();
        jTextField4.requestFocus();
    }

    private void atualizarCombo() {
        jComboBox1.removeAllItems();
        List<Secretaria> secretarias = secretariaController.findSecretariaEntities();
        for (Secretaria secretaria : secretarias) {
            jComboBox1.addItem(secretaria);
        }

    }

    private void colocarData() {
        jFormattedTextField1.setText(String.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Ofício");
        setPreferredSize(new java.awt.Dimension(791, 516));

        jLabel1.setText("Data");

        jLabel2.setText("Número do Ofício");

        jLabel3.setText("Secretaria");

        jLabel4.setText("Descrição");

        jButton1.setText("Voltar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Protocolo", "Data", "Número", "Secretaria", "Descrição"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        }

        jLabel5.setText("Protocolo");

        jFormattedTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFormattedTextField1MouseClicked(evt);
            }
        });

        jTextField1.setEditable(false);
        jTextField1.setEnabled(false);

        jLabel6.setText("ID");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
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
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField4)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField2)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                    .addComponent(jFormattedTextField1))
                                .addGap(42, 42, 42)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap(67, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Integer IdOficio = Integer.valueOf(jTextField1.getText());
        String dataStr = jFormattedTextField1.getText();
        String numeroOficio = jTextField2.getText();
        String descricao = jTextField3.getText();
        Secretaria secretaria = (Secretaria) jComboBox1.getSelectedItem();

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date data = formato.parse(dataStr);
            if (dataStr.isEmpty() || numeroOficio.isEmpty() || descricao.isEmpty()) {
                throw new Exception("Preencha todos os campos");
            }
            prefeitura.entities.Oficio oficio = oficioController.findOficio(IdOficio);
            oficio.setNumeroOficio(Integer.parseInt(numeroOficio));
            oficio.setDataOficio(data);
            oficio.setDescricao(descricao);
            oficio.setIdSecretaria(secretaria);

            oficioController.edit(oficio);
            limpar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);

        }
        atualizarTabela();

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        limpar();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String numeroProtocolo = jTextField4.getText();
        String dataStr = jFormattedTextField1.getText();
        String numeroOficio = jTextField2.getText();
        String descricao = jTextField3.getText();
        Secretaria secretaria = (Secretaria) jComboBox1.getSelectedItem();
        Integer idProtocolo = 0;

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        try {
            if (numeroProtocolo.isEmpty()) {
                throw new Exception("Preencha o número de protocolo");

            }
            List<Protocolo> protocolos = protocoloController.findProtocoloEntities();
            for (Protocolo protocolo : protocolos) {
                if (Integer.valueOf(numeroProtocolo).equals(protocolo.getNumeroProtocolo())) {
                    throw new Exception("Protocolo já existente");
                }
            }

            prefeitura.entities.Protocolo protocolo = new prefeitura.entities.Protocolo(Integer.valueOf(numeroProtocolo), formato.parse(dataStr));
            protocoloController.create(protocolo);
            idProtocolo = protocolo.getIdProtocolo();

            try {
                if (dataStr.isEmpty() || numeroOficio.isEmpty() || descricao.isEmpty()) {
                    throw new Exception("Preencha todos os campos");
                }

                protocolo = protocoloController.findProtocolo(idProtocolo);

                prefeitura.entities.Oficio oficio = new prefeitura.entities.Oficio(Integer.parseInt(numeroOficio),descricao, formato.parse(dataStr),protocolo, secretaria);
                oficioController.create(oficio);

                prefeitura.entities.Protocolo protocolo1 = oficio.getIdProtocolo();
                protocolo1.setIdOficio(oficio);
                protocoloController.edit(protocolo1);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        e.getMessage(),
                        "ERRO",
                        JOptionPane.ERROR_MESSAGE);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }

        atualizarTabela();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        if (evt.getClickCount() == 2) {

            int i = jTable1.getSelectedRow();
            jTextField1.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 0).toString());

            jTextField4.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 1).toString());
            jFormattedTextField1.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 2).toString());
            jTextField2.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 3).toString());
            jTextField3.setText(((DefaultTableModel) jTable1.getModel()).getValueAt(i, 5).toString());
            jButton4.setEnabled(true);
            jButton5.setEnabled(true);
            jButton3.setEnabled(false);

        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Integer IdOficio = Integer.valueOf(jTextField1.getText());
        try {
            if (JOptionPane.showConfirmDialog(this, "Remover o ofício " + IdOficio + " ?") == JOptionPane.OK_OPTION) {
                
                Integer idProtocolo = oficioController.findOficio(IdOficio).getIdProtocolo().getIdProtocolo();
                oficioController.destroy(IdOficio);
                protocoloController.destroy(idProtocolo);

                JOptionPane.showConfirmDialog(this,
                        "Ofício " + IdOficio + " removido com sucesso",
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
            java.util.logging.Logger.getLogger(Oficio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Oficio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Oficio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Oficio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Oficio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<Secretaria> jComboBox1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
