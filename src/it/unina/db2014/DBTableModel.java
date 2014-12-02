/*
 * DBTableModel.java
 *
 * Modified on 2013-06-03
 */
package it.unina.db2014;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;

/**
 * Modello di JTable basate su un ResultSet. <br> Si preferisce basare il
 * modello su un ResultSet, piuttosto che su una query, in modo da poter
 * condividerlo con il DBFrame.
 *
 * @author Massimo
 * @author ADeLuca
 */
public class DBTableModel extends AbstractTableModel {

   private ResultSet rs; // Resultset su cui si basa il modello

   /**
    * Creates a new instance of DBTableModel.
    */
   public DBTableModel() {
      super();
   }

   /**
    * Crea una nuova istanza di DBTableModel.
    * 
    * @param r il ResultSet su cui basare il modello
    */
   public DBTableModel(ResultSet r) {
      super();
      rs = r;
   }

   /**
    * Imposta il Resultset su cui si basa il modello.
    * 
    * @param r il ResultSet su cui basare il modello
    */
   public void setRS(ResultSet r) {
      rs = r;
      fireTableStructureChanged();

   }

   /**
    * Restituisce il nome di una colonna secondo i metadati del ResultSet.
    * 
    * @param col intero, indice di colonna
    * @return stringa, il nome della colonna
    */
   @Override
   public String getColumnName(int col) {
      col++;
      if (rs == null) {
         return "";
      }
      try {
         return rs.getMetaData().getColumnName(col);
      } catch (SQLException e) {
         System.out.println(e.getMessage());
         return "";
      }
   }

   /**
    * Naviga il ResultSet per determinare il numero di righe.
    * 
    * @return intero, numero di righe del modello
    */
   @Override
   public int getRowCount() {
      if (rs == null) {
         return 0;
      }
      try {
         int currentPosition, last;
         currentPosition = rs.getRow();
         rs.last();
         last = rs.getRow();
         rs.absolute(currentPosition);
         return last;
      } catch (/*
               * SQL
               */Exception e) {
         System.out.println(e.getMessage());
         return 0;
      }
   }

   /**
    * Determina il numero di colonne dai metadati del ResultSet
    * 
    * @return intero, numero di colonne
    */
   @Override
   public int getColumnCount() {
      if (rs == null) {
         return 0;
      }
      try {
         return rs.getMetaData().getColumnCount();
      } catch (/*
               * SQL
               */Exception e) {
         System.out.println(e.getMessage());
         return 0;
      }
   }

   /**
    * Restituisce il valore da mostrare in una cella, in base al ResultSet
    * @param row intero, indice di riga
    * @param col intero, indice di colonna
    * @return oggetto da mostrare nella cella (row,col)
    */
   @Override
   public Object getValueAt(int row, int col) {
      int currentPosition;
      Object ob;
      row++;
      col++;
      try {
         currentPosition = rs.getRow();
         rs.absolute(row);
         ob = rs.getObject(col);
         rs.absolute(currentPosition);
         return ob;
      } catch (SQLException e) {
         System.out.println(e.getMessage());
         return null;
      }
   }

   /**
    * Determina se una cella &egrave; modificabile. In questo modello si
    * &grave; scelto di non rendere direttamente modificabile nessuna cella.
    * 
    * @param row intero, indice di riga della cella
    * @param col intero, indice di colonna della cella
    * @return sempre false
    */
   @Override
   public boolean isCellEditable(int row, int col) {
      return false;
   }

   /**
    * Metodo di impostazione di un valore, ignorato a causa delle celle non
    * modificabili.
    * 
    * @param value il valore da (non) impostare
    * @param row riga
    * @param col colonna
    */
   @Override
   public void setValueAt(Object value, int row, int col) {
      //rowData[row][col] = value;
      //fireTableCellUpdated(row, col);
   }
}