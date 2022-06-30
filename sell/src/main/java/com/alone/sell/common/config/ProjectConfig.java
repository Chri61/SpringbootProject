package com.alone.sell.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Chri61
 * @Date 2022/3/27 0027
 */
@Data
@Component
@ConfigurationProperties(prefix = "project")
public class ProjectConfig {

    private String wechatMpAuthorize;
    private String wechatOpenAuthoize;
    private String sell;
}
