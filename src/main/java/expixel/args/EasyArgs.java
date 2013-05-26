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
			} else if(field.getType().equals(Float.TYPE)) {
				processFloatArgument(args, container, field, argument);
			} else if(field.getType().equals(Double.TYPE)) {
				processDoubleArgument(args, container, field, argument);
			} else if(field.getType().equals(Long.TYPE)) {
				processLongArgument(args, container, field, argument);
			} else if(field.getType().equals(Short.TYPE)) {
				processShortArgument(args, container, field, argument);
			}
		} catch (IllegalArgumentException e) {
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
		String stringValue = getValue(args, container, field, argument);
		try {
			field.set(container, Integer.parseInt(stringValue));
		} catch(NumberFormatException e) {
			
		}
	}
	
	private static void processFloatArgument(String[] args, Object container, Field field, Argument argument) throws IllegalArgumentException, IllegalAccessException {
		String stringValue = getValue(args, container, field, argument);
		try {
			field.set(container, Float.parseFloat(stringValue));
		} catch(NumberFormatException e) {}
	}
	
	private static void processDoubleArgument(String[] args, Object container, Field field, Argument argument) throws IllegalArgumentException, IllegalAccessException {
		String stringValue = getValue(args, container, field, argument);
		try {
			field.set(container, Double.parseDouble(stringValue));
		} catch(NumberFormatException e) {}
	}
	
	private static void processLongArgument(String[] args, Object container, Field field, Argument argument) throws IllegalArgumentException, IllegalAccessException {
		String stringValue = getValue(args, container, field, argument);
		try {
			field.set(container, Long.parseLong(stringValue));
		} catch(NumberFormatException e) {}
	}
	
	private static void processShortArgument(String[] args, Object container, Field field, Argument argument) throws IllegalArgumentException, IllegalAccessException {
		String stringValue = getValue(args, container, field, argument);
		try {
			field.set(container, Short.parseShort(stringValue));
		} catch(NumberFormatException e) {}
	}
	
	private static void processStringArgument(String[] args, Object container, Field field, Argument argument) throws IllegalArgumentException, IllegalAccessException {
		String stringValue = getValue(args, container, field, argument);
		field.set(container, stringValue);
	}
	
	private static String getValue(String[] args, Object container, Field field, Argument argument) {
		for(int i = 0; i < args.length; i++) {
			String arg = args[i];
			if(argumentMatch( arg, argument )) {
				if(i < args.length - 1) {
					return args[i + 1];
				}
			}
		}
		return null;
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
