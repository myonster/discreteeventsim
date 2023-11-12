class DoneEvent extends Event {
    private final Server server;

    DoneEvent(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }
    
    @Override
    public ImList<ServerQueue> updateShop(ImList<ServerQueue> shop) {
        return shop;
    }

    @Override
    public Event nextEvent(ImList<ServerQueue> shop) {
        //Reserved for RestEvent

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
            super.getTime(), super.getCustomer().toString(), this.server.toString());
    }

}