import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int qmax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimes;

    Simulator(int nos,int qmax, ImList<Double> at, Supplier<Double> st) {
        this.numOfServers = nos;
        this.qmax = qmax;
        this.arrivalTimes = at;
        this.serviceTimes = st;
    }

    public String simulate() {
        String output = "";
        int served = 0;
        int left = 0;

        //init shop with servers;
        Shop shop = new Shop(this.numOfServers, this.qmax);

        PQ<Event> pqEvents = new PQ<Event>(new EventComp());
        for (int i = 0; i < arrivalTimes.size(); i++) {
            pqEvents = pqEvents.add(new ArriveEvent(
                new Customer(i + 1, this.arrivalTimes.get(i), this.serviceTimes)));
        }

        while (!pqEvents.isEmpty()) {
            Event event = pqEvents.poll().first();
            System.out.println(event.getTime() + "00 " + shop.toString());
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
