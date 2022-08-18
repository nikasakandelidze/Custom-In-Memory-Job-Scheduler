package scheduler;

public interface JobScheduler {
    void schedule(Job job, long delay);

    static JobScheduler create(){
        return new JobSchedulerImplementation();
    }
}
