package ps6;

import java.util.Vector;
/**
 * Created by eiros_000 on 19/3/2017.
 */
public class FirstExample {

    //@precondition: list is not null
    //@postcondition: it retrieves the last element of the current list
    public static Object getLast(Vector list) {
        synchronized (list) {
            int lastIndex = list.size()-1;
            return list.get(lastIndex);
        }
    }

    //@precondition: list is not null
    //@postcondition: the last element of list is removed
    public static void deleteLast(Vector list) {
        synchronized (list) {
            int lastIndex = list.size()-1;
            list.remove(lastIndex);
        }
    }

    //@precondition: list != null /\ obj != null
    //@postcondition: return true iff obj is contained in the list
    public static boolean contains(Vector list, Object obj) {
        synchronized (list) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(obj)) {
                    return true;
                }
            }
            return false;
        }
    }
}