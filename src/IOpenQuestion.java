
public interface IOpenQuestion extends IQuestion {

	void setAnswer(IAnswer a);
	void removeAnswer();
	String getText(String text);
	String getAnswerText();
	void setText(String text);
	eDifficulty getEDif();
	void setEDif(eDifficulty eDif);
}
