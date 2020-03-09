package JDBCLessons.JDBCLessonConnection;
import java.sql.*;

public class MainJDBCLessonConnection {
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "1234";
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/Lessons?useSSL=false";

    MainJDBCLessonConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.connectionDB();
    }

    public static void main(String[] args) {
        new MainJDBCLessonConnection();
    }

    public void connectionDB() {
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD)) {
           // System.out.println("We're connected");
           /* statement.executeUpdate("drop table Games");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Games (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL,PRIMARY KEY (id))");
            statement.executeUpdate("insert  into Games (name) values('Warcraft 3 Reforged')");
            statement.executeUpdate("insert  into Games set name = 'WOW'");
            ResultSet resultSet = statement.executeQuery("select * from Games");
            while (resultSet.next()) {
                System.out.println("-----------");
                System.out.println(resultSet.getInt("id"));
                System.out.println(resultSet.getString("name"));
            }
            System.out.println("___________");
            ResultSet resultSet1 = statement.executeQuery("select name from Games where id = 2");
            while (resultSet1.next()) {
                System.out.println(resultSet1.getString("name"));
            }*/
           Statement statement = connection.createStatement();
           statement.execute("drop table Users");
           statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, password CHAR(30) NOT NULL,PRIMARY KEY (id))");
           statement.executeUpdate("insert into Users (name, password) values('Anton','1996')");
           statement.executeUpdate("insert into Users set name = 'Misha', password = '6991'");
           String userId = "1' or 1 = '1";
          // String userId = "1";
  /*         ResultSet resultSet = statement.executeQuery("select * from Users where id = '" + userId + "'");
           while (resultSet.next()){
              // System.out.println(resultSet.getInt("id"));
               System.out.println("userName: " +resultSet.getString("name"));
               System.out.println("userPassword: " + resultSet.getString("password"));
           }*/

  //PreparedStatement use for state when we need defense of sql-injection
  // we say compiler, that he self must use type of arguments 
  PreparedStatement preparedStatement = connection.prepareStatement("select * from Users where id = ?");
  preparedStatement.setString(1,userId);
 // preparedStatement.setString(2,"userName");
  ResultSet resultSet = preparedStatement.executeQuery();
  while (resultSet.next()){
      System.out.println("userName: " +resultSet.getString("name"));
      System.out.println("userPassword: " + resultSet.getString("password"));
  }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
