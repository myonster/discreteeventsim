class ServeEvent extends Event {
    private final Server server;

    ServeEvent(Customer customer, Server server) {
        super(customer, customer.getArrivalTime());
        this.server = server;
    }
    
    @Override
    public Shop updateShop(Shop shop) {
        return shop.removeServer();
    }

    @Override
    public Event nextEvent(Shop shop) {
        return new DoneEvent(this.getCustomer(), this.server);
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
        return String.format("%.3f %s serves by %s",
            this.getTime(), this.getCustomer().toString(), this.server.toString());
    }
}