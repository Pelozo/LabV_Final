package net.pelozo.FinalTPLab5DB2.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
public class ModelMapperConfig {
        @Bean
        public ModelMapper modelMapper() {
            final ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            return modelMapper;
        }
}
