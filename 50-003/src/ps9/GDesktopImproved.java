package ps9;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by eiros_000 on 3/4/2017.
 */
public class GDesktopImproved {
    private final static int N_CONSUMERS = 4;

    //it starts here
    public static void startIndexing (File[] roots) {
        //Queue<File> queue = new LinkedList<File>();
        BlockingQueue<File> queue = new LinkedBlockingQueue<>();
        FileFilter filter = new FileFilter() {
            public boolean accept(File file) {return true;}
        };

        for (File root : roots) {
            (new FileCrawlerProb(queue, filter, root)).start();;
        }

        for (int i = 0; i < N_CONSUMERS; i++) {
            (new IndexerProb(queue)).start();
        }
    }
}

class FileCrawlerProb extends Thread {
    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;

    FileCrawlerProb (BlockingQueue<File> queue, FileFilter filter, File root) {
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
            for (File entry : entries) {
                if (entry.isDirectory()) {
                    crawl(entry);
                }
                else {
                    fileQueue.put(entry);
                }
            }
        }
    }
}

class IndexerProb extends Thread {
    private final BlockingQueue<File> queue;

    public IndexerProb (BlockingQueue<File> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                indexFile(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void indexFile(File file) {
        // code for analyzing the context of the file is skipped for simplicity
    }
}