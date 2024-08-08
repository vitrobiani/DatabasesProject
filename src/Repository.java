import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class Repository implements IReposable {

	public Repository() {

	}

	private final int MAX_ANSWERS = 10;
	private String className;
	public void setClassName(String className) {
		this.className = className;
	}

	private Set<String> qSet;
	private Map<String, IAnswer> aMap;
	private List<IQuestion> qArray;
	private List<IAnswer> aArray;

	public Repository(String className) {

		qSet = new HashSet<>();
		aMap = new HashMap<>();
		qArray = new ArrayList<>();
		aArray = new ArrayList<>();

		this.className = className;
	}

	
	public IAnswer getAnswer(int aNum) {
		return aArray.get(aNum - 1);
	}

	public IQuestion getQuestion(int qNum) {
		return qArray.get(qNum - 1);
	}

	public int getNumOfQuestions() {
		return qArray.size();
	}

	public int getNumOfAnswers() {
		return aArray.size();
	}

	public int getNumOfAnswersByQNum(int qNum) {
		return ((IMCQuestion) qArray.get(qNum - 1)).getNumOfAnswers();
	}

	public String getQuestionText(int q) {
		return qArray.get(q).getQuestionText();
	}

	public String getMCAnswerText(int i, int j) {
		return ((MCQuestion) qArray.get(i)).getAnswerText(j);
	}

	public String getOpenQuestionAnswerText(int q) {
		return ((OpenQuestion) qArray.get(q)).getAnswerText();
	}

	public boolean getMCAnswerValue(int q, int a) {
		return ((MCQuestion) qArray.get(q)).getAnswerValue(a);
	}

	public int getMaxAnswers() {
		return MAX_ANSWERS;
	}

	public List<IQuestion> getValidQuestionsForExam() {

		ArrayList<IQuestion> arr = new ArrayList<>();

		// int res = 0;

		Iterator<IQuestion> it = qArray.iterator();
		while (it.hasNext()) {
			IQuestion q = it.next();
			boolean add = q.addForExam();
			if (add)
				arr.add(q);
		}
		return arr;
	}

	public void setAnswerToOpenFromText(int qNum, String aText) {
		
		IOpenQuestion q = (IOpenQuestion) qArray.get(qNum - 1);
		IAnswer a = addAnswer(aText);
		q.setAnswer(a);
	}

	public boolean addMCQuestion(String text, eDifficulty eDif) {
		if (questionExists(text)) {
			return false;
		}
		
		Creator<IQuestion> creator = new IQCreator();
		IMCQuestion imcq = (IMCQuestion) creator.create(1);
		imcq.setText(text);
		imcq.setEDif(eDif);
		qSet.add(text);
		qArray.add((IQuestion)imcq);
		return true;
	}

	public IOpenQuestion addOpenQuestion(String qText, String aText, eDifficulty eDif) {
		if (questionExists(qText)) {
			return null;
		}
		Creator<IAnswer> ac = new IACreator();
		IAnswer a = ac.create(0);
		a.setText(aText);
		
		Creator<IQuestion> ioqc = new IQCreator();
		IOpenQuestion oq = (IOpenQuestion) ioqc.create(0);
		oq.setText(aText);
		oq.setEDif(eDif);
		
		oq.setAnswer(a);
		qArray.add((IQuestion)oq);
		qSet.add(qText);
		return oq;
	}

	public IOpenQuestion addOpenQuestion(String qText, int aChoice, eDifficulty eDif) {
		return addOpenQuestion(qText, aArray.get(aChoice).toString(), eDif);
	}

	public void addAnswerToOpenQuestionFromRep(int qNum, int aNum) {
		((IOpenQuestion) qArray.get(qNum - 1)).setAnswer(getAnswer(aNum));
	}

	public void addAnswerToMCFromText(int qNum, String aText, boolean v) {
		IMCQuestion q = (IMCQuestion) qArray.get(qNum - 1);
		IAnswer a = addAnswer(aText);
		q.addAnswer(a, v);
	}

	public void addAnswerToMCQuestionFromRep(int aNum, int qNum, boolean value) {

		IMCQuestion q = (IMCQuestion) getQuestion(qNum);
		IAnswer a = getAnswer(aNum);
		q.addAnswer(a, value);
	}

	public IAnswer addAnswer(String aText) {
		if (aMap.containsKey(aText)) {
			return aMap.get(aText);
		}
		
		Creator<IAnswer> creator = new IACreator();
		IAnswer a = creator.create(0);
		a.setText(aText);
		aArray.add(a);
		aMap.put(aText, a);
		return a;
	}

	public void removeAnswerFromMCQuestion(int qNum, int aNum) {
		((IMCQuestion) qArray.get(qNum - 1)).removeAnswer(aNum);
	}

	public void removeAnswerFromOpenQuestion(int qNum) {
		((IOpenQuestion) qArray.get(qNum - 1)).removeAnswer();
	}

	public void removeQuestion(int qNum) {
		IQuestion q = qArray.get(qNum - 1);
		q.removeAnswers();
		qArray.remove(qNum - 1);
	}

	public boolean questionExists(String text) {
		return qSet.contains(text);
	}

	public boolean isEnumVal(String text) {
		for (int i = 0; i < eDifficulty.values().length; i++) {
			if (text.equals(eDifficulty.values()[i].name())) {
				return true;
			}
		}
		return false;
	}

	public boolean canAddAnswerToQNum(int qNum) {
		IQuestion q = getQuestion(qNum);
		return q.canAddAnswer();
	}

	public String WhyCannotAdd(int qNum) {
		IQuestion q = getQuestion(qNum);
		return q.WhyCannotAdd();
	}

	public IAnswer isAnswer(String aText) {
		if (aMap.containsKey(aText)) {
			return aMap.get(aText);
		}
		return null;
	}

	public boolean qNumHasAnswerByText(int qNum, String text) {
		IMCQuestion mCQ = (IMCQuestion) qArray.get(qNum - 1);
		return mCQ.isAnswerFromText(text);
	}

	public boolean qNumHasAnswerFromRep(int qNum, int a) {
		IMCQuestion mCQ = (IMCQuestion) qArray.get(qNum - 1);
		return mCQ.isAnswer(aArray.get(a - 1));
	}

	public boolean isEmptyQuestion(int qNum) {
		if (qNum < 1 || qNum > qArray.size()) {
			return false;
		}
		IQuestion q = qArray.get(qNum - 1);
		return q.isEmpty();

	}

	public boolean isOpenQuestion(int qNum) {
		return (qArray.get(qNum - 1) instanceof IOpenQuestion);
	}

	public boolean isValidQuestionNum(int qNum) {
		return (qNum >= -1 && qNum <= qArray.size());
	}

	public boolean isValidAnswerToQuestion(int qNum, int aNum) {
		return (aNum >= -1 && aNum <= getNumOfAnswersByQNum(qNum));
	}

	public boolean repConditions() {

		List<IQuestion> arr = (ArrayList<IQuestion>) getValidQuestionsForExam();
		return qArray.size() > 0 && arr.size() > 0;
	}

	public boolean noQuestions() {
		return qArray.size() == 0;
	}

	public String getClassName() {
		return className;
	}

	public int questionType(int qNum) {
		return qArray.get(qNum - 1).getType();
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Repository)) {
			return false;
		}
		Repository r = (Repository) other;
		return r.className == this.className && r.qArray.equals(this.qArray) && r.aArray.size() == this.qArray.size()
				&& r.aArray.equals(this.aArray) && r.aArray.size() == this.aArray.size();
	}

	@Override
	public String toString() {

		StringBuffer res = new StringBuffer();
		res.append("Class name: " + className + "\n\n");
		ListIterator<IQuestion> it = qArray.listIterator();
		int idx;
		while (it.hasNext()) {
			IQuestion q = it.next();
			idx = it.previousIndex();
			res.append("Question #" + (idx + 1) + ": " + q.toString() + " [difficulty: " + q.getEDif() + ", serial# "
					+ q.getQSerialNum() + "]\n");
			if (q instanceof OpenQuestion) {
				if (!isEmptyQuestion(idx + 1)) {
					res.append("Answer: " + ((IOpenQuestion) q).getAnswerText() + "\n\n");
				} else {
					res.append("\n");
				}
			} else {
				IMCQuestion mCQ = (IMCQuestion) q;
				ListIterator<IAnswer> ait = mCQ.getAnswers().listIterator();
				IAnswer a;
				int aidx;
				while (ait.hasNext()) {
					a = ait.next();
					aidx = ait.previousIndex();
					res.append("Answer #" + (aidx + 1) + ": " + a.toString() + " - " + mCQ.getAnswerValue(aidx) + "\n");
				}
			}
			res.append("\n");
		}
		return res.toString();
	}

	public String MCQuestionsToString() {
		StringBuffer res = new StringBuffer();
		int MCCounter = 0;
		for (int i = 0; i < qArray.size(); i++) {
			if (qArray.get(i) instanceof IOpenQuestion) {
				continue;
			} else {
				MCCounter++;
				res.append("Question #" + MCCounter + ": " + qArray.get(i).getQuestionText() + "\n");
			}
		}
		return res.toString();
	}

	public String questionsToString() {
		StringBuffer res = new StringBuffer();
		Iterator<IQuestion> it = qArray.iterator();
		int count = 1;
		while (it.hasNext()) {
			IQuestion q = it.next();
			res.append("Question #" + (count++) + ": " + q.getQuestionText() + "\n");
		}
		return res.toString();
	}

	public String allAnswersToString() {
		StringBuffer res = new StringBuffer();
		ListIterator<IAnswer> it = aArray.listIterator();
		IAnswer a;
		while (it.hasNext()) {
			a = it.next();
			res.append("Answer #" + (it.previousIndex() + 1) + ": " + a.toString() + "\n");
		}
		for (int i = 0; i < aArray.size(); i++) {
			res.append("Answer #" + (i + 1) + ": " + aArray.get(i).toString() + "\n");
		}
		return res.toString();
	}

	public String questionAnswersToString(int qNum) {
		StringBuffer res = new StringBuffer();
		IMCQuestion mCQ = (IMCQuestion) qArray.get(qNum - 1);
		Iterator<IAnswer> ait = mCQ.getAnswers().iterator();
		Iterator<Boolean> bit = mCQ.getValues().iterator();
		int count = 1;
		Boolean v;
		IAnswer a;
		while (ait.hasNext()) {
			v = bit.next();
			a = ait.next();
			res.append((count++) + ". " + a.toString() + " - " + v + "\n");
		}
		return res.toString();

	}

//	public void removeData() {
//		for (int i=0; i<qArray.size(); i++) {
//			for (int j=0; j < qArray[i].get)
//		}
}
