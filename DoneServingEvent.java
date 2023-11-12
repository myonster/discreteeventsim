class DoneServingEvent extends Event {
    private final Server server;

    DoneServingEvent(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }
    
    @Override
    public ImList<ServerQueue> updateShop(ImList<ServerQueue> shop) {
        
        int serverIndex = this.server.getID() - 1;
        ImList<ServerQueue> newShop = shop;

        ServerQueue servingServerQueue = shop.get(serverIndex);

        
        servingServerQueue = servingServerQueue.doneServe(); //this is a heavy operation
        newShop = newShop.set(serverIndex, servingServerQueue);

        Server doneServer = servingServerQueue.getServer();
        ImList<Customer> doneQueue = servingServerQueue.getQueue();
        int maxQueueSize = servingServerQueue.getMaxQueueSize();
        double waitTime = servingServerQueue.getWaitTime();

        double restTime = doneServer.getRestTime();
        //System.out.println(super.getTime() + " rest time is: " + restTime);
        //System.out.println(super.getTime() + " Server " 
        //+ doneServer.toString() + " nextTime is " + doneServer.getNextTime());
        
        if (restTime > 0) {
            doneServer = doneServer.updateRestingStatus(true);
            doneServer = doneServer.addTime(super.getTime() + restTime);
            servingServerQueue = new ServerQueue(
                new Pair<Server, ImList<Customer>>(doneServer, doneQueue),
                maxQueueSize, waitTime);
            
            newShop = newShop.set(serverIndex, servingServerQueue);
            //System.out.println(super.getTime() + " Server " + doneServer.toString() 
            //    + " nextTime after rest is \n" + doneServer.getNextTime());

            return newShop;
        }

        return newShop;
    }

    @Override
    public Event nextEvent(ImList<ServerQueue> shop) {

        int serverIndex = this.server.getID() - 1;

        ServerQueue servedServerQueue = shop.get(serverIndex);
        Server servedServer = servedServerQueue.getServer();

        if (servedServer.isResting()) {
            return new RestEvent(super.getCustomer(), servedServer.getNextTime(), servedServer);
        }

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
        return String.format("%.3f %s done serving by %s\n",
            super.getTime(), super.getCustomer().toString(), this.server.toString());
    }

}