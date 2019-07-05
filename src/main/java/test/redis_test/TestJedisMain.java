package test.redis_test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestJedisMain {
    static boolean isEven(double num) { return ((num % 2) == 0); }

    public static void main(String[] args) {
        BlockingQueue<TestJedis> queue = new LinkedBlockingQueue<>();
        IntStream.rangeClosed(0, 5).forEach(i -> queue.add(new TestJedis()));
        var testJedis = new TestJedis();


        var KEYS = List.of("januar", "februar", "mart", "april", "maj", "jun", "jul",
                "avgust", "septembar", "oktobar", "novembar", "decembar");

        var runnables = IntStream.rangeClosed(1, 10).boxed()
                .map(value -> new Worker(queue, KEYS, value, isEven(value))).collect(Collectors.toList());

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        runnables.forEach(executorService::submit);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdownNow();
    }

}

class Worker implements Runnable {

    private Logger logger = Logger.getLogger("JedisLogger");

    private BlockingQueue<TestJedis> queue;
    private List<String> KEYS;
    private Random random;
    private int id;
    private boolean isReader;

    public Worker(BlockingQueue<TestJedis> queue, List<String> KEYS, int id, boolean isReader) {
        this.queue = queue;
        this.KEYS = KEYS;
        this.id = id;
        this.random = ThreadLocalRandom.current();
        this.isReader = isReader;
    }

    @Override
    public void run() {
        while(true) {
            try {
                var testJedis = queue.take();
                logger.info("Queue size: "+queue.size());
                String key = KEYS.get(random.nextInt(KEYS.size()));
                if(isReader)
                    testJedis.read(key, id);
                else
                    testJedis.write(key, String.valueOf(random.nextLong()), id);
                queue.put(testJedis);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Zavrsavam se");
    }
}

