package annotation;
import java.lang.annotation.*;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Test {

	Class<? extends Throwable> expectedException() default Null.class;

	class Null extends Throwable{

	}

}