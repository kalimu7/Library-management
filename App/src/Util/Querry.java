package Util;

public class Querry {
    public static String insertbookQuerry(){
        return " INSERT INTO livre ( titre, auteur, ISBN, status) VALUES (?,?,?,?)";
    }
    public static String Selectallbooksquerry(){
        return "Select *from Livre where status = 'disponible'";
    }

    public static String SelectBorrowed(){
        return "Select *from Livre where status = 'emprunté'";
    }
    public static String Searchbytitle(){
        return "SELECT * FROM livre WHERE livre.titre LIKE   ?  OR livre.auteur LIKE  ? ";
    }

    public static String DeleteBook(){
        return "Delete from livre where livre.ISBN = ? ";
    }

    public static String FindBOOKBYISBN(){
        return "Select *from livre where livre.ISBN = ? ";
    }

    public static String Updatebook(){
        return "UPDATE livre SET `titre`=?,`auteur`=? WHERE ISBN = ? ";
    }

    //add a client to the database
    public static String insertclient(){
        return "insert into client (name,cin) VALUES (?,?)";
    }

    public static String Emprunterlivre(){
        return "INSERT INTO `empruntés-livres`( `id-livre`, `id-d'emprunteur`, `date-de-prise`, `date-de-retour`) VALUES (?,?,?,?)";
    }
    public static String FindClientById(){
        return "Select * from client where id = ? " ;
    }
    public static String UpdateStatus(){
        return "update livre set status = 'emprunté' where id =  ? ";
    }
    public static String BringUserByBookId(){
        return "SELECT * from client WHERE id =  (SELECT `id-d'emprunteur` from `empruntés-livres` WHERE `id-livre` =  ? )";
    }

    public static String UpdateStatusDisponible(){
        return "DELETE FROM  `empruntés-livres` WHERE `id-livre` =  ? ";
    }

    public static String Statistics(String status){
        return "SELECT COUNT(*) from livre where status = '" + status + "'";
    }

    public static String UpdateStatusLost(){
        return "update livre set status = 'perdu' where ISBN =  ? ";
    }
    public static String checkifuserborrow(int iduser){
        return "Select *from `empruntés-livres` where `id-d'emprunteur` = '" + iduser +  "'";
    }
    public static String SwitchToLostAuto(String todaydate){
        return "UPDATE livre SET STATUS = \"perdu\" where id IN (SELECT  `id-livre` from `empruntés-livres` WHERE  DATE(`date-de-retour`) < '" + todaydate + "' ) ";
    }


}
