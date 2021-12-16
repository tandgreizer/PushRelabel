import java.util.ArrayList;

public class PushRelabelListVersion {
  int amountOfVerts;
  ArrayList<Vertex> vertices;
  ArrayList<Edge> edges;

  public PushRelabelListVersion(int amountOfVerts, int[][] cap_matrix) {
    this.amountOfVerts = amountOfVerts;
    this.vertices = new ArrayList<>();
    this.edges = new ArrayList<>();
    for (int i = 0; i < amountOfVerts; i++) {
      vertices.add(new Vertex(i));
    }
    for (int i = 0; i < amountOfVerts; i++) {
      for (int j = 0; j < amountOfVerts; j++) {
        if (cap_matrix[i][j] > 0) {
          edges.add(new Edge(vertices.get(i), vertices.get(j), cap_matrix[i][j]));
        }
      }
    }
  }

  public boolean push(int u) {
    for (int i = 0; i < edges.size(); i++) {
      Edge edge = edges.get(i);
      if (edge.v1.id == u) {
        if (edge.curFlow != edge.capacity) {
          if (edge.v1.height > edge.v2.height) {
            int deltaF = Math.min(edge.capacity - edge.curFlow, edge.v1.e);
            edge.v1.e -= deltaF;
            edge.v2.e += deltaF;

            edge.curFlow += deltaF;

            this.addResidualEdge(edge, deltaF);
            return true;
          }
        }
      }
    }
    return false;
  }

  public void relabel(int u) {
    int min = Integer.MAX_VALUE;

    for (Edge edge : edges) {
      if (edge.v1.id == u) {
        if (edge.curFlow != edge.capacity) {
          if (edge.v2.height < min) {
            min = edge.v2.height;
          }
        }
      }
    }
    vertices.get(u).height = min + 1;
  }

  public int whoIsOverflowing() {
    for (int i = 0; i < vertices.size(); i++) {
      if (vertices.get(i).amIOverflowing()) {
        return i;
      }
    }
    return -1;
  }

  public void initilizePreflow(int source) {
    Vertex sourceV = vertices.get(source);
    sourceV.isSource = true;
    sourceV.height = amountOfVerts;
    ArrayList<Edge> newEdges = new ArrayList<>();

    for (Edge edge : edges) {
      if (edge.v1 == sourceV) {
        edge.curFlow = edge.capacity;
        edge.v2.e = edge.v2.e + edge.curFlow;

        Edge revEdge = new Edge(edge.v2, sourceV, 0);
        revEdge.curFlow = -edge.curFlow;
        newEdges.add(revEdge);
      }
    }
    this.edges.addAll(newEdges);
  }

  public void addResidualEdge(Edge oldEdge, int flow) {
    Vertex u = oldEdge.v2;
    Vertex v = oldEdge.v1;

    for (int i = 0; i < edges.size(); i++) {
      if (edges.get(i).v2 == v && edges.get(i).v1 == u) {
        edges.get(i).curFlow -= flow;
        return;
      }
    }
    Edge revEdge = new Edge(u, v, flow);
    edges.add(revEdge);
  }

  public int[][] outputFlows() {
    int[][] finalFlow = new int[amountOfVerts][amountOfVerts];
    for (Edge edge : edges) {
      if (edge.curFlow > 0) {
        finalFlow[edge.v1.id][edge.v2.id] = edge.curFlow;
      }
    }
    return finalFlow;
  }

  public int[][] getMaxFlow(int source, int sink) {
    vertices.get(sink).isSink = true;
    this.initilizePreflow(source);
    int[][] finalFlow = new int[amountOfVerts][amountOfVerts];

    while (this.whoIsOverflowing() != -1) {
      int u = whoIsOverflowing();
      if (!push(u)) {
        relabel(u);
      }
    }
    for (Edge edge : edges) {
      if (edge.curFlow > 0) {
        finalFlow[edge.v1.id][edge.v2.id] = edge.curFlow;
      }
    }
    return finalFlow;
  }
}
