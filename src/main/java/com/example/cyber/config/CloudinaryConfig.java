package com.example.cyber.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dz32nwlm8",
            "api_key", "912512985318386",
            "api_secret", "dma7-glihGk0PyBBQxL_lLv5drU",
            "secure", true
        ));
    }
}
