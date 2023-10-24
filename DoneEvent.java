class DoneEvent extends Event {
    private final ServerQueue serverQueue;

    
    DoneEvent(Customer customer, ServerQueue serverQueue) {
        super(customer, serverQueue.getServer().getTime());
        this.serverQueue = serverQueue;
    }

    @Override
    public Shop updateShop(Shop shop) {
        ServerQueue sq = shop.getServerQueueByID(this.serverQueue.getServer().getID());
        ServerQueue updatedSQ = sq; //this.serverQueue;
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
        return String.format("%.3f %s done serving by %s\n",
            this.getTime(), this.getCustomer().toString(), this.serverQueue.getServer().toString());
    }

}
