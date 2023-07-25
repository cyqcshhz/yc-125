import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.biz.UserBiz;
import springtest1.Config;

public class App1 {
    public static void main(String[] args) {
        ApplicationContext container = new AnnotationConfigApplicationContext(Config.class);

        UserBiz ub = (UserBiz) container.getBean("userBizImpl");
        ub.add("王五");
    }
}
