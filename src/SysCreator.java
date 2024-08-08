import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SysCreator implements Creator<ISys> {

	@Override
	public ISys create(int choice) {
		
				return Sys.getInstance(choice);
			

	}
}
