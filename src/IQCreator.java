public class IQCreator implements Creator<IQuestion> {

	@Override
	public IQuestion create(int choice) {

		switch (choice)
		{
		case 0:
			return new OpenQuestion();
			
		case 1:
			return new MCQuestion();
			
		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
		}	
	}

}
