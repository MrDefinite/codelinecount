package com.songli.codelinecount.util;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.songli.codelinecount.model.FileInfoModel;

public class FileProcessor implements Runnable {

    private static final Logger logger = LogManager
            .getLogger(FileProcessor.class);
    private int totalLine;
    private int codeLine;
    private int blankLine;
    private int commentLine;
    private List<FileInfoModel> fileInfos;
    private int start;
    private int end;

    public FileProcessor(List<FileInfoModel> fileInfos, int start, int end) {
        if (fileInfos == null || fileInfos.size() == 0) {
            logger.debug("No files in the list!");
            return;
        }

        this.fileInfos = fileInfos;
        this.start = start;
        this.end = end;
    }

    public void run() {
        for (int i = start; i <= end; i++) {
            logger.debug("Dealing with file model " + i);
            resetData();
            
            
            
            
            saveDataToModel(fileInfos.get(i));
        }
    }

    private void resetData() {
        totalLine = 0;
        codeLine = 0;
        blankLine = 0;
        commentLine = 0;
    }
    
    private void saveDataToModel(FileInfoModel model) {
        model.setTotalLine(totalLine);
        model.setBlankLine(blankLine);
        model.setCodeLine(codeLine);
        model.setCommentLine(commentLine);
    }
}
