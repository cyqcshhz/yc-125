package springtest3.user;


import org.springframework.stereotype.Component;
import springtest3.System.ContainerFilter;

@Component("bmiFilter")
public class StudentBmiFilter implements ContainerFilter {

    @Override
    public boolean doFilter(Object obj){
        if (obj==null){
            return false;
        }
        if (!(obj instanceof Student)){
            return false;
        }
        Student s=(Student)obj;
        if (s.getName()==null || "".equalsIgnoreCase(s.getName())){
            return false;
        }
        if (s.getHeight() < 1 || s.getHeight() > 2.7) {

            return false;
        }
        if (s.getWeight()<30 || s.getWeight()>500){
            return false;
        }

        return true;
    }
}
