package cola.study.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: SecurityIgnoreUrl
 * @date 2023/5/13 17:26
 */
@ConfigurationProperties(prefix = "security.ignore")
@Component
public class SecurityIgnoreUrl {
    private String[] urls;

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }
}
