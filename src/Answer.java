@SuppressWarnings("serial")
public class Answer implements IAnswer {

	private String text;

	public Answer() {
		
	}
	
	public Answer(String text) {
		this.text = text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}

	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append(text);
		return res.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Answer)) {
			return false;
		}
		Answer a = (Answer)other;
		return a.text.equals(this.text);
	}
}
