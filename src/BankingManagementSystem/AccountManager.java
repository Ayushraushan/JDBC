package BankingManagementSystem;
import java.sql.*;
import java.util.Scanner;

public class AccountManager {
  private final Connection con;
  private final Scanner sc;
  public AccountManager(Connection con, Scanner sc){
      this.con= con;
      this.sc=  sc;
  }
  public void creditAmount(long accountNumber)throws SQLException{
      sc.nextLine();
      System.out.println("enter your amount for credit: ");
      double amount=sc.nextDouble();
      sc.nextLine();
      System.out.println("Enter your  pin: ");
      String pin=sc.nextLine();
      try{
          con.setAutoCommit(false);
          if(accountNumber!=0){
              String query="Select * from Accounts where account_number=? and pin=?";
              PreparedStatement stmt=con.prepareStatement(query);
              stmt.setLong(1,accountNumber);
              stmt.setString(2,pin);
              ResultSet result=stmt.executeQuery();
              if(result.next()){
                  double balance= result.getDouble("balance");
                  String query1="update Accounts set balance= balance + ? where account_number=?";
                  PreparedStatement stmt1=con.prepareStatement(query1);
                  stmt1.setDouble(1,amount);
                  stmt1.setLong(2,accountNumber);
                  int rowAffected= stmt1.executeUpdate();
                  if(rowAffected>0){
                      System.out.println("Balance credit successful");
                      con.commit();
                      con.setAutoCommit(true);
                  }else{
                      System.out.println("Balance not credited");
                      con.rollback();
                      con.setAutoCommit(true);
                  }



              }else{
                  System.out.println("Invalid Pin");

              }

          }else{
              System.out.println("Account not Exist");
          }

      }catch(SQLException e){
          e.printStackTrace();
      }
      con.setAutoCommit(true);
  }
  public void debitAmount(long accountNumber) throws SQLException {
      sc.nextLine();
      System.out.println("Enter amount");
      double amount = sc.nextDouble();
      sc.nextLine();
      System.out.println("Enter pin");
      String pin = sc.nextLine();
      try {
          con.setAutoCommit(false);
          if (accountNumber != 0) {
              String query= "select * from Accounts where account_number=? and pin=?";
              PreparedStatement stmt=con.prepareStatement(query);
              stmt.setLong(1,accountNumber);
              stmt.setString(2,pin);
              ResultSet result=stmt.executeQuery();
              if(result.next()){
                  double balance= result.getDouble("balance");
                  if(amount<= balance){
                      String query1= "update Accounts set balance= balance- ? where account_number=?";
                      PreparedStatement stmt1= con.prepareStatement(query1);
                      stmt1.setDouble(1,amount);
                      stmt1.setLong(2,accountNumber);
                      int rowAffected= stmt1.executeUpdate();
                      if(rowAffected!=0){
                          System.out.println("Rs." + amount +"debited successfully");
                          con.commit();
                          con.setAutoCommit(true);
                      }else{
                          System.out.println("transaction failed");
                          con.rollback();
                          con.setAutoCommit(true);
                      }

                  }else{
                      System.out.println("insufficient balance");
                  }
              }else{
                  System.out.println("Invalid pin");
              }


          }
      }catch(SQLException e){
          e.printStackTrace();
      }
      con.setAutoCommit(true);


  }
  public void transaction(long accountNumber) throws SQLException{
      sc.nextLine();
      System.out.println("Enter Receiver account number: ");
      long receipientAccountNUmber=sc.nextLong();
      System.out.println("Enter amount: ");
      double amount=sc.nextDouble();
      sc.nextLine();
      System.out.println("Enter Pin: ");
      String pin=sc.nextLine();
      try{
          if(accountNumber!=0 && receipientAccountNUmber!=0) {
              PreparedStatement stmt = con.prepareStatement("select * from Acccounts where account_number=? and pin=>");
              stmt.setLong(1, accountNumber);
              stmt.setString(2, pin);
              ResultSet result = stmt.executeQuery();
              if (result.next()) {
                  double balance=result.getLong("balance");
                  String creditQuery="update Accounts set balance= balance+ ? where account_number=?";
                  String debitQuery="update Accounts set balance=balance- ? where account_number=?";

                  PreparedStatement stmt1= con.prepareStatement(creditQuery);
                  stmt1.setDouble(1,amount);
                  stmt1.setLong(2,accountNumber);
                  int rowAffected1=stmt1.executeUpdate();
                  PreparedStatement stmt2= con.prepareStatement(debitQuery);
                  stmt2.setDouble(1,amount);
                  stmt2.setLong(2,receipientAccountNUmber);
                  int rowAffected2= stmt2.executeUpdate();
                  if(rowAffected1!=0 && rowAffected2!=0){
                      System.out.println("Transaction successful");
                      System.out.println("Money Transfer Successful");
                      con.commit();
                      con.setAutoCommit(true);
                      return;

                  }else{
                      System.out.println("Transaction unsuccessful");
                      con.rollback();
                      con.setAutoCommit(true);


                  }


              } else {
                  System.out.println("invalid pin");
              }
          }else{
              System.out.println("Invalid Account Number");
          }

      }
      catch(SQLException e){
          e.printStackTrace();
      }
      con.setAutoCommit(true);

  }
  public void getBalance(long accountNumber){
      sc.nextLine();
      System.out.println("Enter your pin");
      String pin=sc.nextLine();
      try{
      PreparedStatement stmt=con.prepareStatement("select * from Accounts where account_number=? and pin=?");
      stmt.setLong(1,accountNumber);
      stmt.setString(2,pin);
      ResultSet result= stmt.executeQuery();
      if(result.next()){
          double balance= result.getDouble("balance");
          System.out.println("Balance is" + balance);
          return;
      }
      else{
          System.out.println("invalid Pin");
      }
      }
      catch(SQLException e){
          e.printStackTrace();
      }
  }
}
