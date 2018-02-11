package team.covertdragon.springfestival.module;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SpringFestivalModule {
    String name();
}
