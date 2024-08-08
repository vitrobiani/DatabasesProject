
public abstract class AddQ extends RepUserInterface {

	protected String qText;
	protected String difText;
	
	public AddQ(IReposable rep) {
		super(rep);
	}
	
	
	public abstract void addQuestion();
	
	public void initQuestion(String text, String difText) {

		this.qText = text;
		this.difText = difText;
		
	}

}
