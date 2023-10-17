import java.util.Comparator;

class EventComp implements Comparator<Event> {
    
    @Override
    public int compare(Event e1, Event e2) {
        if ((e1.getTime() - e2.getTime()) > 0) {
            return 1;
        } else if ((e1.getTime() - e2.getTime()) == 0) {
            if(e1.getCustomer().getArrivalTime() > e2.getCustomer().getArrivalTime()) {
                return 1;
            }
            else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
