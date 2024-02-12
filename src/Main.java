import java.sql.*;
public class Main {
    public static void main(String[] args) {
        String url ="jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "@mac11Pro";
        String query = "select * from employes;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("driver loaded succesfully");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getException());
        }
        try{
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("connection established");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String job_title = rs.getString("job_title");
                double salary = rs.getDouble("salary");
                System.out.println();
                System.out.println("==========================================");
                System.out.println("id is : "+id);
                System.out.println("name is : "+name);
                System.out.println("job title is : "+job_title);
                System.out.println("salary is : "+salary);
            }
            rs.close();
            st.close();
            con.close();
            System.out.println("all the files are closed");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
