package io.github.jhipster.registry.ssm;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathResult;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.core.Ordered;

/**
 *
 * @author val
 */
@ConfigurationProperties("amscot")
public class AWSParameterStoreEnvironmentRepository implements EnvironmentRepository, Ordered{
    
    private final Logger log = LoggerFactory.getLogger(AWSParameterStoreEnvironmentRepository.class);
    
    private String parameterStorePrefix;
    private AWSSimpleSystemsManagement systemsManagement;
    
    public AWSParameterStoreEnvironmentRepository(AWSSimpleSystemsManagement systemsManagement, String parameterStorePrefix){
        this.systemsManagement = systemsManagement;
        this.parameterStorePrefix = parameterStorePrefix;
    }

    /**
     * 
     * @param application
     * @param profile
     * @param label
     * @return 
     */
    @Override
    public Environment findOne(String application, String profile, String label) {
        Environment environment = new Environment(application, profile);
        log.info("parameter store params lookup by prefix [{}]", this.parameterStorePrefix);
        Properties properties = new Properties();
        GetParametersByPathRequest paramsRequest = new GetParametersByPathRequest();
        paramsRequest.withPath(this.parameterStorePrefix);
        paramsRequest.withRecursive(Boolean.TRUE);
        GetParametersByPathResult result = this.systemsManagement.getParametersByPath(paramsRequest);
        log.info("got [{}] parameters from the parameter store", result.getParameters().size());
        for(Parameter parameter:result.getParameters()){
            properties.put(parameter.getName().substring(
                    parameter.getName()
                            .indexOf(this.parameterStorePrefix) + (this.parameterStorePrefix.length() + 1)),
                                    parameter.getValue());
        }
        environment.add(new PropertySource("amscotProperties", properties));
        return environment;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
