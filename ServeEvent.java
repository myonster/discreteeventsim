class ServeEvent extends Event {
    private final Server server;

    ServeEvent(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    @Override
    public ImList<ServerQueue> updateShop(ImList<ServerQueue> shop) {
        

        int serverIndex = this.server.getID() - 1;
        ImList<ServerQueue> newShop = shop;

        ServerQueue servingServerQueue = shop.get(serverIndex);

        servingServerQueue = servingServerQueue.serve(super.getTime());
        servingServerQueue = servingServerQueue
            .addWaitTime(super.getTime() - super.getCustomer().getArrivalTime());

        newShop = newShop.set(serverIndex, servingServerQueue);

        return newShop;
    }

    @Override
    public Event nextEvent(ImList<ServerQueue> shop) {
        int serverIndex = this.server.getID() - 1;

        ServerQueue servingServerQueue = shop.get(serverIndex);
        double nextTime = servingServerQueue.getServer().getNextTime();

        return new DoneServingEvent(super.getCustomer(), nextTime, servingServerQueue.getServer());
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

        return String.format("%.3f %s serves by %s\n",
            this.getTime(), this.getCustomer().toString(), this.server.toString());
    }    
}
