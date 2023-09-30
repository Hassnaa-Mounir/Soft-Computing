package fuzzy.system;


import java.util.ArrayList;

class FuzzySet {

    public String fsName;
    String fsType;
    ArrayList<Integer> fsValues;

    FuzzySet(String fsName, String fsType, ArrayList<Integer> fsValues) {
        this.fsName = fsName;
        this.fsType = fsType;
        this.fsValues = fsValues;

    }

    FuzzySet() {
        fsValues = new ArrayList();
    }

    @Override
    public String toString() {
        return "FuzzySet{" +
                "fsName='" + fsName + '\'' +
                ", fsType='" + fsType + '\'' +
                ", fsValues=" + fsValues +
                '}';
    }
}