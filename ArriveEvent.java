class ArriveEvent extends Event {
    
    ArriveEvent(Customer customer) {
        super(customer, customer.getArrivalTime());
    }
    
    @Override
    public ImList<ServerQueue> updateShop(ImList<ServerQueue> shop) {
        ImList<ServerQueue> tempShop = shop;//new ImList<ServerQueue>();

        // Customer that arrives will look at shop
        // Looks at which is empty from 1 -> n (Regardeless of if Server is Resting)
        for (ServerQueue sq: shop) {
            if (sq.isEmpty()) {
                int serverIndex = sq.getServer().getID();

                //This is where we slot in our Customer into ServerQueue

                tempShop = tempShop.set(serverIndex, sq.addToQueue(super.getCustomer()));
                return tempShop;
            }
        }

        // Looks at which one can serve? (Regardeless of if Server is Resting)
        for (ServerQueue sq: shop) {
            if (sq.ableToServe()) {
                int serverIndex = sq.getServer().getID();

                //This is where we slot in our Customer into ServerQueue

                tempShop = tempShop.set(serverIndex, sq.addToQueue(super.getCustomer()));
                return tempShop;
            }
        }

        // when we do this -> essentially customer is not added in!
        return shop;
    }

    @Override
    public Event nextEvent(ImList<ServerQueue> shop) {
        int customerID = super.getCustomer().getID();
        int customerPosition = 0;
        ServerQueue servingSQ = shop.get(0);
        boolean inShop = false;

        // Searching where our customer is
        for (ServerQueue sq: shop) {
            ImList<Customer> customerQueue = sq.getQueue();
            for (Customer customer: customerQueue) {
                if (customerID == customer.getID()) {
                    inShop = true;
                    customerPosition = customerQueue.indexOf(customer); // 0 if its at front 
                    servingSQ = sq;
                    break;
                }
            }
        }
        
        Server server = servingSQ.getServer();

        if (inShop) {
            if (customerPosition == 0) {
                if (server.isResting()) {
                    //return new WaitEvent(super.getCustomer(), super.getTime(), server);
                } else {
                    return new ServeEvent(super.getCustomer(), super.getTime(), server);
                }

            } else {
                //return new WaitEvent(super.getCustomer(), super.getTime(), server);
            }
        }


        return new LeaveEvent(super.getCustomer(), super.getTime());
    }


    @Override
    public boolean isDone(){
        return false;
    }

    @Override
    public boolean isServiceProvided(){
        return false;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s arrives\n",
            super.getTime(), super.getCustomer().toString());
    }
}
