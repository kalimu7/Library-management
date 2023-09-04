package service;

import Util.DatabaseConnection;
import Util.Querry;
import model.Empruntéslivres;
import model.Livre;
import model.Client;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class DatabaseService {
    private int Bookidtoinsert;
    private int ClientId;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    //add a book;
    public void insertbook(Livre livre) throws SQLException {

        try(Connection connection =  databaseConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(Querry.insertbookQuerry());
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setInt(3, livre.getISBN());
            preparedStatement.setString(4, livre.getStatus());
            preparedStatement.setInt(5, livre.getQuantie());

            int rows = preparedStatement.executeUpdate();
            if(rows > 0){
                System.out.println("the book added");
            }else{
                System.out.println("the book isnt added");
            }
        }
    }
    //Select all the books
    public void SelectAllbooks() throws SQLException {
        try (Connection connection =  databaseConnection.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Querry.Selectallbooksquerry());

            while (resultSet.next()){
                printbooks(
                        new Livre(resultSet.getInt("id"),
                                resultSet.getString("titre"),
                                resultSet.getString("auteur"),
                                resultSet.getInt("ISBN"),
                                resultSet.getString("status"),
                                resultSet.getInt("quantite"))
                );
            }
        }
    }
    public void printbooks(Livre livre){
        System.out.println("books id " + livre.getId() );
        System.out.println("books title " + livre.getTitre() );
        System.out.println("books author " + livre.getAuteur() );
        System.out.println("books ISBN " + livre.getISBN() );
        System.out.println("books Status " + livre.getStatus() );
        System.out.println("books quantite " + livre.getQuantie() );
        System.out.println("------------------------------------------");
    }
    public void searchbook() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        try (Connection connection =  databaseConnection.getConnection()){
            Statement statement = connection.createStatement();


                System.out.println("enter the title of your book");
                ResultSet resultSet = statement.executeQuery(Querry.Searchbytitle(scanner.nextLine()));

                        while (resultSet.next()){
                            printbooks(
                                    new Livre(resultSet.getInt("id"),
                                            resultSet.getString("titre"),
                                            resultSet.getString("auteur"),
                                            resultSet.getInt("ISBN"),
                                            resultSet.getString("status"),
                                            resultSet.getInt("quantite"))
                            );
                        }
                        if(!resultSet.next()){
                            System.out.println("there is no book with this title");
                            return;
                        }
        }
    }
    public void deletebook(int ISBN) throws SQLException{

        try (Connection connection =  databaseConnection.getConnection()){
            Statement statement = connection.createStatement();

            int rows = statement.executeUpdate(Querry.DeleteBook(ISBN));
            if(rows > 0){
                System.out.println("book is deleted");
            }else{
                System.out.println("there is no book with this ISBN");
            }
        }
    }

    //get book by ISBN.
    /*
    public boolean FindbookbyISBN(int ISBN) throws SQLException{
        Scanner scanner = new Scanner(System.in);
        boolean BookFind = false;
        try (Connection connection = databaseConnection.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Querry.FindBOOKBYISBN(ISBN));

            if(resultSet.next()){
                BookFind = true;
                System.out.println("this is are the info of the book that has ISBN = " + ISBN);
                printbooks(
                        new Livre(resultSet.getInt("id"),
                                resultSet.getString("titre"),
                                resultSet.getString("auteur"),
                                resultSet.getInt("ISBN"),
                                resultSet.getString("status"),
                                resultSet.getInt("quantite"))
                );
                this.Bookidtoinsert = resultSet.getInt("id");

            }else{
                System.out.println("there is no book with this ISBN");
            }
        }
        return BookFind;
    } */
    public BooksearchResult FindbookbyISBN(int ISBN) throws SQLException{
        Scanner scanner = new Scanner(System.in);
        boolean BookFind = false;
        try (Connection connection = databaseConnection.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Querry.FindBOOKBYISBN(ISBN));

            if(resultSet.next()){
                BookFind = true;
                System.out.println("this is are the info of the book that has ISBN = " + ISBN);
                printbooks(
                        new Livre(resultSet.getInt("id"),
                                resultSet.getString("titre"),
                                resultSet.getString("auteur"),
                                resultSet.getInt("ISBN"),
                                resultSet.getString("status"),
                                resultSet.getInt("quantite"))
                );
                this.Bookidtoinsert = resultSet.getInt("id");

            }else{
                System.out.println("there is no book with this ISBN");
            }
        }
        return new BooksearchResult(BookFind,this.Bookidtoinsert);
    }



    public void UpdateBook(Livre livre,int ISBN) throws SQLException{
        System.out.println(livre);

        try(Connection connection = databaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(Querry.Updatebook(ISBN));
            preparedStatement.setString(1,livre.getTitre());
            preparedStatement.setString(2,livre.getAuteur());
            preparedStatement.setInt(3,livre.getQuantie());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0){
                System.out.println("book updated successfully");
            }else{
                System.out.println("failed to update");
            }
        }

    }
    //borrow a book using
    public CleintResult Addclient(Client client) throws SQLException{
        Scanner scanner = new Scanner(System.in);
        boolean ClientAdded = false;

        try(Connection connection = databaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(Querry.insertclient());
            preparedStatement.setString(1,client.getName());
            preparedStatement.setString(2,client.getCin());
            int rows = preparedStatement.executeUpdate();
            if(rows > 0){
                System.out.println("client added");
                ClientAdded = true;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT MAX(id) from client");
                resultSet.next();
                this.ClientId = resultSet.getInt(1);
            }else{
                System.out.println("book isnt added");
            }
        }
        return new CleintResult(ClientAdded,this.ClientId);
    }
    public void BorrowBook(Empruntéslivres empruntéslivres) throws SQLException{
        try(Connection connection = databaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(Querry.Emprunterlivre());
            preparedStatement.setInt(1,empruntéslivres.getIdlivre());
            preparedStatement.setInt(2,empruntéslivres.getIddemprunteur());
            preparedStatement.setString(3, String.valueOf(empruntéslivres.getDatedepris()));
            preparedStatement.setString(4,String.valueOf(empruntéslivres.getDatederetour()));
            int rows = preparedStatement.executeUpdate();
            if(rows > 0){
                System.out.println("this book is borrowed successfully");
            }else{
                System.out.println("book cant be booked");
            }
        }
    }
    public boolean FindMemberById(int id) throws SQLException{
        try (Connection connection = databaseConnection.getConnection()){
            boolean memberexist = false;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Querry.FindClientById(id));
            if(resultSet.next()){
                memberexist = true;
                System.out.println("check his info first");
                System.out.println("client id = " + resultSet.getInt("id"));
                System.out.println("client name = " + resultSet.getString("name"));
                System.out.println("client cin = " + resultSet.getString("cin"));
            }
            return memberexist;
        }
    }

}
