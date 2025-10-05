package murach.data;

import java.sql.*;
import org.postgresql.ds.PGSimpleDataSource;

public class ConnectionPool {

    private static ConnectionPool pool = null;
    private PGSimpleDataSource dataSource;

    private ConnectionPool() {
        try {
            dataSource = new PGSimpleDataSource();

            // 🧠 Lấy thông tin kết nối từ biến môi trường (Render)
            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String pass = System.getenv("DB_PASS");

            // ⚙️ Nếu chạy local thì dùng mặc định
            if (url == null || user == null || pass == null) {
                url = "jdbc:postgresql://dpg-d360ghvdiees738n9bfg-a.singapore-postgres.render.com/murachdb_qgsx";
                user = "murachdb";
                pass = "hYGAYuCZPyhAC910QZcD3A8eytGj97bk";
            }

            dataSource.setURL(url);
            dataSource.setUser(user);
            dataSource.setPassword(pass);

            System.out.println("✅ Kết nối PostgreSQL (Render) đã sẵn sàng!");
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi khởi tạo ConnectionPool: " + e);
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("❌ Lỗi lấy connection: " + e);
            return null;
        }
    }

    public void freeConnection(Connection c) {
        try {
            if (c != null) c.close();
        } catch (SQLException e) {
            System.out.println("❌ Lỗi đóng connection: " + e);
        }
    }
}
