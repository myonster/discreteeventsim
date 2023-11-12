class ArriveEvent extends Event {
    
    ArriveEvent(Customer customer) {
        super(customer, customer.getArrivalTime());
    }
    
    @Override
    public ImList<QueueSystem> updateShop(ImList<QueueSystem> shop) {
        ImList<QueueSystem> tempShop = shop;//new ImList<QueueSystem>();

        // Customer that arrives will look at shop
        // Looks at which is empty from 1 -> n (Regardeless of if Server is Resting)
        for (QueueSystem sq: shop) {
            if (sq.isEmpty()) {
                int serverIndex = sq.getServer().getID() - 1;

                //This is where we slot in our Customer into QueueSystem
                //System.out.println("is empty");

                tempShop = tempShop.set(serverIndex, sq.addToQueue(super.getCustomer()));
                return tempShop;

            }
        }

        // Looks at which is empty from 1 -> n (Regardeless of if Server is Resting)
        // for (QueueSystem sq: shop) {
        //     if (sq.getQueue().isEmpty()) {
        //         int serverIndex = sq.getServer().getID() - 1;

        //         //This is where we slot in our Customer into QueueSystem
        //         //System.out.println("is empty");

        //         tempShop = tempShop.set(serverIndex, sq.addToQueue(super.getCustomer()));
        //         return tempShop;

        //     }
        // }

        // Looks at which one can serve? (Regardeless of if Server is Resting)
        //boolean ableToServe = false;

        for (QueueSystem sq: shop) {
            if (sq.ableToServe()) {
                int serverIndex = sq.getServer().getID() - 1;

                //This is where we slot in our Customer into QueueSystem

                tempShop = tempShop.set(serverIndex, sq.addToQueue(super.getCustomer()));
                return tempShop;
            }
        }
        // System.out.println("we left");
        // when we do this -> essentially customer is not added in!
        return shop;
    }

    @Override
    public Event nextEvent(ImList<QueueSystem> shop) {
        int customerID = super.getCustomer().getID();
        int customerPosition = 0;
        QueueSystem servingSQ = shop.get(0);
        boolean inShop = false;

        // Searching where our customer is
        for (QueueSystem sq: shop) {
            ImList<Customer> customerQueue = sq.getQueue();
            for (Customer customer: customerQueue) {
                if (customerID == customer.getID()) {
                    inShop = true;
                    customerPosition = customerQueue.indexOf(customer); // 0 if its at front
                    //System.out.println(customerPosition); 
                    servingSQ = sq;
                    break;
                }
            }
        }
        
        Server server = servingSQ.getServer();
        int serverIndex = server.getID();

        if (inShop) {
            if (customerPosition == 0) {
                if (server.isResting()) {
                    return new WaitEvent(super.getCustomer(), super.getTime(), serverIndex);
                } else {
                    return new ServeEvent(super.getCustomer(), super.getTime(), serverIndex);
                }

            } else {
                return new WaitEvent(super.getCustomer(), super.getTime(), serverIndex);
            }
        }


        return new LeaveEvent(super.getCustomer(), super.getTime());
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
            super.getTime(), super.getCustomer().toString());
    }
}
