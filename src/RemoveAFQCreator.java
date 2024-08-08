public class RemoveAFQCreator implements Creator<IRepUserInterface> {

	private IReposable rep;
	
	public RemoveAFQCreator(IReposable rep) {
		this.rep = rep;
	}
	@Override
	public IRepUserInterface create(int choice) {
		switch (choice)
		{
		case 1:
			return new RemoveAFOQ(rep);
		case 2: 
			return new RemoveAFMCQ(rep);
		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
		}
	}
	

}
