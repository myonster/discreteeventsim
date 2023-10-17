class Server {
    private final int id;
    private final double nextTime;

    Server(int id) {
        this.id = id;
        this.nextTime = 0;
    }

    Server(int id, double nextTime) {
        this.id = id;
        this.nextTime = nextTime;
    }

    int getID() {
        return this.id;
    }

    double getTime() {
        return this.nextTime;
    }

    Server serve(double serviceTime) {
        return new Server(this.id, serviceTime);
    }

    @Override
    public String toString() {
        return String.format("%d", this.id);
    }
}