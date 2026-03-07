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
    it gets injected into ModelMapper modelMapper because the type 'ModelMapper' matches. */
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
