
package dawj.ch02;
//자바를 통해 CSV파일을 열고 읽어 처리함.


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ReadingCSVFiles {
    public static void main(String[] args) {
        File dataFile = new File("Countries.csv");
        try {
            Scanner input = new Scanner(dataFile);
            input.useDelimiter(",|\\s");
            String column1 = input.next();
            String column2 = input.next();
            System.out.printf("%-10s%12s%n", column1, column2);
            while (input.hasNext()) {
                String country = input.next();
                int population = input.nextInt();
                System.out.printf("%-10s%,12d%n", country, population);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
}
/*
Country     Population
Argentina   41,343,201
Brazil     201,103,330
Chile       16,746,491
Columbia    47,790,000
Paraguay     6,375,830
Peru        29,907,003
Venezuela   27,223,228*/

//Excel 로 읽기... (fail)

/*  Data Analysis with Java
 *  John R. Hubbard
 *  March 30, 2017
 */

package dawj.ch02;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FromMapToExcel {
    public static void main(String[] args) {
        Map<String,Integer> map = new TreeMap();
        load(map, "Countries.dat");
        print(map);
        storeXL(map, "Countries.xls", "Countries Worksheet");
    }
    
    /** Loads the data from the specified file into the specified map.
    */
    public static void load(Map map, String fileSpec) {
        File file = new File(fileSpec);
        try {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                String country = input.next();
                int population = input.nextInt();
                map.put(country, population);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
    
    public static void print(Map map) {
        Set countries = map.keySet();
        for (Object country : countries) {
            Object population = map.get(country);
            System.out.printf("%-10s%,12d%n", country, population);
        }
    }
    
    /** Stores the specified map in the specified worksheet of 
        the specified Excel workbook file.
     * @param map
     * @param fileSpec
     * @param sheet
    */
    public static void storeXL(Map map, String fileSpec, String sheet) {
        try {
            FileOutputStream out = new FileOutputStream(fileSpec);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet(sheet);
            Set countries = map.keySet();
            short rowNum = 0;
            for (Object country : countries) {
                Object population = map.get(country);
                HSSFRow row = worksheet.createRow(rowNum);
                row.createCell(0).setCellValue((String)country);
                row.createCell(1).setCellValue((Integer)population);
                ++rowNum;
            }
            workbook.write(out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}


