package ps9;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by eiros_000 on 3/4/2017.
 */
public class GDesktopImproved {
    private final static int BOUND = 20;
    private final static int N_CONSUMERS = 4;

    public static void startIndexing (File[] roots) {
        BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);
        FileFilter filter = new FileFilter() {
            public boolean accept(File file) {return true;}
        };

        for (File root : roots) {
            (new FileCrawler(queue, filter, root)).start();;
        }

        for (int i = 0; i < N_CONSUMERS; i++) {
            (new Indexer(queue)).start();
        }
    }
}

class FileCrawler extends Thread {
    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;

    FileCrawler (BlockingQueue<File> queue, FileFilter filter, File root) {
        this.fileQueue = queue;
        this.fileFilter = filter;
        this.root = root;
    }

    public void run() {
        try {
            crawl(root);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void crawl(File root) throws InterruptedException {
        File[] entries = root.listFiles(fileFilter);

        if (entries != null) {
            ExecutorService exec = Executors.newFixedThreadPool(entries.length);
            for (File entry : entries) {
                if (entry.isDirectory()) {
                    Runnable task = new Runnable(){
                        @Override
                        public void run() {
                            try {
                                crawl(entry);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    exec.execute(task);
                } else {
                    fileQueue.put(entry);
                }
            }
        }
    }
}

class Indexer extends Thread {
    private final BlockingQueue<File> queue;

    public Indexer (BlockingQueue<File> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (true) {
                indexFile(queue.take());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void indexFile(File file) {
        // TODO Auto-generated method stub
    }
}