


/**CPTabel.java**/

package GUI;
import GUI.Util.CPTValidator;
import java.util.ArrayList;
import javax.swing.*;

public class CPTable extends javax.swing.JDialog {
ShapeNode par;
ArrayList statelist;
boolean havParent=false;
Boolean isUtility;
Boolean isError ;
Double[] probs;
    /** Creates new form CPTable */
    public CPTable(ShapeNode parent, boolean modal) {
        initComponents();
        par=parent;
        statelist=new ArrayList();
        String s="";
        String[] columnNames;
        isError=false;
        this.setLocationRelativeTo(par);
        isUtility = par.node.getKind()!=null? par.node.getKind().toString().equalsIgnoreCase(node_type.UTILITY_NODE.toString()):false;
        
       if((par.node.states !=null && par.node.states.length >0) || isUtility) {
           //check if node has at least one parent...
           if(par.node.allparents.size() > 0){
               int parentsLenlessOne = par.node.allparents.size()-1;
               if( isUtility){
                   
                   columnNames = new String[par.node.allparents.size()+1];
                  
                   
               }else{
                   columnNames = new String[par.node.getNumstates() + parentsLenlessOne+1];
               }
               
              
             int counter=0;
               
             for(int index=0;index<=parentsLenlessOne;index++){
                 columnNames[index]=par.node.allparents.get(index).toString();
             }
             
            if(!isUtility){
                for(int i=parentsLenlessOne+1;i<(columnNames.length);i++){
                 
                    columnNames[i]=par.node.states[counter];
                    ++counter;
                
                }
             }
            else{
                for(int i=parentsLenlessOne+1;i<(columnNames.length);i++){
                 
                    columnNames[i]=new String("Values");
                   
                
                }
            }

           }
            else{
                
                columnNames= new String[2];
                columnNames[0]="States ";//par.getFullName();
             for(int i=1;i<columnNames.length;i++){
                
                 columnNames[i]=new String("Values");
                
                }
                
            }
           
       
       
       if((par.node.CPT!=null) && par.node.allparents.isEmpty()){
           boolean hasOldState = false;
          // Create a table with CPT as rows and states as column names
          // if(par.node.OldCPT==null){
             //  par.node.OldCPT=par.node.CPT;
          // }
           if((par.node.OldCPT!=null) && par.node.OldCPT[0][0]!=null){
               par.node.CPT = par.node.OldCPT;
               hasOldState = true;
           }
           if((par.node.CPT!=null) && par.node.CPT[0][0]!=null){
               par.node.OldCPT = par.node.CPT;
               hasOldState = true;
           }
            
           if(isUtility){
             Object[][] data = new Object[2][2];
             par.node.CPT= data;
             par.node.OldCPT=null;
           }
           else{
               
                try{
                   if(!hasOldState){
                        par.node.CPT= new Object[par.node.states.length][2];
                        par.node.setCPTstates();
                   }
                  
            
                }
           catch(Exception ex){ex.printStackTrace();}
           }
           
           // par.node.setNumstates(par.node.states.length);
          //  par.node.setCPTstates();
            jTable1=new JTable(par.node.CPT,columnNames);
            jScrollPane1.setViewportView(jTable1);
           
       }
       
       else if(par.node.CPT!=null){
           // Create a table with CPT as rows and states as column names
           try{
            par.node.UpdateCPT(par.node);
            jTable1=new JTable(par.node.CPT,columnNames);
            jScrollPane1.setViewportView(jTable1);
           }
           catch(Exception ex){
              // ex.printStackTrace();
               javax.swing.JOptionPane.showMessageDialog(parent, "Error occured generating CPT, try recreating links.");
               isError=true;
               return;
           }
       }
       else
       {
           Object[][] data;
           if(par.node.allparents.size()==0)
                if(!isUtility){
                 data=new Object[par.node.states.length][2];
                }
                else{
                     data=new Object[2][2];
                }
           else{
               if(!isUtility){
                 data=new Object[par.node.states.length][par.node.allparents.size()+1 ];
                }
                else{
                     data=new Object[2][2];
                }
            
           }
           
           jTable1=new JTable(data,columnNames);
           jScrollPane1.setViewportView(jTable1);
       }
     }

       jLabel1.setText("States and Values");
    }
      

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setColumnSelectionAllowed(true);
        jTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable1PropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(168, 168, 168)
                                .addComponent(btnOk)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancel)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancel, btnOk});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        int numberOfParents = (par.node.allparents.size() > 0) ? par.node.allparents.size(): 1 ;
        Double[][] data=new Double[jTable1.getRowCount()][jTable1.getColumnCount()-numberOfParents];
        Double[] probs = new Double[jTable1.getRowCount()];
        int colCount = jTable1.getColumnCount();
        if(isError){
            this.dispose();
            return;
        }
       if(this.par.node.states == null) {
          if(isUtility && this.par.node.CPT[0][0]!=null){
           }
          else{
            this.dispose();
            return;
          }
       }
      
        for(int i=0;i<jTable1.getRowCount();i++){
            try{
                for(int j=numberOfParents;j<colCount;j++){
                    data[i][j-numberOfParents]=Double.parseDouble(jTable1.getModel().getValueAt(i, j).toString());
                }
            }
            catch(Exception ex){this.dispose(); return;}
            
        }
        if(par.node.allparents.isEmpty() || isUtility){
            int j;
            for(j=0;j<data.length;j++){
                probs[j]=data[j][0];
            }
          if(!isUtility){
           if(! CPTValidator.isValidCPT(probs)){
               
               javax.swing.JOptionPane.showMessageDialog(par, "Probabilities must sum up to 1.0");
               return;
           }
          }
        }
        else{ // check for each row of data[][] if they sum to 1.0
            
             int j;
             String msg;
             probs = new Double[data[0].length];
            for(j=0;j<data.length;j++){
                
                for(int k=0;k<data[0].length;k++){
                  probs[k]=data[j][k];
                }
                 msg = String.format("Probabilities must sum to 1.0 on row : %d", (j+1));
                if(! CPTValidator.isValidCPT(probs)){
                    javax.swing.JOptionPane.showMessageDialog(par, msg);
                    return;
                }
            }
           
            
        }
        par.node.setCPTable(data,numberOfParents,colCount);
        
        if(par.node.allparents.isEmpty() || isUtility){
            par.node.OldCPT=par.node.CPT;
        }
       
        this.dispose();
      
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void jTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange


    }//GEN-LAST:event_jTable1PropertyChange

    /**
    * @param args the command line arguments
    */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

}
