class Customer {
    private final int id;
    private final double arrivalTime;
    private final double serviceTime;
    
    Customer(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }
 
    double getArrivalTime() {
        return this.arrivalTime;
    }

    double getServiceTime() {
        return this.serviceTime;
    }

    @Override
    public String toString() {
        return String.format("%s",this.id);
    }

}
    
