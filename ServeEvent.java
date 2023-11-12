class ServeEvent extends Event {
    private final int serverID;

    ServeEvent(Customer customer, double time, int serverID) {
        super(customer, time);
        this.serverID = serverID;
    }

    @Override
    public ImList<QueueSystem> updateShop(ImList<QueueSystem> shop) {
        

        ImList<QueueSystem> newShop = shop;

        QueueSystem servingQueueSystem = shop.get(this.serverID - 1);

        servingQueueSystem = servingQueueSystem.serve(super.getTime());
        servingQueueSystem = servingQueueSystem
            .addWaitTime(super.getTime() - super.getCustomer().getArrivalTime());

        newShop = newShop.set(serverID - 1, servingQueueSystem);

        return newShop;
    }

    @Override
    public Event nextEvent(ImList<QueueSystem> shop) {

        QueueSystem servingQueueSystem = shop.get(this.serverID - 1);
        double nextTime = servingQueueSystem.getServer().getNextTime();

        return new DoneServingEvent(super.getCustomer(), nextTime, this.serverID);
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

        return String.format("%.3f %s serves by %d\n",
            this.getTime(), this.getCustomer().toString(), this.serverID);
    }    
}
