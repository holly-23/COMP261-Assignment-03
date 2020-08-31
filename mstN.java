public class mstN implements Comparable<mstN> {
    private Node start;
    private Node end;
    private double weight;
    private Segment conComponent;


    public mstN (Node start, Node end, double weight, Segment conComponent){
        this.start = start;
        this.end = end;
        this.weight = weight;
        this.conComponent = conComponent;
    }
    public Node getStart(){
        return start;
    }
    public Node getEnd(){
        return end;
    }
    public double getWeight(){
        return weight;
    }
    public Segment getConComponent(){
        return conComponent;
    }

    @Override
    public int compareTo(mstN o) {
        if (this.weight < o.weight) {
            return -1;
        } else if (this.weight > o.weight) {
            return 1;
        } else {
            return 0;
        }
    }
}
