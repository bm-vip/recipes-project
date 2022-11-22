package maxima.europe.recipes.config;

import org.bitbucket.gt_tech.spring.data.querydsl.value.operators.experimental.QuerydslPredicateArgumentResolverBeanPostProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Configuration
public class QueryDslValueOperatorsConfig {
    @Bean
    @Qualifier
    public DefaultFormattingConversionService defaultConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
//        conversionService.addConverter(this.uriToEntityConverter(conversionService));
//        conversionService.addConverter(StringToLdapNameConverter.INSTANCE);
//        this.addFormatters(conversionService);
//        this.configurerDelegate.configureConversionService(conversionService);
        return conversionService;
    }

    @Bean
    public QuerydslPredicateArgumentResolverBeanPostProcessor querydslPredicateArgumentResolverBeanPostProcessor(
            QuerydslBindingsFactory factory, DefaultFormattingConversionService conversionServiceDelegate) {
        return new QuerydslPredicateArgumentResolverBeanPostProcessor(factory, conversionServiceDelegate,
                new Class[]{Date.class, LocalDate.class, Timestamp.class, Boolean.class, boolean.class});
    }
}
