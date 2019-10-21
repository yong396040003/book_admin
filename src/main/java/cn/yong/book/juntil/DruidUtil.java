package cn.yong.book.juntil;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * dbcp连接池
 * Description:
 * Date: 18:07 2019/10/15
 *
 * @author yong
 * @see
 */
public class DruidUtil {
    private static final Logger logger = LoggerFactory.getLogger(DruidUtil.class);

    private static DataSource dataSource;
    //静态代码块 防止多次加载驱动减小开销
    static {
        Properties p = new Properties();
        try {
            ClassLoader c = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = c.getResourceAsStream("db.properties");
            if (inputStream == null) {
                throw new FileNotFoundException("db.properties文件未找到");
            }
            p.load(inputStream);
            //dbcp
            //dataSource = BasicDataSourceFactory.createDataSource(p);
            //druid
            dataSource = DruidDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            throw new RuntimeException("db.properties读取失败", e);
        }
    }

    /**
     * 获取数据库连接对象
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接失败", e);
        }
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("数据库关闭失败", e);
            }
        }
    }
}
