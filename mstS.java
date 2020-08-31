import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class mstS {
    public Set<Node> forest;
    Set<Segment> tree = new HashSet<>();
    Graph graph = Mapper.graph;


    public Set kruskalsAlgorithm(){
        graph.unhighlight();
        forest = new HashSet<>();
        PriorityQueue<mstN> currentPQ = new PriorityQueue<mstN>();
        //store nodes in a tree
        for(Node node : graph.nodes.values()){
            makeSet(node);
        }
        for(Segment segment : graph.segments){
            Node node1 = segment.start;
            Node node2 = segment.end;
            double currentLen = segment.length;
            mstN currentNode = new mstN(node1, node2, currentLen, segment);

            currentPQ.add(currentNode);
        }
        while(!currentPQ.isEmpty()){
            mstN currentNode = currentPQ.poll();
            Node currentNodeStart = currentNode.getStart();
            Node currentNodeEnd = currentNode.getEnd();
            Node rootNodeStart = find(currentNodeStart);
            Node rootNodeEnd = find(currentNodeEnd);
            //check if in different trees
            if(rootNodeStart != rootNodeEnd){
                union(currentNodeStart, currentNodeEnd);
                tree.add(currentNode.getConComponent());
            }
        }
        graph.setHighlightedMstSegs(tree);
        return tree;
    }

    private void makeSet(Node node){
        node.parent = node;
        node.depth = 0;
        forest.add(node);
    }

    private Node find(Node node){
        Node parentNode = node.parent;
        if(parentNode == node){
            return node;
        }
        else{
            Node rootNode = find(parentNode);       //find root node
            return rootNode;
        }
    }

    private void union(Node nodeX, Node nodeY){
        Node xRoot = find(nodeX);
        Node yRoot = find(nodeY);

        if(xRoot == yRoot){
            return;     // does nothing if part of same set
        }else if(xRoot.depth < yRoot.depth){
            xRoot.parent = yRoot;
            forest.remove(xRoot);
        }else{
            yRoot.parent = xRoot;
            forest.remove(yRoot);

            if(xRoot.depth == yRoot.depth){
                xRoot.depth = xRoot.depth + 1;
            }
        }

    }

}
