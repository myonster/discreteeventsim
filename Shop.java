class Shop {
    private final ImList<ServerQueue> serverQueueShop;

    Shop(int numOfServers, int qmax) {
        ImList<ServerQueue> list = new ImList<ServerQueue>();
        for (int i = 1; i <= numOfServers; i++) {
            list = list.add(new ServerQueue(new Server(i), qmax));
        }
        this.serverQueueShop = list;
    }

    Shop(ImList<ServerQueue> serverQueueList) {
        this.serverQueueShop = serverQueueList;
    }

    ServerQueue getServerQueueByID(int id) {
        return this.serverQueueShop.get(id - 1);
    }

    //this will update the serverQueue in the shop
    Shop updateServerQueueInShop(ServerQueue serverQueue) {
        ImList<ServerQueue> list = this.serverQueueShop;

        return new Shop(list.set(serverQueue.getServer().getID() - 1, serverQueue));
    }

    //if they are busy at all
    boolean canServe() {
        for (ServerQueue sq : this.serverQueueShop) {
            if (sq.isAtCounter() || sq.canQueue()) {
                return true;
            }
        }
        return false;
    }

    // finds the best sq from 1 to n
    // first search is if they can serve
    // second search is if they have a full queue
    ServerQueue getServerQueue() {
        for (ServerQueue sq : this.serverQueueShop) {
            if (sq.isAtCounter()) {
                return sq;
            }
        }

        for (ServerQueue sq : this.serverQueueShop) {
            if (sq.canQueue()) {
                return sq;
            }
        }

        return this.getServerQueueByID(1);

    }

    ImList<ServerQueue> getList() {
        return this.serverQueueShop;
    }

    @Override
    public String toString() {
        return this.serverQueueShop.toString();
    }
}
