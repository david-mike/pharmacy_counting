import java.util.Comparator;

/**
* DataEntryComparator implements Comparator<DataEntry> 
* so that the we sort the dataEntry by totalCost
* If there is a tie between totalCost, the names will be compared.
*/

class DataEntryComparator implements Comparator<DataEntry> {
    public int compare(DataEntry a, DataEntry b) {
        int compareResult = Double.compare(b.totalCost, a.totalCost);
        if (compareResult == 0) {
            compareResult = a.name.compareTo(b.name);
        }
        return compareResult;
    }
}
