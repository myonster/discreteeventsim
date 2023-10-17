class DoneEvent extends Event {
    private final Server server;
    
    DoneEvent(Customer customer, Server server) {
        super(customer, customer.getArrivalTime() + customer.getServiceTime());
        this.server = server.serve(customer.getArrivalTime() + customer.getServiceTime());
    }

    @Override
    public Shop updateShop(Shop shop) {
        return shop.addServer(this.server);
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
        return String.format("%.3f %s done serving by %s",
            this.getTime(), this.getCustomer().toString(), this.server.toString());
    }

}
