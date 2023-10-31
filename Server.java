import java.util.function.Supplier;

class Server {
    private final int id;
    private final double nextTime;
    private final Supplier<Double> restTime;

    Server(int id, Supplier<Double> restTime) {
        this.id = id;
        this.nextTime = 0;
        this.restTime = restTime;
    }

    Server(int id, double nextTime, Supplier<Double> restTime) {
        this.id = id;
        this.nextTime = nextTime;
        this.restTime = restTime;
    }

    int getID() {
        return this.id;
    }

    double getTime() {
        return this.nextTime;
    }

    Server serve(double serviceTime) {
        return new Server(this.id, serviceTime, this.restTime);
    }

    double rest() {
        return this.restTime.get();
    }

    @Override
    public String toString() {
        return String.format("%d", this.id);
    }
}