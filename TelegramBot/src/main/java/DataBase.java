import org.glassfish.jersey.server.model.Suspendable;

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

    public static String changeFavouriteCity(String id, String text) {
        Connection connection = connect();
        try {
            Statement statement = connection.createStatement();
            String[] words = text.split(" ", 2);
            String city = words[1];
            String foundCity = GetInfFrOWM.checkCity(city);

            if(city.equals(foundCity)){
                statement.executeUpdate("UPDATE users SET city='" + city + "' WHERE id='" + id + "'");
                return "Город установлен";
            }else if(foundCity == null){
                return "Город не найден, проверьте введенные данные";
            }else{
                return String.format("Город с таким названием не найден, возможно вы имели ввиду '%s'", foundCity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Произошла ошибка";
        }
    }
}
