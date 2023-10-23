class ArriveEvent extends Event {

    ArriveEvent(Customer customer) {
        super(customer, customer.getArrivalTime());
    }

    @Override
    public Event nextEvent(Shop shop) {
        if (shop.canServe()) {
            ServerQueue optimalServerQueue = shop.getServerQueue();
            if (optimalServerQueue.isAtCounter()) {
                return new ServeEvent(this.getCustomer(), this.getTime(),
                    optimalServerQueue.addToQueue());
            } else {
                return new WaitEvent(this.getCustomer(), this.getTime(),
                    optimalServerQueue);
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
        return String.format("%.3f %s arrives\n",
            this.getTime(), this.getCustomer().toString());
    }
}
