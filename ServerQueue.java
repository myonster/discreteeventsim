class ServerQueue {
    private final Server server;
    private final int queueSize;
    private final int qmax;
    private final boolean isAtCounter;
    private final ImList<Double> timeList;
    private final double queueTime;
    private final boolean restTime;

    ServerQueue(Server server, int qmax) {
        this.server = server;
        this.qmax = qmax;
        this.queueSize = 0;
        this.isAtCounter = true;
        this.timeList = new ImList<Double>();
        this.queueTime = 0;
        this.restTime = false;
    }

    ServerQueue(Server server, int qmax, int queue, boolean status,
        ImList<Double> list, double qtime, boolean restTime) {
        this.server = server;
        this.qmax = qmax;
        this.queueSize = queue;
        this.isAtCounter = status;
        this.timeList = list;
        this.queueTime = qtime;
        this.restTime = restTime;
    }

    int getQueueSize() {
        return this.queueSize;
    }

    boolean canQueue() {
        return this.getQueueSize() < this.qmax;
    }
    
    double getWaitTime() {
        return this.queueTime;
    }

    ServerQueue addWaitTime(double time) {
        return new ServerQueue(this.server, this.qmax, 
            this.queueSize, false, this.timeList, this.queueTime + time, this.restTime);
    }

    ServerQueue notAtCounter() {
        return new ServerQueue(this.server, this.qmax, 
            this.queueSize, false, this.timeList, this.queueTime, this.restTime);
    }

    ServerQueue backAtCounter() {
        return new ServerQueue(this.server, this.qmax,
            this.queueSize, true, this.timeList, this.queueTime, this.restTime);
    }

    ServerQueue addToQueue() {
        return new ServerQueue(this.server, this.qmax, this.queueSize + 1,
            this.isAtCounter, this.timeList, this.queueTime, this.restTime);
    }

    ServerQueue removeFromQueue() {
        return new ServerQueue(this.server, this.qmax, this.queueSize - 1,
            this.isAtCounter, this.timeList, this.queueTime, this.restTime);
    }

    ServerQueue serve(double time) {
        return new ServerQueue(this.server.serve(time), this.qmax, this.queueSize - 1,
        this.isAtCounter, this.timeList.add(time), this.queueTime, false);
    }

    double getRest() {
        return this.getServer().rest();
    }

    ServerQueue rest(double time) {

        return new ServerQueue(this.server.serve(this.getServer().getTime() + time), 
        this.qmax, this.queueSize,
        this.isAtCounter, this.timeList.set(this.timeList.size() - 1, this.getLastTiming() + time),
        this.queueTime, true);
    }

    ServerQueue waitServeRest() {

        return new ServerQueue(this.server, 
        this.qmax, this.queueSize,
        this.isAtCounter, this.timeList,
        this.queueTime, false);
    }

    boolean isResting() {
        return this.restTime;
    }

    

    boolean isTimeListEmpty() {
        return !(this.timeList.size() > 0);
    }

    @Override
    public String toString() {
        return String.format("Server: %s | (size,max): %d,%d | waitTime: %.3f",
            this.server.toString(), this.queueSize, this.qmax, this.queueTime);
    }
}
