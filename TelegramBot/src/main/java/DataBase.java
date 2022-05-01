import org.glassfish.jersey.server.model.Suspendable;

import java.sql.*;

public class DataBase {

    private static final String driverName = "org.postgresql.Driver";   //com.mysql.cj.jdbc.Driver
    private static final String connectionString = "jdbc:postgresql://localhost:5432/weatherbot";  //jdbc:mysql://localhost/weatherbot?serverTimezone=Europe/Moscow&useSSL=false
    private static final String login = "root";
    private static final String password = "root";

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

    public static void add(String id, String name) {
        ResultSet resultSet;
        Connection connection = connect();
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users WHERE id ='" + id + "'");
            if(resultSet.next()){
                System.out.println("Пользователь уже есть");
            }else {
                statement.executeUpdate("INSERT INTO users (id, name, status) VALUES ('" + id + "','" + name + "','user')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String changeFavouriteCities(String id, String text) {
        Connection connection = connect();
        try {
            Statement statement = connection.createStatement();
            String[] words = text.split(" ");
            String[] cities = new String[5];
            if(words.length > 6){
                return "Неверный формат";
            }else{
                int i = 0;
                while(i < words.length - 1){
                    cities[i] = "'" + words[i + 1] + "'";
                    i++;
                }
                while(i < 5){
                    cities[i] = "NULL";
                    i++;
                }
                statement.executeUpdate("UPDATE users SET city1=" + cities[0] + ", city2=" + cities[1]+ ", city3=" + cities[2]+ ", city4=" + cities[3]+ ", city5=" + cities[4] + " WHERE id='" + id + "'");
                return "Города установлены";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Произошла ошибка";
        }
    }
}
