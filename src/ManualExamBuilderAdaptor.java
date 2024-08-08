import java.io.FileNotFoundException;
import java.io.IOException;

public class ManualExamBuilderAdaptor extends hw3_ManualExamBuilder implements IExamBuilder {

	
	public ManualExamBuilderAdaptor(IReposable rep)
	{
		super(rep);
	}
	
	@Override
	public void createExam() throws FileNotFoundException, IOException {
		super.hw3_createExam();

	}

}
