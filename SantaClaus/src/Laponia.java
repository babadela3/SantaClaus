import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Laponia {

    static int elfCount;
    static int reindeerCount;

    static int elvesHelpCounter;
    static int attachesCounter;
    static boolean stopAttaches;
    ;
    static Semaphore santaSemaphore;
    static Semaphore reindeerSemaphore;
    static Semaphore elfSemaphore;

    static Mutex mutex;
    static Mutex elfMutex;

    private static final int totalElves = 10;
    private static final int totalReindeers = 9;
    static final int totalAttaches = 30;

    private static void init(){
        stopAttaches = false;
        elfCount = reindeerCount = 0;
        attachesCounter = 0;
        elvesHelpCounter = 0;

        santaSemaphore = new Semaphore(0);
        elfSemaphore = new Semaphore(0);
        reindeerSemaphore = new Semaphore(0);

        mutex = new Mutex();
        elfMutex = new Mutex();
    }

    public static void main(String[] args) {
        try {
            init();
            List<Thread> threads = new ArrayList<>();
            SantaClaus santaClaus = new SantaClaus();
            threads.add(new Thread(santaClaus));

            for (int i = 0; i < totalElves; i++) {
                Elf elf = new Elf(i + 1);
                threads.add(new Thread(elf));
            }

            for(int i = 0; i < totalReindeers; i++) {
                Reindeer reindeer = new Reindeer(i + 1);
                threads.add(new Thread(reindeer));
            }

            for(Thread thread : threads) {
                thread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
