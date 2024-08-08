public class MenuActionCompleteListener implements IMenuActionCompleteListener {

	private int command;
	 
    @Override
    public void update(int command) {
        this.command = command;
        display();
    }
 
    private void display() {
    	
        switch (command)
        {
        case 1:
        	System.out.println("Classes shown successfully.");
        	break;
        case 2:
    		System.out.println("Class added successfully.");
        	break;
        case 3:
    		System.out.println("Class removed successfully.");
        	break;
        	
        }
    }
}
