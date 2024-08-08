import java.util.Scanner;


public abstract class RepUserInterface implements IRepUserInterface {

	protected IReposable rep;
	protected Scanner s;
	
	public RepUserInterface(IReposable rep) {
		this.rep = rep;
		this.s = new Scanner(System.in);
	}
	
	
	

}
