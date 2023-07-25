package springtest1;

import org.springframework.stereotype.Repository;
import springdao.UserDao;

public class UserDaoImpl implements UserDao {
    public UserDaoImpl(){
        System.out.println("UserDaoImpl类的构造");
    }
    @Override
    public void add(String uname){
        System.out.println("添加了："+uname);
    }
}
