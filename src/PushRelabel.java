public class PushRelabel {
  int[][] capacityMatrix;
  Vertex[] vertices;
  int[][] preFlowMatrix;
  int[][] addjencyMatrix;


  public void initalizePreflow(int source) {
    for (Vertex v : vertices) {
      v.height = 0;
      v.e = 0;
    }
//    for (int i = 0; i < addjencyMatrix.length; i++) {
//      for (int j = 0; j < addjencyMatrix.length; j++) {
//        if (addjencyMatrix[i][j] == 1) {
//          preFlowMatrix
//        }
//      }
//    }
    vertices[source].height = vertices.length;
    vertices[source].isSource = true;

    for (int i = 0; i < addjencyMatrix.length; i++) {
      preFlowMatrix[source][i] = capacityMatrix[source][i];
      vertices[i].e = capacityMatrix[source][i];
      vertices[source].e = vertices[source].e - capacityMatrix[source][i];
    }

  }
  public void push(Vertex u, Vertex v) {

    // Applies when: u is overflowing, cf(u,v) > 0, and u:h = v.h +1
    if (this.cf(u, v) <= 0) {
      throw new IllegalArgumentException("U is not overflowing");
    }
    if (u.height != v.height + 1) {
      throw new IllegalArgumentException("Heights are wrong");
    }
    int deltaFuV = Math.min(u.e,this.cf(u,v));

    if (addjencyMatrix[u.id][v.id] == 1) {
      preFlowMatrix[u.id][v.id] = preFlowMatrix[u.id][v.id] + deltaFuV;
    } else {
      preFlowMatrix[v.id][u.id] = preFlowMatrix[v.id][u.id] - deltaFuV;
    }
    u.e = u.e - deltaFuV;
    v.e = v.e + deltaFuV;
  }

  private int cf(Vertex u, Vertex v) {
    return this.capacityMatrix[u.id][v.id] - this.preFlowMatrix[u.id][v.id];
  }

  public void relabel(Vertex u) {
    int minHeight = Integer.MAX_VALUE;

    for (int i = 0; i < preFlowMatrix.length; i++) {
      if (preFlowMatrix[u.id][i] < capacityMatrix[u.id][i]) {
        if (minHeight > vertices[i].height) {
          minHeight = vertices[i].height;
        }
      }
    }
    u.height = minHeight + 1;
  }




}
