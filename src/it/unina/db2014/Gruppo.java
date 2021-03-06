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
import java.util.ArrayList;

/**
 *
 * @author Massimo
 * @author ADeLuca
 */
public class Gruppo extends DBFrame1 {

   /**
    * Creates new form DBFrame
    */
   public Gruppo() {
      super();
      initComponents();
      tUtentiModel = new DefaultListModel();
      tUtenti.setModel(tUtentiModel);
      setModalita(APPEND_QUERY);
      setFrameTable(tabCaratteristica);
      setNomeTabella("gruppo");
   }
   
   public Gruppo(int usr) {
      this();
      tUtenteCodice.setText(Integer.toString(usr));
   }

   public final void setModalita(int modo) {
      super.setModalita(modo);
      switch (modo) {
         case APPEND_QUERY:
            tUtenteCodice.setEnabled(true);
            tNome.setEnabled(true);
            tDescrizione.setEnabled(true);
            tCreazione.setEnabled(true);
            tUtenti.setEnabled(true);
            tUtentiData.setEnabled(true);
            break;
         case BROWSE:
            tUtenteCodice.setEnabled(false);
            tNome.setEnabled(false);
            tDescrizione.setEnabled(false);
            tCreazione.setEnabled(false);
            tUtenti.setEnabled(false);
            tUtentiData.setEnabled(false);
            break;
         case UPDATE:
            tUtenteCodice.setEnabled(true);
            tNome.setEnabled(true);
            tDescrizione.setEnabled(true);
            tCreazione.setEnabled(true);
            tUtenti.setEnabled(true);
            tUtentiData.setEnabled(true);
            break;
      }
   }

   protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste gi? un'altro Gruppo con lo stesso codice";
         JOptionPane.showMessageDialog(this, msg, "Errore", 
                 JOptionPane.ERROR_MESSAGE);
      } else {
         super.mostraErrori(e, query, contesto);
      }
   }

   protected void premutoOK() {
      if (getPadre() != null) {
         getPadre().setProprietaPadre("Gruppo", getTCodice().getText());
         dispose();
      }
   }
   
   public void setProprietaPadre(String proprieta, String valore) {
        if (proprieta.equals("Utente")) {
            tUtenteCodice.setText(valore);
            tDescrizione.requestFocusInWindow();
        }
        if (proprieta.equals("Membro")) {
            if (!tUtentiModel.contains(valore)){
                tUtentiModel.addElement(valore);
            }
            tDescrizione.requestFocusInWindow();
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
        tNome = new javax.swing.JTextField();
        spCaratteristica = new javax.swing.JScrollPane();
        tabCaratteristica = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        tDescrizione = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tCreazione = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tUtenteCodice = new javax.swing.JTextField();
        tUtenteDescrizione = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tUtenti = new javax.swing.JList();
        tUtentiData = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gruppo");

        jLabel2.setText("Nome");

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

        jLabel4.setText("Descrizione");

        jLabel5.setText("Creazione");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/it/unina/logo.png"))); // NOI18N
        jLabel1.setText("jLabel1");

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

        jLabel16.setText("Creatore (F2 cerca)");

        tUtenti.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "azz", "dzx", "ftjh" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        tUtenti.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tUtentiFocusLost(evt);
            }
        });
        tUtenti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tUtentiKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tUtenti);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText(" (F2 cerca)");

        jLabel18.setText("(F3 Elimina)");

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Data");

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Membri");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(spCaratteristica, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(42, 42, 42)
                                .add(jLabel22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 69, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)
                                .add(tUtentiData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 385, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(jLabel2)
                                    .add(jLabel16)
                                    .add(jLabel4)
                                    .add(jLabel5)
                                    .add(jLabel17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel18))
                                .add(18, 18, 18)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(jScrollPane2)
                                    .add(tCreazione)
                                    .add(tDescrizione)
                                    .add(layout.createSequentialGroup()
                                        .add(tUtenteCodice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(tUtenteDescrizione))
                                    .add(tNome, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE))))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 258, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jLabel1)
                        .add(18, 18, 18))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(132, 132, 132)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(tUtenteCodice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(tUtenteDescrizione)
                            .add(jLabel16))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(tNome, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel2))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(tDescrizione, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel4))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(tCreazione, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel5))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel17)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel21)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel18))
                            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 101, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(tUtentiData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel22))
                        .add(18, 18, 18)))
                .add(spCaratteristica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 208, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(17, 17, 17))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void tUtentiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tUtentiFocusLost
        Object o;
        try {
            if (!tUtenti.isSelectionEmpty() && getTCodice().getText() != null) {
                o = Database.leggiValore("select DATACREAZIONE from MEMBERSHIP where"
                    + " UTENTEID = " + tUtentiModel.elementAt(tUtenti.getSelectedIndex()).toString()
                    + " AND GRUPPOID = " + getTCodice().getText());
                if (o != null) {
                    tUtentiData.setText(o.toString());
                }
            }
        } catch (NumberFormatException e) {
        }
    }//GEN-LAST:event_tUtentiFocusLost

    private void tUtentiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tUtentiKeyPressed
        System.out.println("pressed");
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            Utente m;
            if (tUtenti.isSelectionEmpty()) m = new Utente();
            else  m = new Utente(Integer.decode(tUtentiModel.elementAt(tUtenti.getSelectedIndex()).toString()));
            m.setPadre(this);
            m.setVisible(true);
            m.bMembro = true;
            m.Cerca();
        }
        else if (evt.getKeyCode() == KeyEvent.VK_F3 && !tUtenti.isSelectionEmpty()) {
            tUtentiModel.removeElementAt(tUtenti.getSelectedIndex());
        }
    }//GEN-LAST:event_tUtentiKeyPressed

   protected void mostraDati() {
      try {
         tUtenteCodice.setText(rs.getString("CREATOREID"));
         tNome.setText(rs.getString("NOME"));
         tDescrizione.setText(rs.getString("DESCRIZIONE"));
         tCreazione.setText(rs.getString("DATACREAZIONE"));
         
         tUtentiModel.clear();
         ArrayList<String> arr = Database.leggiValori("SELECT UTENTEID FROM MEMBERSHIP WHERE GRUPPOID = " + getTCodice().getText());
         for(String item : arr) tUtentiModel.addElement(item);
         
         super.mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }

   protected void pulisci() {
        super.pulisci();
        tUtenteCodice.setText("");
        tNome.setText("");
        tDescrizione.setText("");
        tCreazione.setText(Timestamp.from(Instant.now()).toString());
        tUtentiModel.clear();
        tUtentiData.setText("");
   }

   protected PreparedStatement creaSelectStatement() {
      Connection con;
      PreparedStatement st = null;
      String ID = getTCodice().getText(),
        CREATOREID = tUtenteCodice.getText(),
        NOME = tNome.getText(),
        DESCRIZIONE = tDescrizione.getText(),
        DATACREAZIONE = tCreazione.getText();
      Pattern pat;
      Matcher matc;
      int k = 1;
      super.creaSelectStatement();
      query += " where 1=1 ";
      if (ID.length() > 0) {
         query += "and ID= ? ";
      }
      if (NOME.length() > 0) {
         if (NOME.indexOf("%") >= 0) {
            query += "and NOME like ? ";
         } else {
            query += "and NOME = ? ";
         }
      }
      if (DESCRIZIONE.length() > 0) {
         if (DESCRIZIONE.indexOf("%") >= 0) {
            query += "and DESCRIZIONE like ? ";
         } else {
            query += "and DESCRIZIONE = ? ";
         }
      }
      if (CREATOREID.length() > 0) {
         if (CREATOREID.indexOf("%") >= 0) {
            query += "and CREATOREID like ? ";
         } else {
            query += "and CREATOREID = ? ";
         }
      }
      
      if(!tUtentiModel.isEmpty()){
          //query += "and ( ";
          query += "and " + tUtentiModel.getSize() + "  = ( SELECT COUNT(*) FROM " + Database.schema + ".MEMBERSHIP WHERE ID = GRUPPOID and ( ";
          for(Object item : tUtentiModel.toArray()) query += "UTENTEID = " + item + " OR ";
          //query += "1=2 )";
          query += "1=2 ) ) ";
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
         if (NOME.length() > 0) {
            st.setString(k++, NOME);
         }
         if (DESCRIZIONE.length() > 0) {
            st.setString(k++, DESCRIZIONE);
         }
         if (CREATOREID.length() > 0) {
            st.setString(k++, CREATOREID);
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
      String queryIns, app;
      PreparedStatement st;
      int codice = Integer.decode(getTCodice().getText());
      queryIns = "insert all into " + Database.schema + ".gruppo (ID,"
              + "CREATOREID,NOME,DESCRIZIONE,DATACREAZIONE) values(?,?,?,?,?)";
      
      if (tUtentiData.getText().length() > 0) app = tUtentiData.getText();
      else app = Timestamp.from(Instant.now()).toString();  
      for(Object o : tUtentiModel.toArray()){
          queryIns += "into " + Database.schema + ".MEMBERSHIP (GRUPPOID,UTENTEID,DATACREAZIONE)"
                  + " values(" + codice + "," + o.toString() + ",TIMESTAMP '" + app + "') ";
      }
      queryIns += " SELECT * FROM dual";
      
      st = c.prepareStatement(queryIns);
      st.setInt(1, codice);
      st.setString(2, tUtenteCodice.getText());
      st.setString(3, tNome.getText());
      st.setString(4, tDescrizione.getText());
      if (tCreazione.getText().length() > 0) st.setTimestamp(5, Timestamp.valueOf(tCreazione.getText()));
      else st.setTimestamp(5,  Timestamp.from(Instant.now()));
      return st;
   }

   protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException {
      int codice = Integer.decode(getTCodice().getText());
      String queryUp;
      PreparedStatement st;
      queryUp = "update " + Database.schema + ".gruppo set CREATOREID=?,NOME=?,DESCRIZIONE=?,DATACREAZIONE=? where ID=?";
      st = c.prepareStatement(queryUp);
      st.setInt(5, codice);
      st.setString(1, tUtenteCodice.getText());
      st.setString(2, tNome.getText());
      st.setString(3, tDescrizione.getText());
      if (tCreazione.getText().length() > 0) st.setTimestamp(4, Timestamp.valueOf(tCreazione.getText()));
      else st.setTimestamp(4,  Timestamp.from(Instant.now()));
      
      ArrayList<String> old = Database.leggiValori("SELECT UTENTEID FROM " + Database.schema + ".MEMBERSHIP WHERE GRUPPOID = " + codice);
      for(String item : old){
          if(!tUtentiModel.contains(item)) eseguiComando("delete from " + Database.schema + ".MEMBERSHIP where UTENTEID = " + item + " and GRUPPOID = " + codice);
      }
      for(Object item : tUtentiModel.toArray()){
          if(!old.contains(item)) eseguiComando("insert into " + Database.schema + ".MEMBERSHIP (GRUPPOID,UTENTEID,DATACREAZIONE) values(" + codice + "," + item
                  + ",TIMESTAMP '" + Timestamp.from(Instant.now()).toString() + "') ");
      }
      
      return st;
   }
 
   /**
    * @param args the command line arguments
    */
   public static void main1(String args[]) {
      java.awt.EventQueue.invokeLater(new Runnable() {

         public void run() {
            new Gruppo().setVisible(true);
         }
      });
   }
   
    private DefaultListModel tUtentiModel;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane spCaratteristica;
    private javax.swing.JTextField tCreazione;
    private javax.swing.JTextField tDescrizione;
    private javax.swing.JTextField tNome;
    private javax.swing.JTextField tUtenteCodice;
    private javax.swing.JTextField tUtenteDescrizione;
    private javax.swing.JList tUtenti;
    private javax.swing.JTextField tUtentiData;
    private javax.swing.JTable tabCaratteristica;
    // End of variables declaration//GEN-END:variables
}
