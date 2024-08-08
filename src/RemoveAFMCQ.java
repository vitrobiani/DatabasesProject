public class RemoveAFMCQ extends RemoveAFQ {
	
	public RemoveAFMCQ(IReposable rep) {
		super(rep);
	}

	public void removeQuestion(int qNum) {
		System.out.println(
				"This question is open. To substitute its answer, remove it by typing 1 and re-enter question with menu option 3, or choose 0 to return to menu:");
		int aChoiceNum = s.nextInt();
		while (aChoiceNum != 0 && aChoiceNum != 1) {
			System.out.println("Invalid option. Please try again: ");
			aChoiceNum = s.nextInt();
		}
		if (aChoiceNum == 0) {
			return;

		} else {
			rep.removeQuestion(qNum);
			System.out.println("Question removed successfully.");
			return;
		}

	}
}
