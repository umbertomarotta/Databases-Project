/*
 * DBFrame.java
 *
 * Modified on 2013-06-03
 */
package it.unina.db2014;

import java.sql.*;
import javax.swing.*;

/**
 * Classe base per i frame di gestione delle tabelle principali. <br> Permette
 * le operazioni di inserimento, cancellazione, ricerca e navigazione.
 *
 * @author Massimo
 * @author ADeLuca
 * @version %I%, 2013
 */
abstract public class DBFrame1 extends javax.swing.JFrame implements UsaLookup {

   /**
    * Creates new form DBFrame.
    */
   public DBFrame1() {
      initComponents();
      bOk.setVisible(false);
   }
   /**
    * Indica che il frame si trova nello stato di inserimento di un nuovo record
    * o dei parametri di ricerca.
    */
   final static public int APPEND_QUERY = 1;
   /**
    * Indica che il frame si trova nello stato di navigazione (ricerca
    * gi&agrave; effettuata).
    */
   final static public int BROWSE = 2;
   /**
    * Indica che il frame si trova nello stato di modifica dei dati.
    */
   final static public int UPDATE = 3;
   /**
    * indica che l'eccezione &egrave; stata sollevata eseguendo un comando
    * <i>SELECT</i>.
    */
   final static public int CONTESTO_ESEGUI_QUERY = 1;
   
   private int modalita; // Stato corrente del Frame
   protected int pos = 1; // Posizione del record corrente nel ResultSet.
   private DBTableModel modelloTabella; // modello della tabella di navigazione
   /**
    * ResultSet dell'ultima query eseguita.
    */
   protected ResultSet rs;
   /**
    * Query da eseguire.
    */
   protected String query;
   private String nomeTabella; // nome della tabella (o vista)
   private javax.swing.JTable tabFrameTable;
   private UsaLookup padre = null; // form che usa quello corrente come Lookup

   /**
    * Imposta il Puntatore al Form che usa quello corrente cone Lookup.
    */
   public void setPadre(UsaLookup p) {
      padre = p;
      if (padre == null) {
         bOk.setVisible(false);
      } else {
         bOk.setVisible(true);
      }
   }

   /**
    * Restituisce il puntatore al Form che usa quello corrente come Lookup.
    */
   public UsaLookup getPadre() {
      return padre;
   }

   DBTableModel getModelloTabella() {
      return modelloTabella;
   }

   void setModelloTabella(DBTableModel gmt) {
      modelloTabella = gmt;
   }

   String getQuery() {
      return query;
   }

   void setQuery(String query) {
      this.query = query;
   }

   String getNomeTabella() {
      return nomeTabella;
   }

   void setNomeTabella(String nomeTabella) {
      this.nomeTabella = nomeTabella;
      //imposta una query senza risultati
      // query="select * from "+Database.schema+"."+nomeTabella
      //    +" where codice<>codice";
   }

   /**
    * Metodo usato dalle form di lookup per passare dati al form chiamante. <br>
    * In DBFrame &egrave; disponibile un metodo vuoto per le form che non
    * necessitano di lookup.
    */
   @Override
   public void setProprietaPadre(String proprieta, String valore) {
      //non pu? essere abstract, per alcuni frame non ? necessario
   }

   /**
    * Imposta la tabella di navigazione del form. <br> In genere dovrebbe essere
    * richiamato nei costruttori delle classi derivate.
    *
    */
   protected void setFrameTable(JTable t) {
      tabFrameTable = t;
      modelloTabella = new DBTableModel();
      tabFrameTable.setModel(modelloTabella);
      tabFrameTable.getSelectionModel().setSelectionMode(
              ListSelectionModel.SINGLE_SELECTION);
      tabFrameTable.addMouseListener(new java.awt.event.MouseAdapter() {

         public void mouseReleased(java.awt.event.MouseEvent evt) {
            //       selezioneTabellaCambiata();
         }
      });

      tabFrameTable.getSelectionModel().addListSelectionListener(
              new javax.swing.event.ListSelectionListener() {

                 public void valueChanged(
                         javax.swing.event.ListSelectionEvent e) {
                    selezioneTabellaCambiata();
                 }
              });
   }

   /**
    * Chiave primaria del record corrente.
    */
   protected javax.swing.JTextField getTCodice() {
      return tCodice;
   }

   /**
    * Imposta lo stato corrente del form. <br> In base allo stato vengono
    * abilitati o disabilitati alcuni oggetti del form.
    * 
    * @param modo intero che rappresenta lo stato
    */
   public void setModalita(int modo) {
      modalita = modo;
      switch (modo) {
         case APPEND_QUERY:
            bPrimo.setEnabled(false);
            bPrecedente.setEnabled(false);
            bSuccessivo.setEnabled(false);
            bUltimo.setEnabled(false);
            bNuovo.setEnabled(true);
            bApri.setEnabled(false);
            bSalva.setEnabled(true);
            bCerca.setEnabled(true);
            bAnnulla.setEnabled(true);
            bElimina.setEnabled(false);
            if (tabFrameTable != null) {
               tabFrameTable.setEnabled(false);
            }
            tCodice.setEnabled(true);
            bOk.setEnabled(false);
            break;
         case BROWSE:
            bPrimo.setEnabled(true);
            bPrecedente.setEnabled(true);
            bSuccessivo.setEnabled(true);
            bUltimo.setEnabled(true);
            bNuovo.setEnabled(true);
            bApri.setEnabled(true);
            bSalva.setEnabled(false);
            bCerca.setEnabled(false);
            bAnnulla.setEnabled(false);
            bElimina.setEnabled(false);
            if (tabFrameTable != null) {
               tabFrameTable.setEnabled(true);
            }
            tCodice.setEnabled(false);
            bOk.setEnabled(true);
            break;
         case UPDATE:
            bPrimo.setEnabled(false);
            bPrecedente.setEnabled(false);
            bSuccessivo.setEnabled(false);
            bUltimo.setEnabled(false);
            bNuovo.setEnabled(true);
            bApri.setEnabled(false);
            bSalva.setEnabled(true);
            bCerca.setEnabled(false);
            bAnnulla.setEnabled(true);
            bElimina.setEnabled(true);
            if (tabFrameTable != null) {
               tabFrameTable.setEnabled(false);
            }
            tCodice.setEnabled(false);
            bOk.setEnabled(false);
            break;
      }
   }

   /**
    * Mostra una descrizione di un errore SQL in un linguaggio comprensibile per
    * l'utente finale. <br> L'implementazione di DBFrame fa eccezione, essa
    * fornisce un messaggio standard per gli errori non previsti esplicitamente.
    * 
    * @param e eccezione SQLException catturata
    * @param query l'istruzione SQL che ha causato l'errore
    * @param contesto intero per distinguere se l'eccezione ha avuto origine
    * da una query
    */
   protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if ((e.getErrorCode() == 17068 | e.getErrorCode() == 17011 | e.getErrorCode() == 17014 | e.getErrorCode() == 17289) & 
              contesto == 0) {
         return; //questo errore non mi interessa
      }
      System.out.println(query);
      msg = "ErrorCode= " + e.getErrorCode() + "\n";
      msg += "Message= " + e.getMessage() + "\n";
      msg += "SQLState= " + e.getSQLState() + "\n";

      JOptionPane.showMessageDialog(this, msg, "Errore",
              JOptionPane.ERROR_MESSAGE);
   }

   /**
    * Mostra una descrizione di un errore SQL in un linguaggio comprensibile per
    * l'utente finale. <br> L'implementazione di DBFrame fa eccezione, essa
    * fornisce un messaggio standard per gli errori non previsti esplicitamente.
    *
    */
   protected void mostraErrori(SQLException e) {
      mostraErrori(e, "", 0);
   }

   /**
    * Mostra una descrizione di un errore in un linguaggio comprensibile per
    * l'utente finale. <br> L'implementazione di DBFrame fa eccezione, essa
    * fornisce un messaggio standard per gli errori non previsti esplicitamente.
    *
    */
   protected void mostraErrori(Exception e, int contesto) {
      String msg;
      msg = "Message= " + e.getMessage() + "\n";

      JOptionPane.showMessageDialog(this, msg, "Errore",
              JOptionPane.ERROR_MESSAGE);
   }

   /**
    * Mostra una descrizione di un errore in un linguaggio comprensibile per
    * l'utente finale. <br> L'implementazione di DBFrame fa eccezione, essa
    * fornisce un messaggio standard per gli errori non previsti esplicitamente.
    *
    */
   protected void mostraErrori(Exception e) {
      mostraErrori(e, 0);
   }

   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dBTableModel1 = new it.unina.db2014.DBTableModel();
        bPrimo = new javax.swing.JButton();
        bPrecedente = new javax.swing.JButton();
        bSuccessivo = new javax.swing.JButton();
        bUltimo = new javax.swing.JButton();
        bNuovo = new javax.swing.JButton();
        bSalva = new javax.swing.JButton();
        bCerca = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tCodice = new javax.swing.JTextField();
        bApri = new javax.swing.JButton();
        bAnnulla = new javax.swing.JButton();
        bElimina = new javax.swing.JButton();
        bOk = new javax.swing.JButton();
        bNuovoCodice = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bPrimo.setText("|<");
        bPrimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrimoActionPerformed(evt);
            }
        });

        bPrecedente.setText("<");
        bPrecedente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrecedenteActionPerformed(evt);
            }
        });

        bSuccessivo.setText(">");
        bSuccessivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSuccessivoActionPerformed(evt);
            }
        });

        bUltimo.setText(">|");
        bUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUltimoActionPerformed(evt);
            }
        });

        bNuovo.setText("Nuovo");
        bNuovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNuovoActionPerformed(evt);
            }
        });

        bSalva.setText("Salva");
        bSalva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalvaActionPerformed(evt);
            }
        });

        bCerca.setText("Cerca");
        bCerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCercaActionPerformed(evt);
            }
        });

        jLabel1.setText("Codice");

        bApri.setText("Apri");
        bApri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bApriActionPerformed(evt);
            }
        });

        bAnnulla.setText("Annulla");
        bAnnulla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAnnullaActionPerformed(evt);
            }
        });

        bElimina.setText("Elimina");
        bElimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminaActionPerformed(evt);
            }
        });

        bOk.setText("OK");
        bOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOkActionPerformed(evt);
            }
        });

        bNuovoCodice.setText("123...");
        bNuovoCodice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNuovoCodiceActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(bPrimo)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bPrecedente)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bSuccessivo)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bUltimo)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bNuovo)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bApri)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bSalva))
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .add(55, 55, 55)
                        .add(tCodice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 143, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(20, 20, 20)
                        .add(bNuovoCodice)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(bCerca)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bElimina))
                    .add(layout.createSequentialGroup()
                        .add(bOk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bAnnulla)))
                .add(96, 96, 96))
        );

        layout.linkSize(new java.awt.Component[] {bCerca, bOk}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.linkSize(new java.awt.Component[] {bAnnulla, bElimina}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(bPrimo)
                    .add(bPrecedente)
                    .add(bSuccessivo)
                    .add(bUltimo)
                    .add(bNuovo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(bSalva, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(bApri, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(bElimina, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(bCerca, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(20, 20, 20)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(tCodice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(bNuovoCodice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(bOk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(bAnnulla, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(372, Short.MAX_VALUE))
        );

        layout.linkSize(new java.awt.Component[] {bAnnulla, bApri, bCerca, bElimina, bNuovo, bNuovoCodice, bOk, bSalva, bUltimo}, org.jdesktop.layout.GroupLayout.VERTICAL);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bNuovoCodiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNuovoCodiceActionPerformed
       impostaCodice();
    }//GEN-LAST:event_bNuovoCodiceActionPerformed

   /**
    * Calcola un nuovo codice in base ai valori gi&agrave; presenti.
    */
   protected void impostaCodice() {
      String codice;
      System.out.println("select max(ID)+1 from " + Database.schema + "."
              + this.nomeTabella);
      codice = Database.leggiValore("select nvl(max(ID)+1,1) from "
              + Database.schema + "." + this.nomeTabella).toString();
      tCodice.setText(codice);
   }
   
   protected void impostaCodice(String codice) {
      tCodice.setText(codice);
   }

   /**
    * Metodo da usare nelle form di lookup per passare i dati alla form
    * chiamante.
     *
    */
   protected void premutoOK() {
      //non pu? essere abstract, per alcuni frame non ? necessario
   }

    private void bOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOkActionPerformed
       premutoOK();
    }//GEN-LAST:event_bOkActionPerformed

    private void bEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminaActionPerformed
       String cmd;
       cmd = "delete from " + Database.schema + "." + nomeTabella
               + " where ID=" + tCodice.getText();
       try {
          if (rs.isLast()) {
             pos--;
          }
       } catch (SQLException e) {
          mostraErrori(e);
       }
       eseguiComando(cmd);
       pulisci();
       eseguiQuery();
    }//GEN-LAST:event_bEliminaActionPerformed

    private void bAnnullaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAnnullaActionPerformed
       eseguiQuery();
       if (getPadre() != null) {
          try {
             rs.close();
          } catch (SQLException e) {
             mostraErrori(e);
          } finally {
             dispose();
          }
       }
    }//GEN-LAST:event_bAnnullaActionPerformed

   private void selezioneTabellaCambiata() {
      //Load.Start();
      try {
         rs.absolute(
                 tabFrameTable.getSelectionModel().getMinSelectionIndex() + 1);
         mostraDati();
      } catch (SQLException e) {
         mostraErrori(e);
      } catch (Exception a) {
      } finally {
          //Load.End();
      }
   }

    private void bApriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bApriActionPerformed

       setModalita(UPDATE);
       // vecchioCodice=Integer.getInteger(tCodice.getText());
    }//GEN-LAST:event_bApriActionPerformed

   /**
    * Mostra i dati presenti nel record corrente. <br> Necessita di overriding
    * in tutte le classi derivate di DBFrame.  
    *
    */
   protected void mostraDati() {
      try {
         tCodice.setText(rs.getString("ID"));
         pos = rs.getRow();
         //tabFrameTable.getSelectionModel().setSelectionInterval(pos-1,pos-1);
         tabFrameTable.setRowSelectionInterval(pos - 1, pos - 1);
      } catch (SQLException e) {
         mostraErrori(e);
      }
   }
    private void bUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUltimoActionPerformed
// TODO add your handling code here:
       try {
          rs.last();
       } catch (SQLException e) {
          mostraErrori(e);
       }
       mostraDati();
    }//GEN-LAST:event_bUltimoActionPerformed

    private void bSuccessivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSuccessivoActionPerformed
// TODO add your handling code here:
       try {
          if (!rs.isLast()) {
             rs.next();
          }
       } catch (SQLException e) {
          mostraErrori(e);
       }
       mostraDati();
    }//GEN-LAST:event_bSuccessivoActionPerformed

    private void bPrecedenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrecedenteActionPerformed
// TODO add your handling code here:
       try {
          if (!rs.isFirst()) {
             rs.previous();
          }
       } catch (SQLException e) {
          mostraErrori(e);
       }
       mostraDati();
    }//GEN-LAST:event_bPrecedenteActionPerformed

    private void bPrimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrimoActionPerformed
       try {
          rs.first();
       } catch (SQLException e) {
          mostraErrori(e);
       }
       mostraDati();
    }//GEN-LAST:event_bPrimoActionPerformed

   /**
    * Crea lo statement di ricerca. <br> Necessita di overriding in tutte le
    * classi derivate di DBFrame.  
    */
   protected PreparedStatement creaSelectStatement() {
      query = "select * from " + Database.schema + "." + nomeTabella + " ";
      return null;
   }
    private void bCercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCercaActionPerformed
       //creaSelectQuery();
       //System.out.println(query);
       eseguiQuery();
    }//GEN-LAST:event_bCercaActionPerformed

    public void Cerca(){                                       
       eseguiQuery();
    }  
   /**
    * Esegue una ricerca in base ai valori impostati nel form. <br> Non
    * necessita di overriding nella classi derivate, occorre invece
    * specializzare il metodo <i>creaSelectStatement</i>.
    */
   protected void eseguiQuery(){

      PreparedStatement st;
      //Load.Start();
      //while(true) if(false) break; //else System.out.println("2!");
      try {                 
         st = creaSelectStatement();
         rs = st.executeQuery();
         modelloTabella.setRS(rs);
         rs.absolute(pos);
         //mostraDati();
         setModalita(BROWSE);
      } catch (SQLException e) {
         mostraErrori(e, query, CONTESTO_ESEGUI_QUERY);
      } catch (java.lang.NullPointerException e) {
         //System.out.println(e.toString());
         /* non devo mostrare nessun errore
          * si dovrebbe verificare solo se st=null
          * quando la connessione ? caduta */
      } finally {
          //Load.End();
      }
   }

   /**
    * Cancella i dati presenti in tutti i controlli presenti sul form. <br>
    * Necessita di overriding in tutte le classi derivate di DBFrame.  
    */
   protected void pulisci() {
      tCodice.setText("");
   }
    private void bNuovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNuovoActionPerformed
       pulisci();
       pos = 1;
       try {
          if (rs != null) {
             rs.close();
          }
          rs = null;
          modelloTabella.setRS(rs);
          setModalita(APPEND_QUERY);
       } catch (SQLException e) {
          mostraErrori(e);
       }
    }//GEN-LAST:event_bNuovoActionPerformed

   /**
    * Crea lo statement di inserimento. <br> Necessita di overriding in tutte le
    * classi derivate di DBFrame.  
    */
   abstract protected PreparedStatement getComandoInserimento(Connection c)
           throws SQLException;

   /**
    * Crea lo statement di aggiornamento. <br> Necessita di overriding in tutte
    * le classi derivate di DBFrame.  
    */
   abstract protected PreparedStatement getComandoAggiornamento(Connection c)
           throws SQLException;

    private void bSalvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalvaActionPerformed
       //String query;
       PreparedStatement st;
       boolean ret;
       boolean frr = true;
       Connection c = null;
       if (this.tCodice.getText().trim().length() == 0) {
          impostaCodice();
       } else {
          System.out.println("il codice non risulta vuoto");
       }
       try {
          c = Database.nuovaConnessione();
          if (modalita == APPEND_QUERY) {
             st = getComandoInserimento(c);
          } else {
             st = getComandoAggiornamento(c);
          }
          System.out.println(1);
          ret = false;
          c.setAutoCommit(false);
          ret = st.executeUpdate() >= 0;//eseguiComando(query,c);
          if (ret) {
             System.out.println(10);
             ret = eseguiSalva(c);
          }
          if (ret) {
             c.commit();
          } else {
             c.rollback();
          }
          c.setAutoCommit(true);
          System.out.println(100);
       } catch (SQLException e) {
          mostraErrori(e);
          ret = false;
       } catch (IllegalArgumentException e){
          JOptionPane.showMessageDialog(this, "Uno o piu' campi sono formattati male!", "Errore", JOptionPane.ERROR_MESSAGE);
          ret = false;
          frr = false;
       }
       if (ret) {
          if (modalita == APPEND_QUERY) {
             bCercaActionPerformed(evt);
          } else {
             eseguiQuery();
          }
       } else {
          try {
             c.rollback();
             c.setAutoCommit(true);
          } catch (SQLException e) {
             mostraErrori(e);
             ret = false;
          }
       }
    }//GEN-LAST:event_bSalvaActionPerformed

   /**
    * Esegue le operazioni collaterali al salvataggio del record principale, ad
    * esempio tutti i dati nelle relazioni. 
    * 
    * @param con connessione corrente
    * @return sempre true
    */
   protected boolean eseguiSalva(Connection con) {
      return true;
   }

   protected boolean eseguiComando(String cmd) {
      return eseguiComando(cmd, null);
   }
   //modificato

   private boolean eseguiComando(String cmd, Connection c) {
      Statement s;
      //String queryX;
      Connection mycon = null;
      try {
         if (c == null) {
            mycon = Database.nuovaConnessione();
         } else {
            mycon = c;
         }
         s = mycon.createStatement();
         s.execute(cmd);
      } catch (SQLException e) {
         mostraErrori(e);
         return false;
      } finally {
         if (c == null) {
            try {
               mycon.close();
            } catch (SQLException e) {
               mostraErrori(e);
            }
         }
      }
      return true;
   }
   
    protected Boolean bTag = false;
    protected Boolean bStar = false;
    protected Boolean bFollower = false;
    protected Boolean bFollowing = false;
    protected Boolean bMembro = false;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAnnulla;
    private javax.swing.JButton bApri;
    private javax.swing.JButton bCerca;
    private javax.swing.JButton bElimina;
    private javax.swing.JButton bNuovo;
    private javax.swing.JButton bNuovoCodice;
    private javax.swing.JButton bOk;
    private javax.swing.JButton bPrecedente;
    private javax.swing.JButton bPrimo;
    private javax.swing.JButton bSalva;
    private javax.swing.JButton bSuccessivo;
    private javax.swing.JButton bUltimo;
    private it.unina.db2014.DBTableModel dBTableModel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField tCodice;
    // End of variables declaration//GEN-END:variables
}
