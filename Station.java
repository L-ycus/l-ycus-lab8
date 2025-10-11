import java.util.ArrayList;

public class Station {
    protected String name;
    protected String color;
    protected boolean isAvailable;
    protected Station prev;
    protected Station next;
    protected boolean visited;
    private ArrayList<Station> visitList;

    public Station(String c, String n) {
        color = c;
        name = n;
        isAvailable = true;
        visited = false;
        visitList = new ArrayList<Station>();
    }

    public String toString() {
        String p, n;
        if(prev == null) {
            p = "none";
        }
        else p = prev.getName();
        if(next == null) {
            n = "none";
        } else n = next.getName();

        return "STATION " + name + ": " + color + " line, in service: " + isAvailable + ", previous station: " + p + ", next station: " + n;
    }

    public void addNext(Station n) {
        this.next = n;
        n.prev = this;
    } 

    public void addPrev(Station p) {
        this.prev = p;
        p.next = this;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void switchAvailable() {
        this.isAvailable = !isAvailable;
    } 

    public boolean equals(Station s) {
        return s.getName().equals(this.name) && s.getColor().equals(this.color);
    }

    public String getName() {
        return name;
    }
    public String getColor() {
        return color;
    }

    public void connect(Station s) {
        if(s.getClass().equals(EndStation.class)) {
            this.addNext(s);
            s.next=this;
            return;
        }
        this.next = s;
        if(s.getClass().equals(TransferStation.class)) {
            if(s.prev == null) {
                System.out.println("transfer station prev not full");
                s.addPrev(this);
                return;
            }
            System.out.println("transfer station prev already full" + s.getName());
            TransferStation s2 = (TransferStation) s;
            s2.addTransferStationPrev(this);
            return;
        }
        this.addNext(s);
    }

    public int tripLength(Station s) {
        return helper(this, s, 0);
    }

    private int helper(Station curr, Station dest, int length) {
        if(curr.equals(dest)) {
            //System.out.println("CALLING RESET");
            reset();
            return length;
        }
        if(curr.visited) {
            return -1; //will this affect trip length?
        }

        curr.visited = true;
        
        //System.out.println("NO TRANSFER: " + curr.getName()+ " " + dest.getName());
        if(curr.getClass().equals(TransferStation.class)) {
            //System.out.println("IS TRANSFERR: " + curr.getName());
            TransferStation temp = (TransferStation)  curr;
            for(int i = 0; i < temp.otherStations.size(); i++) {
                //System.out.println("Going through transfer station's other stations" + temp.otherStations.get(i).getName() + " " + dest.getName());
                //System.out.println("curr: " + curr.getName());
                visitList.add(curr);
                int ret = helper(temp.otherStations.get(i), dest, length+1);
                if(ret != -1) {
                    return ret;
                }
            }
        }
        if(curr.next.visited == false) {
            visitList.add(curr);
            return helper(curr.next, dest, length+1);
        }
        //System.out.println(curr.getName() + " " + dest.getName());
            
        visitList.add(curr);
        return -1;
    }

    private void reset() {
        for(int i = 0; i < visitList.size(); i++) {
            //System.out.println("FReeing: " + visitList.get(i).getName());
            visitList.get(i).visited = false;
        }
    }
}

