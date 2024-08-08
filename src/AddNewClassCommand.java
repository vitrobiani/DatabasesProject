public class AddNewClassCommand implements Command {

	private SysCommandReceiver receiver;
	
	public AddNewClassCommand(SysCommandReceiver receiver) {
		this.receiver = receiver;
	}
	
	public void execute() {
		receiver.addNewClass();
	}
}
