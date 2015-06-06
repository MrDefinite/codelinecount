package com.songli.codelinecount;

public class App {
    public static void main(String[] args) throws Exception {

        /**
         * count line for a directory: java -jar clc /path/to/directoryorfile
         */

        if (args.length != 1) {
            throw new Exception("Params wrong!");
        }

        LineCounter counter = LineCounter.getCounter(args[0]);

        counter.beginLineCount();
        counter.outputLineCount();
    }
}
