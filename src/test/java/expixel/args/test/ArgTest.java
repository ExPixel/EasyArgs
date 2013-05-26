package expixel.args.test;

import java.util.Arrays;

import expixel.args.Argument;
import expixel.args.BadArgumentException;
import expixel.args.EasyArgs;

public class ArgTest {
	
	@Argument(sname="i", lname="integer", 
			desc="A test integer.")
	public int testInteger = 0;

	@Argument(sname="s", lname="string", 
			desc="A test string.")
	public String testString;

	@Argument(sname="ss", lname="secondstring", 
			desc="A second test string.")
	public String secondTestString;

	@Argument(sname="b", lname="boolean", 
			desc="A test boolean.")
	public boolean testSwitch;

	@Argument(sname="b2", lname="boolean2", 
			desc="A second test boolean.")
	public boolean secondTestSwitch;
	
	public static void main(String[] args) {
		new ArgTest().testArgs(args);
	}
	
	public void testArgs(String[] args) {
		String s = "";
		for(String a : args) s = s.concat(a + " ");
		System.out.println("String: " + s);
		System.out.println("Args: " + Arrays.toString(args));
		EasyArgs.processArguments(args, this);
		System.out.println("Container: " + this.toString());
		
	}

	@Override
	public String toString() {
		return "ArgTest [testInteger=" + testInteger + ", testString="
				+ testString + ", secondTestString=" + secondTestString
				+ ", testSwitch=" + testSwitch + ", secondTestSwitch="
				+ secondTestSwitch + "]";
	}
	
	
}
