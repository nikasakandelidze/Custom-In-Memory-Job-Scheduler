import scheduler.Job;
import scheduler.JobScheduler;

public class Main {
    public static void main(String[] args) {
        var scheduler = JobScheduler.create();
        System.out.println("started");
        scheduler.schedule(new Job(){
            @Override
            public void run() {
                System.out.println("after 10 seconds");
            }
        }, 10000);

        scheduler.schedule(new Job(){
            @Override
            public void run() {
                System.out.println("yeep");
            }
        }, 5000);

    }
}
