public class IACreator implements Creator<IAnswer> {

	@Override
	public IAnswer create(int choice) {
		switch (choice)
		{
		case 0:
			return new Answer();
		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
		}
	}

	

}
