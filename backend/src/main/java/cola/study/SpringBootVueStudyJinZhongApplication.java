package cola.study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SpringBootVueStudyJinZhongApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootVueStudyJinZhongApplication.class, args);
        log.info("SpringBoot-Vue-study-JinZhong run success");
    }

}
