class ServeEvent extends Event {
    private final ServerQueue serverQueue;
    private final double serviceTime;

    ServeEvent(Customer customer, double time, ServerQueue serverQueue) {
        super(customer, time);
        this.serverQueue = serverQueue;
        this.serviceTime = customer.getServiceTime();
    }

    ServeEvent(Customer customer, double time, ServerQueue serverQueue, double serviceTime) {
        super(customer, time);
        this.serverQueue = serverQueue;
        this.serviceTime = serviceTime;
    }
    
    @Override
    public Shop updateShop(Shop shop) {
        // takes out a server from a shop -> makes him busy
        ServerQueue updatedSQ = this.serverQueue;
        updatedSQ = updatedSQ.removeFromQueue();
        updatedSQ = updatedSQ.serve(this.getTime() + this.serviceTime);
        updatedSQ = updatedSQ.notAtCounter();

        return shop.updateServerQueueInShop(updatedSQ);
    }

    @Override
    public Event nextEvent(Shop shop) {
        return new DoneEvent(this.getCustomer(), 
        shop.getServerQueueByID(this.serverQueue.getServer().getID()), this.serviceTime);
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
        return String.format("%.3f %s serves by %s",
            this.getTime(), this.getCustomer().toString(), this.serverQueue.getServer().toString());
    }
}