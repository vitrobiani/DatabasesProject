public class CommandCreator implements Creator<Command> {

	private ISys sys;
	
	public CommandCreator(ISys sys)
	{
		this.sys = sys;
	}
	
	@Override
	public Command create(int choice) {
		
		switch (choice)
		{
		
		case 0:
			return null;
			
		case 1:
			return new ShowClassesCommand((SysCommandReceiver) sys);
		
		case 2:
			return new AddNewClassCommand((SysCommandReceiver) sys);
			
		case 3: 
			return new RemoveClassCommand((SysCommandReceiver) sys);
			

		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
					
	}
	}
}
