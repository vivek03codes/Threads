package multithreaddownloader;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadDownloader {
    private static final int THREAD_COUNT = 4;
    private static final String FILE_URL = "https://code.visualstudio.com/sha/download?build=stable&os=darwin-universal";
    private static final String OUTPUT_FILE = "vscode.zip";

    static void main() throws Exception {
        new MultiThreadDownloader().downloadFile(FILE_URL, OUTPUT_FILE, THREAD_COUNT);
    }

    public void downloadFile(String fileUrl, String outputFile, int numThreads) throws Exception {
        //Step 1: Get File size
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("HEAD");
        int fileSize = conn.getContentLength();
        conn.disconnect();

        System.out.println("Total file size: " + fileSize + " bytes");

        //Step 2: Divide the file into chunks
        int chunkSize = fileSize / numThreads;

        //Step 3: Create Thread pool
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        //Step 4: Submit the download task
        for (int i = 0; i < numThreads; i++) {
            int startByte = i * chunkSize;
            int endByte = (i == numThreads - 1) ? fileSize - 1 : startByte + chunkSize + 1;
            String partFile = outputFile + ".part" + i;

            executor.submit(new DownloadTask(FILE_URL, partFile, startByte, endByte, i));
        }

        //Step 5: Shutdown and wait for termination
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        mergeFiles(outputFile, numThreads);
        System.out.println("File downloaded successfully: " + outputFile);
    }

    public void mergeFiles(String outputFile, int numParts) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            for (int i = 0; i < numParts; i++) {
                String partFile = outputFile + ".part" + i;
                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(partFile))) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = bis.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }
                }
                new File(partFile).delete(); //Clean Up
            }
        }
    }

    static class DownloadTask implements Runnable {
        private final String fileURL;
        private final String partFileName;
        private final int startByte;
        private final int endByte;
        private final int threadId;

        DownloadTask(String fileURL, String partFileName, int startByte, int endByte, int threadId) {
            this.fileURL = fileURL;
            this.partFileName = partFileName;
            this.startByte = startByte;
            this.endByte = endByte;
            this.threadId = threadId;
        }


        @Override
        public void run() {
            try {
                System.out.println("Thread " + threadId + " downloading bytes: " + startByte + " - " + endByte);
                HttpURLConnection conn = (HttpURLConnection) new URL(fileURL).openConnection();
                conn.setRequestProperty("Range", "bytes=" + startByte + "-" + endByte);

                try (InputStream input = conn.getInputStream();
                     RandomAccessFile output = new RandomAccessFile(partFileName, "rw")) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    long totalBytesRead = 0;
                    long totalBytesToRead = endByte - startByte + 1;

                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                        totalBytesRead += bytesRead;

                        double progress = (totalBytesRead * 100.0 / totalBytesToRead);
                        System.out.printf("Thread %d: %.2f%%%s", threadId, progress, "\r");
                    }
                }
            } catch (IOException e) {
                System.err.println("Thread " + threadId + " failed: " + e.getMessage());
            }
        }
    }


}
