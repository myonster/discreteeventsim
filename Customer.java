import java.util.function.Supplier;

class Customer {
    private final int id;
    private final double arrivalTime;
    private final Supplier<Double> serviceTime;
    
    Customer(int id, double arrivalTime, Supplier<Double> serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }
 
    double getArrivalTime() {
        return this.arrivalTime;
    }

    double getServiceTime() {
        return this.serviceTime.get();
    }

    @Override
    public String toString() {
        return String.format("%s",this.id);
    }

}
    
