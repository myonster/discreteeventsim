class ArriveEvent extends Event {

    ArriveEvent(Customer customer) {
        super(customer, customer.getArrivalTime());
    }

    @Override
    public Event nextEvent(Shop shop) {
        if (!shop.isEmpty()) {
            if (super.time >= shop.getServer().getTime()) {
                return new ServeEvent(this.getCustomer(), shop.getServer());
            }
        }
        return new LeaveEvent(this.getCustomer());
    }

    @Override
    public Shop updateShop(Shop shop) {
        return shop;
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
        return String.format("%.3f %s arrives",
            this.getTime(), this.getCustomer().toString());
    }
}
