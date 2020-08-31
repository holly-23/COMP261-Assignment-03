import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArticulationPoints {

    private Node nodes;
    private int reach;
    private Node prev;
    private int depth;
    public List<Node> visited = new ArrayList<>();
    private List<Node> children;

    public ArticulationPoints(Node node, int depth, Node parent){
        this.nodes = node;
        this.depth = depth;
        this.prev = parent;

    }
    public int getDepth(){
        return depth;
    }

    public Node getNode(){
        return nodes;
    }
    public Node getParent(){
        return prev;
    }
    public Node getChild(){
        return children.get(0);
    }
}
