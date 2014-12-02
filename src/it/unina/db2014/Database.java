/*
 * Database.java
 * 
 * Modified on 2013-06-01
 */
package it.unina.db2014;

import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;
import java.util.*;

/**
 * Classe principale di connessione al database.
 *
 * @author Massimo
 * @author ADeLuca
 * @version %I%, 2014
 */
public class Database {

   static public String host = "localhost";
   static public String servizio = "xe";
   static public int porta = 1521;
   static public String user = "";
   static public String password = "";
   static public String schema = "auto";
   static private OracleDataSource ods;
   static private Connection defaultConnection;

   /**
    * Restituisce la connessione di default al DB.
    * 
    * @return Connection al database
    * @throws SQLException in caso di errore di connessione
    */
   static public Connection getDefaultConnection() throws SQLException {
      if (defaultConnection == null || defaultConnection.isClosed()) {
         defaultConnection = nuovaConnessione();
         //System.out.println("nuova connessione");   //debug
      } else {
         //System.out.println("ricicla connessione"); //debug
      }

      return defaultConnection;
   }

   /**
    * Imposta la connessione di default al DB.
    * 
    * @param c la Connection da impostare come default
    */
   static public void setDefaultConnection(Connection c) {
      defaultConnection = c;
   }

   /**
    * Restituisce una nuova connessione al DB.
    * 
    * @return la nuova Connection
    * @throws SQLException in caso di errore di connessione
    */
   static public Connection nuovaConnessione() throws SQLException {
      ods = new OracleDataSource();
      ods.setDriverType("thin");
      ods.setServerName(host);
      ods.setPortNumber(porta);
      ods.setUser(user);
      ods.setPassword(password);
      ods.setDatabaseName(servizio);
      return ods.getConnection();
   }

   /**
    * Effettua una query e restituisce il primo valore.
    * 
    * @param query String contenente l'interrogazione
    * @return oggetto contenente la prima colonna della prima riga del risultato
    */
   static public Object leggiValore(String query) {
      Object ret;
      Connection con;
      Statement st;
      ResultSet rs;
      ret = null;
      try {
         con = getDefaultConnection();
         st = con.createStatement();
         rs = st.executeQuery(query);
         rs.next();
         ret = rs.getObject(1);
      } catch (SQLException e) {  //nessuna azione
      }
      return ret;
   }
   
   /**
    * Effettua una query e restituisce la prima colonna.
    * 
    * @param query String contenente l'interrogazione
    * @return oggetto contenente la prima colonna del risultato
    */
   
   static public ArrayList<String> leggiValori(String query) {
      ArrayList<String> ret;
      Connection con;
      Statement st;
      ResultSet rs;
      ret = new ArrayList<>();
      try {
         con = getDefaultConnection();
         st = con.createStatement();
         rs = st.executeQuery(query);
         while(!rs.isAfterLast()){
             rs.next();
             ret.add(rs.getObject(1).toString());
         }  
      } catch (SQLException e) {  //nessuna azione
      }
      return ret;
   }

   /**
    * Effettua una query e restituisce il primo valore.
    * 
    * @param query String contenente l'interrogazione con segnaposto
    * @param codice int per rimpiazzare il segnaposto
    * @return oggetto contenente la prima colonna della prima riga del risultato
    */
   static public Object leggiValore(String query, int codice) {
      Object ret;
      Connection con;
      PreparedStatement st;
      ResultSet rs;
      ret = null;
      //System.out.println(query + codice); //debug
      try {
         con = getDefaultConnection();
         st = con.prepareStatement(query);
         st.setInt(1, codice);
         rs = st.executeQuery();
         rs.next();
         ret = rs.getObject(1);
      } catch (SQLException e) {
      }
      return ret;
   }
}
