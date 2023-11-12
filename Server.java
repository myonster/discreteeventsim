import java.util.function.Supplier;

class Server {
    private final int id;
    private final double nextTime;
    private final boolean isServing;
    private final boolean isResting;
    private final Supplier<Double> restTime;

    Server(int id, Supplier<Double> restTime) {
        this.id = id;
        this.nextTime = 0;
        this.isServing = false;
        this.isResting = false;
        this.restTime = restTime;
    }

    Server(int id, double nextTime, boolean isServing, boolean isResting, Supplier<Double> restTime) {
        this.id = id;
        this.nextTime = nextTime;
        this.isServing = isServing;
        this.isResting = isResting;
        this.restTime = restTime;
    }

    int getID() {
        return this.id;
    }

    double getNextTime() {
        return this.nextTime;
    }

    double getRestTime() {
        return this.restTime.get();
    }

    boolean isServing() {
        return this.isServing;
    }

    boolean isResting() {
        return this.isResting;
    }

    Server addTime(double time) {
        return new Server(this.id, time, this.isServing(), this.isResting(), this.restTime);
    }

    Server updateServingStatus(boolean status) {
        return new Server(this.id, this.nextTime, status, this.isResting(), this.restTime);
    }

    Server updateRestingStatus(boolean status) {
        return new Server(this.id, this.nextTime, this.isServing(), status, this.restTime);
    }

    @Override
    public String toString() {
        return String.format("%s", this.id);
    }
}
