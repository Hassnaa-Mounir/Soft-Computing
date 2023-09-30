package fuzzy.system;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


class FuzzySys {

    public String systemName;

    double predicted_value = 0.0;
    String predicted_type="";
    String description;
    ArrayList<Variable> vars;
    ArrayList<Rule> rules=new ArrayList<>();
    ArrayList<Integer> crispValues;
    ArrayList<Double> memeberShip ;

    FuzzySys(String systemName, String description, ArrayList<Variable> vars,
             ArrayList<String> rules, ArrayList<Integer> crispValues) {
        this.crispValues = crispValues;
        this.description = description;
       // this.rules = rules;
        this.systemName = systemName;
        this.vars = vars;
    }

    FuzzySys() {
        vars = new ArrayList();
      //  rules = new ArrayList();
        crispValues = new ArrayList();
        memeberShip = new ArrayList();

    }

    double MemberShip(ArrayList<Integer> p1 ,ArrayList<Integer> p2  , int crisp)
    {

        double slope = (double)(p2.get(1) - p1.get(1)) / (double) (p2.get(0)-p1.get(0));
        double constant = p2.get(1) - (slope * p2.get(0)) ;
        double membership = (slope * crisp) + constant ;
        return membership ;

    }

    void ruleAnalyzer(String rule){

        String[] splitter =rule.split("\\s+");

        Rule obj=new Rule((splitter));
        rules.add(obj);



    }
    double and(double x,double y){
        return Math.min(x, y);
    }
    double or(double x,double y){
        return Math.max(x, y);
    }
    double andNot(double x,double y){
        return and(x,(1-y));
    }
    double orNot(double x,double y){
        return or(x,(1-y));
    }


    void fuzzification ()
    {
        boolean touchEndPoint = false ;
        // if the crisp point touches the end point on any line then it will be doublicated in the next line that complting the trap or triangle

        for(int i =0 ; i < crispValues.size() ; i++)
        {
            for(int j=0 ;j < vars.get(i).fuzzysets.size();j++)
            {
                // String fuzzy_set=vars.get(i).fuzzysets.get(j).fsName;

                if ("tri".equals(vars.get(i).fuzzysets.get(j).fsType) ||"TRI".equals(vars.get(i).fuzzysets.get(j).fsType) )
                {
                    if (vars.get(i).fuzzysets.get(j).fsValues.get(0) <= crispValues.get(i) &&  crispValues.get(i) <= vars.get(i).fuzzysets.get(j).fsValues.get(1))
                    {
                        ArrayList<Integer> p1 = new ArrayList();
                        ArrayList<Integer> p2 = new ArrayList();

                        p1.add(vars.get(i).fuzzysets.get(j).fsValues.get(0));
                        p1.add(0);

                        p2.add(vars.get(i).fuzzysets.get(j).fsValues.get(1));
                        p2.add(1);

                        memeberShip.add(MemberShip(p1, p2, crispValues.get(i)));


                    }

                    else if (vars.get(i).fuzzysets.get(j).fsValues.get(1) <= crispValues.get(i) &&  crispValues.get(i) <= vars.get(i).fuzzysets.get(j).fsValues.get(2))
                    {
                        ArrayList<Integer> p1 = new ArrayList();
                        ArrayList<Integer> p2 = new ArrayList();

                        p1.add(vars.get(i).fuzzysets.get(j).fsValues.get(1));
                        p1.add(1);

                        p2.add(vars.get(i).fuzzysets.get(j).fsValues.get(2));
                        p2.add(0);

                        memeberShip.add(MemberShip(p1, p2, crispValues.get(i)));
                    }
                    else{
                        memeberShip.add(0.0);
                    }

                }

                else if ("trap".equals(vars.get(i).fuzzysets.get(j).fsType) || "TRAP".equals(vars.get(i).fuzzysets.get(j).fsType) )
                {

                    if((vars.get(i).fuzzysets.get(j).fsValues.get(0) <= crispValues.get(i)) &&
                            ( crispValues.get(i) <= vars.get(i).fuzzysets.get(j).fsValues.get(1)))
                    {

                        ArrayList<Integer> p1 = new ArrayList();
                        ArrayList<Integer> p2 = new ArrayList();

                        p1.add(vars.get(i).fuzzysets.get(j).fsValues.get(0));
                        p1.add(0);

                        p2.add(vars.get(i).fuzzysets.get(j).fsValues.get(1));
                        p2.add(1);

                        memeberShip.add(MemberShip(p1, p2, crispValues.get(i)));



                    }

                    else if ((vars.get(i).fuzzysets.get(j).fsValues.get(1) <= crispValues.get(i)) &&
                            ( crispValues.get(i) <= vars.get(i).fuzzysets.get(j).fsValues.get(2)))
                    {


                        ArrayList<Integer> p1 = new ArrayList<>();
                        ArrayList<Integer> p2 = new ArrayList<>();

                        p1.add(vars.get(i).fuzzysets.get(j).fsValues.get(1));
                        p1.add(1);

                        p2.add(vars.get(i).fuzzysets.get(j).fsValues.get(2));
                        p2.add(1);

                        memeberShip.add(MemberShip(p1, p2, crispValues.get(i)));


                    }

                    else  if((vars.get(i).fuzzysets.get(j).fsValues.get(2) <= crispValues.get(i)) &&
                            ( crispValues.get(i) <= vars.get(i).fuzzysets.get(j).fsValues.get(3)))
                    {


                        ArrayList<Integer> p1 = new ArrayList();
                        ArrayList<Integer> p2 = new ArrayList();
                        p1.add(vars.get(i).fuzzysets.get(j).fsValues.get(2));
                        p1.add(1);

                        p2.add(vars.get(i).fuzzysets.get(j).fsValues.get(3));
                        p2.add(0);

                        memeberShip.add(MemberShip(p1, p2, crispValues.get(i)));
                    }
                    else{
                        memeberShip.add(0.0);
                    }

                }
            }
        }


    }

    void fuzzifyPredictValue ()
    {
        boolean touchEndPoint = false ;
        // if the crisp point touches the end point on any line then it will be doublicated in the next line that complting the trap or triangle


           int i=crispValues.size()-1;
            for(int j=0 ;j < vars.get(i).fuzzysets.size();j++)
            {
                // String fuzzy_set=vars.get(i).fuzzysets.get(j).fsName;

                if ("tri".equals(vars.get(i).fuzzysets.get(j).fsType) ||"TRI".equals(vars.get(i).fuzzysets.get(j).fsType) )
                {
                    if (vars.get(i).fuzzysets.get(j).fsValues.get(0) <= crispValues.get(i) &&  crispValues.get(i) <= vars.get(i).fuzzysets.get(j).fsValues.get(1))
                    {
                        ArrayList<Integer> p1 = new ArrayList();
                        ArrayList<Integer> p2 = new ArrayList();

                        p1.add(vars.get(i).fuzzysets.get(j).fsValues.get(0));
                        p1.add(0);

                        p2.add(vars.get(i).fuzzysets.get(j).fsValues.get(1));
                        p2.add(1);

                        memeberShip.add(MemberShip(p1, p2, crispValues.get(i)));


                    }

                    else if (vars.get(i).fuzzysets.get(j).fsValues.get(1) <= crispValues.get(i) &&  crispValues.get(i) <= vars.get(i).fuzzysets.get(j).fsValues.get(2))
                    {
                        ArrayList<Integer> p1 = new ArrayList();
                        ArrayList<Integer> p2 = new ArrayList();

                        p1.add(vars.get(i).fuzzysets.get(j).fsValues.get(1));
                        p1.add(1);

                        p2.add(vars.get(i).fuzzysets.get(j).fsValues.get(2));
                        p2.add(0);

                        memeberShip.add(MemberShip(p1, p2, crispValues.get(i)));
                    }
                    else{
                        memeberShip.add(0.0);
                    }

                }

                else if ("trap".equals(vars.get(i).fuzzysets.get(j).fsType) || "TRAP".equals(vars.get(i).fuzzysets.get(j).fsType) )
                {

                    if((vars.get(i).fuzzysets.get(j).fsValues.get(0) <= crispValues.get(i)) &&
                            ( crispValues.get(i) <= vars.get(i).fuzzysets.get(j).fsValues.get(1)))
                    {

                        ArrayList<Integer> p1 = new ArrayList();
                        ArrayList<Integer> p2 = new ArrayList();

                        p1.add(vars.get(i).fuzzysets.get(j).fsValues.get(0));
                        p1.add(0);

                        p2.add(vars.get(i).fuzzysets.get(j).fsValues.get(1));
                        p2.add(1);

                        memeberShip.add(MemberShip(p1, p2, crispValues.get(i)));



                    }

                    else if ((vars.get(i).fuzzysets.get(j).fsValues.get(1) <= crispValues.get(i)) &&
                            ( crispValues.get(i) <= vars.get(i).fuzzysets.get(j).fsValues.get(2)))
                    {


                        ArrayList<Integer> p1 = new ArrayList<>();
                        ArrayList<Integer> p2 = new ArrayList<>();

                        p1.add(vars.get(i).fuzzysets.get(j).fsValues.get(1));
                        p1.add(1);

                        p2.add(vars.get(i).fuzzysets.get(j).fsValues.get(2));
                        p2.add(1);

                        memeberShip.add(MemberShip(p1, p2, crispValues.get(i)));


                    }

                    else  if((vars.get(i).fuzzysets.get(j).fsValues.get(2) <= crispValues.get(i)) &&
                            ( crispValues.get(i) <= vars.get(i).fuzzysets.get(j).fsValues.get(3)))
                    {


                        ArrayList<Integer> p1 = new ArrayList();
                        ArrayList<Integer> p2 = new ArrayList();
                        p1.add(vars.get(i).fuzzysets.get(j).fsValues.get(2));
                        p1.add(1);

                        p2.add(vars.get(i).fuzzysets.get(j).fsValues.get(3));
                        p2.add(0);

                        memeberShip.add(MemberShip(p1, p2, crispValues.get(i)));
                    }
                    else{
                        memeberShip.add(0.0);
                    }

                }
            }



    }

    void inference(){
        double temp_result1=-1;
        double temp_result2=-1;


        for (Rule rule :rules){
            int ctr=0;//ctr to be increased by fuzzysets .size
           // int var_count=0;
           String current_variable_value= rule.variables.get(ctr).value;
            int num_of_fuzzy_sets1=vars.get(ctr).fuzzysets.size();

        for (int i=0;i<num_of_fuzzy_sets1;i++){

            if (current_variable_value.equals(vars.get(ctr).fuzzysets.get(i).fsName)){

              temp_result1= memeberShip.get(i);
                System.out.println("temp res 1 is "+temp_result1);


            }
        }
        ctr++;//ctr=1
            current_variable_value= rule.variables.get(ctr).value;
            int num_of_fuzzy_sets2=vars.get(ctr).fuzzysets.size();

            for (int i=0;i<num_of_fuzzy_sets2;i++){

                if (current_variable_value.equals(vars.get(ctr).fuzzysets.get(i).fsName)){

                    temp_result2= memeberShip.get(num_of_fuzzy_sets1+i);
                    System.out.println("temp res 2 is "+temp_result2);

                }
            }
            switch (rule.operator) {
                case "and" -> rule.result.put(rule.resultSet, and(temp_result1, temp_result2));
                case "or" -> rule.result.put(rule.resultSet, or(temp_result1, temp_result2));
                case "and_not" -> rule.result.put(rule.resultSet, andNot(temp_result1, temp_result2));
                case "or_not" -> rule.result.put(rule.resultSet,orNot(temp_result1, temp_result2));
            }
        }


    }




    void defuzzification()
    {
        HashMap<String, Integer> centroids = new HashMap<>();
        Variable variable = vars.get(vars.size()-1);
        for(int i=0;i<variable.fuzzysets.size();i++)
        {
            FuzzySet fs = variable.fuzzysets.get(i);
            if(fs.fsType.equals("trap"))
            {
                centroids.put(variable.fuzzysets.get(i).fsName, (fs.fsValues.get(1)+fs.fsValues.get(2))/2  );
            }
            else //triangle
            {
                centroids.put(variable.fuzzysets.get(i).fsName, fs.fsValues.get(1) );
            }

        }

        System.out.println(centroids);

        Double tmp_up=0.0;
        Double tmp_down =0.0;
        for (Rule rule : rules)
        {
            System.out.println(rule.resultSet);
            tmp_up+=rule.result.get(rule.resultSet) * centroids.get(rule.resultSet);
            tmp_down+=rule.result.get(rule.resultSet);

        }


    predicted_value=tmp_up/tmp_down;



        predicted_type="";
        this.crispValues.add((int)(Math.ceil(this.predicted_value)));

        this.fuzzifyPredictValue();

        /////////////////////////////////out var inference type
        Variable out_var=vars.get(vars.size()-1);

        //member ship pointer is pointer for output variable

        HashMap<Double,String> classifier=new HashMap<>();
        int membership_pointer= memeberShip.size()-out_var.fuzzysets.size();
        int counter=0;//this loop over fuzzysets automatic with looping over membership array

        double max=memeberShip.get(membership_pointer);

        classifier.put(memeberShip.get(membership_pointer),out_var.fuzzysets.get(counter).fsName);

        counter++;


        for(int i= membership_pointer+1;i<memeberShip.size();i++)// i said membership.size-1 as i will call set by index
        {

            classifier.put(memeberShip.get(i),out_var.fuzzysets.get(counter).fsName);

            if (memeberShip.get(i)>max){

                max=memeberShip.get(i);

            }
            counter++;

        }
        predicted_type=classifier.get(max);

    }

    void run(FileWriter fileWriter) throws IOException {

        fileWriter.write("Running the simulation...\n");

        fuzzification();
        fileWriter.write("Fuzzification => done\n");


        inference();
        fileWriter.write("Inference => done\n");

        defuzzification();
        fileWriter.write("Defuzzification => done\n");

        fileWriter.write("The predicted "+vars.get(vars.size()-1).varName+ " is " + predicted_type +   " (" + predicted_value +  ")\n" );
    }


}
