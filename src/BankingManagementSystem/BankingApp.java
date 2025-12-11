package BankingManagementSystem;
import java.net.SocketOption;
import java.sql.*;
import java.util.Scanner;

public class BankingApp {
private static String url="jdbc:mysql://localhost:3306/BankingSystem";
private static String userName="root";
private static String password="Ayush@198";

    public static void main(String[] args) throws Exception {
        //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try {
            Connection con = DriverManager.getConnection(url, userName, password);
            Scanner sc = new Scanner(System.in);
            User user=new User(con, sc);
            AccountManager accountManager=new AccountManager(con,sc);
            Accounts account=new Accounts(con,sc);

            String email;
            long accountNumber;
            while(true){
                System.out.println("*** Welcome to banking System. ***");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.println("Enter your Choice");
                int choice= sc.nextInt();
                switch(choice){
                    case 1:
                    {
                         user.register();
                         break;
                    }
                    case 2:{
                        email=user.login();
                        if(email!= null){
                            System.out.println("user Logged In.");
                            if(!account.accountExist(email)){
                                System.out.println("1. open account");
                                System.out.println("2. exit");
                                if(sc.nextInt()==1){
                                    accountNumber=account.openAccount(email);
                                    System.out.println("Account created successful");
                                    System.out.println("your account number is "+ accountNumber);

                                }
                                else {
                                    break;
                                }


                            }
                            accountNumber=account.generateAccountNumber();
                            int choice2=0;
                            while(choice2!=5){
                                System.out.println("Debit money");
                                System.out.println("credit money");
                                System.out.println("Transfer money");
                                System.out.println("check Balance");
                                System.out.println("logout");
                                choice2= sc.nextInt();
                                switch(choice2){
                                    case 1:{
                                        accountManager.debitAmount(accountNumber);
                                        break;

                                    }
                                    case 2:{
                                        accountManager.creditAmount(accountNumber);
                                        break;
                                    }
                                    case 3:{
                                        accountManager.transaction(accountNumber);
                                        break;
                                    }
                                    case 4:{
                                        accountManager.getBalance(accountNumber);
                                    }
                                    case 5:{
                                        break;

                                    }
                                    default:{
                                        System.out.println("Enter  valid choice");
                                    }

                                }
                            }
                        }else{
                            System.out.println("email or pssword incorrect");
                        }

                    }
                    case 3:
                    {
                        System.out.println("Thank you for using banking System.");
                        System.out.println("Exiting...");
                        return;
                    }
                    default:{
                        System.out.println("invalid input");
                        System.out.println("please Enter valid input.");
                        break;
                    }
                }
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
