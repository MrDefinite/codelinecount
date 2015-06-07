package com.songli.codelinecount.util;

import java.util.List;

import com.songli.codelinecount.model.FileInfoModel;

public class FileProcessor implements Runnable {

    private List<FileInfoModel> fileInfos;
    private int start;
    private int end;

    public FileProcessor(List<FileInfoModel> fileInfos, int start, int end) {
        if (fileInfos == null || fileInfos.size() == 0) {
            System.out.println("No files in the list!");
            return;
        }

        this.fileInfos = fileInfos;
        this.start = start;
        this.end = end;
    }

    public void run() {
        for (int i = start; i <= end; i++) {

        }
    }

}
