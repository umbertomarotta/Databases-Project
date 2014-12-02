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
public class Discussione extends DBFrame1 {

   /**
    * Creates new form DBFrame
    */
   public Discussione() {
      super();
      initComponents();
      setModalita(APPEND_QUERY);
      setFrameTable(tabCaratteristica);
      setNomeTabella("discussione");
   }
   
   public Discussione(int usr) {
      this();
      tUtenteCodice.setText(Integer.toString(usr));
   }

   public final void setModalita(int modo) {
      super.setModalita(modo);
      switch (modo) {
         case APPEND_QUERY:
            tUtenteCodice.setEnabled(true);
            tGruppoCodice.setEnabled(true);
            tTitolo.setEnabled(true);
            tTesto.setEnabled(true);
            tCreazione.setEnabled(true);
            break;
         case BROWSE:
            tUtenteCodice.setEnabled(false);
            tGruppoCodice.setEnabled(false);
            tTitolo.setEnabled(false);
            tTesto.setEnabled(false);
            tCreazione.setEnabled(false);
            break;
         case UPDATE:
            tUtenteCodice.setEnabled(true);
            tGruppoCodice.setEnabled(true);
            tTitolo.setEnabled(true);
            tTesto.setEnabled(true);
            tCreazione.setEnabled(true);
            break;
      }
   }

   protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste gi? un'altra Discussione con lo stesso codice";
         JOptionPane.showMessageDialog(this, msg, "Errore", 
                 JOptionPane.ERROR_MESSAGE);
      } else {
         super.mostraErrori(e, query, contesto);
      }
   }

   protected void premutoOK() {
      if (getPadre() != null) {
         getPadre().setProprietaPadre("Discussione", getTCodice().getText());
         dispose();
      }
   }
   
   public void setProprietaPadre(String proprieta, String valore) {
        if (proprieta.equals("Utente")) {
            tUtenteCodice.setText(valore);
            tTesto.requestFocusInWindow();
        }
        if (proprieta.equals("Gruppo")) {
            tGruppoCodice.setText(valore);
            tTesto.requestFocusInWindow();
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
        tTitolo = new javax.swing.JTextField();
        spCaratteristica = new javax.swing.JScrollPane();
        tabCaratteristica = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        tTesto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tCreazione = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tGruppoCodice = new javax.swing.JTextField();
        tGruppoDescrizione = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tUtenteCodice = new javax.swing.JTextField();
        tUtenteDescrizione = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Discussione");

        jLabel2.setText("Titolo");

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

        jLabel4.setText("Testo");

        jLabel5.setText("Creazione");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/it/unina/logo.png"))); // NOI18N
        jLabel1.setText("jLabel1");

        tGruppoCodice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tGruppoCodiceFocusLost(evt);
            }
        });
        tGruppoCodice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tGruppoCodiceKeyPressed(evt);
            }
        });

        jLabel11.setText("Gruppo (F2 cerca)");

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

        jLabel16.setText("Starter (F2 cerca)");

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
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel2)
                                .add(26, 26, 26)
                                .add(tTitolo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 385, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(layout.createSequentialGroup()
                                .add(jLabel5)
                                .add(26, 26, 26)
                                .add(tCreazione, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 385, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(layout.createSequentialGroup()
                                .add(jLabel4)
                                .add(26, 26, 26)
                                .add(tTesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 385, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel11)
                                    .add(jLabel16))
                                .add(13, 13, 13)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(tUtenteCodice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(tUtenteDescrizione, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 288, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(layout.createSequentialGroup()
                                        .add(tGruppoCodice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(tGruppoDescrizione, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 288, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
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
                            .add(tGruppoCodice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(tGruppoDescrizione, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel11))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2)
                            .add(tTitolo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel4)
                            .add(tTesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel5)
                            .add(tCreazione, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(115, 115, 115)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(spCaratteristica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 208, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tGruppoCodiceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tGruppoCodiceFocusLost
        Object o;
        try {
            if (Integer.decode(tGruppoCodice.getText()) > 0) {
                o = Database.leggiValore("select nome from gruppo where "
                    + "ID=?", Integer.decode(tGruppoCodice.getText()));
                if (o != null) {
                    tGruppoDescrizione.setText(o.toString());
                }
            }
        } catch (NumberFormatException e) {
            //mostraErrori(e,TMARCACODICEFOCUSLOST);
            //tMarcaCodice.grabFocus();
        }
//        tModelloCodice.setText("");
//        tModelloDescrizione.setText("");
    }//GEN-LAST:event_tGruppoCodiceFocusLost

    private void tGruppoCodiceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tGruppoCodiceKeyPressed
        System.out.println("pressed");
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            Gruppo m;
            m = new Gruppo();
            m.setPadre(this);
            m.setVisible(true);
        }
    }//GEN-LAST:event_tGruppoCodiceKeyPressed

    private void tUtenteCodiceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tUtenteCodiceFocusLost
        Object o;
        try {
            if (Integer.decode(tUtenteCodice.getText()) > 0) {
                o = Database.leggiValore("select username from utente where "
                    + "ID=?", Integer.decode(tUtenteCodice.getText()));
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

   protected void mostraDati() {
      try {
         tUtenteCodice.setText(rs.getString("STARTERID"));
         tGruppoCodice.setText(rs.getString("GRUPPOID"));
         tTitolo.setText(rs.getString("TITOLO"));
         tTesto.setText(rs.getString("TESTO"));
         tCreazione.setText(rs.getString("DATACREAZIONE"));
         super.mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }

   protected void pulisci() {
        super.pulisci();
        tUtenteCodice.setText("");
        tGruppoCodice.setText("");
        tTitolo.setText("");
        tTesto.setText("");
        tCreazione.setText(Timestamp.from(Instant.now()).toString());
   }

   protected PreparedStatement creaSelectStatement() {
      Connection con;
      PreparedStatement st = null;
      String ID = getTCodice().getText(),
        STARTERID = tUtenteCodice.getText(),
        GRUPPOID = tGruppoCodice.getText(),
        TITOLO = tTitolo.getText(),
        TESTO = tTesto.getText(),
        DATACREAZIONE = tCreazione.getText();
      Pattern pat;
      Matcher matc;
      int k = 1;
      super.creaSelectStatement();
      query += " where 1=1 ";
      if (ID.length() > 0) {
         query += "and ID = ? ";
      }
      if (GRUPPOID.length() > 0) {
         if (GRUPPOID.indexOf("%") >= 0) {
            query += "and GRUPPOID like ? ";
         } else {
            query += "and GRUPPOID = ? ";
         }
      }
      if (TITOLO.length() > 0) {
         if (TITOLO.indexOf("%") >= 0) {
            query += "and TITOLO like ? ";
         } else {
            query += "and TITOLO = ? ";
         }
      }
      if (TESTO.length() > 0) {
         if (TESTO.indexOf("%") >= 0) {
            query += "and TESTO like ? ";
         } else {
            query += "and TESTO = ? ";
         }
      }
      if (STARTERID.length() > 0) {
         if (STARTERID.indexOf("%") >= 0) {
            query += "and STARTERID like ? ";
         } else {
            query += "and STARTERID = ? ";
         }
      }
      
      pat = Pattern.compile("where$|and$");
      matc = pat.matcher(query);
      query = matc.replaceAll("");
      //System.out.println(query);
      //System.out.println("azz: " + COMPRESSION + " num: " + COMPRESSION.length());
      //query+=" order by codice";
      try {
         con = Database.getDefaultConnection();
         st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, 
                 ResultSet.CONCUR_READ_ONLY);

         if (ID.length() > 0) {
            st.setInt(k++, Integer.decode(ID));
         }
         if (GRUPPOID.length() > 0) {
            st.setString(k++, GRUPPOID);
         }
         if (TITOLO.length() > 0) {
            st.setString(k++, TITOLO);
         }
         if (TESTO.length() > 0) {
            st.setString(k++, TESTO);
         }
         if (STARTERID.length() > 0) {
            st.setString(k++, STARTERID);
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
      queryIns = "insert into " + Database.schema + ".discussione (ID,"
              + "GRUPPOID,STARTERID,TITOLO,TESTO,DATACREAZIONE) values(?,?,?,?,?,?)";
      st = c.prepareStatement(queryIns);
      st.setInt(1, Integer.decode(getTCodice().getText()));
      st.setString(2, tGruppoCodice.getText());
      st.setString(3, tUtenteCodice.getText());
      st.setString(4, tTitolo.getText());
      st.setString(5, tTesto.getText());
      if (tCreazione.getText().length() > 0) st.setTimestamp(6, Timestamp.valueOf(tCreazione.getText()));
      else st.setTimestamp(6,  Timestamp.from(Instant.now()));
      return st;
   }

   protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException {
      String queryUp;
      PreparedStatement st;
      queryUp = "update " + Database.schema + ".discussione set GRUPPOID=?,STARTERID=?,TITOLO=?,TESTO=?,DATACREAZIONE=? where ID=?";
      st = c.prepareStatement(queryUp);
      st.setInt(6, Integer.decode(getTCodice().getText()));
      st.setString(1, tGruppoCodice.getText());
      st.setString(2, tUtenteCodice.getText());
      st.setString(3, tTitolo.getText());
      st.setString(4, tTesto.getText());
      if (tCreazione.getText().length() > 0)  st.setTimestamp(5, Timestamp.valueOf(tCreazione.getText()));
      else st.setTimestamp(5,  Timestamp.from(Instant.now()));
      return st;
   }
 
   /**
    * @param args the command line arguments
    */
   public static void main1(String args[]) {
      java.awt.EventQueue.invokeLater(new Runnable() {

         public void run() {
            new Discussione().setVisible(true);
         }
      });
   }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane spCaratteristica;
    private javax.swing.JTextField tCreazione;
    private javax.swing.JTextField tGruppoCodice;
    private javax.swing.JTextField tGruppoDescrizione;
    private javax.swing.JTextField tTesto;
    private javax.swing.JTextField tTitolo;
    private javax.swing.JTextField tUtenteCodice;
    private javax.swing.JTextField tUtenteDescrizione;
    private javax.swing.JTable tabCaratteristica;
    // End of variables declaration//GEN-END:variables
}
