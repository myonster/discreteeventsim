class WaitEvent extends Event {
    private final Server server;

    WaitEvent(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    @Override
    public ImList<ServerQueue> updateShop(ImList<ServerQueue> shop) {
        return shop;
    }

    @Override
    public Event nextEvent(ImList<ServerQueue> shop) {
        return this;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean isServiceProvided() {
        return false;
    }
    
    @Override
    public String toString() {

    return String.format("%.3f %s waits at %s\n",
        super.getTime(), super.getCustomer().toString(), this.server.toString());
    }
    
}
