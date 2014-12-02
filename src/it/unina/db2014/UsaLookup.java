/*
 * UsaLookup.java
 *
 * Modified on 2013-06-02
 */
package it.unina.db2014;

/**
 * Interfaccia delle componenti che possono usare form di ricerca.
 * 
 * @author Massimo
 * @author ADeLuca
 */
public interface UsaLookup {
   /**
    * Imposta una propriet&agrave; del componente padre.
    * 
    * @param proprieta stringa con il nome della propriet&agrave;
    * @param valore stringa con il valore associato
    */
   public void setProprietaPadre(String proprieta, String valore);
}
