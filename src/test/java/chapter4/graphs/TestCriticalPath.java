package chapter4.graphs;

import chapter4.graphs.impl.task.JobSchedule;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

/**
 * {@link JobSchedule} test
 */
public class TestCriticalPath {

    private TestData testData = new TestData(null,
            "src/test/java/chapter4/graphs/data/",
            "jobsPC.txt",
            null,
            null);


    /**
     * jobsPC.txt
     *   job   start  finish
     *  --------------------
     *     0     0.0    41.0
     *     1    41.0    92.0
     *     2   123.0   173.0
     *     3    91.0   127.0
     *     4    70.0   108.0
     *     5     0.0    45.0
     *     6    70.0    91.0
     *     7    41.0    73.0
     *     8    91.0   123.0
     *     9    41.0    70.0
     *  Finish time:   173.0
     */
    @Test
    public void test() {
        In in = testData.getIn(DataSize.TINY, true);
        JobSchedule cp = new JobSchedule(in);
        System.out.println(cp.dag);

        System.out.println(" job   start  finish");
        System.out.println("--------------------");
        for (int jobID = 0; jobID < cp.jobCount(); jobID++)
            System.out.printf("%4d %7.1f %7.1f\n", jobID, cp.startTime(jobID), cp.finnishTime(jobID));
        System.out.printf("Finish time: %7.1f\n", cp.minTotalTime());
    }

}
