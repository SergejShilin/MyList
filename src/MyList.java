import java.lang.Object;

public interface MyList {
	void add (Object elem);

	void add(int index, Object elem);

	Object set(int index, Object elem);

	boolean remove(Object elem);

	Object remove (int index);

	void clear();

	Object get(int index);

	int indexOf(Object elem);

	boolean contains(Object elem);

	int size();

}



