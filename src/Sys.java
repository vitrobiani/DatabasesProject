import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;


@SuppressWarnings("serial")
public class Sys implements ISys, SysSubject, SysCommandReceiver, Serializable {


	private int command;
	private transient List<IMenuActionCompleteListener> observers = new ArrayList<>();
	private static volatile ISys instance = null; // Double-checked locking
	private Map<String, IReposable> rMap;
	private Creator<IReposable> reposableCreator;
	
	public Creator<IReposable> getReposableCreator() {
		return reposableCreator;
	}


	public void setReposableCreator(Creator<IReposable> iReposableCreator) {
		reposableCreator = iReposableCreator;
	}



	public static ISys getInstance(int i) {
		if (instance == null) {

			synchronized (Sys.class) {
				if (instance == null)
					switch (i)
					{
					case 0:
						ObjectInputStream inFile = null;
						try {
							inFile = new ObjectInputStream(new FileInputStream("sys.dat"));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						try {
							instance = (ISys) inFile.readObject();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						try {
							inFile.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					case 1:
						instance = new Sys();
						break;
					case 2:
						instance = new Sys(null);
						break;
					}
			}
		}
		return instance;
	}
	

	
	private Sys() {
		
		rMap = new LinkedHashMap<>();
		
		IReposable rep1 = new Repository("Math");
		IReposable rep2 = new Repository("English");

		rMap.put("Math", rep1);
		rMap.put("English", rep2);
		
		rep1.addMCQuestion("q1", eDifficulty.easy);
		rep1.addMCQuestion("q2", eDifficulty.medium);
		rep1.addOpenQuestion("q3", "a1", eDifficulty.medium);
		rep1.addMCQuestion("q4", eDifficulty.hard);
		rep2.addMCQuestion("q5", eDifficulty.easy);
		rep2.addMCQuestion("q6", eDifficulty.medium);
		rep2.addMCQuestion("q7", eDifficulty.hard);
		rep2.addMCQuestion("q8", eDifficulty.easy);
		
		rep1.addAnswerToMCFromText(1, "a1", false);
		rep1.addAnswerToMCFromText(1, "a2", false);
		rep1.addAnswerToMCFromText(1, "a3", false);
		rep1.addAnswerToMCFromText(1, "a4", true);
		rep1.addAnswerToMCFromText(1, "a5", true);

		rep1.addAnswerToMCFromText(2, "a1", false);
		rep1.addAnswerToMCFromText(2, "a6", false);

		rep1.addAnswerToMCFromText(4, "a7", true);
		rep1.addAnswerToMCFromText(4, "a8", true);
		rep1.addAnswerToMCFromText(4, "a9", true);
		rep1.addAnswerToMCFromText(4, "a10", true);
		rep1.addAnswerToMCFromText(4, "a11", true);
		rep1.addAnswerToMCFromText(4, "a12", true);
		rep1.addAnswerToMCFromText(4, "a13", true);
		rep1.addAnswerToMCFromText(4, "a14", true);
		rep1.addAnswerToMCFromText(4, "a15", true);
		rep1.addAnswerToMCFromText(4, "a16", true);
		
		rep2.addAnswerToMCFromText(1, "a17", false);
		rep2.addAnswerToMCFromText(1, "a18", false);
		rep2.addAnswerToMCFromText(1, "a19", false);
		rep2.addAnswerToMCFromText(1, "a20", false);
		rep2.addAnswerToMCFromText(1, "a21", false);

		rep2.addAnswerToMCFromText(2, "a22", false);
		rep2.addAnswerToMCFromText(2, "a23", true);
		rep2.addAnswerToMCFromText(2, "a24", false);
		rep2.addAnswerToMCFromText(2, "a25", false);
		rep2.addAnswerToMCFromText(2, "a26", false);
		
		rep2.addAnswerToMCFromText(3, "a27", false);
		rep2.addAnswerToMCFromText(3, "a28", true);
		rep2.addAnswerToMCFromText(3, "a29", false);
		rep2.addAnswerToMCFromText(3, "a30", false);
		
		rep2.addAnswerToMCFromText(4, "a31", false);
		rep2.addAnswerToMCFromText(4, "a32", true);
		rep2.addAnswerToMCFromText(4, "a33", false);
		rep2.addAnswerToMCFromText(4, "a34", false);
		

	}

	private Sys(Map<String, IReposable> cMap) {


		rMap = new LinkedHashMap<>();
		for (Map.Entry<String, IReposable> entry : cMap.entrySet()) {
			rMap.put(entry.getKey(), entry.getValue());
		}
		
	}
	
	public void addNewClass() {

		Scanner s = new Scanner(System.in);
		System.out.println("Enter class name, or 0 for menu:");

		String name = s.nextLine();
		while (this.classExists(name) && !(name.equals("0"))) {
			System.out.println("Class already exists. Please try again: ");
			name = s.nextLine();
		}
		if (name.equals("0")) {
			return;
		}
		this.addReposable(name, 1);
		this.command = 2;
		notifyObservers();
	}
	
	public void addReposable(String text, int type) {
		IReposable rep = reposableCreator.create(type);
		rep.setClassName(text);
		rMap.put(text, rep);
	}

	public void removeClass() {

		Scanner s = new Scanner(System.in);
		System.out.println("Choose class to remove (or 0 for menu): ");
		System.out.println(this.classesToString());
		int classnum = s.nextInt();
		s.nextLine();
		while (classnum < 0 || classnum > this.getNumOfReps()) {
			System.out.println(
					"Invalid number: please choose beteween 1 and " + this.getNumOfReps() + " , or 0 for menu:");
			classnum = s.nextInt();
		}
		if (classnum == 0) {
			return;
		}
		this.removeReposable(this.getRepByNum(classnum));
		this.command = 3;
		notifyObservers();
	}

	public void removeReposable(IReposable rep) {
		String s = rep.getClassName();
		rMap.remove(s);
	}
	
	public void showQuestionsAndAnswers(IReposable rep) {
		if (rep.noQuestions()) {
			System.out.println("No questions yet. Add some and try again.");
		} else {
			System.out.println(rep.toString());
		}
	}
	
	



	

	public void addAnswerToQuestion(IReposable rep, Scanner s, Creator<IRepUserInterface> rui) {

		if (rep.noQuestions()) {
			System.out.println("No Questions yet. Add some and try again.");
			return;
		} else {

			System.out.println("Choose question, or type 0 to return to menu:");

			System.out.println(rep.questionsToString());
			int qNum = s.nextInt();
			while (qNum != 0) {
				if (qNum < 1 || qNum > rep.getNumOfQuestions()) {
					System.out.println("Invalid question number. Please try again or type 0 to return to menu.");
					System.out.println(rep.questionsToString());
					qNum = s.nextInt();
				} else if (!rep.canAddAnswerToQNum(qNum)) {
					if (rep.isOpenQuestion(qNum)) {
						System.out.println(
								"This open question already has an answer. To change it, remove this question with menu option 3 and re-enter it again and add an answer.");
					} else {
						System.out.println("Question already has " + rep.getMaxAnswers()
								+ " answers. Choose another or type 0 to return to menu.");
					}
					System.out.println(rep.questionsToString());
					qNum = s.nextInt();
				} else {

					System.out.println(
							"Choose whether to add from repository (type 1) or enter text (type 2). To return to menu type 0.");
					int choice = s.nextInt();
					while (choice > 2 || choice < 0) {
						System.out.println(
								"Invalid option. Choose whether to add from repository (type 1) or enter text (type 2). To return to menu type 0");
						choice = s.nextInt();
					}
					if (choice == 0) {
						return;
					}

					AddA addAnswer = (AddA) rui.create(choice);
					addAnswer.addAnswer(qNum);

				}
			}
		}
	}

	public void addQuestion(IReposable rep, Scanner s, Creator<IRepUserInterface> rui) {

		String text;
		System.out.println("Enter question text: (or 0 for menu) ");
		s.nextLine();
		text = s.nextLine();
		while (rep.questionExists(text) && !text.equals("0")) {
			System.out.println("Question already exists. Type another (or 0 for menu):");
			text = s.nextLine();
		}
		if (text.equals("0")) {
			return;
		} else

			System.out.println("Please enter difficulty: easy, medium, or hard (or 0 for menu): ");
		String difText = s.nextLine();
		while (!rep.isEnumVal(difText) && !difText.equals("0")) {
			System.out.println("Invalid difficulty type. Please enter easy, medium or hard, (or 0 for menu):");
			difText = s.nextLine();
		}
		if (difText.equals("0")) {
			return;
		} else {
			System.out.println("Choose question type: 1 for open, 2 for multiple choice, 0 for menu:");
			int qType = s.nextInt();
			while (qType != 0 && qType != 1 && qType != 2) {
				System.out.println("Invalid input. Please try again: ");
				qType = s.nextInt();
			}
			AddQ aq = (AddQ) rui.create(qType);

			aq.initQuestion(text, difText);
			aq.addQuestion();
			System.out.println("Question added successfully.");
		}
	}

	public void removeAnswerFromQuestion(IReposable rep, Scanner s, Creator<IRepUserInterface> rui) {

		if (rep.noQuestions()) {
			System.out.println("No questions yet. Add some and try again.");
		} else {
			System.out.println("Choose question, or type 0 to return to menu:");
			System.out.println(rep.questionsToString());
			int qNum = s.nextInt();
			while (qNum < 1 || qNum > rep.getNumOfQuestions() || rep.isEmptyQuestion(qNum)) {
				if (qNum == 0) {
					return;
				}

				else if (qNum < 1 || qNum > rep.getNumOfQuestions()) {
					System.out.println("Invalid question number. Please try again or type 0 to return to menu.");
					System.out.println(rep.questionsToString());
					qNum = s.nextInt();
				} else {
					System.out.println("Question doesn't have any answers. Choose another or 0 for menu.");
					System.out.println(rep.questionsToString());
					qNum = s.nextInt();
				}
			}

			int t = rep.questionType(qNum);
			RemoveAFQ raq = (RemoveAFQ) rui.create(t);
			raq.removeQuestion(qNum);
		}
	}

	public void removeQuestion(IReposable rep, Scanner s) {
		if (rep.noQuestions()) {
			System.out.println("No questions yet. Add some and try again");
			return;
		} else {
			System.out.println("Choose question to remove, or 0 for menu:");
			System.out.println(rep.questionsToString());
			int qNum = s.nextInt();
			while (qNum < 0 || qNum > rep.getNumOfQuestions()) {
				System.out.println("Invalid option. Try again, or choose 0 for menu:");
				System.out.println(rep.questionsToString());
				qNum = s.nextInt();
			}
			if (qNum == 0) {
				return;
			} else {
				rep.removeQuestion(qNum);
				System.out.println("Question removed successfully.");
			}
		}
	}

	public void createTest(IReposable rep, Scanner s, Creator<IExamBuilder> iebc)
			throws FileNotFoundException, IOException {
		System.out.println("Choose 1 for manual exam, 2 for automatic exam, or 0 to return to menu:");
		int res = s.nextInt();
		while (res != 0 && res != 1 && res != 2) {
			System.out.println("Wrong option - please try again: ");
			res = s.nextInt();
		}
		if (res == 0) {
			return;
		}

		else {

			IExamBuilder ieb = iebc.create(res);
			ieb.createExam();

		}
	}
	
	public void showClasses() {
		
		System.out.println(classesToString());

		Scanner s = new Scanner(System.in);
		int classnum = s.nextInt();
		s.nextLine();
		while (!validClassNum(classnum) && classnum != 0) {
			System.out.println(
					"Invalid number. Please enter a number between 1 and " + getNumOfReps() + " (or 0 for menu): ");
			classnum = s.nextInt();
		}
		if (classnum == 0) {

			this.command = 1;
			notifyObservers();
			return ;
		}
		IReposable rep = getRepByNum(classnum);
		int choice = -1;
		do {
			printMenu();

			Creator<IRepUserInterface> rui;
			choice = s.nextInt();
			switch (choice) {

			case 1:
				showQuestionsAndAnswers(rep);
				break;
			case 2:
				rui = new AddACreator(rep);
				addAnswerToQuestion(rep, s, rui);
				break;
			case 3:
				rui = new AddQCreator(rep);
				addQuestion(rep, s, rui);
				break;
			case 4:
				rui = new RemoveAFQCreator(rep);
				removeAnswerFromQuestion(rep, s, rui);
				break;
			case 5:
				removeQuestion(rep, s);
				break;
			case 6:
				Creator<IExamBuilder> iebc = new ExamBuilderCreator(rep);
				try {
					createTest(rep, s, iebc);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			case 7:
				System.out.println(classesToString());
				choice = s.nextInt();
				break;
			case 0:

				break;
			default:
				System.out.println("Wrong option. Please try again:\n");
			}
		} while (choice != 0);

		
		
	}

	public int getNumOfReps() {
		return rMap.size();
	}
	
	
	public boolean noQuestions() {
		for (Map.Entry<String, IReposable> entry : rMap.entrySet()) {
			if (entry.getValue().noQuestions())
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		for (Map.Entry<String, IReposable> entry : rMap.entrySet()) {
			res.append(entry.getValue().toString());
		}
		return res.toString();
	}

	public String classesToString() {
		StringBuffer res = new StringBuffer();
		int idx = 1;
		res.append("Please choose subject: (or 0 to exit):\n");
		for (Map.Entry<String, IReposable> entry : rMap.entrySet()) {
			res.append("Class #" + idx + ": " + entry.getValue().getClassName() + "\n");
			idx++;
		}
		return res.toString();
	}

	public boolean validClassNum(int n) {
		return (n > 0 && n <= rMap.size());
	}

	public IReposable getRep(String text) {
		return rMap.get(text);
	}

	public IReposable getRepByNum(int num) {
		int idx = 1;
		IReposable res;
		for (Map.Entry<String, IReposable> entry : rMap.entrySet()) {
			res = entry.getValue();
			if (idx++ == num)
				return res;
		}
		return null;
	}

	public boolean classExists(String name) {
		return rMap.containsKey(name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(rMap);
	}



	
	public static void printMenu() {
		System.out.println("Choose option:");

		System.out.println("1)  Show all questions and corresponding answers");
		System.out.println("2)  Add new answer to an existing question");
		System.out.println("3)  Add new question");
		System.out.println("4)  Remove answer to a question");
		System.out.println("5)  Remove question and its answers");
		System.out.println("6)  Create new test");
		System.out.println("7)  Back to class menu");
		System.out.println("0)  Exit");
	}



	@Override
	public void addObserver(IMenuActionCompleteListener observer) {
		observers.add(observer);
	}



	@Override
	public void removeObserver(IMenuActionCompleteListener observer) {
		observers.remove(observer);
		
	}



	@Override
	public void notifyObservers() {
		  for (IMenuActionCompleteListener observer : observers) {
	            observer.update(command);
	        }
	}

}
