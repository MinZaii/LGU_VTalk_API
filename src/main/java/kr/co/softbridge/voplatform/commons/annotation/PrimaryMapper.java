package kr.co.softbridge.voplatform.commons.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface PrimaryMapper {
    String value() default "";
}
