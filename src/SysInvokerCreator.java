public class SysInvokerCreator implements Creator<ISysInvoker> {

	@Override
	public ISysInvoker create(int choice) {

		switch (choice) {
		case 0:
			return new SysInvoker();

		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
		}
	}

}
