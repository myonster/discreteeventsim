class WaitRestEvent extends WaitEvent {

    WaitRestEvent(Customer customer, double time, Server server) {
        super(customer, time,server);
    }

    @Override
    public Event nextEvent(ImList<ServerQueue> shop) {
        int customerID = super.getCustomer().getID();
        int serverIndex = super.server.getID() - 1;

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
            return new ServeEvent(super.getCustomer(), server.getNextTime(), server);
        }

        return new WaitingEvent(super.getCustomer(), server.getNextTime(), server);
    }

}
