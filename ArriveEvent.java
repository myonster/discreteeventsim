class ArriveEvent extends Event {

    ArriveEvent(Customer customer) {
        super(customer, customer.getArrivalTime());
    }

    @Override
    public Event nextEvent(Shop shop) {

        if (shop.canServe()) {
            for (ServerQueue sq : shop.getList()) {
                if (sq.isAtCounter()) {
                    if (!sq.isResting()) {
                        return new ServeEvent(this.getCustomer(), this.getTime(),
                            sq.addToQueue());

                    } else if (sq.getServer().getTime() <= this.getCustomer().getArrivalTime()) {
                        
                        return new ServeEvent(this.getCustomer(), this.getTime(),
                            sq.addToQueue());
                    }
                }
            }

            for (ServerQueue sq : shop.getList()) {
                if (sq.canQueue()) {
                    return new WaitEvent(this.getCustomer(), this.getTime(), sq);
                }
            }

            /* 
            if (optimalServerQueue.isAtCounter()) {
                
                if (!optimalServerQueue.isTimeListEmpty()) {
                    if (optimalServerQueue.getLastTiming() > this.getCustomer().getArrivalTime()) {
                        return new WaitEvent(this.getCustomer(), this.getTime(),
                        optimalServerQueue);
                    }
                }
                return new ServeEvent(this.getCustomer(), this.getTime(),
                    optimalServerQueue.addToQueue());
            } else {
                return new WaitEvent(this.getCustomer(), this.getTime(),
                    optimalServerQueue);
            }
            */
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
