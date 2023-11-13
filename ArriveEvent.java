class ArriveEvent extends Event {
    
    ArriveEvent(Customer customer) {
        super(customer, customer.getArrivalTime());
    }
    

    @Override
    public ImList<QueueSystem> updateShop(ImList<QueueSystem> shop) {
        ImList<QueueSystem> tempShop = shop;//new ImList<QueueSystem>();

        // Customer that arrives will look at shop
        // Looks at which is empty from 1 -> n (Regardeless of if Server is Resting)
        int index = 0;
        for (QueueSystem sq: shop) {
            if (sq.isEmpty()) {
                int serverIndex = index;

                //This is where we slot in our Customer into QueueSystem
                //System.out.println("is empty");

                tempShop = tempShop.set(serverIndex, sq.addToQueue(super.getCustomer()));
                return tempShop;

            }
            index++;
        }

        //logic for this is if all humans resting/serving we push customers
        //to self-check
        // index = 0;
        // for (QueueSystem sq: shop) {
            
        //     if (sq.ableToServe() && sq.isAutomated()) {
        //         int serverIndex = index;
        //         //This is where we slot in our Customer into QueueSystem

        //         tempShop = tempShop.set(serverIndex, sq.addToQueue(super.getCustomer()));
        //         return tempShop;
        //     }
        //     index++;
        // }
        // }

        // Looks at which one can serve? (Regardeless of if Server is Resting)
        //boolean ableToServe = false;
        index = 0;
        for (QueueSystem sq: shop) {
            
            if (sq.ableToServe()) {
                int serverIndex = index;

                //This is where we slot in our Customer into QueueSystem

                tempShop = tempShop.set(serverIndex, sq.addToQueue(super.getCustomer()));
                return tempShop;
            }
            index++;
        }
        // System.out.println("we left");
        // when we do this -> essentially customer is not added in!
        return shop;
    }

    @Override
    public Event nextEvent(ImList<QueueSystem> shop) {
        int customerID = super.getCustomer().getID();
        int customerPosition = 0;
        int serverIndex = 0;
        //QueueSystem servingSQ = shop.get(0);
        boolean inShop = false;
        boolean selfcheck = false;

        // Searching where our customer is
        for (QueueSystem sq: shop) {
            ImList<Customer> customerQueue = sq.getQueue();
            
            if (sq.isAutomated()) {
                selfcheck = true;
            }

            for (Customer customer: customerQueue) {
                if (customerID == customer.getID()) {
                    inShop = true;
                    customerPosition = customerQueue.indexOf(customer); // 0 if its at front
                    break;
                }
            }

            if (inShop) {
                break;
            }
            serverIndex++;
        }

        //logic for self-check arrival
        if (selfcheck) {
            QueueSystem queueSys = shop.get(serverIndex);

            // [c1, c2, c3 ...]
            // serve immediate
            if (queueSys.isEmpty()) {
                return new ServeEvent(super.getCustomer(), super.getTime(),
                serverIndex + 1, queueSys);
            } else {
                return new WaitEvent(super.getCustomer(), super.getTime(),
                serverIndex + 1, queueSys);
            }
        }

        if (inShop) {
            if (customerPosition == 0) {
                if (shop.get(serverIndex).getServer(super.getCustomer().getID())
                    .isResting()) {
                    return new WaitEvent(super.getCustomer(), super.getTime(), 
                    serverIndex + 1, shop.get(serverIndex)); // we +1 because this the id
                } else {
                    return new ServeEvent(super.getCustomer(), super.getTime(), 
                    serverIndex + 1, shop.get(serverIndex));
                }

            } else {
                return new WaitEvent(super.getCustomer(), super.getTime(), 
                serverIndex + 1, shop.get(serverIndex));
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
