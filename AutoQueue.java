class AutoQueue implements QueueSystem {

    private final int headId; // NUM OF SELFCHECKERS ?
    private final ImList<Customer> customerQueue;
    private final ImList<Pair<Server, Integer>> serversList; 
    private final double totalWaitTime;
    private final int maxQueueSize;
    //this is basically a pair class on steroids
    // Note Server Template is 
    // -> Server(int id, double nextTime, boolean isServing, boolean isResting)
    
    //constructor for Simulator -> headId will be servers k + 1
    AutoQueue(ImList<Pair<Server, Integer>> serversList, int maxQueueSize) {
        this.headId = 0;
        this.serversList = serversList;
        this.totalWaitTime = 0;
        this.customerQueue = new ImList<Customer>();
        this.maxQueueSize = maxQueueSize;
    }

    AutoQueue(int headId, ImList<Customer> customerQueue,
        ImList<Pair<Server, Integer>> serversList, 
        double waitTime, int maxQueueSize) {

        this.headId = headId;
        this.customerQueue = customerQueue;
        this.serversList = serversList;
        this.totalWaitTime = waitTime;
        this.maxQueueSize = maxQueueSize;
    }

    //utility function

    
    @Override
    public boolean isAutomated() {
        return true;
    }

    @Override
    public Server getServer(int custId) {
        //layering check
        for (Pair<Server, Integer> pair: this.serversList) {
            if (pair.second() == custId) {
                return pair.first();
            }
        }
        // System.out.println("Did not meet criteria");
        return this.serversList.get(0).first();
    }

    @Override
    public Server optimalServer() {
        
        for (Pair<Server, Integer> pair: this.serversList) {
            if (pair.second() == 0) {
                return pair.first();
            }
        }

        return this.serversList.get(0).first();
    }

    @Override
    public ImList<Customer> getQueue() {
        return this.customerQueue;
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
    public double getNextTime() {
        double n = this.serversList.get(0).first().getNextTime();

        for (Pair<Server, Integer> pair: this.serversList) {
            if (pair.first().getNextTime() < n) {
                n = pair.first().getNextTime();
            }
        }
        return n;
    }

    @Override
    public QueueSystem addWaitTime(double time) {
        return new AutoQueue(this.headId, this.customerQueue,
            this.serversList, this.totalWaitTime + time, this.maxQueueSize);
    }

    @Override
    public QueueSystem addToQueue(Customer customer) {

        //are we gna wait? or we slottign it in boys
        ImList<Customer> templist = this.customerQueue;
        ImList<Pair<Server, Integer>> tempSL = this.serversList;

        // if (this.isEmpty()) {
        
        //     int custID = customer.getID();

        //     int index = 0;
        //     for (Pair<Server, Integer> pair: this.serversList) {
        //         if (pair.second() == 0) {
        //             Server server = pair.first();
        //             tempSL = tempSL.set(index,
        //                 new Pair<Server,Integer>(server, custID));
        //             break;
        //         }
        //         index++;
        //     }
        //     return new AutoQueue(this.headId, this.customerQueue, 
        //         tempSL, this.totalWaitTime);
        // }
        
        templist = templist.add(customer);

        return new AutoQueue(this.headId, templist, 
            this.serversList, this.totalWaitTime, this.maxQueueSize);
    }

    @Override
    public QueueSystem doneServe(Customer customer) {
        int custID = customer.getID();
        int index = 0;
        ImList<Pair<Server, Integer>> tempSL = this.serversList;

        for (Pair<Server, Integer> pair: this.serversList) {
            if (pair.second() == custID) {
                tempSL = tempSL.set(index, new Pair<Server,Integer>(
                    pair.first(), 0));
                
                break;
            }
            
            index++;
        }
        return new AutoQueue(this.headId, this.customerQueue,
            tempSL, this.totalWaitTime, this.maxQueueSize);
    }

    @Override
    public QueueSystem serve(double time, Customer customer) {
        ImList<Pair<Server, Integer>> tempSL = this.serversList;

        int custID = customer.getID();

        int index = 0;
        for (Pair<Server, Integer> pair: this.serversList) {
            if (pair.second() == 0) {
                Server server = pair.first();
                server = server.updateServingStatus(true);
                server = server.addTime(time + customer.getServiceTime());

                tempSL = tempSL.set(index,
                    new Pair<Server,Integer>(server, custID));

                break;
            }
            index++;
        }
        return new AutoQueue(this.headId, this.customerQueue.remove(0),
            tempSL, this.totalWaitTime, this.maxQueueSize);
    }

    //this function just tells us that a customer can be slotted in. nothing else
    @Override
    public boolean ableToServe() {
        if (this.isEmpty()) {
            return true;
        }
        return (this.customerQueue.size() < this.maxQueueSize);
    }

    //just nobody there at the queue
    @Override
    public boolean isEmpty() {
        for (Pair<Server, Integer> pair: this.serversList) {
            if (pair.second() == 0) {
                return true;
            }
        }
        return false;
    }
}