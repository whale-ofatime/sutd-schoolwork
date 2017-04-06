package ps9;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by eiros_000 on 3/4/2017.
 */

/**
 * How deadlock happens:
 * Both Taxi and Dispatcher calls for setLocation and getImage methods
 * These methods lock its class (synchronized) and tries to acquire the lock of the other
 * when it runs dispatcher.notifyAvailable and t.getLocation
 * This results in deadlock and there is no progress
 */

public class DLExample {
}


class Taxi {
    private Point location, destination;
    private final Dispatcher dispatcher;

    public Taxi(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    // which lock does it take?
    public synchronized Point getLocation() {
        return location;
    }

    // which lock does it take?
    public synchronized void setLocation(Point location) {
        // location update and check with the destination should be atomic
        this.location = location;
        if (location.equals(destination))
            dispatcher.notifyAvailable(this);
    }

    // which lock does it take?
    public synchronized Point getDestination() {
        return destination;
    }
}

class Dispatcher {
    private final Set<Taxi> taxis;
    private final Set<Taxi> availableTaxis;

    public Dispatcher() {
        taxis = new HashSet<Taxi>();
        availableTaxis = new HashSet<Taxi>();
    }

    public synchronized void notifyAvailable(Taxi taxi) {
        availableTaxis.add(taxi);
    }

    public synchronized Image getImage() {
        Image image = new Image();
        for (Taxi t : taxis)
            image.drawMarker(t.getLocation());
        return image;
    }
}

class Image {
    public void drawMarker(Point p) {
    }
}

class Point {

}

