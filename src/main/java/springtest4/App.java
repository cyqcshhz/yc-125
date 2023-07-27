package springtest4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        ApplicationContext ac=new AnnotationConfigApplicationContext(Config.class);
        DataSource ds=(DataSource) ac.getBean("myDataSource");

        Connection con=ds.getConnection();
        System.out.println(con);
    }
}
