package fuzzy.system;



import java.util.ArrayList;
import java.util.HashMap;

public class Rule {
    ArrayList<Variable>variables;
    String operator;
    HashMap<String, Double> result;

    String resultSet;

    Rule(String[] array){
        variables=new ArrayList<>();
         result= new HashMap<String, Double>();
// static for rule of 2 variables with 2 values and one output
        variables.add(new Variable(array[0],array[1]));
        operator=array[2];
        variables.add(new Variable(array[3],array[4]));
        resultSet=array[7];


    }

    @Override
    public String toString() {
        return "Rule{" +
                "variables=" + variables +
                ", operator='" + operator + '\'' +
                ", result=" + result +
                '}';
    }
}
