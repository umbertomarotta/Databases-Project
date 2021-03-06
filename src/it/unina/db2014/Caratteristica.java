/*
 * DBFrame.java
 *
 * Modified on 2013-06-03
 */
package it.unina.db2014;

import java.sql.*;
import javax.swing.*;
import java.util.regex.*;

/**
 *
 * @author Massimo
 * @author ADeLuca
 */
public class Caratteristica extends DBFrame {

   /**
    * Creates new form DBFrame
    */
   public Caratteristica() {
      super();
      initComponents();
      setModalita(APPEND_QUERY);
      setFrameTable(tabCaratteristica);
      setNomeTabella("caratteristica");
   }

   public final void setModalita(int modo) {
      super.setModalita(modo);
      switch (modo) {
         case APPEND_QUERY:
            tDescrizione.setEnabled(true);
            break;
         case BROWSE:
            tDescrizione.setEnabled(false);
            break;
         case UPDATE:
            tDescrizione.setEnabled(true);
            break;
      }
   }

   protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste gi? un'altra caratteristica con lo stesso codice";
         JOptionPane.showMessageDialog(this, msg, "Errore", 
                 JOptionPane.ERROR_MESSAGE);
      } else {
         super.mostraErrori(e, query, contesto);
      }
   }

   protected void premutoOK() {
      if (getPadre() != null) {
         getPadre().setProprietaPadre("Caratteristica", getTCodice().getText());
         dispose();
      }
   }

   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jLabel2 = new javax.swing.JLabel();
      tDescrizione = new javax.swing.JTextField();
      spCaratteristica = new javax.swing.JScrollPane();
      tabCaratteristica = new javax.swing.JTable();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setTitle("Caratteristiche");

      jLabel2.setText("Descrizione");

      tabCaratteristica.setModel(new javax.swing.table.DefaultTableModel(
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
      spCaratteristica.setViewportView(tabCaratteristica);

      org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(layout.createSequentialGroup()
            .add(19, 19, 19)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(spCaratteristica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 774, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(layout.createSequentialGroup()
                  .add(jLabel2)
                  .add(26, 26, 26)
                  .add(tDescrizione, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 385, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(18, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(layout.createSequentialGroup()
            .addContainerGap(157, Short.MAX_VALUE)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel2)
               .add(tDescrizione, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(18, 18, 18)
            .add(spCaratteristica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 268, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
      );

      getAccessibleContext().setAccessibleDescription("");

      pack();
   }// </editor-fold>//GEN-END:initComponents

   protected void mostraDati() {
      try {
         tDescrizione.setText(rs.getString("Descrizione"));
         super.mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }

   protected void pulisci() {
      super.pulisci();
      tDescrizione.setText("");
   }

   protected PreparedStatement creaSelectStatement() {
      Connection con;
      PreparedStatement st = null;
      String codice, descr;
      Pattern pat;
      Matcher matc;
      int k = 1;
      super.creaSelectStatement();
      codice = getTCodice().getText();
      descr = tDescrizione.getText();
      /*
       * if (codice.length()>0 || descr.length()>0 || sede.length()>0)
        {
       */
      query += " where";
      //}
      if (codice.length() > 0) {
         query += " codice= ? and";
      }
      if (descr.length() > 0) {
         if (descr.indexOf("%") >= 0) {
            query += " descrizione like ? ";
         } else {
            query += " descrizione = ? ";
         }
      }
      pat = Pattern.compile("where$|and$");
      matc = pat.matcher(query);
      query = matc.replaceAll("");
      System.out.println(query);
      //query+=" order by codice";
      try {
         con = Database.getDefaultConnection();
         st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, 
                 ResultSet.CONCUR_READ_ONLY);

         if (codice.length() > 0) {
            st.setInt(k++, Integer.decode(codice));
         }
         if (descr.length() > 0) {
            st.setString(k++, descr);
         }
      } catch (SQLException e) {
      }
      return st;
   }

   protected PreparedStatement getComandoInserimento(Connection c)
           throws SQLException {
      String queryIns;
      PreparedStatement st;
      queryIns = "insert into " + Database.schema + ".caratteristica (codice,"
              + "Descrizione) values(?,?)";
      st = c.prepareStatement(queryIns);
      st.setInt(1, Integer.decode(getTCodice().getText()));
      st.setString(2, tDescrizione.getText());
      return st;
   }

   protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException {
      String queryUp;
      PreparedStatement st;
      queryUp = "update " + Database.schema + ".caratteristica set descrizione=? "
              + "where codice=?";
      st = c.prepareStatement(queryUp);
      st.setInt(2, Integer.decode(getTCodice().getText()));
      st.setString(1, tDescrizione.getText());
      return st;
   }

   /**
    * @param args the command line arguments
    */
   public static void main1(String args[]) {
      java.awt.EventQueue.invokeLater(new Runnable() {

         public void run() {
            new Caratteristica().setVisible(true);
         }
      });
   }
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JLabel jLabel2;
   private javax.swing.JScrollPane spCaratteristica;
   private javax.swing.JTextField tDescrizione;
   private javax.swing.JTable tabCaratteristica;
   // End of variables declaration//GEN-END:variables
}
