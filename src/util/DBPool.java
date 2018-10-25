package util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.*;


/**
 * Created by rio on 2018/10/23.
 */
public class DBPool {

    private static ComboPooledDataSource ds;

    //静态初始化块进行初始化
    static {
        try {
            ds = new ComboPooledDataSource();//创建连接池实例

            ds.setDriverClass("com.mysql.cj.jdbc.Driver"); // mysql-connector-java 8.0.x
            ds.setJdbcUrl("jdbc:mysql://localhost:3306/lottery?autoReconnect=true&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");//设置连接数据库的URL
            ds.setUser("root");//设置连接数据库的用户名
            ds.setPassword("abcd1234");//设置连接数据库的密码

            ds.setMaxPoolSize(40);//设置连接池的最大连接数

            ds.setMinPoolSize(2);//设置连接池的最小连接数

            ds.setInitialPoolSize(10);//设置连接池的初始连接数

            ds.setMaxStatements(100);//设置连接池的缓存Statement的最大数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取与指定数据库的连接
    public static ComboPooledDataSource getInstance(String DBUrl) {
        if (DBUrl != null && !"".equals(DBUrl)) {
            ds.setJdbcUrl(DBUrl);
        }
        return ds;
    }

    //从连接池返回一个连接
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    //释放资源
    public static void closeDB(Connection conn, PreparedStatement ps, ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (null != ps) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
