package Poject;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class HotelReservationSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "@mac11Pro";

    public static void main(String[] args) throws ClassNotFoundException,SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection con = DriverManager.getConnection(url,username,password);
            while (true){
                Scanner sc = new Scanner(System.in);
                System.out.println("========== HOTEL RESERVATION SYSTEM ==========");
                System.out.println("1. Reserve a room");
                System.out.println("2. View reservation");
                System.out.println("3. Get room number");
                System.out.println("4. Update reservation");
                System.out.println("5. Delete reservation");
                System.out.println("0. Exit");
                System.out.println("Enter your choice");
                int choice = sc.nextInt();
                switch (choice){
                    case 1:
                        reserveroom(con,sc);
                        break;
                    case 2:
                        viewreservation(con);
                        break;
                    case 3:
                        getroomnumber(con,sc);
                        break;
                    case 4:
                        updatereservation(con,sc);
                        break;
                    case 5:
                        deletereservation(con,sc);
                        break;
                    case 0:
                        exit();
                        sc.close();
                        return;
                    default:
                        System.out.println("invalid choice, try again.");
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
    public static void reserveroom(Connection con, Scanner sc){
        try {
            System.out.println("Enter guest name:");
            String guest_name = sc.next();
            sc.nextLine();
            System.out.println("Enter room number:");
            int room_no = sc.nextInt();
            System.out.println("Enter contact number :");
            String contact_number = sc.next();
            String query = "INSERT INTO reservations(guest_name,room_number,contact_number)" + "values('" + guest_name + "', " + room_no + ",'" + contact_number + "')";
            try (Statement st = con.createStatement()) {
                int rowaffected = st.executeUpdate(query);
                if (rowaffected > 0) {
                    System.out.println(" insert succesfull " + rowaffected + "row(s) affected");
                } else {
                    System.out.println("insertion failed");
                }
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static void viewreservation(Connection con){
        String query = "select reservation_id,guest_name,room_number,contact_number,reservation_date from reservations;";
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int reservation_id = rs.getInt("reservation_id");
                String guest_name = rs.getString("guest_name");
                int room_number = rs.getInt("room_number");
                String contact_number = rs.getString("contact_number");
                String reservation_date = rs.getString("reservation_date").toString();
                System.out.println();
                System.out.println("RESERVATION ID is :"+reservation_id);
                System.out.println("GUEST NAME is :"+guest_name);
                System.out.println("ROOM NUMBER is :"+room_number);
                System.out.println("CONTACT NUMBER is :"+contact_number);
                System.out.println("RESERVATION DATE is :"+reservation_date);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static void getroomnumber(Connection con, Scanner sc){
       try {
           System.out.println("Enter reservation id:");
           int reservation_id = sc.nextInt();
           System.out.println("Enter guest name:");
           String guest_name = sc.next();
           String query = "select room_number from reservations " +
                   "where reservation_id = " + reservation_id +
                   " and guest_name = '" + guest_name + "'";
           try (Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query)) {

               if (rs.next()) {
                   int room_number = rs.getInt("room_number");
                   System.out.println("Room number of reservation id:"
                           + reservation_id + " and guest name:"
                           + guest_name + " is: " + room_number);
               } else {
                   System.out.println("Reservation not found for the given ID and guest name");
               }
           }
       }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void updatereservation(Connection con, Scanner sc){
        try{
        System.out.println("Enter reservation id to update:");
        int reservation_id = sc.nextInt();
        sc.nextLine();
        if(!reservationexist(con,reservation_id)){
            System.out.println("Reservation not found for given ID");
            return;
        }
        System.out.println("Enter new guest name:");
        String newguest_name = sc.nextLine();
        System.out.println("Enter new room number:");
        int newroom_number = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter new contact number:");
        String newcontact_number = sc.nextLine();
        String query = "update reservations set guest_name = '" + newguest_name+ "', "
                +"room_number = " + newroom_number+", "
                +"contact_number = '" + newcontact_number+"' "
                +"where reservation_id = " +reservation_id;
        try (Statement st = con.createStatement()){
            int rowaffected = st.executeUpdate(query);
            if (rowaffected > 0) {
                System.out.println("Reservation updated successfully");
            } else {
                System.out.println("Update request failed");
            }
        }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void deletereservation(Connection con, Scanner sc){
        try {
            System.out.println("Enter reservation id to be deleted:");
            int reservation_id = sc.nextInt();

            if (!reservationexist(con, reservation_id)) {
                System.out.println("Reservation not found for given ID");
                return;
            }
            String query = "delete from reservations where reservation_id = " + reservation_id;
            try (Statement st = con.createStatement()) {
                int rowaffected = st.executeUpdate(query);
                if (rowaffected > 0) {
                    System.out.println("Reservation deleted successfully");
                } else {
                    System.out.println("Deletion request failed");
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static boolean reservationexist(Connection con, int reservationid){
        try{
            String query ="select reservation_id from reservations where reservation_id = "+ reservationid;
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(query)){
                return rs.next();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static void exit()throws InterruptedException {
        System.out.println("Exiting system");
        int i = 6;
        while (i != 0){
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thank you for using HOTEL RESERVATION SYSTEM !!");
    }
}
