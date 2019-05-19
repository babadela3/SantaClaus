import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Laponia {

    static int elfCount;
    static int reindeerCount;

    static AtomicInteger attachesCounter;
    static boolean stopAttaches;
;
    static Semaphore santaSemaphore;
    static Semaphore reindeerSemaphore;
    static Semaphore elfSemaphore;

    static Mutex mutex;
    static Mutex elfMutex;

    private static final int totalElves = 10;
    private static final int totalReindeers = 9;
    static final int totalAttaches = 5;

    private static void init(){
        stopAttaches = false;
        elfCount = reindeerCount = 0;
        attachesCounter = new AtomicInteger(1);

        santaSemaphore = new Semaphore(0);
        elfSemaphore = new Semaphore(0);
        reindeerSemaphore = new Semaphore(0);

        mutex = new Mutex();
        elfMutex = new Mutex();
    }

    public static void main(String[] args) {
        try {
            init();
            HashSet<Thread> threads = new HashSet<>();
            SantaClaus santaClaus = new SantaClaus();
            threads.add(new Thread(santaClaus));

            for (int i = 0; i < totalElves; i++) {
                Elf elf = new Elf(i);
                threads.add(new Thread(elf));
            }

            for(int i = 0; i < totalReindeers; i++) {
                Reindeer reindeer = new Reindeer(i);
                threads.add(new Thread(reindeer));
            }

            for(Thread thread : threads) {
                thread.start();
            }

            //TODO: Find a better way to stop the elves threads
            while(!stopAttaches) {
                Thread.sleep(100);
            }

            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
