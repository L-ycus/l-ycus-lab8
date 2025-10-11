
import java.util.ArrayList;

public class TransferStation extends Station{
    protected ArrayList<Station> otherStations;

    public TransferStation(String c, String n) {
        super(c, n);
        otherStations = new ArrayList<Station>();
    }

    public String toString() {
        String p,n;
        if(this.next == null) {
            n = "none";
        } else n = next.getName();
        
        if(this.prev == null) {
            p = "none";
        }else p = prev.getName();
        
        String ret = "TRANSFERSTATION " + name + ": " + color + " line, in service: " + isAvailable() + ", previous station: " + p + ", next station: " + n + "\n\tTransfers: \n"; 
        for(int i = 0; i < otherStations.size(); i ++) {
            ret = ret + "\t" + otherStations.get(i).toString() + "\n";
        }    
        
        return ret;
    }

    public void addTransferStationNext(Station s) {
        otherStations.add(s);
        s.prev = this;
    }

    public void addTransferStationPrev(Station s) {
        otherStations.add(s);
        s.next = this;
    }

    public void connect(Station s) {
        if(this.next == null) {
            this.addNext(s);
            return;
        }
        this.addTransferStationNext(s);
        //s.addPrev(this);
    }
}
