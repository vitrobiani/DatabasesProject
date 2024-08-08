public class AddAConsole extends AddA {
	
	public AddAConsole(IReposable rep) {
		super(rep);
	}

	public void addAnswer(int qNum) {
		System.out.println("Enter answer text: (or type the word 'exit' for menu)");
		s.nextLine();
		String aText = s.nextLine();
		if (aText.equals("exit)")) {
			return;
		} else {
			if (rep.isOpenQuestion(qNum)) {
				rep.setAnswerToOpenFromText(qNum, aText);
				System.out.println("Answer added successfully.");
				return;
			} else {
				if (rep.qNumHasAnswerByText(qNum, aText)) {
					System.out.println("Answer already exists for this question.");
				} else {
					String value = "";
					while (!value.equals("0") && !value.equals("true") && !value.equals("false")
							&& !value.equals("True") && !value.equals("False")) {
						System.out.println("Enter value: (true/false) or 0 for menu");
						value = s.nextLine();
						if (value.equals("0")) {
							return;
						}
					}
					boolean v;
					if (value.equals("true") || value.equals("True")) {
						v = true;
					} else {
						v = false;
					}
					rep.addAnswerToMCFromText(qNum, aText, v);
					System.out.println("Answer added successfully.");
					return;
				}
			}
		}
			}
}
