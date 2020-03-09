package JDBCLessons.JDBCLessonBLOB;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class MainJDBCLessonBLOB {
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "1234";
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/Lessons?useSSL=false";

    MainJDBCLessonBLOB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.connectionDB();
    }

    public static void main(String[] args) {
        new MainJDBCLessonBLOB();
    }

    public void connectionDB() {
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD)) {
             //System.out.println("We're connected");
            Statement statement = connection.createStatement();
            statement.execute("drop table DataTable");
            statement.executeUpdate("create table if not exists DataTable (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, img BLOB, PRIMARY KEY (id))");

            BufferedImage image = ImageIO.read(new File("C:\\Users\\Антон\\IdeaProjects\\JDBCLessons\\src\\JDBCLessons\\JDBCLessonBLOB\\1.jpg"));
            Blob blob = connection.createBlob();
           try(OutputStream outputStream = blob.setBinaryStream(1)){
               ImageIO.write(image, "jpg", outputStream);
           }
           PreparedStatement preparedStatement = connection.prepareStatement("insert into DataTable (name,img) values (?,?)");
           preparedStatement.setString(1,"Gattaca");
           preparedStatement.setBlob(2, blob);
           preparedStatement.execute();
           ResultSet resultSet = statement.executeQuery("select * from DataTable");
           while (resultSet.next()){
               Blob blob1 = resultSet.getBlob("img");
               BufferedImage image1 = ImageIO.read(blob.getBinaryStream());
               File file = new File("saved.png");
               ImageIO.write(image1,"png",file);
           }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
