public class AddQCreator implements Creator<IRepUserInterface> {

	private IReposable rep;

	public AddQCreator(IReposable rep) {
		this.rep = rep;
	}

	@Override
	public AddQ create(int choice) {
		switch (choice) {
		case 1:
			return new AddOQ(rep);
		case 2:
			return new AddMCQ(rep);
		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
		}
	}
}
