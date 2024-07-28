package cn.aijavapro.middleware.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Wuchubuzai
 * @version 1.0
 * 2024年07月21日 11:21
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {
    
    @Test
    public void test() {
        System.out.println(Integer.parseInt("23"));
        System.out.println(Integer.parseInt("53534534543534534534534534"));
    }
    
}
