import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class yazlab1 {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/kayitSistemi";
        String user = "postgres";
        String password = "123456";

        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Tasarim t = new Tasarim();
        t.ekran();
    }
}
