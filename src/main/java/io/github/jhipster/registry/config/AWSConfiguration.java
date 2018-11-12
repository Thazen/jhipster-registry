package io.github.jhipster.registry.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 *
 * @author val
 */
@Configuration
public class AWSConfiguration {

    /**
     * 
     * @return 
     */
    @Bean
    public AWSCredentialsProvider credentialsProvider(){
        return new DefaultAWSCredentialsProviderChain();
    }
    
    /**
     * 
     * @param credentialsProvider
     * @return 
     */
    @Bean
    @DependsOn("credentialsProvider")
    public AWSSimpleSystemsManagement systemsManagement(AWSCredentialsProvider credentialsProvider){
        return AWSSimpleSystemsManagementClientBuilder.standard()
                .withCredentials(credentialsProvider).withRegion(Regions.US_EAST_1).build();
    }
}
