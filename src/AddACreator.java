public class AddACreator implements Creator<IRepUserInterface> {

		private IReposable rep;
		
		public AddACreator(IReposable rep) {
			this.rep = rep;
		}
		
		@Override
		public AddA create(int choice) {
			switch (choice) {
			case 1:
				return new AddARep(rep);
			case 2:
				return new AddAConsole(rep);
			default:
				throw new IllegalArgumentException("Unexpected value: " + choice);
			}
			
		}
		
	}
