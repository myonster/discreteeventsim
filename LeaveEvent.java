class LeaveEvent extends Event {
    
    LeaveEvent(Customer customer, double time) {
        super(customer, time);
    }
    
    @Override
    public ImList<QueueSystem> updateShop(ImList<QueueSystem> shop) {
        return shop;
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
        return false;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s leaves\n", 
            super.getTime(), super.getCustomer().toString());
    }
}
