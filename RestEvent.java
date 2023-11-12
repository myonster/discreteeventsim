class RestEvent extends Event {
    private final int serverID;

    RestEvent(Customer customer, double time, int serverID) {
        super(customer, time);
        this.serverID = serverID;
    }
    
    @Override
    public ImList<QueueSystem> updateShop(ImList<QueueSystem> shop) {
        int serverIndex = this.serverID - 1;
        ImList<QueueSystem> newShop = shop;

        QueueSystem servingQueueSystem = shop.get(serverIndex);

        Server doneServer = servingQueueSystem.getServer();
        ImList<Customer> doneQueue = servingQueueSystem.getQueue();
        int maxQueueSize = servingQueueSystem.getMaxQueueSize();
        double waitTime = servingQueueSystem.getWaitTime();

        doneServer = doneServer.updateRestingStatus(false);

        servingQueueSystem = new ServerQueue(
            new Pair<Server, ImList<Customer>>(doneServer, doneQueue),
            maxQueueSize, waitTime);
        
        newShop = newShop.set(serverIndex, servingQueueSystem);
        return newShop;
    }

    @Override
    public Event nextEvent(ImList<QueueSystem> shop) {
        return new DoneEvent(super.getCustomer(), super.getTime(), this.serverID);
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean isServiceProvided() {
        return true;
    }

    @Override
    public String toString() {
        return "";
        //return "Server" + this.server.toString()+ " is Resting \n";
    }
}

