package com.songli.codelinecount.util;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.songli.codelinecount.model.FileResModel;

public class OutputUtil {

    private static final Logger logger = LogManager.getLogger(OutputUtil.class);

    private OutputUtil() {
    }

    public static void printToConsole(Map<String, FileResModel> resDict) {
        logger.info("==============================Output code line count==============================");
        logger.info("Type       files            blank           comment            code          total");
        logger.info("==================================================================================");
        for (String fileType : resDict.keySet()) {
            FileResModel resModel = resDict.get(fileType);
            System.out.println(fileType + "       " + resModel.getFileNumber()
                    + "            " + resModel.getBlankLine() + "           "
                    + resModel.getCommentLine() + "            "
                    + resModel.getCodeLine() + "          "
                    + resModel.getTotalLine());
        }

        logger.info("========================================End=======================================");
    }

}
