package com.wt.master.core.config;

import com.wt.master.core.property.HttpEncodingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;


/**
 * http请求编码处理
 *
 * @author lichking2019@aliyun.com
 * @date Apr 29, 2019 at 3:36:30 PM
 */

@Configuration
//通过这个注解，可以使配置类使用@Autowired引入属性类
@EnableConfigurationProperties(HttpEncodingProperties.class)
//当CharacterEncodingFilter在累路径下的时候，引入这个配置类
@ConditionalOnClass(CharacterEncodingFilter.class)
//根据属性文件中的enable的值决定是否引入这个配置类，如果确实，那么默认引入
@ConditionalOnProperty(prefix = "spring.http.encoding", value = "enable", matchIfMissing = true)
public class HttpEncodingAutoConfiguration {
    @Autowired
    private HttpEncodingProperties httpEncodingProperties;

    @Bean
    @ConditionalOnMissingBean
    public CharacterEncodingFilter characterEncodingFilter(){
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding(this.httpEncodingProperties.getCharset().name());
        characterEncodingFilter.setForceEncoding(this.httpEncodingProperties.isForce());
        return characterEncodingFilter;
    }
}
