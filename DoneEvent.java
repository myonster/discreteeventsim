class DoneEvent extends Event {
    private final int serverID;
    
    DoneEvent(Customer customer, double time, int serverID) {
        super(customer, time);
        this.serverID = serverID;
    }
    
    @Override
    public ImList<QueueSystem> updateShop(ImList<QueueSystem> shop) {
        int serverIndex = this.serverID - 1;
        ImList<QueueSystem> newShop = shop;

        QueueSystem servingQueueSystem = shop.get(serverIndex);

        servingQueueSystem = servingQueueSystem.doneServe(
            super.getCustomer()); //this is a heavy operation
        newShop = newShop.set(serverIndex, servingQueueSystem);

        return newShop;

    }

    @Override
    public Event nextEvent(ImList<QueueSystem> shop) {
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
        return "";
    }
}

