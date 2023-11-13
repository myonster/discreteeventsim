class ServeEvent extends Event {
    private final int serverID;
    private final QueueSystem queue;

    ServeEvent(Customer customer, double time, int serverID, QueueSystem qu) {
        super(customer, time);
        this.serverID = serverID;
        this.queue = qu;
    }

    @Override
    public ImList<QueueSystem> updateShop(ImList<QueueSystem> shop) {
        

        ImList<QueueSystem> newShop = shop;

        QueueSystem servingQueueSystem = shop.get(this.serverID - 1);

        servingQueueSystem = servingQueueSystem.serve(
            super.getTime(), super.getCustomer());
        servingQueueSystem = servingQueueSystem
            .addWaitTime(super.getTime() - super.getCustomer().getArrivalTime());

        newShop = newShop.set(serverID - 1, servingQueueSystem);

        return newShop;
    }

    @Override
    public Event nextEvent(ImList<QueueSystem> shop) {

        QueueSystem servingQueueSystem = shop.get(this.serverID - 1);
        double nextTime = servingQueueSystem
            .getServer(super.getCustomer().getID()).getNextTime();

        return new DoneServingEvent(super.getCustomer(), nextTime, this.serverID,
            servingQueueSystem);
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
        Server server = this.queue.optimalServer();

        return String.format("%.3f %s serves by %s\n",
            this.getTime(), this.getCustomer().toString(), server.toString());
    }    
}
