
public class EndStation extends Station{

    public EndStation(String c, String n) {
        super(c, n);
    }

    public String toString() {
        String n, p;
        if(this.next == null) {
            n = "none";
        } else n = next.getName();
        
        if(this.prev == null) {
            p = "none";
        }else p = prev.getName();
        
        return "ENDSTATION " + name + ": " + color + " line, in service: " + isAvailable + ", previous station: " + p + ", next station: " + n;
    }

    public void makeEnd() {
        if(this.next != null) {
            prev = this.next;
            return;
        }
        if(this.prev != null) {
            next = this.prev;
            return;
        }
    }

    public void connect(Station s) {
        this.addNext(s);
        this.addPrev(s);
        s.addPrev(this);
    }
}