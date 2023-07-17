import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbManager {
    private static final DataSource dataSource = createDataSource(); // Initialize the connection pool

    // Create and configure the Hikari Datasource
    private static DataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/Freelance");
        config.setUsername("user");
        config.setPassword("password");
        return new HikariDataSource(config);
    }


    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Other methods for releasing resources and handling exceptions
}