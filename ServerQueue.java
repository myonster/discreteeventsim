class ServerQueue implements QueueSystem {
    //this is basically a pair class on steroids
    private final Pair<Server, ImList<Customer>> serverQueuePair;
    private final int maxQueueSize;
    private final double totalWaitTime;

    // Note Server Template is 
    // -> Server(int id, double nextTime, boolean isServing, boolean isResting)

    @Override
    public boolean isAutomated() {
        return false;
    }

    ServerQueue(Pair<Server, ImList<Customer>> serverQueue, int qmax) {
        this.serverQueuePair = serverQueue;
        this.maxQueueSize = qmax;
        this.totalWaitTime = 0;
    }

    ServerQueue(Pair<Server, ImList<Customer>> serverQueue, int qmax, double wt) {
        this.serverQueuePair = serverQueue;
        this.maxQueueSize = qmax;
        this.totalWaitTime = wt;
    }

    @Override
    public Server getServer(int id) {
        return this.serverQueuePair.first();
    }

    @Override
    public Server optimalServer() {
        return this.getServer();
    }
    
    @Override
    public double getNextTime() {
        return this.getServer().getNextTime();
    }

    Server getServer() {
        return this.serverQueuePair.first();
    }


    @Override
    public ImList<Customer> getQueue() {
        return this.serverQueuePair.second();
    }

    @Override
    public int getMaxQueueSize() {
        return this.maxQueueSize;
    }

    @Override
    public double getWaitTime() {
        return this.totalWaitTime;
    }

    @Override
    public QueueSystem addWaitTime(double time) {
        return new ServerQueue(this.serverQueuePair,
            this.maxQueueSize, this.totalWaitTime + time);
    }

    @Override
    public QueueSystem addToQueue(Customer customer) {
        //catching bug
        if (!this.ableToServe()) {
            //System.out.println("ADDING TO QUEUE EVEN WHEN WE CANT SERVE");
            return this;
        }
        
        ImList<Customer> customerQueue = this.getQueue();
        customerQueue = customerQueue.add(customer);

        Server server = this.getServer(); // We dont touch server at all bec is just adding queue
        
        return new ServerQueue(new Pair<Server, ImList<Customer>>(server, customerQueue), 
            this.maxQueueSize, this.totalWaitTime);

    }

    @Override
    public QueueSystem doneServe(Customer customer) {
        Server server = this.getServer();
        ImList<Customer> customerQueue = this.getQueue();
        customerQueue = customerQueue.remove(0);

        server = server.updateServingStatus(false);

        return new ServerQueue(new Pair<Server, ImList<Customer>>(server, customerQueue), 
            this.maxQueueSize, this.totalWaitTime);
    }

    @Override
    public QueueSystem serve(double time, Customer customer) {
        // Take out the first customer from queue
        // Server that customer by removign from queue and adding to nextTime in Server

        //If the queue has nobody -> it doesnt change the state
        if (this.getQueue().isEmpty()) {
            return this;
        }

        //First customer in the Customer Queue
        Customer customerGettingServed = this.getQueue().get(0);
        Server server = this.getServer();

        //Remove the customer from the Customer Queue
        ImList<Customer> customerQueue = this.getQueue();
        //customerQueue = customerQueue.remove(0);

        server = server.updateServingStatus(true);
        server = server.addTime(time + customerGettingServed.getServiceTime());
        
        return new ServerQueue(new Pair<Server, ImList<Customer>>(server, customerQueue), 
            this.maxQueueSize, this.totalWaitTime);
    }

    //this function just tells us that a customer can be slotted in. nothing else
    @Override
    public boolean ableToServe() {
        if (this.getServer().isServing()) {
            return (this.getQueue().size() < this.maxQueueSize + 1);
        }
        return (this.getQueue().size() < this.maxQueueSize + 1);
    }

    //just nobody there at the queue
    @Override
    public boolean isEmpty() {
        if (this.getServer().isResting()) {
            return false;
        }

        return this.getQueue().isEmpty();
    }
}
