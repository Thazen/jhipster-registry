package io.github.jhipster.registry.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
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
public class AwsEcsEurikaSupport implements ApplicationListener<ApplicationEnvironmentPreparedEvent>{

    private final Logger log = LoggerFactory.getLogger(AwsEcsEurikaSupport.class);
        
    private static final String EURIKA_SUPPORT_USE_ENV_ENV_VAR = "EURIKA_SUPPORT_USE_ENV";
    private static final String EURIKA_INSTANCE_PREFERIPADDRESS_ENV_VAR = "EURIKA_INSTANCE_PREFERIPADDRESS";
    private static final String EURIKA_CLIENT_SERVICEURL_DEFAULTZONE_ENV_VAR = "EURIKA_CLIENT_SERVICEURL_DEFAULTZONE";   
    private static final String EURIKA_INSTANCE_IP_VAR = "eureka.instance.ip-address";
    private static final String EURIKA_INSTANCE_PREFERIPADDRESS_VAR = "eureka.instance.prefer-ip-address";
    private static final String EURIKA_CLIENT_SERVICEURL_DEFAULTZONE_VAR = "eureka.client.service-url.defaultZone";    
    
    /**
     * 
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
               
        Properties properties = new Properties();        
        Boolean useEnvConfig = Boolean.valueOf(System.getenv(EURIKA_SUPPORT_USE_ENV_ENV_VAR));
        if(useEnvConfig != null && useEnvConfig){
            String eurikaUrl = System.getenv(EURIKA_CLIENT_SERVICEURL_DEFAULTZONE_ENV_VAR);
            Boolean preferIpAddress = Boolean.valueOf(System.getenv(EURIKA_INSTANCE_PREFERIPADDRESS_ENV_VAR));  

            properties.put(EURIKA_CLIENT_SERVICEURL_DEFAULTZONE_VAR, eurikaUrl);
            properties.put(EURIKA_INSTANCE_PREFERIPADDRESS_VAR, preferIpAddress);
        }else{
            String hostAddress = "127.0.0.1";
            try {
                hostAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.warn("The host name could not be determined, using `localhost` as fallback");
            }
            properties.put(EURIKA_INSTANCE_IP_VAR, hostAddress);
            log.debug("added [{}] ip to eurika support", hostAddress);
        }
        environment.getPropertySources().addFirst(new PropertiesPropertySource("eurikaSupport", properties));
    }
    
}
