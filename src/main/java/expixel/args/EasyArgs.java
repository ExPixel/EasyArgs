package expixel.args;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 
 * @author Adolph C.
 *
 */
public class EasyArgs {
	
	public static <T> T processArguments(String[] args, Class<T> containerClass) throws InstantiationException, IllegalAccessException {
		return processArguments(args, containerClass.newInstance());
	}
	
	public static <T> T processArguments( String[] args, T container ) {
		Field[] fields = container.getClass().getFields();
		for(Field field: fields) {
			Argument argument = field.getAnnotation(Argument.class);
			if(argument != null)
				processArgument( args, container, field, argument );
		}
		return container;
	}
	
	private static void processArgument( String[] args, Object container, Field field, Argument argument ) {
		try {
			if( field.getType().equals(String.class) ) {
				processStringArgument(args, container, field, argument);
			} else if(field.getType().equals(Boolean.TYPE)) {
					processBooleanArgument(args, container, field, argument);
			} else if(field.getType().equals(Integer.TYPE)) {
				processIntegerArgument(args, container, field, argument);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private static void processBooleanArgument(String[] args, Object container, Field field, Argument argument) throws IllegalArgumentException, IllegalAccessException {
		for(int i = 0; i < args.length; i++) {
			String arg = args[i];
			if(argumentMatch( arg, argument )) {
				field.set(container, true);
				return;
			}
		}
		field.set(container, false);
	}
	
	private static void processIntegerArgument(String[] args, Object container, Field field, Argument argument) throws IllegalArgumentException, IllegalAccessException {
		for(int i = 0; i < args.length; i++) {
			String arg = args[i];
			if(argumentMatch( arg, argument )) {
				String intString = null;
				if(i < args.length - 1) {
					intString = args[i + 1];
				}
				if(intString != null) {
					intString = intString.trim();
					try {
						int val = Integer.parseInt(intString);
						field.set(container, val);
					} catch(NumberFormatException e) {
					}
				}
				return;
			}
		}
	}
	
	private static void processStringArgument(String[] args, Object container, Field field, Argument argument) throws IllegalArgumentException, IllegalAccessException {
		for(int i = 0; i < args.length; i++) {
			String arg = args[i];
			if(argumentMatch( arg, argument )) {
				if(i < args.length - 1) {
					field.set(container, args[i + 1]);
				}
			}
		}
	}
	
	public static boolean argumentMatch(String str, Argument argument) {
		str = str.trim();
		if(argument.sname().trim().length() > 0) {
			if( str.equals("-" + argument.sname().trim()) ) return true;
		}
		if(argument.lname().trim().length() > 0) {
			if( str.equals("--" + argument.lname().trim()) ) return true;
		}
		return false;
	}
	
}
