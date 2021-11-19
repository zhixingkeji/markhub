package com.zhixingkeji.api.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.parameters.P;
import java.util.Properties;


/*
* 验证码配置
*
* */
@Configuration
public class KaptchaConfig {
    @Bean
    DefaultKaptcha producer() {
        Properties properties = new Properties();
        // 图片边框
        properties.put("kaptcha.border", "yes");
        // 边框颜色
        properties.put("kaptcha.border.color", "105,179,90");
        // 字体颜色
        properties.put("kaptcha.textproducer.font.color", "red");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 图片高
        properties.put("kaptcha.image.height", "40");
        // 图片宽
        properties.put("kaptcha.image.width", "120");
        // 字体大小
        properties.put("kaptcha.textproducer.font.size", "30");

        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }
}
