class ServerQueue {
    //this is basically a pair class on steroids
    private final Pair<Server, ImList<Customer>> serverQueuePair;
    private final int maxQueueSize;

    // Note Server Template is -> Server(int id, double nextTime, boolean isServing, boolean isResting)

    ServerQueue(Pair<Server, ImList<Customer>> serverQueue, int qmax) {
        this.serverQueuePair = serverQueue;
        this.maxQueueSize = qmax;
    }

    Server getServer() {
        return this.serverQueuePair.first();
    }

    ImList<Customer> getQueue() {
        return this.serverQueuePair.second();
    }

    ServerQueue addToQueue(Customer customer) {
        //catching bug
        if (!this.ableToServe()) {
            System.out.println("ADDING TO QUEUE EVEN WHEN WE CANT SERVE");
            return this;
        }
        
        ImList<Customer> customerQueue = this.getQueue();
        customerQueue = customerQueue.add(customer);

        Server server = this.getServer(); // We dont touch server at all bec is just adding queue
        
        return new ServerQueue(new Pair<Server, ImList<Customer>>(server, customerQueue), 
            this.maxQueueSize);

    }

    ServerQueue serve() {
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
        customerQueue = customerQueue.remove(0);

        server = server.addTime(customerGettingServed.getServiceTime());

        return new ServerQueue(new Pair<Server, ImList<Customer>>(server, customerQueue), 
            this.maxQueueSize);
    }

    //this function just tells us that a customer can be slotted in. nothing else
    boolean ableToServe() {
        return (this.getQueue().size() < this.maxQueueSize + 1);
    }

    //just nobody there at the queue
    boolean isEmpty() {
        return this.getQueue().isEmpty();
    }
}
