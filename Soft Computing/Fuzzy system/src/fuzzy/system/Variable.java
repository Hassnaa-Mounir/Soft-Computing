package fuzzy.system;

import java.util.ArrayList;

class Variable
{

    public ArrayList<Integer> varRange;
    public ArrayList<FuzzySet> fuzzysets;
    String varName;
    String varType;
    String value;


    Variable(ArrayList<Integer> varRange, ArrayList<FuzzySet> fuzzysets, String varName, String varType) {
        this.varName = varName;
        this.fuzzysets = fuzzysets;
        this.varRange = varRange;
        this.varType = varType;
    }

    Variable() {
        varRange = new ArrayList<>();
        fuzzysets = new ArrayList<>();
    }
    Variable(String name,String value){
        this.varName=name;
        this.value=value;

    }

    @Override
    public String toString() {
        return "Variable{" +
                "varRange=" + varRange +
                ", fuzzysets=" + fuzzysets +
                ", varName='" + varName + '\'' +
                ", varType='" + varType + '\'' +
                ", value='" + value + '\'' +

                '}';
    }
}