class RestEvent extends Event {
    private final Server server;

    RestEvent(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }
    
    @Override
    public ImList<ServerQueue> updateShop(ImList<ServerQueue> shop) {
        int serverIndex = this.server.getID() - 1;
        ImList<ServerQueue> newShop = shop;

        ServerQueue servingServerQueue = shop.get(serverIndex);

        Server doneServer = servingServerQueue.getServer();
        ImList<Customer> doneQueue = servingServerQueue.getQueue();
        int maxQueueSize = servingServerQueue.getMaxQueueSize();

        doneServer = doneServer.updateRestingStatus(false);

        servingServerQueue = new ServerQueue(
            new Pair<Server, ImList<Customer>>(doneServer, doneQueue), maxQueueSize);
        
        newShop = newShop.set(serverIndex, servingServerQueue);
        return newShop;
    }

    @Override
    public Event nextEvent(ImList<ServerQueue> shop) {
        return new DoneEvent(super.getCustomer(), super.getTime());
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

