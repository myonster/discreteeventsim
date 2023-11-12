class Server {
    private final int id;
    private final double nextTime;
    private final boolean isServing;
    private final boolean isResting;

    Server(int id) {
        this.id = id;
        this.nextTime = 0;
        this.isServing = false;
        this.isResting = false;
    }

    Server(int id, double nextTime, boolean isServing, boolean isResting) {
        this.id = id;
        this.nextTime = nextTime;
        this.isServing = isServing;
        this.isResting = isResting;
    }

    int getID() {
        return this.id;
    }

    double getNextTime() {
        return this.nextTime;
    }

    boolean isServing() {
        return this.isServing;
    }

    boolean isResting() {
        return this.isResting;
    }

    Server addTime(double time) {
        return new Server(this.id, this.nextTime + time, this.isServing(), this.isResting());
    }



    @Override
    public String toString() {
        return String.format("%s", this.id);
    }
}
