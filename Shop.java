class Shop {
    private final ImList<ServerQueue> listOfServersWithQueue;
    
    Shop(ImList<ServerQueue> listOfServers) {
        this.listOfServersWithQueue = listOfServers;
    }

    //updates a particular ServerQueue
    Shop update(ServerQueue ServerWithQueue) {
        Server server = ServerWithQueue.getServer();
        int serverIndex = server.getID();
        ImList<ServerQueue> temp = this.listOfServersWithQueue;

        temp = temp.set(serverIndex, ServerWithQueue);

        return new Shop(temp);
    }

}