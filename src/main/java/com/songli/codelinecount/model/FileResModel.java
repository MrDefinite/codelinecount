package com.songli.codelinecount.model;

public class FileResModel {

    private int totalLine;
    private int codeLine;
    private int blankLine;
    private int commentLine;
    private int fileNumber;

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

    public int getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(int fileNumber) {
        this.fileNumber = fileNumber;
    }

}
