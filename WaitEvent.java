class WaitEvent extends Event {
    private final ServerQueue serverQueue;
    
    WaitEvent(Customer customer, double time, ServerQueue serverQueue) {
        super(customer, time);
        this.serverQueue = serverQueue;
    }

    @Override
    public Shop updateShop(Shop shop) {
        ServerQueue updatedSQ = this.serverQueue;
        updatedSQ = updatedSQ.addToQueue();
        updatedSQ = updatedSQ.addQueueTimeList(this.getCustomer().getServiceTime());

        return shop.updateServerQueueInShop(updatedSQ);
    }

    @Override
    public Event nextEvent(Shop shop) {
        ServerQueue sq = shop.getServerQueueByID(this.serverQueue.
            getServer().getID());
        double time = sq.getLastTiming(); //HERE IS THE PROBLEM

        return new ServeEvent(this.getCustomer(), time, sq);
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
        return String.format("%.3f %s waits at %s",
            this.getTime(), this.getCustomer().toString(), this.serverQueue.getServer().toString());
    }

}
