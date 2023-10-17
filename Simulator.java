import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimes;

    Simulator(int nos, ImList<Double> at, Supplier<Double> st) {
        this.numOfServers = nos;
        this.arrivalTimes = at;
        this.serviceTimes = st;
    }

    public String simulate() {
        String output = "";
        int served = 0;
        int left = 0;

        //init shop with servers;
        Shop shop = new Shop();
        for (int i = 1; i <= this.numOfServers; i++) {
            shop = shop.addServer(new Server(i));
        }

        PQ<Event> pqEvents = new PQ<Event>(new EventComp());
        for (int i = 0; i < arrivalTimes.size(); i++) {
            pqEvents = pqEvents.add(new ArriveEvent(
                new Customer(i + 1, this.arrivalTimes.get(i), this.serviceTimes)));
        }

        while (!pqEvents.isEmpty()) {
            Event event = pqEvents.poll().first();
            shop = event.updateShop(shop);
            output += event.toString() + "\n";

            if (event.isDone()) {
                pqEvents = pqEvents.poll().second();
                
                if (event.isServiceProvided()) {
                    served++;
                } else {
                    left++;
                }

            } else {
                pqEvents = pqEvents.poll().second().add(event.nextEvent(shop));
            }
        }

        return String.format("%s[%d %d]",output, served, left);
    }
}
