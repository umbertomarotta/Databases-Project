/*
 * CaratteristicheTable.java
 *
 * Modified on 2013-06-03
 */
package it.unina.db2014;

import java.sql.*;
import javax.swing.*;

/**
 *
 * @author Massimo
 * @author ADeLuca
 */
public class CaratteristicheTable extends javax.swing.JTable
        implements UsaLookup {

   /**
    * Creates a new instance of CaratteristicheTable.
    */
   public CaratteristicheTable() {
      // super();
      modalita = BROWSE;
      modello = new CaratteristicaTableModel();
      setModel(modello);
      getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      this.setColumnSelectionAllowed(true);
      this.setCellSelectionEnabled(true);
      getColumnModel().getSelectionModel().setSelectionMode(
              ListSelectionModel.SINGLE_SELECTION);
      getColumnModel().getSelectionModel().addListSelectionListener(
              new javax.swing.event.ListSelectionListener() {

                 public void valueChanged(
                         javax.swing.event.ListSelectionEvent e) {
                    selezioneColonnaCambiata(e);
                 }
              });
      addKeyListener(new java.awt.event.KeyAdapter() {

         public void keyReleased(java.awt.event.KeyEvent evt) {
            tableKeyReleased(evt);
         }
      });
   }
   final static public int UPDATE = 1;
   final static public int BROWSE = 2;
   private int modalita;
   private int rigaCorrente = 0;
   private int rigaVecchia = 0;
   private int colonnaVecchia = 0;
   private int codice = -1;
   CaratteristicaTableModel modello;

   public void setModalita(int modo) {
      modalita = modo;
      if (modalita == UPDATE) {
         modello.setEditabile(true);
      } else {
         modello.setEditabile(false);
      }
   }

   private void tableKeyReleased(java.awt.event.KeyEvent evt) {
      int riga, colonna;
      if (modalita == UPDATE) {
         riga = getSelectedRow();
         colonna = getSelectedColumn();
         if (evt.getKeyCode() == evt.VK_DELETE & evt.isShiftDown()) {
            System.out.println("--premuto canc");
            modello.cancella(this.getSelectedRow());
            if (riga < modello.getColumnCount()) {
               setRowSelectionInterval(riga, riga);
            } else {
               setRowSelectionInterval(modello.getColumnCount() - 1,
                       modello.getColumnCount() - 1);
            }
            setColumnSelectionInterval(colonna, colonna);
         }
         if (evt.getKeyCode() == evt.VK_DOWN
                 & (modello.getRowCount() - 1 == riga
                 //getSelectionModel().getMinSelectionIndex()
                 | modello.getRowCount() == 0)) {
            modello.inserisciRiga();

            setRowSelectionInterval(modello.getRowCount() - 1,
                    modello.getRowCount() - 1);

            setColumnSelectionInterval(0, 0);
         }

         if (evt.getKeyCode() == evt.VK_F2) {
            Caratteristica m;
            m = new Caratteristica();
            m.setPadre(this);
            m.setVisible(true);
         }
      }
   }

   public void setProprietaPadre(String proprieta, String valore) {
      int riga;
      if (proprieta.equals("Caratteristica")) {
         riga = getSelectedRow();
         modello.setValueAt(valore, riga, 0);
         modello.fireTableRowsUpdated(riga, 0);
         setColumnSelectionInterval(1, 1);
      }
   }

   public void setCodice(int codice) {
      modello.setAllestimentoCodice(codice);
      getSelectionModel().setSelectionInterval(0, 0);
   }

   public void pulisci() {
      modello.pulisci();
   }

   protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;
      if (e.getErrorCode() == 1) {
         msg = "Esiste gi? questa caratteristica";
         JOptionPane.showMessageDialog(this, msg, "Errore",
                 JOptionPane.ERROR_MESSAGE);
      } else {
         JOptionPane.showMessageDialog(this, e.getMessage(), "Errore",
                 JOptionPane.ERROR_MESSAGE);
      }
   }

   public void selezioneColonnaCambiata(javax.swing.event.ListSelectionEvent e){
      int riga, colonna;
      Object val;
      riga = getSelectedRow();
      colonna = getSelectedColumn();
      System.out.println("colonna:" + e.getFirstIndex() + " "
              + e.getLastIndex() + " " + colonna);
      if (modalita == UPDATE & (e.getFirstIndex() == 0 | e.getLastIndex() == 0)
              & colonna != 0) {
         modello.setValueAt(Database.leggiValore("select descrizione from "
                 + Database.schema + ".caratteristica "
                 + " where codice=" + modello.getValueAt(riga, 0)),
                 riga, 1);
      }
      if (modalita == UPDATE & (e.getFirstIndex() == 1 | e.getLastIndex() == 1)
              & colonna != 1) {
         System.out.println("vediamo");
         val = Database.leggiValore("select codice from "
                 + Database.schema + ".caratteristica "
                 + " where descrizione like '%" + modello.getValueAt(riga, 1)
                 + "%'");
         System.out.println("vediamo" + val + " colonna" + colonna);
         if (val != null) {
            modello.setValueAt(val, riga, 0);
         }
         setColumnSelectionInterval(0, 0);
         if (colonna >= 0) {
            setColumnSelectionInterval(colonna, colonna);
         }

      }
   }
}
