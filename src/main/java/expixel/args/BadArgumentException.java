package expixel.args;

/**
 * 
 * @author Adolph C.
 * 
 * Not yet in use.
 *
 */
public class BadArgumentException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7436915475422069735L;
	
	String variable;
	String type;
	String value;
	
	public BadArgumentException(String variable, String type, String value) {
		super();
		this.variable = variable;
		this.type = type;
		this.value = value;
	}

	public BadArgumentException() {}
	
	@Override
	public String getMessage() {
		return String.format("Expected %s for variable %s but recieved %s.", variable, type, value);
	}

}
