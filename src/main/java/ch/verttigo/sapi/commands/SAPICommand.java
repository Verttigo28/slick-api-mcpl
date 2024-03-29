package ch.verttigo.sapi.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SAPICommand {
    String getCommand();

    String getDescription();

    String getUsage();

    int getParameters() default 0;

    boolean canOverrideParameterLimit() default false;

    boolean hasConsoleSupport() default false;

    String getPermission() default "sapi.none";

    String getDefaultError() default "Invalid Registry.";
}
