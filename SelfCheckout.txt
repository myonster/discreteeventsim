import java.util.function.Supplier;

class SelfCheckout extends Server {
    private final int head;

    SelfCheckout(int id, Supplier<Double> restTime, int head) {
        super(id, restTime, false);
        this.head = head;
    }

    SelfCheckout(int id, double nextTime, boolean isServing, boolean isResting,
        Supplier<Double> rt, boolean ai, int head) {
        super(id, nextTime, isServing, false, rt, ai);
        this.head = head;
    }

    @Override
    public double getRestTime() {
        return 0.0;
    }

    @Override
    boolean isResting() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("self-check %s", super.getID());
    }

}
