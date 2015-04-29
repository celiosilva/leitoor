package br.com.delogic.leitoor.validador;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
@Size(max = 100)
@Pattern(regexp = "([a-zA-Z0-9\\._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9]{2,}$)*", message = "Deve ser algo como nome@provedor.com(.br)")
public @interface EmailValido {

    String message() default "EmailValido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
