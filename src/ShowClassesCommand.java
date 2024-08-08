public class ShowClassesCommand implements Command {

	private SysCommandReceiver receiver;
	
	public ShowClassesCommand(SysCommandReceiver receiver) {
		this.receiver = receiver;
	}
	@Override
	public void execute() {
		receiver.showClasses();
		
	}

}
