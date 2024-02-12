import java.sql.*;
public class Insertionoperation {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "@mac11Pro";
        String query = " insert into employes(id,name,job_title,salary)values(3,'sanvi','ui/ux designer',500000.0);";
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection con = DriverManager.getConnection(url,username,password);
            Statement st = con.createStatement();
           int row = st.executeUpdate(query);
           if(row > 0){
               System.out.println("row inserted "+row+"rows affected");
           }
           else{
               System.out.println("insertion declined");
           }
           st.close();
           con.close();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
