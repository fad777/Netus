
/*
 * UtilityNodeProperty.java
 */

package GUI;

import java.util.ArrayList;
import javax.swing.JOptionPane;


public class UtilityNodeProperty extends javax.swing.JDialog {
ShapeNode par;
ArrayList statelist;
String horizon ;
    /** Creates new form NodeProperty */
    public UtilityNodeProperty(ShapeNode parent, boolean modal) {
        initComponents();
        par=parent;
        this.setLocationRelativeTo(par);
        statelist=new ArrayList();
        this.setTitle("Property Editor Dialog");
        nodeNameField.setText(par.getFullName());
        horizon ="1";
        if(par.node.states!=null)
        for(int i=0;i<par.node.states.length;i++)
        {
            statelist.add(par.node.states[i]);
            list1.add(par.node.states[i]);
        }
        jTextField3.setText(par.node.type.toString());
        jButton4.setEnabled(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nodeNameField = new javax.swing.JTextField();
        txtLabel = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        list1 = new java.awt.List();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cboHorizon = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setModal(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        txtLabel.setEditable(false);

        jButton1.setText("Add >>");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnOk.setText("Ok");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        jLabel1.setText("Node Name");

        jLabel2.setText("Label");

        jLabel3.setText("Satisfactions");

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        list1.setBackground(java.awt.Color.white);
        list1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                list1ItemStateChanged(evt);
            }
        });

        jButton4.setText("Update");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel4.setText("Kind");

        jTextField3.setEditable(false);

        jLabel6.setText("Horizon");

        cboHorizon.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2" }));
        cboHorizon.setToolTipText("Select Name");
        cboHorizon.setDoubleBuffered(true);
        cboHorizon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboHorizonMouseClicked(evt);
            }
        });
        cboHorizon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHorizonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nodeNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton4)
                                    .addComponent(jButton1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(cboHorizon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(list1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancel, btnOk, jButton1, jButton4});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextField3, nodeNameField, txtLabel});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nodeNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(cboHorizon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnOk)
                            .addComponent(btnCancel)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(list1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         statelist.add(txtLabel.getText());
        list1.add(txtLabel.getText());
        txtLabel.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
         String oldName = par.node.getFullName();
        boolean renamed = false;
        this.setAlwaysOnTop(false);
        for(Object n:par.rootpane.nodes){
            String name =((ShapeNode)n).getFullName();
            
            if((name.equalsIgnoreCase(nodeNameField.getText()) && !name.equalsIgnoreCase(oldName)) ){
               
                 String numStr = name.replaceAll("\\D+", "");
                Integer num = Integer.parseInt(numStr);
                ++num;
                name = name.replace(numStr,num.toString());
                nodeNameField.setText(name);
                renamed=true;       
                
            }
        }
        if(renamed) javax.swing.JOptionPane.showMessageDialog(null, "Name already exits. Renamed!",
                                                                 "Name Conflicts",
                                                                 JOptionPane.WARNING_MESSAGE);  
        
        par.node.name=nodeNameField.getText();
        par.fullname = nodeNameField.getText();
        par.node.containerName=nodeNameField.getText();
        par.node.setNumstates(statelist.size());
        
        for(int i=0;i<statelist.size();i++)
        {
            par.node.states[i]=statelist.get(i).toString();
        }
        par.label="U" + cboHorizon.getSelectedItem().toString().trim();
        par.node.label=par.label;
        par.node.setCPTstates();
        par.repaint();
        this.dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

    private void list1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_list1ItemStateChanged
        // TODO add your handling code here:
        jButton4.setEnabled(true);
    }//GEN-LAST:event_list1ItemStateChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        statelist.remove(list1.getSelectedItem());
        list1.remove(list1.getSelectedIndex());
        statelist.add(txtLabel.getText());
        list1.add(txtLabel.getText());
        jButton4.setEnabled(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
            // TODO add your handling code here:
       
         if(par.label.isEmpty()){
             par.label= "U" + cboHorizon.getSelectedItem().toString().trim();
             txtLabel.setText(par.label.toString());
         }
         else{
               txtLabel.setText(par.label.toString());
         }
    }//GEN-LAST:event_formWindowActivated

    private void cboHorizonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboHorizonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHorizonMouseClicked

    private void cboHorizonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHorizonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHorizonActionPerformed

    /**
    * @param args the command line arguments
    */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox cboHorizon;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jTextField3;
    private java.awt.List list1;
    private javax.swing.JTextField nodeNameField;
    private javax.swing.JTextField txtLabel;
    // End of variables declaration//GEN-END:variables

}
