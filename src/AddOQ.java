
public class AddOQ extends AddQ {

	
	public AddOQ(IReposable rep) {
		super(rep);
	}
	
	public void addQuestion()
	{
			System.out.println(
					"Choose whether to add the answer from the repository (1), enter a new answer (2), or cancel and return to menu (0)");
			int aChoiceNum = s.nextInt();
			while (aChoiceNum != 0 && aChoiceNum != 1 && aChoiceNum != 2) {
				System.out.println("Invalid option. Please try again:");
				aChoiceNum = s.nextInt();
			}
			if (aChoiceNum == 0) {
				return;
			} else if (aChoiceNum == 1) {
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
				rep.addOpenQuestion(qText, aChoice - 1, eDifficulty.valueOf(difText));
			} else {
				System.out.println("Enter answer text, or type \"exit\" for menu: ");
				s.nextLine();
				String aText = s.nextLine();
				if (aText.equals("exit")) {
					return;
				}
				rep.addOpenQuestion(qText, aText, eDifficulty.valueOf(difText));
			}
		}
	}
