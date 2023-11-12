class DoneEvent extends Event {
    private final Server server;
    
    DoneEvent(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }
    
    @Override
    public ImList<ServerQueue> updateShop(ImList<ServerQueue> shop) {
        int serverIndex = this.server.getID() - 1;
        ImList<ServerQueue> newShop = shop;

        ServerQueue servingServerQueue = shop.get(serverIndex);

        servingServerQueue = servingServerQueue.doneServe(); //this is a heavy operation
        newShop = newShop.set(serverIndex, servingServerQueue);

        return newShop;

    }

    @Override
    public Event nextEvent(ImList<ServerQueue> shop) {
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

