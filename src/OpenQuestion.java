@SuppressWarnings("serial")
public class OpenQuestion extends Question implements IOpenQuestion {

	private IAnswer answer;

	public OpenQuestion() {
		this.type = 1;
	}

	public OpenQuestion(String qText, IAnswer a, eDifficulty eDif) {
		super(qText, eDif);
		this.answer = a;
	}

	public String getAnswerText() {
		return answer.toString();
	}

	public IAnswer getAnswer() {
		return answer;
	}


	public void removeAnswer() {
		answer = null;
	}

//	public boolean canAddAnswer() {
//		return (answer == null);
//	}

	public void setAnswer(IAnswer a) {
		answer = a;
	}
	
	public boolean checkForManualExam() {
		return true;
	}

	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append(super.toString());
		res.append("Answer: " + answer);
		return res.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof OpenQuestion)) {
			return false;
		}
		if (!super.equals(other)) {
			return false;
		}
		OpenQuestion o = (OpenQuestion) other;
		return o.answer.equals(this.answer);
	}

	@Override
	public void removeAnswers() {
		this.removeAnswer();
	}

	@Override
	public boolean canAddAnswer() {
		return false;
	}

	@Override
	public String WhyCannotAdd() {
		return "This open question already has an answer. Remove it and try again.";
	}

	@Override
	public boolean isEmpty() {
		return this.answer == null;
	}

	@Override
	public boolean addForExam() {
		return true;
	}

	@Override
	public String getText(String text) {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void setEDif(eDifficulty eDif) {
		this.difficulty = eDif;
	}

}
