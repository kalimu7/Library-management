package service;

import Util.DatabaseConnection;
import Util.Querry;
import model.Empruntéslivres;
import model.Livre;
import model.Client;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.util.Date;
import java.util.Scanner;



public class DatabaseService {
    private int Bookidtoinsert;
    private String BookDisponibilite;
    private int ClientId;
    private  String statistics;

    DatabaseConnection databaseConnection = new DatabaseConnection();
    //add a book;
    public void insertbook(Livre livre)  {

        try {
            Connection connection =  databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Querry.insertbookQuerry());
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setInt(3, livre.getISBN());
            preparedStatement.setString(4, livre.getStatus());


            int rows = preparedStatement.executeUpdate();
            if(rows > 0){
                System.out.println("the book added");
            }else{
                System.out.println("the book isnt added");
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    //Select dsiponible the books
    public void DisponibleBook()  {
        try{
            Connection connection =  databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Querry.Selectallbooksquerry());

            while (resultSet.next()){
                printbooks(
                        new Livre(resultSet.getInt("id"),
                                resultSet.getString("titre"),
                                resultSet.getString("auteur"),
                                resultSet.getInt("ISBN"),
                                resultSet.getString("status")

                )
                        );
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    //all borrowed books
    public void BorrowedBooks() {
        try {
            Connection connection =  databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Querry.SelectBorrowed());
            while (resultSet.next()){
                printbooks(
                        new Livre(resultSet.getInt("id"),
                                resultSet.getString("titre"),
                                resultSet.getString("auteur"),
                                resultSet.getInt("ISBN"),
                                resultSet.getString("status")
                        )
                );
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    public void printbooks(Livre livre){

        System.out.println("books title " + livre.getTitre() );
        System.out.println("books author " + livre.getAuteur() );
        System.out.println("books ISBN " + livre.getISBN() );
        System.out.println("books Status " + livre.getStatus() );

        System.out.println("------------------------------------------");
    }
    public void searchbook(String TitleAuthor)  {

        try {
            Connection connection =  databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Querry.Searchbytitle());
            preparedStatement.setString(1,"%" + TitleAuthor + "%");
            preparedStatement.setString(2,"%" + TitleAuthor +"%");


                System.out.println("enter the title of your book");
                ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()){
                            printbooks(
                                    new Livre(resultSet.getInt("id"),
                                            resultSet.getString("titre"),
                                            resultSet.getString("auteur"),
                                            resultSet.getInt("ISBN"),
                                            resultSet.getString("status")
                                           )
                            );
                        }
                        if(!resultSet.next()){
                            System.out.println("there is no book with this title");
                            return;
                        }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    public void deletebook(int ISBN) {

        try {
            Connection connection =  databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Querry.DeleteBook());
            preparedStatement.setInt(1,ISBN);

            int rows = preparedStatement.executeUpdate();
            if(rows > 0){
                System.out.println("book is deleted");
            }else{
                System.out.println("there is no book with this ISBN");
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }


    public BooksearchResult FindbookbyISBN(int ISBN) {
        Scanner scanner = new Scanner(System.in);
        boolean BookFind = false;
        try (Connection connection = databaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(Querry.FindBOOKBYISBN());
            preparedStatement.setInt(1,ISBN);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                BookFind = true;
                System.out.println("this is are the info of the book that has ISBN = " + ISBN);
                printbooks(
                        new Livre(resultSet.getInt("id"),
                                resultSet.getString("titre"),
                                resultSet.getString("auteur"),
                                resultSet.getInt("ISBN"),
                                resultSet.getString("status")
                              )
                );
                this.Bookidtoinsert = resultSet.getInt("id");
                this.BookDisponibilite = resultSet.getString("status");
            }else{
                System.out.println("there is no book with this ISBN");
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        return new BooksearchResult(BookFind,this.Bookidtoinsert,this.BookDisponibilite);

    }



    public void UpdateBook(Livre livre,int ISBN) {
        try{
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Querry.Updatebook());
            preparedStatement.setString(1,livre.getTitre());
            preparedStatement.setString(2,livre.getAuteur());
            preparedStatement.setInt(3,ISBN);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0){
                System.out.println("book updated successfully");
            }else{
                System.out.println("failed to update");
            }
        }catch(SQLException e){
            System.out.println(e);
        }

    }
    //borrow a book using
    public CleintResult Addclient(Client client) {
        Scanner scanner = new Scanner(System.in);
        boolean ClientAdded = false;

        try{
            Connection connection = databaseConnection.getConnection();
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
        }catch(SQLException e){
            System.out.println(e);
        }
        return new CleintResult(ClientAdded,this.ClientId);
    }
    public void emprunter(Empruntéslivres empruntéslivres,int Bookid) throws SQLException{
        try{
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Querry.Emprunterlivre());
            preparedStatement.setInt(1,empruntéslivres.getIdlivre());
            preparedStatement.setInt(2,empruntéslivres.getIddemprunteur());
            preparedStatement.setString(3, String.valueOf(empruntéslivres.getDatedepris()));
            preparedStatement.setString(4,String.valueOf(empruntéslivres.getDatederetour()));
            int rows = preparedStatement.executeUpdate();

            if(rows > 0){
                PreparedStatement preparedStatement1 = connection.prepareStatement(Querry.UpdateStatus());
                preparedStatement1.setInt(1,Bookid);
                preparedStatement1.executeUpdate();
                System.out.println("this book is borrowed successfully");
            }else{
                System.out.println("book cant be booked");
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    public boolean FindMemberById(int id) throws SQLException{
        boolean memberexist = false;
        try {
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Querry.FindClientById());
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                memberexist = true;
                System.out.println("check his info first");
                System.out.println("client id = " + resultSet.getInt("id"));
                System.out.println("client name = " + resultSet.getString("name"));
                System.out.println("client cin = " + resultSet.getString("cin"));
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        return memberexist;
    }
    public void retourner(int Bookid) {
            Scanner scanner = new Scanner(System.in);
            try(Connection connection = databaseConnection.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(Querry.BringUserByBookId());
                preparedStatement.setInt(1,Bookid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    System.out.println("here is the info of the person who is borrowing this book");

                    System.out.println("his name " + resultSet.getString("name"));
                    System.out.println("his cin " + resultSet.getString("cin"));
                    System.out.println("if the info info matched type 1");
                    System.out.println("if the info doesnt match type 2");
                    int choice = Integer.parseInt(scanner.nextLine());
                    if(choice == 1){
                        PreparedStatement preparedStatement1 = connection.prepareStatement(Querry.UpdateStatusDisponible());
                        preparedStatement1.setInt(1,Bookid);
                        preparedStatement1.executeUpdate();
                        System.out.println("the book is disponible now");
                    } else if (choice == 2) {
                        return;
                    }

                }else{
                    System.out.println("something is wrong");
                }
            }catch(SQLException e){
                System.out.println(e);
            }
    }
    public void visualiserLesStatistique() throws IOException{
        try{
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();
            ResultSet Disponible = statement.executeQuery(Querry.Statistics("disponible"));
            ResultSet Emprunte = statement1.executeQuery(Querry.Statistics("emprunté"));
            ResultSet Perdu = statement2.executeQuery(Querry.Statistics("perdu"));
            if(Disponible.next() & Emprunte.next() & Perdu.next()){
                System.out.println("-----------------------------------------------------------");
                this.statistics = "Livre Disponible :" + Disponible.getInt("COUNT(*)")  + " livre emprunté :" + Emprunte.getInt("COUNT(*)") + " Livre Perdu :" + Perdu.getInt("COUNT(*)") ;
                System.out.println(this.statistics);
                System.out.println("---------------------------------------------------------");
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
            Date date = new Date();
            String NowDate = formatter.format(date);
            String filename = "statistique" + NowDate +".txt";
            String path = "C:\\Users\\adm\\Downloads\\";
            File file = new File(path,  filename);
            if(file.createNewFile()){
                Files.writeString(Path.of(path + filename), this.statistics );
            }else {
                System.out.println("file not created");
            }

        }catch(SQLException e){
            System.out.println(e);
        }
    }
    public void LostBook(int ISBN) {
        try (Connection connection = databaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(Querry.UpdateStatusLost());
            preparedStatement.setInt(1,ISBN);
            int rows = preparedStatement.executeUpdate();
            if(rows > 0){
                System.out.println("ok the book status is saved as Lost now");
            }else {
                System.out.println("something went wrong");
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    public boolean checkifuserborrowedABook(int BookId ) {
        boolean temp = true;
        try (Connection connection = databaseConnection.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Querry.checkifuserborrow(BookId));
            if(resultSet.next()){
                temp = false;
            }

        }catch(SQLException e){
            System.out.println(e);
        }
        return temp;
    }
    public void  SwitchBookToLostAutomatically() {
        try {
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(Querry.SwitchToLostAuto(String.valueOf(LocalDate.now())));
        }catch(SQLException e){
            System.out.println(e);
        }
    }




}
