import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

class Solution {

    HashMap<String,DataEntry> map = new HashMap<String,DataEntry>();
 /**
 * Returns nothing. 
 * The outPutFileName specify the absolute path of the output file.
 *
 * @param  outPutFileName  the path (the absolute paht, including the name) of the output file
 * @return      void
 */
    private void writeMapOutput(String outPutFileName) {
        try {
            File outputFile = new File(outPutFileName);
            outputFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write("drug_name,num_prescriber,total_cost");
            writer.newLine();

            Set set = map.entrySet();
            Iterator iterator = set.iterator();
            TreeSet<DataEntry> resultSet = new TreeSet<DataEntry>(new DataEntryComparator());
            while(iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry)iterator.next();
                DataEntry de = (DataEntry)mentry.getValue();
                resultSet.add(de);
            }
            Iterator<DataEntry> it = resultSet.iterator();
            while (it.hasNext()) {
                DataEntry de = it.next();
                writer.write(de.name + "," + de.prescriberSet.size() + "," + de.totalCost);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch(FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        } catch(IOException e) {
            System.out.println("Something is wrong.");
            System.exit(1);
        }
    }

 /**
 * Returns nothing. 
 * The outPutFileName specify the absolute path of the output file.
 * 
 * @param  outPutFileName  the path (the absolute paht, including the name) of the output file
 * @param  strList         the string list for the outputs that are different from most of the outputs
 * @return      void
 */
    private void writeUnnormalOutput(String outPutFileName, List<String> strList) {
        try {
            File outputFile = new File(outPutFileName);
            outputFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            for (String str : strList) {
                writer.write(str);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch(FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        } catch(IOException e) {
            System.out.println("Something is wrong.");
            System.exit(1);
        }
    }

 /**
 * Returns a StringBuilder. 
 * The line is the the input string, and idx specify starting index of the string inside the double quotes.
 * 
 * @param  line            the input string that contains double qoutes
 * @param  idx             the starting index of inside double qoutes in the line 
 * @return      a StringBuilder that contains the content inside the double qoutes
 */
    private StringBuilder parseStrInsideDoubleQoutes(String line, Integer idx) {
        StringBuilder sb = new StringBuilder();
        int length = line.length();
        if (idx >= length) {
            return null;
        }
        char c = line.charAt(idx);
        if (c != '"') {
            while (idx < length && (c = line.charAt(idx)) != ',') {
                sb.append(c);
                idx++;
            }
        } else {
            idx++;
            while (idx < length && (c = line.charAt(idx)) != '"') {
                sb.append(c);
                idx++;
            }
        }
        return sb;
    }

 /**
 * Returns an int, -1 means the data is invalid, 1 means everything is fine. 
 * Will parse the lastname and first name of the precribers, the drug name and the cost of the drug
 * 
 * @param  columns         the columns to store the lastname, firstname, drugname, and cost
 * @param  line            a line (an entry) from the dataset
 * @return     an int to represent the parse result, -1 means invalid entry
 */
    private int parseEntry(String[] columns, String line) {
        String lastName = null;
        String fisrtName = null;
        String drugName = null;
        String costStr = null;
        int length = line.length();
        //get rid of the id
        char c = ' ';
        int idx = 0;
        while (idx < length && (c = line.charAt(idx)) != ',') {
            idx++;
        }
        idx++;

        //parse the lastname
        StringBuilder sb = new StringBuilder();
        if (idx > length) {
            return -1;
        }
        c = line.charAt(idx);
        if (c != '"') {
            while (idx < length && (c = line.charAt(idx)) != ',') {
                sb.append(c);
                idx++;
            }
            idx++;
        } else {
            idx++;
            while (idx < length && (c = line.charAt(idx)) != '"') {
                sb.append(c);
                idx++;
            }
            idx++;
            idx++;
        }

        lastName = sb.toString().trim();
        //pasrse the first name
        if (idx > length) {
            return -1;
        }
        sb = new StringBuilder();
        c = line.charAt(idx);
        if (c != '"') {
            while (idx < length && (c = line.charAt(idx)) != ',') {
                sb.append(c);
                idx++;
            }
            idx++;
        } else {
            idx++;
            while (idx < length && (c = line.charAt(idx)) != '"') {
                sb.append(c);
                idx++;
            }
            idx++;
            idx++;
        }
        fisrtName = sb.toString().trim();
        sb = new StringBuilder();
        c = line.charAt(idx);
        if (c != '"') {
            while (idx < length && (c = line.charAt(idx)) != ',') {
                sb.append(c);
                idx++;
            }
            idx++;
        } else {
            idx++;
            while (idx < length && (c = line.charAt(idx)) != '"') {
                sb.append(c);
                idx++;
            }
            idx++;
            idx++;
        }
        drugName = sb.toString().trim();
        sb = new StringBuilder();
        if (idx >= length) {
            return -1;
        }
        while (idx < length) {
            c = line.charAt(idx);
            sb.append(c);
            idx++;
        }
        costStr = sb.toString().trim();
        columns[0] = lastName;
        columns[1] = fisrtName;
        columns[2] = drugName;
        columns[3] = costStr;

        return 1;
    }
    
 /**
 * Returns nothing
 * Will read the csv file and process the data inside the method
 * 
 * @param  fileName         the path of the input data
 * @return     void
 */
    private void readFile(String fileName) {
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(new File(fileName)));
            if (br != null) {
                String line = null;
                //read the header
                br.readLine();
                List<String> unNormalList = new ArrayList<>();
                String[] values = new String[4];
                while ((line = br.readLine())!= null) {
                    if(line.length() == 0) {
                        continue;
                    }
                    parseEntry(values, line);
                    String name = values[2];
                    String fullName = values[1] + " " + values[0];

                    double cost = 0;
                    try {
                        cost = Double.parseDouble(values[3]);
                    } catch(NumberFormatException e) {
                        continue;
                    }

                    if (map.containsKey(name)) {
                        DataEntry de = map.get(name);
                        de.prescriberSet.add(fullName);
                        de.totalCost += cost;
                    } else {
                        Set<String> set = new HashSet<>();
                        set.add(fullName);
                        DataEntry de = new DataEntry(name, set, cost);
                        map.put(name,de);
                    }
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    // main method, starting point of the whole program
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(args.length);
        System.out.println(args[0]);
        System.out.println(args[1]);

        if (args.length < 2) { // handles invalid inputs
            System.out.println("Please type in the input file and the output file.");
            System.out.println("Example Usage: Java -Xmx4g Solution path/input.txt path/output.txt");
            return;
        }
        s.readFile(args[0]);
        s.writeMapOutput(args[1]);
    }
}
