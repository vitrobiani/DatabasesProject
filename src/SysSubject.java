public interface SysSubject {

	void addObserver(IMenuActionCompleteListener observer);
    void removeObserver(IMenuActionCompleteListener observer);
    void notifyObservers();
}
