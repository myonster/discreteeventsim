class LeaveEvent extends Event {
    
    LeaveEvent(Customer customer) {
        super(customer, customer.getArrivalTime());
    }

    @Override
    public Event nextEvent(Shop shop) {
        return this;
    }

    @Override
    public Shop updateShop(Shop shop) {
        return shop;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    public boolean isServiceProvided() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s leaves", 
            this.getTime(), this.getCustomer().toString());
    }
}
