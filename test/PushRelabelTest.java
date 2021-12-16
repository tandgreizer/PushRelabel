import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PushRelabelTest {

  PushRelabel p1;
  PushRelabelListVersion p2;
  PushRelabel p3;

  @BeforeEach
  void setUp() {
    int[][] capMat = {
        {0, 16, 13, 0, 0, 0},
        {0, 0, 0, 12, 0, 0},
        {0, 4, 0, 0, 14, 0},
        {0, 0, 9, 0, 0, 20},
        {0, 0, 0, 7, 0, 4},
        {0, 0, 0, 0, 0, 0}
    };
    int[][] capMat2 = {
        {0, 10, 8, 0, 0, 0},
        {0, 0, 2, 5, 0, 0},
        {0, 0, 0, 0, 10, 0},
        {0, 0, 0, 0, 0, 7},
        {0, 0, 0, 8, 0, 10},
        {0, 0, 0, 0, 0, 0}
    };
    p1 = new PushRelabel(capMat, 6);
    p2 = new PushRelabelListVersion(6, capMat);
    p3 = new PushRelabel(capMat2, 6);
  }

  // @Test
//  void maxFlow() {
//    int[][] capMat = {
//        {0,16,13,0,0,0},
//        {0,0,0,12,0,0},
//        {0,4,0,0,14,0},
//        {0,0,9,0,0,20},
//        {0,0,0,7,0,4},
//        {0,0,0,0,0,0}
//    };
//    //assertEquals("", p1.maxFlow(0,5));
//    //p1.maxFlow(0,5);
//    p1.initalizePreflow(0, 5);
//    assertEquals("[[0, 16, 13, 0, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0]]", Arrays.deepToString(p1.preFlowMatrix));
//    p1.relabel(p1.vertices[1]);
//    assertEquals("6 1 0 0 0 0 ", p1.heights());
//    assertTrue(p1.push(1));
//    assertEquals("[[0, 16, 13, 0, 0, 0], "
//        + "[0, 0, 0, 12, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0]]", Arrays.deepToString(p1.preFlowMatrix));
//    assertEquals(4,p1.vertices[1].e);
//    p1.relabel(p1.vertices[1]);
//    assertEquals("6 7 0 0 0 0 ", p1.heights());
//    assertEquals("[[0, 16, 13, 0, 0, 0], "
//        + "[0, 0, 0, 12, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0]]", Arrays.deepToString(p1.preFlowMatrix));
//    assertTrue(p1.push(1));
//    assertEquals(0,p1.vertices[1].e);
//    assertEquals("[[0, 12, 13, 0, 0, 0], "
//        + "[0, 0, 0, 12, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0], "
//        + "[0, 0, 0, 0, 0, 0]]", Arrays.deepToString(p1.preFlowMatrix));
//  }

//  @Test
//  void maxFlow2() {
//    assertEquals(
//        "[[0, 12, 11, 0, 0, 0],"
//        + " [0, 0, 0, 12, 0, 0], "
//        + "[0, 0, 0, 0, 11, 0], "
//        + "[0, 0, 0, 0, 0, 19], "
//        + "[0, 0, 0, 7, 0, 4], "
//        + "[0, 0, 0, 0, 0, 0]]", Arrays.deepToString(p2.getMaxFlow(0, 5)));
//  }
  @Test
  void maxFlow1() {
    assertEquals(
        "[[0, 12, 11, 0, 0, 0],"
            + " [0, 0, 0, 12, 0, 0], "
            + "[0, 0, 0, 0, 11, 0], "
            + "[0, 0, 0, 0, 0, 19], "
            + "[0, 0, 0, 7, 0, 4], "
            + "[0, 0, 0, 0, 0, 0]]", Arrays.deepToString(p1.maxFlow(0, 5)));
  }
  @Test
  void maxFlow3() {
    assertEquals(
        "[[0, 7, 8, 0, 0, 0],"
            + " [0, 0, 2, 5, 0, 0],"
            + " [0, 0, 0, 0, 10, 0],"
            + " [0, 0, 0, 0, 0, 5],"
            + " [0, 0, 0, 0, 0, 10],"
            + " [0, 0, 0, 0, 0, 0]]", Arrays.deepToString(p3.maxFlow(0, 5)));
  }

  @Test
  void incrememtedTest() {
    System.out.println(p2.edges.size());
    p2.initilizePreflow(0);
    System.out.println(p2.edges.size());
    p2.vertices.get(5).isSink = true;

    assertEquals(
        "[[0, 16, 13, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0]]",
        Arrays.deepToString(p2.outputFlows()));

    p2.push(1);
    assertEquals(
        "[[0, 16, 13, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0]]",
        Arrays.deepToString(p2.outputFlows()));
    p2.relabel(1);
    p2.push(1);
    assertEquals(
        "[[0, 16, 13, 0, 0, 0], "
            + "[0, 0, 0, 12, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0]]",
        Arrays.deepToString(p2.outputFlows()));
    p2.push(1);
    assertEquals(
        "[[0, 16, 13, 0, 0, 0], "
            + "[0, 0, 0, 12, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0]]",
        Arrays.deepToString(p2.outputFlows()));
    assertEquals(1, p2.vertices.get(1).height);
    p2.relabel(1);
    System.out.println(p2.edges.size());
    assertEquals(7, p2.vertices.get(1).height);
    p2.push(1);
    assertEquals(0, p2.vertices.get(1).e);
    assertEquals(
        "[[0, 12, 13, 0, 0, 0], "
            + "[0, 0, 0, 12, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0], "
            + "[0, 0, 0, 0, 0, 0]]",
        Arrays.deepToString(p2.outputFlows()));
  }


}