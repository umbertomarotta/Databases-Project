/*
 * DBFrame.java
 *
 * Modified on 2013-06-03
 */
package it.unina.db2014;

import java.sql.*;
import javax.swing.*;
import java.util.regex.*;
import java.awt.event.KeyEvent;
import java.time.*;

/**
 *
 * @author Massimo
 * @author ADeLuca
 */
public class Visualizza extends DBFrame1 {

   /**
    * Creates new form DBFrame
    */
   public Visualizza() {
      super();
      initComponents();
      setModalita(APPEND_QUERY);
      setFrameTable(tabCaratteristica);
      setNomeTabella("visualizza");
   }
   
   public Visualizza(int usr) {
      this();
      tUtenteCodice.setText(Integer.toString(usr));
   }

   public final void setModalita(int modo) {
      super.setModalita(modo);
      switch (modo) {
         case APPEND_QUERY:
            tUtenteCodice.setEnabled(true);
            tPostCodice.setEnabled(true);
            tData.setEnabled(true);
            tSelez.setEnabled(true);
            tUtenteDescrizione.setEnabled(true);
            tPostDescrizione.setEnabled(true);
            break;
         case BROWSE:
            tUtenteCodice.setEnabled(false);
            tPostCodice.setEnabled(false);
            tData.setEnabled(false);
            tSelez.setEnabled(false);
            tUtenteDescrizione.setEnabled(false);
            tPostDescrizione.setEnabled(false);
            break;
         case UPDATE:
            tUtenteCodice.setEnabled(true);
            tPostCodice.setEnabled(true);
            tData.setEnabled(true);
            tSelez.setEnabled(true);
            tUtenteDescrizione.setEnabled(true);
            tPostDescrizione.setEnabled(true);
            break;
      }
   }

   protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste gi? un'altro Commento con lo stesso codice";
         JOptionPane.showMessageDialog(this, msg, "Errore", 
                 JOptionPane.ERROR_MESSAGE);
      } else {
         super.mostraErrori(e, query, contesto);
      }
   }

   protected void premutoOK() {
      if (getPadre() != null) {
         getPadre().setProprietaPadre("Commento", getTCodice().getText());
         dispose();
      }
   }
   
   public void setProprietaPadre(String proprieta, String valore) {
        if (proprieta.equals("Utente")) {
            tUtenteCodice.setText(valore);
            tData.requestFocusInWindow();
        }
        if (proprieta.equals("Foto")) {
            tPostCodice.setText(valore);
            tData.requestFocusInWindow();
        }
        if (proprieta.equals("Video")) {
            tPostCodice.setText(valore);
            tData.requestFocusInWindow();
        }
        if (proprieta.equals("Discussione")) {
            tPostCodice.setText(valore);
            tData.requestFocusInWindow();
        }
    }

   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spCaratteristica = new javax.swing.JScrollPane();
        tabCaratteristica = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        tData = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tPostCodice = new javax.swing.JTextField();
        tPostDescrizione = new javax.swing.JTextField();
        tUtenteCodice = new javax.swing.JTextField();
        tUtenteDescrizione = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tSelez = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Visualizza");

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
        tabCaratteristica.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        spCaratteristica.setViewportView(tabCaratteristica);

        jLabel5.setText("Data");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/it/unina/logo.png"))); // NOI18N
        jLabel1.setText("jLabel1");

        tPostCodice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tPostCodiceFocusLost(evt);
            }
        });
        tPostCodice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tPostCodiceKeyPressed(evt);
            }
        });

        tUtenteCodice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tUtenteCodiceFocusLost(evt);
            }
        });
        tUtenteCodice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tUtenteCodiceKeyPressed(evt);
            }
        });

        jLabel16.setText("Utente (F2 cerca)");

        tSelez.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Foto", "Video", "Discussione" }));
        tSelez.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tSelezItemStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(spCaratteristica)
                        .addContainerGap())
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(jLabel16)
                                        .add(15, 15, 15))
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                        .add(tSelez, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(18, 18, 18)))
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(tUtenteCodice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(tUtenteDescrizione, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 288, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(layout.createSequentialGroup()
                                        .add(tPostCodice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(tPostDescrizione, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 288, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(jLabel5)
                                .add(26, 26, 26)
                                .add(tData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 385, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 258, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 137, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(125, 125, 125)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel1)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(tUtenteCodice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(tUtenteDescrizione, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel16))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(tPostCodice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(tPostDescrizione, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(tSelez, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel5)
                            .add(tData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(155, 155, 155)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(spCaratteristica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 208, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tPostCodiceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tPostCodiceFocusLost
        Object o;
        String tab;
        if(tSelez.getSelectedItem().toString().equals("Discussione")) tab = "Discussione";
        else tab = "Files";
        try {
            if (Integer.decode(tPostCodice.getText()) > 0) {
                o = Database.leggiValore("select titolo from " + tab + " where "
                    + "ID = " + Integer.decode(tPostCodice.getText()));
                if (o != null) {
                    tPostDescrizione.setText(o.toString());
                }
            }
        } catch (NumberFormatException e) {
            //mostraErrori(e,TMARCACODICEFOCUSLOST);
            //tMarcaCodice.grabFocus();
        }
//        tModelloCodice.setText("");
//        tModelloDescrizione.setText("");
    }//GEN-LAST:event_tPostCodiceFocusLost

    private void tPostCodiceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tPostCodiceKeyPressed
        System.out.println("pressed");
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
        String sel = tSelez.getSelectedItem().toString();
        DBFrame1 m;
            if(sel.equals("Discussione")) m = new Discussione();
            else if(sel.equals("Foto")) m = new Foto();
            else m = new Video();
            m.setPadre(this);
            m.setVisible(true);
        }
    }//GEN-LAST:event_tPostCodiceKeyPressed

    private void tUtenteCodiceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tUtenteCodiceFocusLost
        Object o;
        try {
            if (Integer.decode(tUtenteCodice.getText()) > 0) {
                o = Database.leggiValore("select username from utente where "
                    + "ID = " + Integer.decode(tUtenteCodice.getText()));
                if (o != null) {
                    tUtenteDescrizione.setText(o.toString());
                }
            }
        } catch (NumberFormatException e) {
            //mostraErrori(e,TMARCACODICEFOCUSLOST);
            //tMarcaCodice.grabFocus();
        }
//        tModelloCodice.setText("");
//        tModelloDescrizione.setText("");
    }//GEN-LAST:event_tUtenteCodiceFocusLost

    private void tUtenteCodiceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tUtenteCodiceKeyPressed
        System.out.println("pressed");
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            Utente m;
            m = new Utente();
            m.setPadre(this);
            m.setVisible(true);
        }
    }//GEN-LAST:event_tUtenteCodiceKeyPressed

    private void tSelezItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tSelezItemStateChanged
        tPostCodice.setText("");
        tPostDescrizione.setText("");
    }//GEN-LAST:event_tSelezItemStateChanged

   protected void mostraDati() {
      try {
         tUtenteCodice.setText(rs.getString("UTENTEID"));
         String sel = rs.getString("DISCUSSIONEID");
         if (sel == null){
             sel = rs.getString("FILEID");
             tSelez.setSelectedItem("Foto");
             Object o = Database.leggiValore("select id from " + Database.schema + ".FILES RIGHT OUTER JOIN "
                    + Database.schema + ".FOTO " +  "ON ID = FILEID WHERE "
                    + "ID = " + sel);
            if (o != null) tSelez.setSelectedItem("Foto");
            else tSelez.setSelectedItem("Video");   
         }
         else tSelez.setSelectedItem("Discussione");
         tPostCodice.setText(sel);
         tData.setText(rs.getString("DATA"));
         
         //tUtenteCodiceFocusLost(null);
         //tPostCodiceFocusLost(null);
         super.mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }

   protected void pulisci() {
        super.pulisci();
        tUtenteCodice.setText("");
        tPostCodice.setText("");
        tUtenteDescrizione.setText("");
        tPostDescrizione.setText("");
        tData.setText(Timestamp.from(Instant.now()).toString());
   }

   protected PreparedStatement creaSelectStatement() {
      Connection con;
      PreparedStatement st = null;
      String ID = getTCodice().getText(),
        UTENTEID = tUtenteCodice.getText(),
        postid = tPostCodice.getText(),
        tab = tSelez.getSelectedItem().toString(),
        DATA = tData.getText();
      Pattern pat;
      Matcher matc;
      int k = 1;
      super.creaSelectStatement();
      query += " where 1=1 ";
      if (ID.length() > 0) {
         query += "and ID = ? ";
      }
      if (postid.length() > 0) {
         if (tab.equals("Discussione")) tab = "DISCUSSIONEID";
         else tab = "FILEID";
         if (postid.indexOf("%") >= 0) {
            query += "and " + tab + " like ? ";
         } else {
            query += "and " + tab + " = ? ";
         }
      }
      if (UTENTEID.length() > 0) {
         if (UTENTEID.indexOf("%") >= 0) {
            query += "and UTENTEID like ? ";
         } else {
            query += "and UTENTEID = ? ";
         }
      }
      
      pat = Pattern.compile("where$|and$");
      matc = pat.matcher(query);
      query = matc.replaceAll("");
      //System.out.println(query);
      //query+=" order by codice";
      try {
         con = Database.getDefaultConnection();
         st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, 
                 ResultSet.CONCUR_READ_ONLY);

         if (ID.length() > 0) {
            st.setInt(k++, Integer.decode(ID));
         }
         if (postid.length() > 0) {
            st.setString(k++, postid);
         }
         if (UTENTEID.length() > 0) {
            st.setString(k++, UTENTEID);
         }
      } catch (SQLException e) {
      }
      return st;
   }
   
//   protected void impostaCodice() {
//      String codice;
//      System.out.println("select max(ID)+1 from " + Database.schema + "."
//              + this.nomeTabella);
//      codice = Database.leggiValore("select nvl(max(ID)+1,1) from "
//              + Database.schema + "." + this.nomeTabella).toString();
//      tCodice.setText(codice);
//   }

   protected PreparedStatement getComandoInserimento(Connection c)
           throws SQLException {
      String queryIns;
      PreparedStatement st;
      queryIns = "insert into " + Database.schema + ".visualizza (ID,"
              + "UTENTEID,DISCUSSIONEID,FILEID,DATA) values(?,?,?,?,?)";
      st = c.prepareStatement(queryIns);
      st.setInt(1, Integer.decode(getTCodice().getText()));
      st.setString(2, tUtenteCodice.getText());
      if(tSelez.getSelectedItem().toString().equals("Discussione")){
         st.setString(3, tPostCodice.getText());
         st.setString(4, "");
      } else {
         st.setString(4, tPostCodice.getText());
         st.setString(3, ""); 
      }
      if (tData.getText().length() > 0) st.setTimestamp(5, Timestamp.valueOf(tData.getText()));
      else st.setTimestamp(5,  Timestamp.from(Instant.now()));
      return st;
   }

   protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException {
      String queryUp;
      PreparedStatement st;
      queryUp = "update " + Database.schema + ".visualizza set UTENTEID=?,DISCUSSIONEID=?,FILEID=?,TESTO=? where ID=?";
      st = c.prepareStatement(queryUp);
      st.setInt(5, Integer.decode(getTCodice().getText()));
      st.setString(1, tUtenteCodice.getText());
      if(tSelez.getSelectedItem().toString().equals("Discussione")){
         st.setString(2, tPostCodice.getText());
         st.setString(3, "");
      } else {
         st.setString(3, tPostCodice.getText());
         st.setString(2, ""); 
      }
      if (tData.getText().length() > 0) st.setTimestamp(4, Timestamp.valueOf(tData.getText()));
      else st.setTimestamp(4,  Timestamp.from(Instant.now()));
      return st;
   }
 
   /**
    * @param args the command line arguments
    */
   public static void main1(String args[]) {
      java.awt.EventQueue.invokeLater(new Runnable() {

         public void run() {
            new Visualizza().setVisible(true);
         }
      });
   }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane spCaratteristica;
    private javax.swing.JTextField tData;
    private javax.swing.JTextField tPostCodice;
    private javax.swing.JTextField tPostDescrizione;
    private javax.swing.JComboBox tSelez;
    private javax.swing.JTextField tUtenteCodice;
    private javax.swing.JTextField tUtenteDescrizione;
    private javax.swing.JTable tabCaratteristica;
    // End of variables declaration//GEN-END:variables
}
