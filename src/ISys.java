import java.io.Serializable;

public interface ISys extends SysSubject, Serializable {
	

	public void setReposableCreator(Creator<IReposable> iReposableCreator);	
	String classesToString();
	boolean validClassNum(int classnum);
	int getNumOfReps();
	void addNewClass();
	boolean classExists(String name);
	IReposable getRepByNum(int cChoice);
	IReposable getRep(String text);
	void removeClass();

	
}
