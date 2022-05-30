import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList implements Iterable<Object> {
//public class MyArrayList implements MyList {
    int size;           //Size of List
    int capacity;       // Capacity of List
    Object[] elements;  // Init List Empty

    /**
     * My constructor
     */
    public MyArrayList(int size, int capacity) {
        this.size = size;
        this.capacity = capacity;
        this.elements = new Object[capacity];
    }

    /**
     * Added element into end of List
     */
    public void add(Object elem) {
        if (size == capacity) {
            grow();
        }
        elements[size] = elem;
        size++;
    }

    /**
     * Added element into index place of List
     */
    public void add(int index, Object elem) {
        if (index >= capacity) { // Add elem into end or out of List
            while (index >= capacity) {
                grow();
            }
            elements[index] = elem;
            size = index + 1;
        }
        else { //Insert elem into List
            if ((capacity - size) == 1) grow();
            int cnt = size - index;
            System.arraycopy(elements, index, elements, index+1, cnt);
            elements[index] = elem;
            size++;
        }
    }

    /**
     * Grows capacity of List
     */
    private void grow() {
        capacity = (int) (capacity * 1.5);
        Object[] elements_new = new Object[capacity];
        System.arraycopy(elements, 0, elements_new, 0, size);
        elements = elements_new;
    }

    /**
     * Set element into index place of List
     */
    public Object set(int index, Object elem) {
        if (index < size) { // Set elem into List
            elements[index] = elem;
        }
        else if (index < capacity) { // Set elem into End of List but not Out of Capacity
            elements[index] = elem;
            size = index + 1;
        }
        else if (index >= capacity) { // Add elem into End or Out of List
            while (index >= capacity) {
                grow();
            }
            elements[index] = elem;
            size = index + 1;
        }
        return elem;
    }

    /**
     * Remove element from List
     */
    public boolean remove(Object elem) {
        for (int i=0;  i<size; i++) {
            if ((elements[i] != null) && (elements[i].equals(elem))) {
                int cnt = size - i;
                System.arraycopy(elements, i+1, elements, i, cnt);
                size--;
                i--;
                return true;
            }
        }
        return false;
    }

    /**
     * Remove element by index from List
     */
    public Object remove(int index) {
        if (index < size) { //OK
            int cnt = size - index;
            System.arraycopy(elements, index+1, elements, index, cnt);
            size--;
            return 0;
        }
        else return -1;
    }

    /**
     * Clear List
     */
    public void clear() {
        for (int i=0; i<size; i++) elements[i] = null;
        size = 0;
        capacity = 10;
    }

    /**
     * Get element by index from List
     */
    public Object get(int index) {
        if (index < size) { return elements[index]; }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Return index of element in List
     */
    public int indexOf(Object elem) {
        for (int i=0;  i<size; i++) {
            if ((elements[i] != null) && (elements[i].equals(elem))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Check present of element in List
     */
    public boolean contains(Object elem) {
        if (elem != null) {
            for (int i=0;  i<size; i++) {
                if ((elements[i] != null) && (elements[i].equals(elem))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return size of List
     */
    public int size() {
        return size;
    }


    /**
     * For internal testing 
     * 
     * @author Alexander Shylin
     */
    private void printAll() {
        System.out.println(java.util.Arrays.toString(elements));
    }

    public static void main(String[] args) {
        // test init
        MyArrayList lstIt = new MyArrayList(0, 10);

        System.out.println("Test #0 : MyArrayList#Iterator.hasNext(Object)");
        Iterator<Object> it0 = lstIt.iterator();
        System.out.println(it0.hasNext());
        System.out.println("Size is " + lstIt.size());
        System.out.println("");
        // should be 'false'


        System.out.println("Test #00 : MyArrayList#add(Object)");
        for (int i = 0; i < 20; i++) {
            lstIt.add(i);
        }
        ((MyArrayList) lstIt).printAll();
        System.out.println("");
        // should be '1, 3, 5, 7..17, 19, null, ... null'


       System.out.println("Test #000 : MyArrayList#Iterator.next(Object)");
        Iterator<Object> it1 = lstIt.iterator();
        int cnt = 0;
        int Sum = 0;
        System.out.print("[");
        while (it1.hasNext()) { //
            Object el = it1.next();
            System.out.print(el + ", ");
            cnt++;
            Sum += (int) el;
        }
        System.out.print("]");
        System.out.println("");
        System.out.println("Count is " + cnt);
        System.out.println("Sum is " + Sum);
        System.out.println("");
        // should be '20'and 190


        System.out.println("Test #0000 : MyArrayList#Iterator.remove(Object)");
        Iterator<Object> it2 = lstIt.iterator();
        cnt = 0;
        while (it2.hasNext()) { //
            Object el = it2.next();
            if ((int)el%2==0) it2.remove();
        }
        ((MyArrayList) lstIt).printAll();
        System.out.println("");
        // should be '1, 3, 5, 7..17, 19, null, ... null'

        // NEW MyArrayList
        MyArrayList lst = new MyArrayList(0, 10);
        System.out.println("Test #1 : Created NEW MyArrayList#add(Object)");
        for (int i = 0; i < 20; i++) {
            lst.add(i);
       }

        // Note: I have to do explicit conversion because I created the object through the interface MyList which has no method printAll()
        ((MyArrayList) lst).printAll();
        System.out.println("");
        // should print [ 0..19, null, null ] because of call MyArrayList#grow 2 times


        System.out.println("Test #2 : MyArrayList#add(int, Object)");
        for (int i = 0; i < 4; i++) {
            lst.add(10,30+i);
        }
        ((MyArrayList) lst).printAll();
        System.out.println("");
        // should print [0..9, 33, 32, 31, 30, 11..19, null..null (x9 times)]


        System.out.println("Test #3 : MyArrayList#set(int, Object) (replacement)");
        lst.set(10, 42);
        ((MyArrayList) lst).printAll();
        System.out.println("");
        // should print [0..9, 42, 32, 31, 30, 11..19, null..null (x8 times)]


        System.out.println("Test #4 : MyArrayList#set(int, Object) (outbounded)");
        lst.set(40, "500");
        lst.set(41, "500");
        ((MyArrayList) lst).printAll();
        System.out.println("Size is " + lst.size());
        System.out.println("");
        // should print [0..9, 42, 30..33, 11..19, null..null, "500" (at index 40), "500" (at index 41)] 
        // with a length of 73 for inner array (!) because of additional calls of MyArrayList#grow 2 times
        // and a visible size of 42 because index #41 was the last element that we added to the list


        System.out.println("Test #5 : MyArrayList#remove(Object) (multiple elements)");
        lst.remove("500");
        ((MyArrayList) lst).printAll();
        System.out.println("Size is " + lst.size());
        System.out.println("");
        // should print [0..9, 42, 30..33, 11..19, null..null]
        // visible size should be 40 because we removed 2 elements

        System.out.println("Test #6 : MyArrayList#remove(Object) (do nothing if not found)");
        lst.remove("500");
        ((MyArrayList) lst).printAll();
        System.out.println("");
        // should print [0..9, 30..33, 11..19, null..null] 


        System.out.println("Test #7 : MyArrayList#remove(int) (by index)");
        lst.remove(10);
        ((MyArrayList) lst).printAll();
        System.out.println("Size is " + lst.size());
        System.out.println("");
        // should print [0..9, 30..33, 11..19, null..null] 
        // visible size should be 39 because we removed 1 element
        

        System.out.println("Test #8 : MyArrayList#indexOf(Object) (absence check)");
        System.out.print("index of c is ");
        System.out.println(lst.indexOf("500"));
        System.out.println("");
        // should be -1


        System.out.println("Test #9 : MyArrayList#indexOf(Object) (presence check)");
        System.out.print("index of 9 is ");
        System.out.println(lst.indexOf(9));
        System.out.println("");
        // should be 9


        System.out.println("Test #10 : MyArrayList#contains(Object) (absence check)");
        System.out.print("contains of \"500\" is ");
        System.out.println(lst.contains("500"));
        System.out.println("");
        // should be 'false'


        System.out.println("Test #11 : MyArrayList#contains(Object) (presence check)");
        System.out.print("contains of 9 is ");
        System.out.println(lst.contains(9));
        System.out.println("");
        // should be 'true'


        System.out.println("Test #12 : MyArrayList#Size()");
        System.out.println("Size is " + lst.size());
        // should be 39

        lst.clear();
        System.out.println("Test #12 : MyArrayList#clear()");
        System.out.println("Size is " + lst.size());
        // should be 0

        try {
            System.out.println(lst.get(0));
            System.out.println(lst.get(1));
            throw new RuntimeException("Test 12 didn't pass! List should be clean AF");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Test 12 successfully passed!");
        } finally {
        	System.out.println("\n\nEnd of the tests");
        }

    }

    @Override
    public Iterator<Object> iterator() { return new Itr(); }

    private class Itr implements Iterator<Object> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public Object next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            cursor = i + 1;
            return (Object) elements[lastRet = i];
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

    }
}