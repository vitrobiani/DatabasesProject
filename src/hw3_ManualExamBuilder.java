import java.io.File;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.io.IOException;

public class hw3_ManualExamBuilder {

	private IReposable rep;
	private int MAX_ANSWERS;
	private List<Integer> qArray;
	private List<List<Boolean>> aArray;
	private int numOfQuestions;
	private int TestQNum;
	private int numOfValid;
	private Scanner s;

	public hw3_ManualExamBuilder(IReposable rep) {

		this.rep = rep;
		s = new Scanner(System.in);
		numOfValid = rep.getValidQuestionsForExam().size();

		System.out.println("Type number of desired questions, 0 for menu");
		hw3_setNumOfDesiredQuestionsFromUser();
		if (TestQNum == 0) {
			return;
		}

		this.MAX_ANSWERS = rep.getMaxAnswers();
		this.qArray = new ArrayList<>();
		this.aArray = new ArrayList<>(TestQNum);
		for (int i = 0; i < TestQNum; i++) {
			aArray.add(new ArrayList<Boolean>());
			for (int j = 0; j < MAX_ANSWERS; j++) {
				aArray.get(i).add(false);
			}
		}

	}

	

	private void hw3_setNumOfDesiredQuestionsFromUser() {

		boolean isValidInput = false;
		while (!isValidInput) {
			try {
				int num = s.nextInt();
				hw3_setTestQNum(num);
				isValidInput = true;
			} catch (TooManyQuestionsDeclared e) {
				System.out.print(e.getMessage());
				System.out.println(" Choose a number between 1 and " + numOfValid);
			} catch (TestQNumNotInRange e) {
				System.out.print(e.getMessage());
				System.out.println(" Choose a number between 1 and " + numOfValid);

			}
		}
	}

	private boolean hw3_isFull() {
		return (TestQNum == qArray.size());
	}

	private boolean hw3_noQuestions() {
		return (qArray.size() == 0);
	}

	private boolean hw3_lessThanTestQNum() {
		return (qArray.size() < TestQNum);
	}

	private int hw3_getNumOfQuestions() {
		return qArray.size();
	}

	private boolean hw3_questionPicked(int qNum) {

		Iterator<Integer> it = qArray.iterator();
		while (it.hasNext()) {
			if (qNum - 1 == it.next())
				return true;
		}
		return false;
	}

	private boolean hw3_questionHasAnswers(int qNum) {

		Iterator<List<Boolean>> it = aArray.iterator();
		while (it.hasNext()) {
			Iterator<Boolean> bit = it.next().iterator();
			while (bit.hasNext()) {
				if (bit.next() == true)
					return true;
			}
		}
		return false;
	}

	private void hw3_removeQuestion(int qNum) {
		Iterator<Integer> it = qArray.iterator();
		while (it.hasNext()) {
			if (it.next() == qNum - 1)
				it.remove();
		}
	}

	private void hw3_removeAnswersToQuestion(int i) {
		ListIterator<Boolean> it = aArray.get(i).listIterator();
		while (it.hasNext()) {
			it.remove();
			it.add(false);
			it.next();
		}
	}

	private void hw3_addQuestion(int qNum) {
		qArray.add(qNum - 1);
	}

	private void hw3_addAnswerToQuestion(int qNum, int aNum) {
		Iterator<Integer> it = qArray.iterator();
		Iterator<List<Boolean>> balit = aArray.iterator();
		while (it.hasNext()) {

			if (it.next() == qNum - 1)
				balit.next().set(aNum - 1, true);
			else
				balit.next();
		}
	}

	private int hw3_qNumToqArrayNum(int qNum) {
		Iterator<Integer> it = qArray.iterator();
		int count = 0;
		while (it.hasNext()) {
			if (it.next() == qNum - 1)
				return count;
			count++;
		}
		return -1;
	}

	private boolean hw3_answerPicked(int qNum, int aNum) {
		int qIndex = hw3_qNumToqArrayNum(qNum);
		if (qIndex == -1)
			return false;
		if (aArray.get(qIndex).get(aNum - 1) == true)
			return true;
		return false;
	}

	private void hw3_removeAnswerFromMCQuestion(int qNum, int aNum) {
		int qIndex = hw3_qNumToqArrayNum(qNum);
		aArray.get(qIndex).set(aNum - 1, false);
	}

	private boolean hw3_empty() {
		return aArray.isEmpty();

	}

	private boolean hw3_morethan1(int i) {
		int qIndex = hw3_qNumToqArrayNum(i + 1);
		boolean found = false;
		Iterator<Boolean> it = aArray.get(qIndex).iterator();
		while (it.hasNext()) {
			if (it.next() == true) {
				if (found == true)
					return true;
				found = true;
			}
		}
		return false;
	}

	private boolean hw3_none(int n) {
		Iterator<Boolean> it = aArray.get(n).iterator();
		while (it.hasNext()) {
			if (it.next() == true)
				return false;
		}
		return true;
	}

	private boolean hw3_finalizeTest() throws FileNotFoundException, IOException {
		if (hw3_empty())
			return false;
		String testString = "", solutionString = "", tmp = "";
		ListIterator<Integer> iit = qArray.listIterator();
		ListIterator<Boolean> bit;
		while (iit.hasNext()) {
			Integer integer = iit.next();
			tmp = "Question #" + (iit.nextIndex()) + ": " + rep.getQuestionText(integer) + "\n";
			solutionString += tmp;
			testString += tmp;
			int aCount = 1;

			if (rep.isOpenQuestion(integer + 1)) {
				// testString += "Answer: " + rep.getOpenQuestionAnswerText(qArray.get(i) - 1) +
				// "\n\n";
				testString += "\n";
				solutionString += "Answer: " + rep.getOpenQuestionAnswerText(integer) + "\n\n";
			}

			else {
				boolean moreThan1 = hw3_morethan1(integer);
				bit = aArray.get(iit.previousIndex()).listIterator();
				while (bit.hasNext()) {
					if (bit.next() == true) {
						tmp = "Answer #" + aCount + ": " + rep.getMCAnswerText(iit.previous(), bit.previousIndex());
						solutionString += tmp;
						testString += tmp;
						if (moreThan1) {
							solutionString += " - false\n";
							iit.next();
						} else {
							solutionString += rep.getMCAnswerValue(iit.next(), bit.previousIndex()) + " ";

						}
						testString += "\n";
						aCount++;
					}
				}

				tmp = "Answer #" + aCount++ + ": " + "More than one answer is correct";
				solutionString += tmp;
				testString += tmp;
				solutionString += " - " + moreThan1;
				tmp = "\nAnswer #" + aCount++ + ": " + "None of the above";
				solutionString += tmp;
				testString += tmp + "\n\n";
				solutionString += " - " + hw3_none(iit.previousIndex()) + "\n\n";
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
		return true;
	}

	public void hw3_createExam() throws FileNotFoundException, IOException {

		if (!rep.repConditions())
		{
			System.out.println("Insufficient data.");
			return;
		}
		int aChoice = -1, qNum = -2;

		while (qNum != -1 && qNum != 0) {
			System.out.println(
					"Choose question. To finalize type -1, to return to menu type 0. To remove a picked question, choose it again.");
			System.out.println(rep.questionsToString());
			boolean isValidInput = false;
			while (!isValidInput) {
				try {
					qNum = s.nextInt();
					hw3_checkQNum(qNum, rep);
					isValidInput = true;
				} catch (LessThan4Answers e) {
					System.out.print(e.getMessage());
				} catch (InvalidQNumException e) {
					System.out.println(e.getMessage());
				}
			}

			if (qNum == 0) {
				return;
			}

			aChoice = -2;
			if (qNum != -1) {

				if (this.hw3_questionPicked(qNum)) {
					this.hw3_removeQuestion(qNum);
					System.out.println("Question removed successfully.");
				} else {
					if (this.hw3_isFull()) {
						System.out.println("Question number exceeded. Please start over with more questions.");
						return;
					} else {
						if (!rep.isEmptyQuestion(qNum)) {
							this.hw3_addQuestion(qNum);
						} else {
							System.out.println("Question is empty");
						}
					}
					if (rep.isEmptyQuestion(qNum)) {
					} else {
						if (rep.isOpenQuestion(qNum)) {
							this.hw3_addAnswerToQuestion(qNum, 1);
							System.out.println("Question added successfully.");
						} else {
							while (aChoice != 0 && aChoice != -1) {
								System.out.println(
										"Choose answer(s). To remove a picked answer, choose it again. To remove question, type -1. To return to questions type 0.");
								System.out.println(rep.questionAnswersToString(qNum));
								aChoice = s.nextInt();
								while (!rep.isValidAnswerToQuestion(qNum, aChoice)) {
									System.out.println(
											"Invalid Answer number. Try again or type 0 to return to questions.");
									aChoice = s.nextInt();
								}
								if (aChoice != 0) {
									if (aChoice == -1) {
										this.hw3_removeQuestion(qNum);
										System.out.println("Question removed successfully.");
									} else if (this.hw3_answerPicked(qNum, aChoice)) {
										this.hw3_removeAnswerFromMCQuestion(qNum, aChoice);
										System.out.println("Answer removed successfully.");
									} else {
										this.hw3_addAnswerToQuestion(qNum, aChoice);
										System.out.println("Answer added successfully.");
									}
								} else {
									if (!this.hw3_questionHasAnswers(qNum)) {
										this.hw3_removeQuestion(qNum);
									}
								}
							}
						}
					}
				}
			} else {
				if (this.hw3_noQuestions()) {
					System.out.println("No questions chosen. returning to menu.");
					return;
				} else if (this.hw3_lessThanTestQNum()) {
					System.out.println("Only " + this.hw3_getNumOfQuestions()
							+ " question chosen. To finalize choose -1, 0 to return to questions");
					qNum = s.nextInt();
					while (qNum != 0 && qNum != -1) {
						System.out.println("Invalid answer. Try again:");
					}
					if (qNum == 0) {
						qNum = -2;
					} else {
						System.out.println("Finalizing:");
						this.hw3_finalizeTest();
						System.out.println("Done");
					}
				} else {
					System.out.println("Finalizing:");
					this.hw3_finalizeTest();
					System.out.println("Done");
				}
			}
		}
	}

	private void hw3_setTestQNum(int num) throws TooManyQuestionsDeclared, TestQNumNotInRange {
		if (num > 10) {
			throw new TooManyQuestionsDeclared();
		} else if (num < 0 || num > numOfValid) {
			throw new TestQNumNotInRange();
		} else {
			TestQNum = num;
		}
	}

	private boolean hw3_checkQNum(int num, IReposable rep) throws LessThan4Answers, InvalidQNumException {
		if (num == 0 || num == -1) {
			return true;
		}
		if (!rep.isValidQuestionNum(num)) {
			throw new InvalidQNumException();
		}
		IQuestion q = rep.getQuestion(num);
		return q.checkForManualExam();
		}

	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append("MAX ANSWERS: " + MAX_ANSWERS + "\n");
		res.append("qArray: " + qArray.toString() + "\n");
		res.append("aArray: " + aArray.toString() + "\n");
		res.append("numOfQuestions: " + numOfQuestions + "\n");
		res.append("TestQNum: " + TestQNum);
		res.append("numOfValid: " + numOfValid);
		return res.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof hw3_ManualExamBuilder)) {
			return false;
		}
		hw3_ManualExamBuilder m = (hw3_ManualExamBuilder) other;
		return m.MAX_ANSWERS == this.MAX_ANSWERS && m.qArray.equals(this.qArray) && m.aArray.equals(this.aArray)
				&& m.numOfQuestions == this.numOfQuestions && m.TestQNum == this.TestQNum
				&& m.numOfValid == this.numOfValid;
	}



}
