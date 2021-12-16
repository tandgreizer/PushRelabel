public class Edge {
  Vertex v1;
  Vertex v2;
  int capacity;
  int curFlow;


  public Edge(Vertex v1, Vertex v2, int capacity) {
    this.v1 = v1;
    this.v2 = v2;
    this.capacity = capacity;
    this.curFlow = 0;
    this.v1.addEdge(this);
    this.v2.addEdge(this);
  }

  public void setCurFlow(int curFlow) {
    this.curFlow = curFlow;
  }
}
