class WaitEvent extends Event {
    protected final int serverID;
    private final boolean echo;

    WaitEvent(Customer customer, double time, int serverID) {
        super(customer, time);
        this.serverID = serverID;
        this.echo = true;
    }

    WaitEvent(Customer customer, double time, int serverID, boolean status) {
        super(customer, time);
        this.serverID = serverID;
        this.echo = false;
    }

    @Override
    public ImList<QueueSystem> updateShop(ImList<QueueSystem> shop) {
        return shop;
    }

    @Override
    public Event nextEvent(ImList<QueueSystem> shop) {
        int customerID = super.getCustomer().getID();

        int customerPosition = 0;
        QueueSystem servingSQ = shop.get(this.serverID - 1);
        
        Server server = servingSQ.getServer();

        for (QueueSystem sq: shop) {
            ImList<Customer> customerQueue = sq.getQueue();
            for (Customer customer: customerQueue) {
                if (customerID == customer.getID()) {
                    customerPosition = customerQueue.indexOf(customer); // 0 if its at front 
                    break;
                }
            }
        }

        if (customerPosition == 0) {
            if (server.isResting()) {
                return new WaitEvent(super.getCustomer(), server.getNextTime(),
                    this.serverID, false);
            } else {
                //System.out.println(server.toString() + " is going to serve customer " + 
                //customerID + " at " + server.getNextTime());
                return new ServeEvent(super.getCustomer(), super.getTime(), this.serverID);
            }

        }

        return new WaitEvent(super.getCustomer(), server.getNextTime(), this.serverID, false);
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
        if (!this.echo) {
            return "";
        }

        return String.format("%.3f %s waits at %d\n",
            super.getTime(), super.getCustomer().toString(), this.serverID);
    }
}
