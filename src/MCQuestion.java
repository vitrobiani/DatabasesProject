import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

@SuppressWarnings("serial")
public class MCQuestion extends Question implements IMCQuestion{

	private Set<String> aSet;
	private List<IAnswer> aArray;
	private List<Boolean> values;

	public MCQuestion()
	{
		this.type = 2;
		this.aArray = new ArrayList<>();
		this.values = new ArrayList<>();
		aSet = new HashSet<>();
	}
	public MCQuestion(String text, eDifficulty dif) {

		super(text, dif);

		this.type = 2;
		this.aArray = new ArrayList<>();
		this.values = new ArrayList<>();
		aSet = new HashSet<>();
	}

	public String getAnswersText() {
		StringBuffer res = new StringBuffer();
		ListIterator<IAnswer> it = aArray.listIterator();
		while (it.hasNext())
		{
			res.append("Answer #" + (it.nextIndex() + 1) + ": " + it.next().toString() + "\n");
		}
		return res.toString();
	}

	public IAnswer getAnswer(int aNum) {
		return aArray.get(aNum);
	}

	public String getAnswerText(int i) {
		return aArray.get(i).toString();
	}
	
	public List<Boolean> getValues()
	{
		return values;
	}
	
	public List<IAnswer> getAnswers()
	{
		return aArray;
	}

	public void removeAnswer(int aNum) {
		IAnswer a = aArray.get(aNum-1);
		aSet.remove(a.toString());
		aArray.remove(aNum-1);
		values.remove(aNum-1);
	}

	public void removeAllAnswers() {
		aArray.clear();
		values.clear();
		aSet.clear();
	}

	public boolean getAnswerValue(int j) {
		return values.get(j);
	}

	public int getNumOfAnswers() {
		return aArray.size();
	}

	public boolean canAddAnswer() {
		return (aArray.size() < MAX_ANSWERS);
	}

	public boolean isAnswer(IAnswer answer) {
		String text = answer.toString();
		return aSet.contains(text);
	}

	public boolean isAnswerFromText(String text) {
		return aSet.contains(text);
	}

	public void addAnswer(IAnswer a, boolean v) {
		aArray.add(a);
		values.add(v);
		aSet.add(a.toString());
	}
	
	public boolean checkForManualExam() throws LessThan4Answers {
		
		if (getNumOfAnswers() < 4) {
			throw new LessThan4Answers();
		}
		return true;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setEDif(eDifficulty eDif) {
		this.difficulty = eDif;
	}
	
	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append(super.toString());
		return res.toString();
	}

	@Override
	public boolean equals(Object other) {

		if (!(other instanceof MCQuestion)) {
			return false;
		}
		if (!super.equals(other)) {
			return false;
		}
		MCQuestion m = (MCQuestion) other;
		return this.text.equals(m.text);
	}

	@Override
	public void removeAnswers() {
		this.removeAllAnswers();
		
	}

	@Override
	public String WhyCannotAdd() {
		return "Question already has " + MAX_ANSWERS + " answers. Choose another or type 0 to return to menu.";
	}

	@Override
	public boolean isEmpty() {
		return aArray.size() == 0;
	}
	@Override
	public boolean addForExam() {
		if (getNumOfAnswers() < 4) {
			return false;
		}
		int falseCounts = 0;
		Iterator<Boolean> bit = getValues().iterator();
		while (bit.hasNext()) {
			Boolean v = bit.next();
			if (!v)
				falseCounts++;
		}
		if (falseCounts >= 3)
			return true;
		
		return false;
	}
	@Override
	public String getText(String text) {
		return text;
	}

}