package springtest3;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"springtest3.System","springtest3.user"})
public class Config {
}
