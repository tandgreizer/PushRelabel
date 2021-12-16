import java.util.Arrays;

public class PushRelabel {

  int[][] capacityMatrix;
  Vertex[] vertices;
  int[][] preFlowMatrix;
  int[][] addjencyMatrix;

  public PushRelabel(int[][] capacityMatrix, int verticesAmt) {
    this.capacityMatrix = capacityMatrix;
    this.vertices = new Vertex[verticesAmt];
    this.addjencyMatrix = new int[capacityMatrix.length][capacityMatrix.length];
    this.preFlowMatrix = new int[capacityMatrix.length][capacityMatrix.length];
    for (int i = 0; i < verticesAmt; i++) {
      vertices[i] = new Vertex(i);
    }
    for (int i = 0; i < addjencyMatrix.length; i++) {
      for (int j = 0; j < addjencyMatrix.length; j++) {
        if (capacityMatrix[i][j] > 0) {
          addjencyMatrix[i][j] = 1;
        }
      }
    }
  }


  public void initalizePreflow(int source, int sink) {
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
    vertices[sink].isSink = true;

    for (int i = 0; i < addjencyMatrix.length; i++) {
      preFlowMatrix[source][i] = capacityMatrix[source][i];
      vertices[i].e = capacityMatrix[source][i];
      vertices[source].e = vertices[source].e - capacityMatrix[source][i];
    }

  }

  public boolean push(int u) {
    for (int i = 0; i < addjencyMatrix.length; i++) {
      try {
        if (addjencyMatrix[u][i] == 1 || addjencyMatrix[i][u] == 1) {

          push(vertices[u], vertices[i]);
          return true;
        }
      } catch (IllegalArgumentException e) {
        //do nothing we good
      }
    }
    return false;
  }

  public void push(Vertex u, Vertex v) {

    // Applies when: u is overflowing, cf(u,v) > 0, and u:h = v.h +1
    if (this.cf(u, v) <= 0 && preFlowMatrix[v.id][u.id] <= 0) {

      throw new IllegalArgumentException("U is not overflowing");
    }
//    if (u.e <= 0) {
//      throw new IllegalArgumentException("U is not overflowing");
//   }
    if (u.height != v.height + 1) {
      throw new IllegalArgumentException("Heights are wrong");
    }
    //who is smaller amount i can send, or amount i need to send
    int deltaFuV = Math.min(u.e, this.cf(u, v));

    //if v is sending into me instead. set delta to who much i need to send back
    if (preFlowMatrix[v.id][u.id] > 0) {
      deltaFuV = u.e;
    }

    //If u send to v its gonna be a positive send.
    if (addjencyMatrix[u.id][v.id] == 1) {
      preFlowMatrix[u.id][v.id] = preFlowMatrix[u.id][v.id] + deltaFuV;
    } else {
      //if v send to u it means u is sending back
      preFlowMatrix[v.id][u.id] = preFlowMatrix[v.id][u.id] - deltaFuV;
    }
    //update excess amounts
    u.e = u.e - deltaFuV;
    v.e = v.e + deltaFuV;
  }

  private int cf(Vertex u, Vertex v) {
    return this.capacityMatrix[u.id][v.id] - this.preFlowMatrix[u.id][v.id];
  }

  public void relabel(Vertex u) {
    int minHeight = Integer.MAX_VALUE;

    //look to see if there are any u can still send to
    for (int i = 0; i < preFlowMatrix.length; i++) {
      if (preFlowMatrix[u.id][i] < capacityMatrix[u.id][i]) {
        if (minHeight > vertices[i].height) {
          minHeight = vertices[i].height;
        }
      }
    }
    //look to see if there are are any u needs to send back to
    for (int i = 0; i < preFlowMatrix.length; i++) {
      if (preFlowMatrix[i][u.id] > 0) {
        if (minHeight > vertices[i].height) {
          minHeight = vertices[i].height;
        }
      }
    }
    u.height = minHeight + 1;
  }

  private int whoIsOverflowing() {

    for (int i = 0; i < vertices.length; i++) {
      if (vertices[i].amIOverflowing()) {
        return i;
      }
    }
    return -1;
  }

  public String heights() {
    StringBuilder builder = new StringBuilder();
    for (Vertex v : vertices) {
      builder.append(v.height).append(" ");
    }
    return builder.toString();
  }

  public int[][] maxFlow(int source, int sink) {
    this.initalizePreflow(source, sink);


    //if someone is overflowing the preflow isnt valid so fix
    while (whoIsOverflowing() != -1) {
      int u = this.whoIsOverflowing();
      //if i cant push the excess of u
      if (!push(u)) {
        //then raise u so it can be pushed
        relabel(vertices[u]);
      }

    }
    return preFlowMatrix;
  }


}
