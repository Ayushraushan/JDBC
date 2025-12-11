package BankingManagementSystem;

import javax.xml.transform.Result;
import java.sql.*;

import java.util.Scanner;

public class User {
    private final Connection con;
    private final Scanner sc;

    public User(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void register() throws Exception {
        sc.nextLine();
        System.out.println("Enter your full name: ");
        String name = sc.nextLine();
        System.out.println("Enter your email");
        String email = sc.nextLine();
        System.out.println("Enter your password");
        String password = sc.nextLine();
        if(userAlreadyExist(email)){
            System.out.println("User is already Registered");
            return;
        }
        String registerQuery = "insert into user (name, email, password) values(?, ?, ?)";
        try {
            PreparedStatement stmt = con.prepareStatement(registerQuery);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            int affectRows = stmt.executeUpdate();
            if (affectRows != 0) {
                System.out.println("Registration Successfully");
            } else {
                System.out.println("Registration Failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String login() {
        sc.nextLine();
        System.out.println("Enter your email: ");
        String email=sc.nextLine();
        System.out.println("Enter your password: ");
        String password=sc.nextLine();

        String loginQuery="Select * from user where email=? and password=?";
        try {
            PreparedStatement stmt = con.prepareStatement(loginQuery);
            stmt.setString(1,email);
            stmt.setString(2,password);
            ResultSet result= stmt.executeQuery();
            if(result.next()){
                 return email;
            }
            else{
                return null;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;

    }

    public boolean userAlreadyExist(String email){
        String query="select * from user where email= ?";
        try{
            PreparedStatement stmt= con.prepareStatement(query);
            stmt.setString(1,email);
            ResultSet result=stmt.executeQuery();
            if(result.next()){
                return true;

            }
            else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
     return false;
    }

}

