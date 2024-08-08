public class MenuActionCompleteListenerCreator implements Creator<IMenuActionCompleteListener> {

	@Override
	public MenuActionCompleteListener create(int choice) {

		switch (choice) {
		case 0:
			return new MenuActionCompleteListener();

		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);

		}
	}

}
