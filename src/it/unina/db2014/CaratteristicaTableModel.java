/*
 * CaratteristicaTableModel.java
 *
 * Modified on 2013-06-03
 */
package it.unina.db2014;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Massimo
 * @author ADeLuca
 */
public class CaratteristicaTableModel extends AbstractTableModel {

   /**
    * Creates a new instance of CaratteristicaTableModel
    */
   public CaratteristicaTableModel() {
      super();
      dati = new ArrayList<>();
      datiCancellati = new ArrayList<>();
   }
   private boolean editabile = false;
   final static private int NUOVOINVARIATO = 3;
   final static private int NUOVO = 2;
   final static private int MODIFICATO = 1;
   final static private int INVARIATO = 0;

   public void setEditabile(boolean editabile) {
      this.editabile = editabile;
   }
   private List<Object[]> dati;
   private List<Object[]> datiCancellati;
   private int allestimentoCodice;

   protected void mostraErrori(SQLException e, String query, int contesto) {
      String msg;

      if (e.getErrorCode() == 1) {
         msg = "Caratteristica duplicata";
         JOptionPane.showMessageDialog(null, msg, "Errore",
                 JOptionPane.ERROR_MESSAGE);
      } else if ((e.getErrorCode() == 17068 | e.getErrorCode() == 17011) & 
              contesto == 0) {
      } else {
         msg = "ErrorCode= " + e.getErrorCode() + "\n";
         msg += "Message= " + e.getMessage() + "\n";
         msg += "SQLState= " + e.getSQLState() + "\n";

         JOptionPane.showMessageDialog(null, msg, "Errore", 
                 JOptionPane.ERROR_MESSAGE);
      }
   }

   protected void mostraErrori(SQLException e) {
      mostraErrori(e, "", 0);
   }

   protected void mostraErrori(Exception e, String query, int contesto) {
      String msg;


      msg = "Message= " + e.getMessage() + "\n";

      JOptionPane.showMessageDialog(null, msg, "Errore", 
              JOptionPane.ERROR_MESSAGE);

   }

   protected void mostraErrori(Exception e) {
      mostraErrori(e, "", 0);
   }

   public void setAllestimentoCodice(int allestimentoCodice) {

      Connection con;
      Statement st;
      ResultSet rs;
      this.allestimentoCodice = allestimentoCodice;
      dati.clear();
      datiCancellati.clear();
      try {
         con = Database.getDefaultConnection();
         st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
                 ResultSet.CONCUR_READ_ONLY);
         //nessuna possibilit? di sql injection
         rs = st.executeQuery("select * from vcaratteristicaallestimento where "
                 + "allestimentocodice=" + allestimentoCodice 
                 + " order by caratteristica");
         rs.next();
         while (!rs.isAfterLast()) {
            Object riga[];
            riga = new Object[4];
            riga[0] = INVARIATO;
            riga[1] = rs.getInt("caratteristicacodice");
            riga[2] = rs.getString("caratteristica");
            riga[3] = rs.getString("valore");
            dati.add(riga);
            rs.next();
         }
         rs.close();


      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }
      fireTableStructureChanged();
   }

   public boolean salva(Connection c) {
      boolean ret;
      int righe, k;
      String cmd;
      Statement s;
      PreparedStatement ps;
      int q;
      ret = true;
      righe = datiCancellati.size();
      cmd = "";
      try {
         s = c.createStatement();
         for (k = 0; k < righe & ret; k++) {
            cmd = "delete from caratteristicaallestimento "
                    + " where caratteristicacodice=" + datiCancellati.get(k)[1]
                    + " and allestimentocodice=" + allestimentoCodice;
            s.execute(cmd);
         }
         righe = dati.size();
         for (k = 0; k < righe & ret; k++) {
            q = Integer.parseInt(dati.get(k)[0].toString());
            if (Integer.parseInt(dati.get(k)[0].toString()) == MODIFICATO) {
               cmd = "update  caratteristicaallestimento "
                       + " set valore=?"
                       + " where caratteristicacodice=?"
                       + " and allestimentocodice=?";
            }
            if (Integer.parseInt(dati.get(k)[0].toString()) == NUOVO) {
               cmd = "insert into  caratteristicaallestimento "
                       + " (valore,caratteristicacodice,allestimentocodice)"
                       + " values (?,?,?)";
            }
            System.out.println(cmd);
            System.out.println((dati.get(k)[1]));
            if (Integer.parseInt(dati.get(k)[0].toString()) != INVARIATO & 
                    (dati.get(k)[1]) != null) {
               ps = c.prepareStatement(cmd);
               ps.setString(1, dati.get(k)[3].toString());
               ps.setInt(2, Integer.parseInt(dati.get(k)[1].toString()));
               ps.setInt(3, allestimentoCodice);
               ps.executeUpdate();
            }
         }
      } catch (SQLException e) {
         mostraErrori(e);
         ret = false;
      } catch (Exception e) {
         mostraErrori(e);
         ret = false;
      }
      return ret;
   }

   public int getRowCount() {
      return dati.size();
   }

   public int getColumnCount() {
      return 3;
   }

   public Object getValueAt(int row, int col) {
      try {
         return dati.get(row)[col + 1];
      } catch (java.lang.ArrayIndexOutOfBoundsException e) {
         return null;
      }
   }

   void pulisci() {
      dati.clear();
      datiCancellati.clear();
      fireTableDataChanged();
   }

   public String getColumnName(int col) {
      switch (col) {
         case 0:
            return "Codice Carat.";
         case 1:
            return "Caratteristica";
         case 2:
            return "Valore";
      }
      return "boh"; //non vi arriva mai
   }

   public boolean isCellEditable(int row, int col) {
      System.out.println("col==" + col);
      return editabile & (col == 2
              || Integer.parseInt(dati.get(row)[0].toString()) == NUOVO
              || Integer.parseInt(dati.get(row)[0].toString())==NUOVOINVARIATO);
   }

   public void setValueAt(Object value, int row, int col) {
      try {
         dati.get(row)[col + 1] = value;
         if (Integer.parseInt(dati.get(row)[0].toString()) == INVARIATO) {
            System.out.println("modificato");

            dati.get(row)[0] = MODIFICATO;
         }
         if (Integer.parseInt(dati.get(row)[0].toString()) == NUOVOINVARIATO) {
            System.out.println("modificato nuovo");

            dati.get(row)[0] = NUOVO;
         }
      } catch (java.lang.ArrayIndexOutOfBoundsException e) {
      }
   }

   public void inserisciRiga() {
      Object riga[];
      if (Integer.parseInt(dati.get(dati.size() - 1)[0].toString()) != 
              NUOVOINVARIATO) {
         riga = new Object[4];
         riga[0] = NUOVOINVARIATO;
         dati.add(riga);
         fireTableStructureChanged();
      }
   }

   public void cancella(int riga) {
      try {
         datiCancellati.add(dati.get(riga));
         dati.remove(riga);
      } catch (java.lang.ArrayIndexOutOfBoundsException e) {
         System.out.println("la riga non esisteva");
      }
      fireTableStructureChanged();
   }
}
