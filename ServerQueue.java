class ServerQueue {
    private final Server server;
    private final int queueSize;
    private final int qmax;
    private final boolean isAtCounter;
    private final ImList<Double> timeList;

    ServerQueue(Server server, int qmax) {
        this.server = server;
        this.qmax = qmax;
        this.queueSize = 0;
        this.isAtCounter = true;
        this.timeList = new ImList<Double>();
    }

    ServerQueue(Server server, int qmax, int queue, boolean status, ImList<Double> list) {
        this.server = server;
        this.qmax = qmax;
        this.queueSize = queue;
        this.isAtCounter = status;
        this.timeList = list;
    }

    int getQueueSize() {
        return this.queueSize;
    }

    boolean canQueue() {
        return this.getQueueSize() < this.qmax;
    }

    boolean isAtCounter() {
        return this.isAtCounter;
    }

    double getLastTiming() {
        return this.timeList.get(this.timeList.size() - 2);
    }

    Server getServer() {
        return this.server;
    }

    ServerQueue addQueueTimeList(double serviceTime) {
        ImList<Double> list = this.timeList;

        if (list.size() == 0) {
            list = list.add(this.server.getTime());
            list = list.add(this.server.getTime() + serviceTime);
        } else {
            list = list.add(this.timeList.get(this.timeList.size() - 1) + serviceTime);
        }

        return new ServerQueue(this.server, this.qmax,
            this.queueSize, this.isAtCounter, list);
    }

    ServerQueue notAtCounter() {
        return new ServerQueue(this.server, this.qmax, 
            this.queueSize, false, this.timeList);
    }

    ServerQueue backAtCounter() {
        return new ServerQueue(this.server, this.qmax,
            this.queueSize, true, this.timeList);
    }

    ServerQueue addToQueue() {
        return new ServerQueue(this.server, this.qmax, this.queueSize + 1,
            this.isAtCounter, this.timeList);
    }

    ServerQueue removeFromQueue() {
        return new ServerQueue(this.server, this.qmax, this.queueSize - 1,
            this.isAtCounter, this.timeList);
    }

    ServerQueue serve(double time) {
        return new ServerQueue(this.server.serve(time), this.qmax, this.queueSize,
        this.isAtCounter, this.timeList);
    }

    @Override
    public String toString() {
        return String.format("Server: %s | (size,max): %d,%d | counter: %b",
            this.server.toString(), this.queueSize, this.qmax, this.isAtCounter);
    }
}
