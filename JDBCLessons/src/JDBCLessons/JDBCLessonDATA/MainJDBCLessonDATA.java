package JDBCLessons.JDBCLessonDATA;

import java.sql.*;

public class MainJDBCLessonDATA {
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "1234";
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/Lessons?useSSL=false";
    MainJDBCLessonDATA(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.connectionDB();
    }
    public static void main(String[] args) {
        new MainJDBCLessonDATA();
    }
    public void connectionDB() {
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD)) {
            System.out.println("We're connected");
             Statement statement = connection.createStatement();
            statement.execute("drop table TableData");
            statement.executeUpdate("create table if not exists TableData (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, myDate DATE, PRIMARY KEY (id))");
    /*        PreparedStatement preparedStatement = connection.prepareStatement("insert into TableData(name,myDate) values ('someName',?)");
             preparedStatement.setDate(1,new Date(1583548697019L));
             preparedStatement.execute();
            System.out.println(preparedStatement);*/
            statement.executeUpdate("insert into TableData(name,myDate) values ('someName',{d'2020-03-07'})");
            ResultSet resultSet = statement.executeQuery("select * from TableData");
            while (resultSet.next())
                System.out.println(resultSet.getDate("myDate"));
            CallableStatement callableStatement = connection.prepareCall("{call GamesCount(?)}");
            callableStatement.registerOutParameter(1,Types.INTEGER);
            callableStatement.execute();
            System.out.println(callableStatement.getInt(1));
            System.out.println("--------------");

            CallableStatement callableStatement1 = connection.prepareCall("{call getGames(?)}");
            callableStatement1.setInt(1,1);
            if(callableStatement1.execute()){
                ResultSet resultSet1 = callableStatement1.getResultSet();
                while (resultSet1.next()){
                    System.out.println(resultSet1.getInt("id"));
                    System.out.println(resultSet1.getString("name"));
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
