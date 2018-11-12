package io.github.jhipster.registry.config;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import io.github.jhipster.registry.ssm.AWSParameterStoreEnvironmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 *
 * @author val
 */
@Configuration
@ConditionalOnProperty(name = "spring.cloud.config.server.awsps.enabled", havingValue = "true")
public class AWSParameterStoreRepositoryConfiguration {
    
    @Value("${spring.cloud.config.server.awsps.prefix:/dev}")
    private String awsParameterStoreProfile;

    /**
     * 
     * @param systemsManagement
     * @return 
     */
    @Bean
    @DependsOn("systemsManagement")
    public AWSParameterStoreEnvironmentRepository parameterStoreEnvironmentRepository(
            AWSSimpleSystemsManagement systemsManagement){
        return new AWSParameterStoreEnvironmentRepository(systemsManagement, this.awsParameterStoreProfile);
    }
}
