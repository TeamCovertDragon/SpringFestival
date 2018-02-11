package team.covertdragon.springfestival.module;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value = RUNTIME)
@Target(value = TYPE)
public @interface SpringFestivalModule {
    String name();
    String description() default "";
    String[] dependencies() default {};
    String[] antidependencies() default {};

    @Retention(value = RUNTIME)
    @Target(value = FIELD)
    @interface SidedProxy {
        String clientSide() default "";
        String serverSide() default "";
    }

    @Retention(value = RUNTIME)
    @Target(value = FIELD)
    @interface PacketRegistry {
        String value() default "";
    }

    @Retention(value = RUNTIME)
    @Target(value = FIELD)
    @interface Instance {
        String value() default "";
    }

    @Retention(value = RUNTIME)
    @Target(value = FIELD)
    @interface Configuration {
        String value() default "";
    }


}
