@SuppressWarnings("serial")
public class TooManyQuestionsDeclared extends Exception {
	
	public TooManyQuestionsDeclared() {
		super("Maximum number of allowed questions is 10.");
	}
}