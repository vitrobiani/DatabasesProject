import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

public interface IExamBuilder extends Serializable {
	

	void createExam() throws FileNotFoundException, IOException ;

}
