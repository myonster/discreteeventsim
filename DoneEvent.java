class DoneEvent extends Event {
    private final ServerQueue serverQueue;
    private final double serviceTime;
    
    DoneEvent(Customer customer, ServerQueue serverQueue, double serviceTime) {
        super(customer, serverQueue.getServer().getTime());
        this.serverQueue = serverQueue;
        this.serviceTime = serviceTime;
    }

    public double getTime() {
        return (this.getCustomer().getArrivalTime() + this.serviceTime);
    }

    @Override
    public Shop updateShop(Shop shop) {
        ServerQueue updatedSQ = this.serverQueue;
        updatedSQ = updatedSQ.backAtCounter();

        return shop.updateServerQueueInShop(updatedSQ);
    }

    @Override
    public Event nextEvent(Shop shop) {
        return this;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public boolean isServiceProvided() {
        return true;
    }
    
    @Override
    public String toString() {
        return String.format("%.3f %s done serving by %s",
            super.getTime(), this.getCustomer().toString(), this.serverQueue.getServer().toString());
    }

}
