package com.songli.codelinecount.model;

public class FileInfoModel {

    private String fileName;
    private String filePath;
    private String fileType;
    private int totalLine;
    private int codeLine;
    private int blankLine;
    private int commentLine;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getTotalLine() {
        return totalLine;
    }

    public void setTotalLine(int totalLine) {
        this.totalLine = totalLine;
    }

    public int getCodeLine() {
        return codeLine;
    }

    public void setCodeLine(int codeLine) {
        this.codeLine = codeLine;
    }

    public int getBlankLine() {
        return blankLine;
    }

    public void setBlankLine(int blankLine) {
        this.blankLine = blankLine;
    }

    public int getCommentLine() {
        return commentLine;
    }

    public void setCommentLine(int commentLine) {
        this.commentLine = commentLine;
    }

}
