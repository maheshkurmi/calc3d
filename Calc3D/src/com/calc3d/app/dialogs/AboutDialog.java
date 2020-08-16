package com.calc3d.app.dialogs;

import java.awt.Window;
import java.io.IOException;

import javax.swing.JDialog;

/**
*
* @author mahesh
*/
public class AboutDialog extends JDialog {

   /**
    * Creates new form AboutDialogs
    */
   public AboutDialog(Window owner) {
	   super(owner, "About Calc3D", ModalityType.APPLICATION_MODAL);
	   this.setResizable(false);
       initComponents();  
       this.setLocationRelativeTo(null);
   }

   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">
   private void initComponents() {

       lblApp = new javax.swing.JLabel();
       jScrollPane2 = new javax.swing.JScrollPane();
       editorPane = new javax.swing.JEditorPane();
       btnOk = new javax.swing.JButton();

       setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

       lblApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/calc3d/app/resources/pic.png"))); // NOI18N

       editorPane.setFocusable(false);
       jScrollPane2.setViewportView(editorPane);
       lblApp.setBorder(null);
      // jScrollPane2.setBorder(new EmptyBorder(0,5,0,0));
       
       try {
    	   editorPane.setPage(this.getClass().getResource("/com/calc3d/app/resources/about.html"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       btnOk.setText("ok");
       btnOk.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(java.awt.event.ActionEvent evt) {
               btnOkActionPerformed(evt);
           }
       });
       javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
       getContentPane().setLayout(layout);
       layout.setHorizontalGroup(
           layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
           .addGroup(layout.createSequentialGroup()
               .addContainerGap()
               .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                   .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                       .addComponent(lblApp, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                       .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE,500, Short.MAX_VALUE))
                   .addComponent(btnOk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addContainerGap())
       );
       layout.setVerticalGroup(
           layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGroup(layout.createSequentialGroup()
               .addContainerGap()
               .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                   .addComponent(jScrollPane2)
                   .addComponent(lblApp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(btnOk, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
               .addContainerGap())
       );
      // editorPane.setMinimumSize(getSize());
      // editorPane.setPreferredSize(getSize());
       
       pack();
   }// </editor-fold>

   private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {
      this.setVisible(false);
   }
   /**
    * @param args the command line arguments
    */
   public static void main(String args[]) {
       /*
        * Set the Nimbus look and feel
        */
       //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
       /*
        * If Nimbus (introduced in Java SE 6) is not available, stay with the
        * default look and feel. For details see
        * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
        */
       try {
           for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
               if ("Nimbus".equals(info.getName())) {
                   javax.swing.UIManager.setLookAndFeel(info.getClassName());
                   break;
               }
           }
       } catch (ClassNotFoundException ex) {
           java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
       } catch (InstantiationException ex) {
           java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
       } catch (IllegalAccessException ex) {
           java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
       } catch (javax.swing.UnsupportedLookAndFeelException ex) {
           java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
       }
       //</editor-fold>

       /*
        * Create and display the form
        */
       java.awt.EventQueue.invokeLater(new Runnable() {

           public void run() {
               new AboutDialog(null).setVisible(true);
           }
       });
   }
   // Variables declaration - do not modify
   private javax.swing.JButton btnOk;
   private javax.swing.JEditorPane editorPane;
   private javax.swing.JScrollPane jScrollPane2;
   private javax.swing.JLabel lblApp;
   // End of variables declaration
}
