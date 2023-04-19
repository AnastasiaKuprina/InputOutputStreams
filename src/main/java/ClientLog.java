import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    //StringBuilder log = new StringBuilder("productNum,amount\n");
    //private String log = "";//""productNum,amount\n";
    private List<String[]> log = new ArrayList<>();


    public void log(int productNum, int amount) {
        //log+=String.format("%d%d,%n", productNum, amount);
        log.add(new String[]{"" + productNum, "" + amount});
    }

    public void exportAsCSV(File txtFile) throws IOException {
        if (!txtFile.exists()) {
            log.add(0, new String[]{"productNum, amount"});
            //log = "productNum,amount\n";
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))) {
            writer.writeAll(log);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //        try{
//            FileWriter writer = new FileWriter(txtFile);
//            writer.write(log);
//            //writer.close();
//        }catch(Exception e){
////            throw new RuntimeException(e);
//        }

    }
}
