import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lc on 16/9/7.
 */
public class Spider {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring-mybatis.xml");
        
    }
}
