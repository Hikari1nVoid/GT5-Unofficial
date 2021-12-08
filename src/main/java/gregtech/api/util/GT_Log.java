package gregtech.api.util;

import gregtech.api.RecipeDebugConstants;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * Just a simple Logging Function. If on Server, then this will point to System.out and System.err
 */
public class GT_Log {
    public static PrintStream out = System.out;
    public static PrintStream err = System.err;
    public static PrintStream recipe;

    static {
        try {
            recipe = new PrintStream(new BufferedOutputStream(new FileOutputStream("GTRecipes.csv")), true);
            recipe.println(RecipeDebugConstants.CSV_HEADER);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            recipe = err;
        }
    }

    public static PrintStream ore = new LogBuffer();
    public static PrintStream pal = null;
    public static PrintStream exp = new LogBuffer();
    public static File mLogFile;
    public static File mOreDictLogFile;
    public static File mPlayerActivityLogFile;
    public static File mExplosionLog;

    public static class LogBuffer extends PrintStream {
        public final List<String> mBufferedOreDictLog = new ArrayList<String>();

        public LogBuffer() {
            super(new OutputStream() {
                @Override
                public void write(int arg0) {/*Do nothing*/}
            });
        }

        @Override
        public void println(String aString) {
            mBufferedOreDictLog.add(aString);
        }
    }
}
