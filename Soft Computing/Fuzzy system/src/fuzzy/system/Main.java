package fuzzy.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Scanner;




public class Main {

    public static void main(String[] args) {
        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);
            FuzzySys fuzzy = new FuzzySys();
            fuzzy.systemName = myReader.nextLine();
            fuzzy.description = myReader.nextLine();
//            System.out.println(fuzzy.systemName);
//            System.out.println(fuzzy.description);

            int num_vars = Integer.parseInt(myReader.nextLine());
                //Variables and their fuzzy sets
//            System.out.println(num_vars);
                for (int i = 0; i < num_vars; i++)

                {
                    //variable details
                    String data = myReader.nextLine();
//                    System.out.println(data);
                    String[] var_parts = data.split("\\s+");
                    Variable var = new Variable();
                    var.varName = var_parts[0];
                    var.varType = var_parts[1].toLowerCase();
                    var.varRange.add(Integer.parseInt(var_parts[2]));
                    var.varRange.add(Integer.parseInt(var_parts[3]));

                    //fuzzy set of this variable
                    int num_fuzzysets = Integer.parseInt(myReader.nextLine());
                    for(int j=0;j<num_fuzzysets;j++)
                    {
                        FuzzySet fSet = new FuzzySet();

                        String set= myReader.nextLine();
                        String[] set_parts = set.split("\\s+");
                        String tri_trap = set_parts[1];//to handle the type of graph
                        fSet.fsName = set_parts[0];
                        fSet.fsType = set_parts[1];
                        tri_trap = tri_trap.toLowerCase();

                        if(tri_trap.equals("trap"))
                        {
                            for (int kk = 2; kk <= 5; kk++)
                            {
                                fSet.fsValues.add(Integer.parseInt(set_parts[kk]));
                            }
                        }
                        else //triangle only 3 points
                        {
                            for (int kk = 2; kk <= 4; kk++)
                            {
                                fSet.fsValues.add(Integer.parseInt(set_parts[kk]));
                            }
                        }

                        var.fuzzysets.add(fSet);

                    }

                    fuzzy.vars.add(var);
                    System.out.println(fuzzy.vars.get(i));
                }


                int num_rules = Integer.parseInt(myReader.nextLine());
                for (int i = 0; i < num_rules; i++)
                {
                    String rule= myReader.nextLine();
                    fuzzy.ruleAnalyzer(rule);

                }

            //System.out.println(fuzzy.rules);

            fuzzy.crispValues.add(Integer.parseInt(myReader.nextLine()));
                fuzzy.crispValues.add(Integer.parseInt(myReader.nextLine()));


            //System.out.println(fuzzy.crispValues);


            myReader.close();

            FileWriter myWriter = new FileWriter("output.txt");


            fuzzy.run(myWriter);





            System.out.println(fuzzy.memeberShip);


            System.out.println("Successfully wrote predicted value to the file.");
            myWriter.close();

        }




        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
