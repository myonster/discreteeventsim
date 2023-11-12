import java.util.function.Supplier;

class Server {
    private final int id;
    private final double nextTime;
    private final boolean isServing;
    private final boolean isResting;
    private final Supplier<Double> restTime;
    private final boolean human;

    Server(int id, Supplier<Double> restTime) {
        this.id = id;
        this.nextTime = 0;
        this.isServing = false;
        this.isResting = false;
        this.restTime = restTime;
        this.human = true;
    }

    Server(int id, double nextTime, boolean isServing, boolean isResting, Supplier<Double> rt) {
        this.id = id;
        this.nextTime = nextTime;
        this.isServing = isServing;
        this.isResting = isResting;
        this.restTime = rt;
        this.human = true;
    }

    //Constructor for AI
    Server(int id, Supplier<Double> restTime, boolean ai) {
        this.id = id;
        this.nextTime = 0;
        this.isServing = false;
        this.isResting = false;
        this.restTime = restTime;
        this.human = ai;
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
        if (!this.human) {
            return String.format("self-check %s", this.id);
        }

        return String.format("%s", this.id);
    }
}
