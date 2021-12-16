import java.util.ArrayList;

public class Vertex {
  int height;
  int id;
  ArrayList<Edge> edges;
  int e;
  boolean isSource;
  boolean isSink;

  public Vertex(int id) {
    this.height = 0;
    this.id = id;
    this.edges = new ArrayList<>();
    this.e = 0;
    this.isSource = false;
    this.isSink = false;
  }

  public void addEdge(Edge edge) {
    this.edges.add(edge);
  }

  public void updateMyE() {
    int inflow = 0;
    int outflow = 0;
    for (Edge e : edges) {
      if (e.v1 == this) {
        outflow = outflow + e.curFlow;
      } else if (e.v2 == this){
        inflow = inflow + e.curFlow;
      }
    }
    this.e = inflow - outflow;
  }

  public boolean amIOverflowing() {
    if (isSink || isSource) {
      return false;
    } else {
      return this.e > 0;
    }
  }
}
