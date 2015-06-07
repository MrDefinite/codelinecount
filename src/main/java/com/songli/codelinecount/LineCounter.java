package com.songli.codelinecount;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.songli.codelinecount.exception.CLCException;
import com.songli.codelinecount.model.FileInfoModel;
import com.songli.codelinecount.model.FileResModel;
import com.songli.codelinecount.util.FileProcessor;
import com.songli.codelinecount.util.OutputUtil;

public class LineCounter {

    private static final Logger logger = LogManager.getLogger(LineCounter.class);
    private static final int PROCESS_ARRAY_SIZE = 8;
    private static volatile LineCounter counter;

    // Save file info
    private List<FileInfoModel> fileInfos;
    // Save result info
    private Map<String, FileResModel> resDict;

    private LineCounter(String filePath) throws CLCException {
        fileInfos = new ArrayList<FileInfoModel>();
        File file = new File(filePath);
        if (file.isFile()) {
            initFileInfo(file);
        } else if (file.isDirectory()) {
            initFileInfoByDirectory(file);
        } else {
            throw new CLCException("You need to specify a file or directory!");
        }

    }

    private void initFileInfoByDirectory(File directory) {
        if (directory == null || !directory.isDirectory()) {
            logger.warn("This is not a directory!");
            return;
        }

        File[] files = directory.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                initFileInfo(file);
            } else if (file.isDirectory()) {
                initFileInfoByDirectory(file);
            }
        }
    }

    private String getFileSuffix(String fileName) {
        if (!fileName.contains(".")) {
            return "plain";
        }

        int sufLoc = fileName.lastIndexOf(".");

        return fileName.substring(sufLoc + 1);
    }

    private void initFileInfo(File file) {
        if (file == null || !file.isFile()) {
            logger.warn("This is not a file!");
            return;
        }

        FileInfoModel model = new FileInfoModel();
        model.setBlankLine(0);
        model.setCodeLine(0);
        model.setCommentLine(0);
        model.setTotalLine(0);
        model.setFileType(getFileSuffix(file.getName()));
        model.setFileName(file.getName());
        model.setFilePath(file.getAbsolutePath());

        fileInfos.add(model);
    }

    public static LineCounter getCounter() throws CLCException {
        if (counter == null) {
            throw new CLCException("You need to specify a file or directory!");
        }

        return counter;
    }

    public static LineCounter getCounter(String filePath) {
        if (counter == null) {
            synchronized (LineCounter.class) {
                if (counter == null) {
                    try {
                        counter = new LineCounter(filePath);
                    } catch (CLCException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }

        return counter;
    }

    private void generateResInfo() {
        resDict = new HashMap<String, FileResModel>();
        for (FileInfoModel model : fileInfos) {
            if (!resDict.containsKey(model.getFileType())) {
                FileResModel resModel = new FileResModel();
                resModel.setBlankLine(model.getBlankLine());
                resModel.setCodeLine(model.getCodeLine());
                resModel.setCommentLine(model.getCommentLine());
                resModel.setTotalLine(model.getTotalLine());
                resModel.setFileNumber(1);

                resDict.put(model.getFileType(), resModel);
            } else {
                FileResModel resModel = resDict.get(model.getFileType());
                resModel.setBlankLine(resModel.getBlankLine()
                        + model.getBlankLine());
                resModel.setCodeLine(resModel.getCodeLine()
                        + model.getCodeLine());
                resModel.setCommentLine(resModel.getCommentLine()
                        + model.getCommentLine());
                resModel.setTotalLine(resModel.getTotalLine()
                        + model.getTotalLine());
                resModel.setFileNumber(resModel.getFileNumber() + 1);
            }
        }
    }

    private List<FileProcessor> createProcessors() {
        List<FileProcessor> processors = new ArrayList<FileProcessor>();

        final int processorNum = getProcessorNumber();

        for (int i = 0; i < processorNum; i++) {
            logger.debug("Creating processor " + i);
            FileProcessor processor;

            if (i != processorNum - 1) {
                processor = new FileProcessor(fileInfos,
                        i * PROCESS_ARRAY_SIZE, (i + 1) * PROCESS_ARRAY_SIZE
                                - 1);
            } else {
                processor = new FileProcessor(fileInfos,
                        i * PROCESS_ARRAY_SIZE, fileInfos.size() - 1);
            }

            processors.add(processor);
        }

        return processors;
    }

    private int getProcessorNumber() {
        return fileInfos.size() % PROCESS_ARRAY_SIZE == 0 ? fileInfos.size()
                / PROCESS_ARRAY_SIZE : fileInfos.size() / PROCESS_ARRAY_SIZE
                + 1;
    }

    public void beginLineCount() throws InterruptedException {
        List<FileProcessor> processors = createProcessors();

        Thread[] threadPool = new Thread[processors.size()];

        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i] = new Thread(processors.get(i));
            threadPool[i].start();
        }

        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i].join();
        }

        generateResInfo();
    }

    public void outputLineCount() {
        logger.info("Begin output result!");
        OutputUtil.printToConsole(resDict);
    }
}
