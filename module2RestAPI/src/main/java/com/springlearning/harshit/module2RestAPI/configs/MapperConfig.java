package com.springlearning.harshit.module2RestAPI.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    /* While registering beans using @Bean annotation, the method name becomes the default bean name in the spring container
    unless a specific name is specified using 'name' attribute along with the annotation. [Example -> @Bean(name = "modelMapper")]

    However, even when bean is registered in spring container with name 'getModelMapper', we can use it like :

        @Autowired
        private ModelMapper modelMapper

    This is because, Spring first performs dependency injection by type, so even if the bean name is getModelMapper,
    it can be injected into any class using above mentioned way because the type 'ModelMapper' matches.
    But in case of multiple bean definitions of type 'ModelMapper', we'll anyway need to provide names to those beans. */
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
