package djtslMinTraceAlgorithm;
import java.util.*;

public class DJTest {
    //无向图数组初始化
    int[][] weight = {{0, 7, 9, 999, 999, 14},
            {7, 0, 10, 15, 999, 999},
            {9, 10, 0, 11, 999, 2},
            {999, 15, 11, 999, 6, 999},
            {999, 999, 999, 6, 0, 9},
            {14, 999, 2, 999, 9, 0}};

    //未计算路径的点集合
    Set<Integer> notUse = new HashSet<Integer>();
    int[] dis = {0, 7, 9, 999, 999, 14};
    //记录每个点最短路径的路径数组
    List<Integer>[] traces = new LinkedList[6];

    public static void main(String[] args) {
        DJTest inst = new DJTest();
        inst.init();
        while (true) {
            if (inst.notUse.isEmpty()) break;
            inst.doDJAlgorithm();
        }
        System.out.println("最短路径为");
        List trace = inst.getTrace(inst.traces, 2);
        for (int i = 0; i < trace.size(); i++) {
            System.out.print(Integer.parseInt(trace.get(i).toString()) + 1 + ",");
        }
    }

    //初始化
    public void init() {
        //初始化时直接将源点0去除
        for (int i = 1; i < 6; i++) {
            notUse.add(i);
        }
        for (int i = 0; i < 6; i++) {
            traces[i] = new LinkedList<Integer>();
            traces[i].add(0);
            traces[i].add(i);
        }
    }

    //每次发现目标点的最新最短路则更新路径数组，target为实际点index-1
    public List[] setTraces(List[] traces, int current, int target) {
        traces[target].set(traces[target].size() - 1, current);
        traces[target].add(target);
        return traces;
    }

    //递归获取路径，目标点target的最短路径为该路径中上个节点的最短路径加上target，target为实际点index-1
    public List<Integer> getTrace(List<Integer>[] trace, int target) {
        int last = trace[target].get(trace[target].size() - 2);
        if (last != 0) {
            trace[target] = getTrace(trace, last);
            trace[target].add(target);
        }
        return trace[target];
    }

    //计算源点到各个目标点的最短距离
    public void doDJAlgorithm() {
        //获取dis数组中的最小值加入判断集合
        int currentMin = 9999;
        int currentIndex = 0;
        for (int i : notUse) {
            if (dis[i] < currentMin) {
                currentIndex = i;
                currentMin = dis[i];
            }
        }
        //已经判断过的点排除在下次取点的集合外
        notUse.remove(new Integer(currentIndex));
        for (int i = 0; i < 6; i++) {
            //若源点到中间点的距离与中间点到目标点的距离之和小于源点到目标点的距离则替换远点到目标点的最短路径为经过当前目标点的路径
            if (weight[currentIndex][i] != 999 && dis[currentIndex] + weight[currentIndex][i] < dis[i]) {
                dis[i] = dis[currentIndex] + weight[currentIndex][i];
                setTraces(traces, currentIndex, i);
            }
        }

        for (int i = 0; i < 6; i++) {
            System.out.print(dis[i] + ",");
        }
        System.out.print("\n");
    }

}
