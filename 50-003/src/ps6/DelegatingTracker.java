package ps6;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap; //this is thread-safe!
import java.util.concurrent.ConcurrentMap; //this is thread-safe!

/**
 * Created by eiros_000 on 19/3/2017.
 */

//is this class thread-safe?
public class DelegatingTracker {
    private final ConcurrentMap<String, Point> locations;

    public DelegatingTracker(Map<String, Point> locations) {
        this.locations = new ConcurrentHashMap<String, Point>(locations);
    }

    public synchronized Map<String, Point> getLocations () {
        return Collections.unmodifiableMap(new HashMap<String, Point>(locations));
    }

    //is this an escape? No, because Point is thread-safe
    public synchronized Point getLocation (String id) {
        return locations.get(id);
    }

    //is this thread-safe? Yes. String is an immutable object, so id cannot be changed once it is assigned
    public synchronized void setLocation (String id, int x, int y) {
        if (!locations.containsKey(id)) {
            throw new IllegalArgumentException ("No such ID: " + id);
        }

        locations.get(id).set(x, y);
    }

    //is this class not thread-safe? It is thread-safe
    //is a Point object mutable? No, because it is not passing out the reference
    class Point {
        private int x, y;

        private Point (int[] a) {
            this(a[0], a[1]);
        }

        public Point (Point p) {
            this(p.get());
        }

        public Point (int x, int y) {
            this.x = x;
            this.y = y;
        }

        public synchronized int[] get() {
            return new int[] {x, y};
        }

        public synchronized void set(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}