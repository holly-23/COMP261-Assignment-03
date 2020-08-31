import java.awt.*;
import java.io.File;
import java.util.*;

/**
 * This represents the data structure storing all the roads, nodes, and
 * segments, as well as some information on which nodes and segments should be
 * highlighted.
 * 
 * @author tony
 */
public class Graph {
	// map node IDs to Nodes.
	Map<Integer, Node> nodes = new HashMap<>();
	// map road IDs to Roads.
	Map<Integer, Road> roads;
	// just some collection of Segments.
	Collection<Segment> segments;

	Node highlightedNode;
	Collection<Road> highlightedRoads = new HashSet<>();
	Collection<Node> highlightedAP = new HashSet<>();
	Collection<Segment> highlightedMstSegs = new HashSet<>();

	public Graph(File nodes, File roads, File segments, File polygons) {
		this.nodes = Parser.parseNodes(nodes, this);
		this.roads = Parser.parseRoads(roads, this);
		this.segments = Parser.parseSegments(segments, this);
		setNeighbour();
	}

	public void draw(Graphics g, Dimension screen, Location origin, double scale) {
		// a compatibility wart on swing is that it has to give out Graphics
		// objects, but Graphics2D objects are nicer to work with. Luckily
		// they're a subclass, and swing always gives them out anyway, so we can
		// just do this.
		Graphics2D g2 = (Graphics2D) g;

		// draw all the segments.
		g2.setColor(Mapper.SEGMENT_COLOUR);
		for (Segment s : segments)
			s.draw(g2, origin, scale);

		// draw the segments of all highlighted roads.
		g2.setColor(Mapper.HIGHLIGHT_COLOUR);
		g2.setStroke(new BasicStroke(3));
		for (Road road : highlightedRoads) {
			for (Segment seg : road.components) {
				seg.draw(g2, origin, scale);
			}
		}

		for(Segment seg: segments) {
			if (highlightedMstSegs.contains(seg)) {
				g2.setColor(Color.YELLOW);
				seg.draw(g2, origin, scale);
			}
		}

		// draw all the nodes.
		g2.setColor(Mapper.NODE_COLOUR);
		for (Node n : nodes.values())
			n.draw(g2, screen, origin, scale);

		// draw the highlighted node, if it exists.
		if (highlightedNode != null) {
			g2.setColor(Mapper.HIGHLIGHT_COLOUR);
			highlightedNode.draw(g2, screen, origin, scale);
		}
		for(Node node: nodes.values()) {
			if (highlightedAP.contains(node)) {
				g2.setColor(Color.GREEN);
				node.draw(g2, screen, origin, scale);
			}
		}
	}

	public void setHighlight(Node node) {
		this.highlightedNode = node;
	}


	public void setNeighbour(){
		for(Node node : nodes.values()){
			for(Segment segment : node.segments){
				if(node != segment.start) {
					node.neighbours.add(segment.start);
				}
				else{
					node.neighbours.add(segment.end);
				}
			}
		}
	}

	public void setHighlight(Collection<Road> roads) {
		this.highlightedRoads = roads;
	}
	public void setHighlightedAP(Set<Node> highlightedAP) {
		this.highlightedAP.addAll(highlightedAP);
	}
	public void setHighlightedMstSegs(Set<Segment> highlightedMstSegs){
		this.highlightedMstSegs.addAll(highlightedMstSegs);
	}
	public void unhighlight(){
		highlightedAP.clear();
		highlightedMstSegs.clear();
	}
}

// code for COMP261 assignments