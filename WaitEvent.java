class WaitEvent extends Event {
    protected final Server server;
    private final boolean echo;

    WaitEvent(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
        this.echo = true;
    }

    WaitEvent(Customer customer, double time, Server server, boolean status) {
        super(customer, time);
        this.server = server;
        this.echo = false;
    }

    @Override
    public ImList<ServerQueue> updateShop(ImList<ServerQueue> shop) {
        return shop;
    }

    @Override
    public Event nextEvent(ImList<ServerQueue> shop) {
        int customerID = super.getCustomer().getID();
        int serverIndex = this.server.getID() - 1;

        int customerPosition = 0;
        ServerQueue servingSQ = shop.get(serverIndex);
        
        Server server = servingSQ.getServer();

        for (ServerQueue sq: shop) {
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
                return new WaitEvent(super.getCustomer(), server.getNextTime(), server, false);
            } else {
                //System.out.println(server.toString() + " is going to serve customer " + 
                //customerID + " at " + server.getNextTime());
                return new ServeEvent(super.getCustomer(), super.getTime(), server);
            }

        }

        return new WaitEvent(super.getCustomer(), server.getNextTime(), server, false);
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

        return String.format("%.3f %s waits at %s\n",
            super.getTime(), super.getCustomer().toString(), this.server.toString());
    }
}
