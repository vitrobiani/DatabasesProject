import java.io.Serializable;


public interface IQuestion extends Serializable {

	int getQSerialNum();
	String getQuestionText();
	public eDifficulty getEDif();
	public void setEDifficulty(String text);
	public void removeAnswers();
	public boolean canAddAnswer();
	public String WhyCannotAdd();
	public boolean isEmpty();
	public int getType();
	public boolean checkForManualExam() throws LessThan4Answers;
	public boolean addForExam();
	
		
}
