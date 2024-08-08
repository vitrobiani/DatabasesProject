public class ExamBuilderCreator implements Creator<IExamBuilder>{

	private IReposable rep;
	public ExamBuilderCreator(IReposable rep) {
		this.rep = rep;
	}
	
	@Override
	public IExamBuilder create(int choice) {
		
		switch (choice)
		{
		case 1:
			IExamBuilder targetInterface = new ManualExamBuilderAdaptor(rep);
			return targetInterface;
		case 2: 
			return new AutomaticExamBuilder(rep);
		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
		}	
	}
	}

