
public class BestPath {

	private int source;
	private int destination;
	private int source1;
	private int destination1;
	private int distance;
	
	public BestPath(int source, int destination, int source1, int destination1, int distance ){
		this.source= source;
		this.destination=destination;
		this.source1= source1;
		this.destination1=destination1;
		this.distance= distance;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public int getSource1() {
		return source1;
	}

	public void setSource1(int source1) {
		this.source1 = source1;
	}

	public int getDestination1() {
		return destination1;
	}

	public void setDestination1(int destination1) {
		this.destination1 = destination1;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	}
