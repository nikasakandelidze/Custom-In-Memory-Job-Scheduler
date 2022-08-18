package scheduler;

import java.util.PriorityQueue;

class JobSchedulerImplementation implements JobScheduler{
    private final JobQueue jobQueue = new JobQueue();
    private final SchedulerThread schedulerThread = new SchedulerThread(jobQueue);

    public JobSchedulerImplementation() {
        schedulerThread.start();
    }

    @Override
    public void schedule(Job job, long delay) {
        if (delay < 0){
            throw new IllegalArgumentException("Illegal delay time.");
        }
        synchronized (jobQueue){
            synchronized (job.monitor){
                job.state = Job.SCHEDULED;
                job.executionTime = System.currentTimeMillis() + delay; //exact time when it should be executed
            }
            jobQueue.add(job);
            if(job == jobQueue.peek()){
                jobQueue.notify();
            }
        }
    }

    private static class SchedulerThread extends Thread{
        private final JobQueue jobQueue;

        public SchedulerThread(JobQueue jobQueue) {
            this.jobQueue = jobQueue;
        }

        public void run() {
            try {
                mainLoop();
            } finally {
                // Someone killed this Thread, behave as if Timer cancelled
                synchronized(jobQueue) {
                    jobQueue.clear();  // Eliminate obsolete references
                }
            }
        }
        //[N, M]
        private void mainLoop(){
            while(true){
                try{
                    Job job;
                    boolean jobShouldBeExecuted;
                    synchronized (jobQueue){
                        while(jobQueue.isEmpty())
                            jobQueue.wait();
                        job = jobQueue.peek();
                        long now;
                        synchronized (job.monitor){
                            now = System.currentTimeMillis();
                            long executionTime = job.executionTime;
                            jobShouldBeExecuted = executionTime < now;
                            if(jobShouldBeExecuted){
                                // we don't execute job here because of performance reasons, since we hold locks here.
                                jobQueue.pop();
                                job.state = Job.EXECUTED;
                            }
                        }
                        if(!jobShouldBeExecuted){
                            jobQueue.wait(job.executionTime - now);
                        }
                    }
                    if(jobShouldBeExecuted){
                        job.run();
                    }
                }catch (Exception ignored){}
            }
        }
    }

    private static class JobQueue{
        private static final int INITIAL_CAPACITY = 100;
        private final PriorityQueue<Job> queue = new PriorityQueue<>(INITIAL_CAPACITY, (a,b)-> (int) (a.executionTime - b.executionTime));

        public void add(Job job){
            queue.add(job);
        }

        public Job peek(){
            return queue.peek();
        }

        public Job pop(){
            return queue.poll();
        }

        public boolean isEmpty(){
            return queue.isEmpty();
        }

        public void clear(){
            queue.clear();
        }
    }

}
