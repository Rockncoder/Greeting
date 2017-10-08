package com.tekadept.greeting.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.context.annotation.*
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.multipart.MultipartResolver
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = arrayOf("com.tekadept.greeting"))
class MvcConfig : WebMvcConfigurerAdapter() {

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        val objectMapper = Jackson2ObjectMapperBuilder.json().build<ObjectMapper>()
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        return objectMapper
    }

    @DependsOn("objectMapper")
    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(MappingJackson2HttpMessageConverter(objectMapper()))
        converters.add(StringHttpMessageConverter())
    }

    @Bean
    fun multipartResolver(): MultipartResolver {
        val multipartResolver = CommonsMultipartResolver()
        multipartResolver.setMaxUploadSize(MAX_UPLOAD_SIZE)
        return multipartResolver
    }

    companion object {
        private val MAX_UPLOAD_SIZE = 125_829_120L

        @Bean
        fun properties(): PropertySourcesPlaceholderConfigurer {
            val configurer = PropertySourcesPlaceholderConfigurer()
            configurer.setLocalOverride(true)
            return configurer
        }
    }
}
