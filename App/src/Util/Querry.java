package Util;

public class Querry {
    public static String insertbookQuerry(){
        return " INSERT INTO livre ( titre, auteur, ISBN, status, quantite) VALUES (?,?,?,?,?)";
    }
    public static String Selectallbooksquerry(){
        return "Select *from Livre";
    }
    public static String Searchbytitle(String title){
        return "SELECT * FROM livre WHERE livre.titre =  '" + title + "' OR livre.auteur = '" + title + "'";
    }

    public static String DeleteBook(int ISBN){
        return "Delete from livre where livre.ISBN =  ' " + ISBN + "'";
    }

    public static String FindBOOKBYISBN(int ISBN){
        return "Select *from livre where livre.ISBN =  ' " + ISBN + "'";
    }

    public static String Updatebook(int ISBN){
        return "UPDATE livre SET `titre`=?,`auteur`=?,`quantite`=? WHERE ISBN = '" + ISBN + "'";
    }

    //add a client to the database
    public static String insertclient(){
        return "insert into client (name,cin) VALUES (?,?)";
    }

    public static String Emprunterlivre(){
        return "INSERT INTO `empruntés-livres`( `id-livre`, `id-d'emprunteur`, `date-de-prise`, `date-de-retour`) VALUES (?,?,?,?)";
    }
    public static String FindClientById(int id){
        return "Select * from client where id = '" + id + "'";

    }
}
