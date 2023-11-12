class DoneServingEvent extends Event {
    private final int serverID;

    DoneServingEvent(Customer customer, double time, int serverID) {
        super(customer, time);
        this.serverID = serverID;
    }
    
    @Override
    public ImList<QueueSystem> updateShop(ImList<QueueSystem> shop) {
        
        ImList<QueueSystem> newShop = shop;

        QueueSystem servingQueueSystem = shop.get(this.serverID - 1);

        
        //servingQueueSystem = servingQueueSystem.doneServe(); //this is a heavy operation
        newShop = newShop.set(this.serverID - 1, servingQueueSystem);

        Server doneServer = servingQueueSystem.getServer();
        ImList<Customer> doneQueue = servingQueueSystem.getQueue();
        int maxQueueSize = servingQueueSystem.getMaxQueueSize();
        double waitTime = servingQueueSystem.getWaitTime();

        double restTime = doneServer.getRestTime();
        //System.out.println(super.getTime() + " rest time is: " + restTime);
        //System.out.println(super.getTime() + " Server " 
        //+ doneServer.toString() + " nextTime is " + doneServer.getNextTime());
        
        if (restTime > 0) {
            doneServer = doneServer.updateRestingStatus(true);
            doneServer = doneServer.addTime(super.getTime() + restTime);
            servingQueueSystem = new ServerQueue(
                new Pair<Server, ImList<Customer>>(doneServer, doneQueue),
                maxQueueSize, waitTime);
            
            newShop = newShop.set(serverID - 1, servingQueueSystem);
            //System.out.println(super.getTime() + " Server " + doneServer.toString() 
            //    + " nextTime after rest is \n" + doneServer.getNextTime());

            return newShop;
        }

        return newShop;
    }

    @Override
    public Event nextEvent(ImList<QueueSystem> shop) {

        QueueSystem servedQueueSystem = shop.get(serverID - 1);
        Server servedServer = servedQueueSystem.getServer();

        if (servedServer.isResting()) {
            return new RestEvent(super.getCustomer(), servedServer.getNextTime(), this.serverID);
        }

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
        return String.format("%.3f %s done serving by %d\n",
            super.getTime(), super.getCustomer().toString(), this.serverID);
    }

}