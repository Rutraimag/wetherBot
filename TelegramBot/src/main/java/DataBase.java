import java.sql.*;

public class DataBase {

    private static final String driverName = "com.mysql.cj.jdbc.Driver";
    private static final String connectionString = "jdbc:mysql://localhost/weatherbot?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String login = "root";
    private static final String password = "";

    public static void add(String id, String name) {
        ResultSet resultSet = null;
        Connection connection = connect();
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users WHERE id ='" + id + "'");
            if(resultSet.next()){
                System.out.println("Пользователь уже есть");
            }else {
                statement.executeUpdate("INSERT users(id, name, status) VALUES ('" + id + "','" + name + "','user')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection connect(){
        try {
            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(connectionString, login, password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
