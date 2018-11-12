package io.github.jhipster.registry.ssm;

import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

/**
 *
 * @author val
 */
public class AWSParameterStoreEnvironmentSupport 
        implements ApplicationListener<ApplicationEnvironmentPreparedEvent>{
    
    private final Logger log = LoggerFactory.getLogger(AWSParameterStoreEnvironmentSupport.class);

    private final static String AMSCOT_AWS_PS_ENABLED_VAR = "spring.cloud.config.server.awsps.enabled";
    private final static String AMSCOT_AWS_PS_PREFIX_VAR = "spring.cloud.config.server.awsps.prefix";
    
    private final static String AMSCOT_AWS_PS_ENABLED_ENV_VAR = "SPRING_CLOUD_CONFIG_SERVER_AWSPS_ENABLED";
    private final static String AMSCOT_AWS_PS_PREFIX_ENV_VAR = "SPRING_CLOUD_CONFIG_SERVER_AWSPS_PREFIX";
    
    /**
     * 
     * @param event 
     */
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        Properties properties = new Properties();
        
        String awspsEnabled = System.getenv(AMSCOT_AWS_PS_ENABLED_ENV_VAR);
        String awspsPrefix = System.getenv(AMSCOT_AWS_PS_PREFIX_ENV_VAR);
        
        if(StringUtils.isNotBlank(awspsEnabled)){
            properties.put(AMSCOT_AWS_PS_ENABLED_VAR, awspsEnabled);
        }
        
        if(StringUtils.isNotBlank(awspsPrefix)){
            properties.put(AMSCOT_AWS_PS_PREFIX_VAR, awspsPrefix);
        }
        
        environment.getPropertySources().addFirst(new PropertiesPropertySource("amscotParameterStore", properties));
    }

}
