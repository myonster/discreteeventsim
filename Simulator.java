import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int numOfSelfChecks;
    private final int qmax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimes;
    private final Supplier<Double> restTimes;

    Simulator(int nos, int nosc, int qmax, ImList<Double> at, 
        Supplier<Double> st, Supplier<Double> rt) {

        this.numOfServers = nos;
        this.numOfSelfChecks = nosc;
        this.qmax = qmax;
        this.arrivalTimes = at;
        this.serviceTimes = st;
        this.restTimes = rt;
    }

    public String simulate() {
        String output = "";
        int served = 0;
        int left = 0;
        double waitTime = 0;

        //init shop with servers;
        ImList<QueueSystem> shop = new ImList<QueueSystem>();
        
        for (int i = 1; i <= numOfServers; i++) {
            ImList<Customer> emptyQueue = new ImList<Customer>();
            Pair<Server, ImList<Customer>> serverQueuePair = new Pair<Server, ImList<Customer>>(
                new Server(i, this.restTimes), emptyQueue);

            shop = shop.add(new ServerQueue(serverQueuePair, this.qmax));
        }

        // FOR SELF CHECK SERVERS
        if (this.numOfSelfChecks > 0) {
            ImList<Pair<Server, Integer>> templist = new ImList<Pair<Server,Integer>>();
            for (int i = 1; i <= numOfSelfChecks; i++) {
                Server server = new Server(this.numOfServers + i, () -> 0.0, false);
                templist = templist.add(new Pair<Server, Integer>(server, 0));
            }
            
            shop = shop.add(new AutoQueue(templist));
        }

        PQ<Event> pqEvents = new PQ<Event>(new EventComp());
        for (int i = 0; i < arrivalTimes.size(); i++) {
            pqEvents = pqEvents.add(new ArriveEvent(
                new Customer(i + 1, this.arrivalTimes.get(i), this.serviceTimes)));
        }

        while (!pqEvents.isEmpty()) {
            Event event = pqEvents.poll().first();
            shop = event.updateShop(shop);
            output += event.toString();

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
        for (QueueSystem i : shop) {
            waitTime += i.getWaitTime();
        }
        if (served > 0) {
            waitTime = waitTime / served;
        }

        return String.format("%s[%.3f %d %d]",output, waitTime, served, left);
    }
}
