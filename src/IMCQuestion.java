import java.util.List;


public interface IMCQuestion extends IQuestion {

	void addAnswer(IAnswer a, boolean v);
	void removeAnswer(int aNum);
	boolean isAnswerFromText(String text);
	boolean isAnswer(IAnswer a);
	List<IAnswer> getAnswers();
	List<Boolean> getValues();
	String getText(String text);
	void setText(String text);
	eDifficulty getEDif();
	void setEDif(eDifficulty eDif);
	int getNumOfAnswers();
	boolean getAnswerValue(int aidx);
}
