package com.songli.codelinecount.util;

import java.util.Map;

import com.songli.codelinecount.model.FileResModel;

public class OutputUtil {

    private OutputUtil() {
    }

    public static void printToConsole(Map<String, FileResModel> resDict) {
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
