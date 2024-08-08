import java.io.Serializable;
import java.util.List;


public interface IReposable extends Serializable {
	
	public void setClassName(String className);
	String questionsToString();
	String allAnswersToString();
	String questionAnswersToString(int qNum);
	String getClassName();
	int getNumOfQuestions();
	int getNumOfAnswers();
	int getMaxAnswers();
	int getNumOfAnswersByQNum(int qNum);
	String getQuestionText(int integer);
	String getOpenQuestionAnswerText(int integer);
	String getMCAnswerText(int i, int j);
	boolean getMCAnswerValue(int q, int a);
	IQuestion getQuestion(int num);
	List<IQuestion> getValidQuestionsForExam();
	int questionType(int qNum);
	
	void addAnswerToOpenQuestionFromRep(int qNum, int aChoice);
	void addAnswerToMCQuestionFromRep(int aChoice, int qNum, boolean v);
	void setAnswerToOpenFromText(int qNum, String aText);
	void addAnswerToMCFromText(int qNum, String aText, boolean v);
	IOpenQuestion addOpenQuestion(String qText, String aText, eDifficulty eDif);
	IOpenQuestion addOpenQuestion(String qText, int aChoice, eDifficulty eDif);
	boolean addMCQuestion(String text, eDifficulty eDif);
	void removeAnswerFromOpenQuestion(int qNum);
	void removeQuestion(int qNum);
	void removeAnswerFromMCQuestion(int qNum, int aChoice);
	
	boolean questionExists(String text);
	boolean isValidAnswerToQuestion(int qNum, int aChoice);
	boolean isValidQuestionNum(int num);
	boolean canAddAnswerToQNum(int qNum);
	boolean isOpenQuestion(int qNum);
	boolean qNumHasAnswerFromRep(int qNum, int aChoice);
	boolean qNumHasAnswerByText(int qNum, String aText);
	boolean isEnumVal(String difText);
	boolean isEmptyQuestion(int qNum);
	boolean noQuestions();
	boolean repConditions();
	
	
	
	
	
}
