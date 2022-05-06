import java.sql.*;

public class DataBase {

    private static final String driverName = "org.postgresql.Driver";
    private static final String connectionString = "jdbc:postgresql://localhost:5432/weatherbot";
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

    public static boolean add(String id, String name) {
        ResultSet resultSet;
        Connection connection = connect();
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users WHERE id ='" + id + "'");
            if(resultSet.next()){
                System.out.println("Пользователь уже есть");
                return false;
            }else {
                statement.executeUpdate("INSERT INTO users (id, name, status) VALUES ('" + id + "','" + name + "','user')");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String setFavouriteCity(String id, String text) {
        Connection connection = connect();
        try {
            Statement statement = connection.createStatement();
            String[] words = text.split(" ", 2);
            String city = words[1];
            String check = GetInfFrOWM.checker(city);

            if(check == null){
                statement.executeUpdate("UPDATE users SET city='" + city + "' WHERE id='" + id + "'");
                return "Город установлен";
            }else{
                return check;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Произошла ошибка";
        }
    }

    public static String getFavouriteCity(String id){
        Connection connection = connect();
        try{
            Statement statement = connection.createStatement();
            var resultSet = statement.executeQuery("SELECT * FROM users WHERE id ='" + id + "'");
            if(resultSet.next()){
                var city = resultSet.getString("city");
                if(city == "NULL") return null;
                return city;
            }else{
                return null;
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
