package com.songli.codelinecount;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.songli.codelinecount.model.FileInfoModel;
import com.songli.codelinecount.model.FileResModel;

public class LineCounter {

    private static volatile LineCounter counter;

    // Save file info
    private List<FileInfoModel> fileInfos;
    // Save result info
    private Map<String, FileResModel> resDict;

    private LineCounter(String filePath) throws FileNotFoundException {
        fileInfos = new ArrayList<FileInfoModel>();
        File file = new File(filePath);
        if (file.isFile()) {
            initFileInfo(file);
        } else if (file.isDirectory()) {
            initFileInfoByDirectory(file);
        } else {
            throw new FileNotFoundException(
                    "You need to specify a file or directory!");
        }

    }

    private void initFileInfoByDirectory(File directory) {
        if (directory == null || !directory.isDirectory()) {
            System.out.println("This is not a directory!");
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
            System.out.println("This is not a file!");
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

    public static LineCounter getCounter() throws FileNotFoundException {
        if (counter == null) {
            throw new FileNotFoundException(
                    "You need to specify a file or directory!");
        }

        return counter;
    }

    public static LineCounter getCounter(String filePath) {
        if (counter == null) {
            synchronized (LineCounter.class) {
                if (counter == null) {
                    try {
                        counter = new LineCounter(filePath);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
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

    public void beginLineCount() {

    }

    public void outputLineCount() {
        generateResInfo();

        System.out
                .println("==============================Output code line count==============================");
        System.out
                .println("Type       files            blank           comment            code          total");
        System.out
                .println("==================================================================================");
        for (String fileType : resDict.keySet()) {
            FileResModel resModel = resDict.get(fileType);
            System.out.println(fileType + "       " + resModel.getFileNumber()
                    + "            " + resModel.getBlankLine() + "           "
                    + resModel.getCommentLine() + "            "
                    + resModel.getCodeLine() + "          "
                    + resModel.getTotalLine());
        }

        System.out
                .println("========================================End=======================================");
    }
}
