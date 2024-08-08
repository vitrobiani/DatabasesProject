import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class AutomaticExamBuilder implements IExamBuilder {

	private IReposable rep;
	private List<IQuestion> possibleQList;
	private int TestQNum;
	

	public AutomaticExamBuilder(IReposable rep) {
		this.rep = rep;
	}

	public void createExam() throws FileNotFoundException, IOException {

		Scanner s = new Scanner(System.in);
		possibleQList = rep.getValidQuestionsForExam();
		int numOfValid = possibleQList.size();
		if (numOfValid == 0)
		{
			System.out.println("No questions in this class. Returning to menu.\n");
			return;
		}
		System.out.println("Type number of desired questions, 0 for menu.");
		int TestQNum = s.nextInt();
		while (TestQNum < 0 || TestQNum > numOfValid) {
			System.out.println("Invalid number. Please choose between 1 and " + numOfValid + ", or 0 for menu:");
			TestQNum = s.nextInt();
		}
		if (TestQNum == 0) {
			return;
		}
		List<IQuestion> qArray = new ArrayList<>();
		String testString = "", solutionString = "", tmp = "";

		for (int i = 0; i < TestQNum; i++) {
			int randQ;
			boolean randQExists;
			do {
				randQExists = false;
				randQ = (int) (Math.random() * ((qArray.size() + 1)));
				Iterator<IQuestion> it = qArray.iterator();
				while (it.hasNext())
				{
					IQuestion q = it.next();
					
				if (possibleQList.get(randQ) == q) {
						randQExists = true;
					}
				}
			} while (randQExists == true);
			qArray.add(possibleQList.get(randQ));
			tmp = "Question #" + (i + 1) + ": " + qArray.get(i).getQuestionText() + "\n\n";
			solutionString += tmp;
			testString += tmp;

			if (qArray.get(i) instanceof OpenQuestion) {
				OpenQuestion oq = (OpenQuestion) qArray.get(i);
				testString += "\n";
				solutionString += "Answer: " + oq.getAnswerText() + "\n\n";
			} else {
				MCQuestion mcq = (MCQuestion) qArray.get(i);
				int[] Answers = { -1, -1, -1, -1 };
				for (int l = 0; l < 4; l++) {
					int randA;
					boolean doubleTrue = false;
					boolean trueFlag = false;
					boolean randAExists;
					do {
						doubleTrue = false;
						randAExists = false;
						randA = (int) (Math.random() * ((mcq.getNumOfAnswers())));
						for (int m = 0; m < l; m++) {
							if (randA == Answers[m]) {
								randAExists = true;

							}

						}
						if (mcq.getAnswerValue(randA) == true && trueFlag == true) {
							doubleTrue = true;
							continue;
						}
						if (mcq.getAnswerValue(randA) == true) {
							trueFlag = true;
						}

					} while (randAExists == true || doubleTrue == true);
					Answers[l] = randA;
					tmp = "Answer #" + (l+1) + ": " + mcq.getAnswerText(Answers[l]);
					solutionString += tmp + " - " + mcq.getAnswerValue(Answers[l])  + "\n";
					testString += tmp + "\n";
				}
				String none = "true";
				for (int n = 0; n < 4; n++) {
					if (mcq.getAnswerValue(Answers[n]) == true) {
					none = "false";	
					}
				}
				solutionString += "Answer #5: None of the above - " + none + "\n";
				tmp = "\n";
				solutionString += tmp;
				testString += tmp;
			}
		}
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm");
		File testFile = new File("exam_" + today.format(dtf) + ".txt");
		File solutionFile = new File("solution_" + today.format(dtf) + ".txt");
		PrintWriter pwt = new PrintWriter(testFile);
		PrintWriter pws = new PrintWriter(solutionFile);
		pwt.print(testString);
		pws.print(solutionString);
		pwt.close();
		pws.close();
	}
	
	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append("possibleQList: " + possibleQList.toString() + "\n");
		res.append("TestQNum: " + TestQNum + "\n");
		return res.toString();
	}
	
	@Override 
	public boolean equals(Object other) {
		if (!(other instanceof AutomaticExamBuilder)) {
			return false;
		}
		AutomaticExamBuilder a = (AutomaticExamBuilder)other;
		return a.possibleQList == this.possibleQList && a.TestQNum == this.TestQNum;
	}
}