package scheduler;

public abstract class Job implements Runnable{
    public long executionTime;
    public int state = IDLE;
    public final Object monitor = new Object();

    public static int IDLE = 0;
    public static int SCHEDULED = 1;
    public static int EXECUTED = 2;

    @Override
    public abstract void run();
}
