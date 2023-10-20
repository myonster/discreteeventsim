import java.util.Comparator;

class ServerComp implements Comparator<ServerQueue> {
    
    @Override
    public int compare(ServerQueue sq1, ServerQueue sq2) {
        Server s1 = sq1.getServer();
        Server s2 = sq2.getServer();

        if (sq1.getQueueSize() > sq2.getQueueSize()) {
            return 1;
        } else if (sq1.getQueueSize() == sq2.getQueueSize()) {
            if (s1.getID() > s2.getID()) {
                return 1;
            } 
            else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
