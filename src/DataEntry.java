  
import java.util.*;

/**
* DataEntry class represents the data entries in the pharmacy dataset.
* There are three properties in the class, a name of the drug, a set of prescribers, and the total cost of this drug that
* caused by the prescriptions in the whole dataset.
* 
* It is good practice to make the properties private and set up the setters and getters for those private fields.
* Because of the time constraints of the coding challenage, I did do it that way.
*/

class DataEntry {
    // name represents the name of a drug
    String name;
    // prescriberSet contains all the doctors who has prescribed this drug
    Set<String> prescriberSet;
    // the total amount of the cost of one drug in the dataset
    double totalCost;
    public DataEntry(String name, Set<String> people, double cost) {
        this.name = name;
        prescriberSet = people;
        totalCost = cost;
    }
}
