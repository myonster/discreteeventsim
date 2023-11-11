class ServeEvent extends Event {
    private final ServerQueue serverQueue;
    //private final double serviceTime;

    ServeEvent(Customer customer, double time, ServerQueue serverQueue) {
        super(customer, time);
        this.serverQueue = serverQueue;
        //this.serviceTime = customer.getServiceTime();
    }

    @Override
    public Shop updateShop(Shop shop) {
        // takes out a server from a shop -> makes him busy
        ServerQueue sq = shop.getServerQueueByID(this.serverQueue.getServer().getID());     

        ServerQueue updatedSQ =  sq; //this.serverQueue;
        updatedSQ = updatedSQ.serve(super.getTime() + super.getCustomer().getServiceTime());
        updatedSQ = updatedSQ.notAtCounter();
        updatedSQ = updatedSQ.addWaitTime(super.getTime() - super.getCustomer().getArrivalTime());

        //System.out.println("\n");
        //System.out.println(this.getTime() + " SERVING : " + shop.toString());
        //System.out.println(this.getTime() + " resting ? : " + updatedSQ.isResting());

        if (updatedSQ.getQueueSize() < 0) {
            updatedSQ = updatedSQ.addToQueue();
        }
        
        return shop.updateServerQueueInShop(updatedSQ);
    }

    @Override
    public Event nextEvent(Shop shop) {
        return new DoneEvent(this.getCustomer(), 
        shop.getServerQueueByID(this.serverQueue.getServer().getID()));
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
        return String.format("%.3f %s serves by %s\n",
            this.getTime(), this.getCustomer().toString(), this.serverQueue.getServer().toString());
    }
}