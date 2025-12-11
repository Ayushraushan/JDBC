package BankingManagementSystem;
import java.sql.*;
import java.util.Scanner;
public class Accounts {
    private final Connection con;
    private final Scanner sc;
    public Accounts(Connection con, Scanner sc){
        this.con=con;
        this.sc=sc;


    }
    public long openAccount(String email) {
        if (!accountExist(email)) {
            sc.nextLine();
            String query = "insert into Accounts (name, email, account_number, balance, pin) values(?,?,?,?,?)";
            System.out.println("Enter your full name:  ");
            String fullname = sc.nextLine();
            System.out.println("Enter your initial balance: ");
            double balance = sc.nextDouble();
            sc.nextLine();
            System.out.println("Enter your pin");
            String pin = sc.next();
            try {
                long accountNumber=generateAccountNumber();
                PreparedStatement stmt=con.prepareStatement(query);
                stmt.setString(1, fullname);
                stmt.setString(2,email);
                stmt.setLong(3,accountNumber);
                stmt.setDouble(4,balance);
                stmt.setString(5,pin);
                int rowAffected=stmt.executeUpdate();
                if(rowAffected>0){
                    return accountNumber;
                }
                else{
                    throw new RuntimeException("Account creation failed");
                }

            } catch (SQLException e) {
            }

        }
//        else{
//            System.out.println("Account creation failed");
//        }
        throw new RuntimeException("Account already exist");
    }
    public long getAccountNumber(String email) {
        String query = "select account_number from Accounts where email=?";
        try{
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setString(1,email);
            ResultSet result=stmt.executeQuery();
            if(result.next()){
             return result.getLong("account_number");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        throw new RuntimeException("Account does not exist");
    }

    public  long generateAccountNumber() throws SQLException{
        Statement stmt=con.createStatement();
        ResultSet result=stmt.executeQuery("select account_number from Accounts order by account_number desc Limit 1");
        if(result.next()){
            long lastAccountNumber=result.getLong("account_number");
            return lastAccountNumber+1;

        }
        else{
            return 1000100;
        }

    }

    public boolean accountExist(String email)
    {
        try{
            PreparedStatement stmt=con.prepareStatement("Select account_number from Accounts where email= ?");
            stmt.setString(1,email);
            ResultSet result=stmt.executeQuery();
            if(result.next()){
                return true;
            }else {
                return false;
            }

        }catch(SQLException e){
            e.printStackTrace();

        }
        return false;
    }
}
