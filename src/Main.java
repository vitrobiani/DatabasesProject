import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Main{

	public static void printOpening() {
		System.out.println(
				"Welcome to the test management system. Please choose of the following (or type 0 for exit) :\n");

		System.out.println("1) Show classes");
		System.out.println("2) Add new class");
		System.out.println("3) Remove class");
	}

	public static void exit(ISys s) {
		ObjectOutputStream outFile = null;
		try {
			outFile = new ObjectOutputStream(new FileOutputStream("sys.dat"));
		} catch (
				FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			outFile.writeObject(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			outFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		Creator<IReposable> reposableCreator = new ReposableCreator();
		Creator<ISys> sysCreator = new SysCreator();
		Creator<ISysInvoker> sysInvokerCreator = new SysInvokerCreator();
		Creator<IMenuActionCompleteListener> MACLCreator = new MenuActionCompleteListenerCreator();
		ISys sys = sysCreator.create(1);
		Creator<Command> commandCreator = new CommandCreator(sys);
		sys.setReposableCreator(reposableCreator);

		ISysInvoker sIvk = sysInvokerCreator.create(0);

		IMenuActionCompleteListener defaultObserver = MACLCreator.create(0);
		sys.addObserver(defaultObserver);

		int oChoice = -1;
		Scanner s = new Scanner(System.in);
		while (oChoice != 0) {
			printOpening();
			oChoice = s.nextInt();
			s.nextLine();
			Command command;
			if (oChoice < 0 || oChoice > 3)
				System.out.println("Wrong option. Please try again:\n");
			else if (oChoice == 0)
				break;
			else
			{
				command = commandCreator.create(oChoice);
				sIvk.setCommand(command);
				sIvk.pressButton();
			}
		}
		exit(sys);
		System.out.println("Goodbye.");
		s.close();

	}
}
