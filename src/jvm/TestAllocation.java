package jvm;

public class TestAllocation {

    private final static int _1MB = 1024*1024;

    /**
     * VM args
     *
     * -verbose:gc  -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails  -XX:SurvivorRatio=8
     *              初始堆大小 最大堆大小 年轻堆大小
     *
     * -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution  进入老年代
     *
     *
     * -XX:PretenureSizeThreshold=3145728 -XX:+UseConcMarkSweepGC   大对象进入老年代
     *
     *
     * */
    public static void main(String[] args){

        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];

        allocation3 = null;

        allocation3 = new byte[4 * _1MB];

    }


}
