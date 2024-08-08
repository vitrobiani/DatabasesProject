public class AddARep extends AddA {

	public AddARep(IReposable rep) {
		super(rep);
	}
	
	@Override
	public void addAnswer(int qNum) {
		System.out.println("Choose from the following answers by number: (or 0 for menu)");
		int aChoice = -1;
		System.out.println(rep.allAnswersToString());
		aChoice = s.nextInt();
		while (aChoice < 1 || aChoice > rep.getNumOfAnswers()) {
			if (aChoice == 0) {
				return;
			} else {
				System.out.println("Invalid option. Try again (or 0 for menu)");
				rep.allAnswersToString();
				aChoice = s.nextInt();
			}
		}

		if (rep.isOpenQuestion(qNum)) {
			rep.addAnswerToOpenQuestionFromRep(qNum, aChoice);
			System.out.println("Answer added succesfully.");
			return;
		} else {
			while (rep.qNumHasAnswerFromRep(qNum, aChoice)) {
				System.out
						.println("Answer already picked for this question. Try again (or 0 for menu)");
				rep.allAnswersToString();
				aChoice = s.nextInt();
			}
			String value = "";
			System.out.println("Enter value: (true/false) or 0 for menu");
			s.nextLine();
			value = s.nextLine();
			while (!value.equals("0") && !value.equals("true") && !value.equals("false")
					&& !value.equals("True") && !value.equals("False")) {
				System.out.println("Invalid value. Enter true / false or 0 for menu");
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
			rep.addAnswerToMCQuestionFromRep(aChoice, qNum, v);
			System.out.println("Answer added successfully.");
			return;
		}
		
	}
	
	
}
