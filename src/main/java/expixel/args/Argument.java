/**
 * 
 */
package expixel.args;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.annotation.processing.SupportedAnnotationTypes;

/**
 * @author Adolph C.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Argument {
	public String lname() default "";
	public String sname();
	public String desc() default "";
}
