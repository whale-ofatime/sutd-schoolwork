package ps6;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eiros_000 on 14/3/2017.
 */

class Test extends Thread {
    TrackerFixed tracker;

    public Test (TrackerFixed tra) {
        this.tracker = tra;
    }

    public void run () {
        TrackerFixed.MutablePoint loc = tracker.getLocation("somestring");
        loc.x = -1212000;
    }
}

//is this class thread-safe?
public class TrackerFixed {
    //@guarded by ???
    private final Map<String, MutablePoint> locations;

    //the reference locations, is it going to be an escape?
    public TrackerFixed(Map<String, MutablePoint> locations) {
        //this.locations = locations;
        synchronized (this) {
            this.locations = new HashMap<>();
            for (String key : locations.keySet()) {
                this.locations.put(key, new MutablePoint(locations.get(key)));
            }
        }
    }

    //is this an escape?
    public Map<String, MutablePoint> getLocations () {
        //return locations;
        synchronized (locations) {
            Map<String, MutablePoint> copy = new HashMap<>();
            for (String key : locations.keySet()) {
                copy.put(key, new MutablePoint(locations.get(key)));
            }
            return copy;
        }
    }

    //is this an escape?
    public MutablePoint getLocation (String id) {
        //MutablePoint loc = locations.get(id);
        //return loc;
        synchronized (locations) {
            return new MutablePoint(locations.get(id));
        }
    }

    public void setLocation (String id, int x, int y) {
        synchronized (locations) {
            MutablePoint loc = locations.get(id);

            if (loc == null) {
                throw new IllegalArgumentException ("No such ID: " + id);
            }

            loc.x = x;
            loc.y = y;
        }
    }

    //this class is not thread-safe (why?) and keep it unmodified.
    class MutablePoint {
        public int x, y;

        public MutablePoint (int x, int y) {
            this.x = x;
            this.y = y;
        }

        public MutablePoint (MutablePoint p) {
            this.x = p.x;
            this.y = p.y;
        }
    }
}