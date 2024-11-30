package com.jewelbankers.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Custom annotation for database switching, restricted to class level
@Target(ElementType.TYPE)  // This ensures it can only be applied to classes
@Retention(RetentionPolicy.RUNTIME) // Retained at runtime for reflection
public @interface SwitchUserDatabase {
}
