
import java.util.Vector;

public class MemoryConsumer {

    private static float CAP = 0.8f;  // 80%

    private static int ONE_MB = 1024 * 1024;

    private static Vector cache = new Vector();

    public static void main(String[] args) {

        Runtime rt = Runtime.getRuntime();

        long maxMemBytes = rt.maxMemory();
        long usedMemBytes = rt.totalMemory() - rt.freeMemory();
        long freeMemBytes = rt.maxMemory() - usedMemBytes;

        int allocBytes = Math.round(freeMemBytes * CAP);

        System.out.println("Initial free memory: " + freeMemBytes / ONE_MB + "MB");
        System.out.println("Max memory: " + maxMemBytes / ONE_MB + "MB");
        System.out.println("Reserve: " + allocBytes / ONE_MB + "MB");

        for (int i = 0; i < allocBytes / ONE_MB; i++) {
            cache.add(new byte[ONE_MB]);
        }

        usedMemBytes = rt.totalMemory() - rt.freeMemory();
        freeMemBytes = rt.maxMemory() - usedMemBytes;

        System.out.println("Free memory: " + freeMemBytes / ONE_MB + "MB");
    }
}
