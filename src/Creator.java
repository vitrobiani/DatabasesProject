import java.io.Serializable;

public interface Creator<T> extends Serializable {

		public T create(int choice);
	}
