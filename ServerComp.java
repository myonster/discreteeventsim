import java.util.Comparator;

class ServerComp implements Comparator<Server> {
    
    @Override
    public int compare(Server s1, Server s2) {
        if (s1.getTime() > s2.getTime()) {
            return 1;
        } else if (s1.getTime() == s2.getTime()) {
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
