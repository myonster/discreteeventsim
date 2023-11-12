class DoneEvent extends Event {
    
    DoneEvent(Customer customer, double time) {
        super(customer, time);
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

