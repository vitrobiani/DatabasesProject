public class RemoveClassCommand implements Command {
	
	private SysCommandReceiver receiver;
	
	public RemoveClassCommand(SysCommandReceiver receiver) {
		this.receiver = receiver;
	}
	
	public void execute() {
	receiver.removeClass();
	}

}
