
public class AddMCQ extends AddQ {

	
	public AddMCQ(IReposable rep) {
		super(rep);
	}
	
	public void addQuestion() {
		rep.addMCQuestion(qText, eDifficulty.valueOf(difText));
	}


}
