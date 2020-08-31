import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class findAP {

    Set<Node> articulationPts = new HashSet<>();
    Set<Node> unvisited = new HashSet<>();
    private Graph graph = Mapper.graph;

    public Set<Node> findArt() {
        reset();
        findUnvisitedNodes();
        while (!unvisited.isEmpty()) {
            Node root = unvisited.iterator().next();
            int numOfSubTrees = 0;
            root.depth = 0;
            //System.out.println(root.neighbours);
            for (Node n : root.neighbours) {
                if (n.depth == -1) {
                    findAPIteration(n, 1, root);
                    numOfSubTrees++;
                }
                if (numOfSubTrees > 1) {
                    articulationPts.add(root);
                }
            }
            unvisited.remove(root);
        }
        return articulationPts;
    }
    private void findUnvisitedNodes(){
        for(Node n : graph.nodes.values()){
            if(n.depth == -1){
                unvisited.add(n);
            }
        }
    }

    private void findAPIteration(Node firstNode, int depth, Node root) {

        Stack<ArticulationPoints> articulationPointsStack = new Stack<>();
        ArticulationPoints first = new ArticulationPoints(firstNode, depth, root);
        articulationPointsStack.push(first);
        while (!articulationPointsStack.isEmpty()) {
            ArticulationPoints current = articulationPointsStack.peek();
            int currentDepth = current.getDepth();
            Node currentNode = current.getNode();
            Node currentParent = current.getParent();
            unvisited.remove(currentNode);

            if (currentNode.depth == -1) { //make sure node has not been visited before
                currentNode.depth = currentDepth;
                currentNode.reach = currentDepth;
                for(Node neighbour : currentNode.neighbours) {
                    currentNode.children.add(neighbour); //add neighbours
                    unvisited.remove(neighbour);
                }
            } else if (!currentNode.children.isEmpty()) {
                Node child = currentNode.getChild();
                currentNode.children.remove(0);
                if (child.depth != -1) {
                    currentNode.reach = Math.min(currentNode.reach, child.depth);
                    unvisited.remove(child);
                }else {
                    articulationPointsStack.push(new ArticulationPoints(child,currentDepth + 1, currentNode)); //add child to stack
                }

            } else {
                //check node is not first in stack
                if (currentNode != firstNode) {
                    currentParent.reach = Math.min(currentNode.reach, currentParent.reach);
                    unvisited.remove(currentNode);
                    if (currentNode.reach >= currentParent.depth) {
                        articulationPts.add(currentParent);
                    }
                }
                articulationPointsStack.remove(current);
            }
        }
    }
    public void reset(){
        articulationPts.clear();
        unvisited.clear();
        graph.highlightedAP.clear();
        for (Node node : graph.nodes.values()){
            node.reach = -1;
            node.depth = -1;
            node.children.clear();
        }
    }
}
