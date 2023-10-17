class Shop {
    private final PQ<Server> servers;
    //private final int served;
    //private final int left;

    Shop() {
        this.servers = new PQ<Server>(new ServerComp());
    }

    Shop(Server server) {
        this.servers = new PQ<Server>(new ServerComp()).add(server);
    }

    Shop(PQ<Server> servers) {
        this.servers = servers;
    }

    Server getServer() {
        return this.servers.poll().first();
    }

    boolean isEmpty() {
        return this.servers.isEmpty();
    }

    Shop removeServer() {
        return new Shop(this.servers.poll().second());
    }

    Shop addServer(Server server) {
        return new Shop(this.servers.add(server));
    }
    
    @Override
    public String toString() {
        return this.servers.toString();
    }

}
