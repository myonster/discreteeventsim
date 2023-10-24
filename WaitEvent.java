class WaitEvent extends Event {
    private final ServerQueue serverQueue;
    private final boolean isWaiting;
    
    WaitEvent(Customer customer, double time, ServerQueue serverQueue) {
        super(customer, time);
        this.serverQueue = serverQueue;
        this.isWaiting = false;
    }

    WaitEvent(Customer customer, double time, ServerQueue serverQueue, boolean state) {
        super(customer, time);
        this.serverQueue = serverQueue;
        this.isWaiting = state;
    }


    @Override
    public Shop updateShop(Shop shop) {
        /* */
        ServerQueue updatedSQ = this.serverQueue;
        ServerQueue sq = shop.getServerQueueByID(this.serverQueue.getServer().getID());
        
        if (!this.isWaiting) {
            updatedSQ = updatedSQ.addToQueue();
            return shop.updateServerQueueInShop(updatedSQ);
        } else {
            if (sq.isAtCounter()) {
                return shop;
            }
        }
        //updatedSQ = updatedSQ.addWaitTime(updatedSQ.getLastTiming()
        //    - super.getCustomer().getArrivalTime());

        return shop;
    }

    @Override
    public Event nextEvent(Shop shop) {
        ServerQueue sq = shop.getServerQueueByID(this.serverQueue
            .getServer().getID());

        if (sq.getQueueSize() >= 1) {
            if (sq.isAtCounter()) {
                return new ServeEvent(super.getCustomer(), sq.getLastTiming(), sq);
            }
            return new WaitEvent(super.getCustomer(), sq.getLastTiming(), this.serverQueue, true);
        } else {
            return new ServeEvent(super.getCustomer(), sq.getLastTiming(), sq);
        }
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

        if (this.isWaiting) {
            return "";
        }

        return String.format("%.3f %s waits at %s\n",
            this.getTime(), this.getCustomer().toString(), this.serverQueue.getServer().toString());
    }

}
