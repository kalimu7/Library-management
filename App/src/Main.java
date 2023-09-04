import model.Client;
import model.Empruntéslivres;
import model.Livre;
import service.BooksearchResult;
import service.CleintResult;
import service.DatabaseService;

import java.time.LocalDate;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        DatabaseService databaseService = new DatabaseService();
        try{
            while (true){

                System.out.println(" : Select one of the folowing operation");
                System.out.println("1 : Add a book");
                System.out.println("2 : Update a book");
                System.out.println("3 : Delete a book");
                System.out.println("4 : search for a book");
                System.out.println("5 : Select all the books");
                System.out.println("6 : borrow a book");
                System.out.println("7 : return a book");
                System.out.println("8 : think about the membership");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice){
                    case 1:
                        System.out.println("to Add a book enter its : title,author,ISBN,quantity");

                        Livre livre = new Livre(scanner.nextLine(),scanner.nextLine(),Integer.parseInt(scanner.nextLine()),Integer.parseInt(scanner.nextLine()));
                        databaseService.insertbook(livre);
                        break;
                    case 2:
                        System.out.println("type the name of the book you wish to update");
                        int ISBN = Integer.parseInt(scanner.nextLine());
                        BooksearchResult isfound = databaseService.FindbookbyISBN(ISBN);
                        if(isfound.isBookfound()){
                            System.out.println("enter new title,new author,new quantite + " + isfound.getBookId());
                            Livre livree = new Livre(scanner.nextLine(),scanner.nextLine(),Integer.parseInt(scanner.nextLine()));
                            databaseService.UpdateBook(livree,ISBN);
                        }
                        break;
                    case 3:
                        System.out.println("type the ISBN of the book you would delete");
                        databaseService.deletebook(Integer.parseInt(scanner.nextLine()));
                        break;
                    case 4:
                        System.out.println("type the title of the book or the name of its author");
                        databaseService.searchbook();
                        break;
                    case 5:
                        System.out.println("here is all the books");
                        databaseService.SelectAllbooks();
                        break;
                    case 6:
                        System.out.println("is the person who want to borrow that book a memeber");
                        System.out.println("1-memeber");
                        System.out.println("2-not a memeber");
                        int option = Integer.parseInt(scanner.nextLine());
                        if(option == 1){
                            System.out.println("Enter the ISBN of the book you wish to borrow");
                            int tempISBN = Integer.parseInt(scanner.nextLine());
                            BooksearchResult BookFounded = databaseService.FindbookbyISBN(tempISBN);

                            if(BookFounded.isBookfound()){
                                System.out.println("please enter the id  of the person");
                                int Idmemeber = Integer.parseInt(scanner.nextLine());
                                if(databaseService.FindMemberById(Idmemeber)){
                                    System.out.println("enter the return date year-mounth-day");
                                    Empruntéslivres empruntéslivres = new Empruntéslivres(BookFounded.getBookId(),Idmemeber,LocalDate.now(),LocalDate.parse(scanner.nextLine()));
                                    databaseService.BorrowBook(empruntéslivres);
                                }else{
                                    System.out.println("no member with this id");
                                    return;
                                }
                            }
                        } else if (option == 2) {

                        System.out.println("Enter the ISBN of the book you wish to borrow");
                        int tempISBN = Integer.parseInt(scanner.nextLine());
                        BooksearchResult BookFounded = databaseService.FindbookbyISBN(tempISBN);

                        if(BookFounded.isBookfound()){
                            System.out.println("please enter the name and cin of the person");
                            Client client = new Client(scanner.nextLine(), scanner.nextLine());
                            CleintResult clientadded = databaseService.Addclient(client);
                            if(clientadded.isAddedclient()){
                                System.out.println("enter the return date year-month-day");
                                Empruntéslivres empruntéslivres = new Empruntéslivres(BookFounded.getBookId(),clientadded.getID(),LocalDate.now(),LocalDate.parse(scanner.nextLine()));
                                databaseService.BorrowBook(empruntéslivres);
                            }
                        }
                        }
                        break;
                    case 7:
                        System.out.println("Enter the ISBN of the book you have returned");
                        break;
                    case 111:
                        System.exit(0);
                        break;
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }
}