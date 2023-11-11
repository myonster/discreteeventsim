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
            if (updatedSQ.isAtCounter() && updatedSQ.isResting()) {
                return shop.updateServerQueueInShop(updatedSQ.waitServeRest());
            }
        
            return shop.updateServerQueueInShop(updatedSQ);
        }


        //updatedSQ = updatedSQ.addWaitTime(updatedSQ.getLastTiming()
        //    - super.getCustomer().getArrivalTime());

        return shop;
    }

    @Override
    public Event nextEvent(Shop shop) {
        ServerQueue sq = shop.getServerQueueByID(this.serverQueue
            .getServer().getID());

        //System.out.println("\n");
        //System.out.println(this.getTime() + " Customer " + 
        //this.getCustomer().toString() + " Waits");
        //System.out.println(this.getTime() +" Server Time: " + 
        //sq.getServer().getTime());
        //System.out.println(this.getTime() +" Customer Time: " + 
        //this.customer.getArrivalTime());
        //System.out.println(this.getTime() +" Server Last Time: " + 
        //sq.getLastTiming());
        //System.out.println(this.getTime() +" QueueSize: " + sq.getQueueSize());
        //System.out.println(this.getTime() +" isResting: " + sq.isResting());

        //System.out.println("[Wait] " + this.getTime() + " Shop: " + 
        //shop.toString() + " || \n" + "ServerQueue: "  + sq.toString() + "\n");

        if (sq.getQueueSize() >= 1) {
            if (sq.isAtCounter()) {
                if (sq.getServer().getTime() >= this.getCustomer().getArrivalTime()) {
                    return new ServeEvent(super.getCustomer(), sq.getLastTiming(), sq);
                }
            }
            //System.out.println("Wait again \n");
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
