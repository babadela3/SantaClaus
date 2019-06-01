import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Laponia {

    static int elfCount;
    static int reindeerCount;

    static Semaphore santaSem;
    static Semaphore reindeerSem;
    static Semaphore elfSem;

    static Semaphore counterMutex;
    static Semaphore elfMutex;

    static volatile boolean endOfTheWorld = false;
    static final Semaphore stopSem = new Semaphore(0);
    static int stopCounter = 5;
    static Random generator = new Random();

    static int NUM_ELVES_IN_GROUP = 3;
    static int NUM_REINDEERS_IN_GROUP = 9;

    private static void init() {
        elfCount = 0;
        reindeerCount = 0;

        santaSem = new Semaphore(0);
        reindeerSem = new Semaphore(0);
        elfSem = new Semaphore(0, true);

        counterMutex = new Semaphore(1);
        elfMutex = new Semaphore(1);
    }

    public static void main(String[] args) {
        try {
            HashSet<Thread> threads = new HashSet<>();
            threads.add(new Thread(new SantaClaus()));

            int numOfElves = 10;
            int numOfReindeers = 9;

            for (int i = 0; i < numOfElves; i++) {
                threads.add(new Thread(new Elf(i)));
            }
            for (int i = 0; i < numOfReindeers; i++) {
                threads.add(new Thread(new Reindeer(i)));
            }

            init();

            for (Thread t : threads) {
                t.start();
            }

            try {
                // wait until end of the world
                stopSem.acquire();
                System.out.println("THE END HAS COME!");
                for (Thread t : threads)
                    t.interrupt();
                for (Thread t : threads)
                    t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
