public class ReposableCreator implements Creator<IReposable> {

	@Override
	public IReposable create(int choice) {
		switch (choice) {
		case 1:
			return new Repository();
		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
		}
	}
}