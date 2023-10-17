abstract class Event {
    protected final Customer customer;
    protected final double time;

    Event(Customer customer, double time) {
        this.customer = customer;
        this.time = time;
    }
    
    public Customer getCustomer() {
        return this.customer;
    }

    public double getTime() {
        return this.time;
    }


    abstract Event nextEvent(Shop shop);

    abstract Shop updateShop(Shop shop);

    abstract boolean isDone();

    abstract boolean isServiceProvided();
}