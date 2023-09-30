package com.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class KnapsackGA {

    //random number in a range
    static int getRandomNumber(int min, int max)
    {
        return (int) ((Math.random() * (max - min)) + min);
    }

    // for Generating a random population
    static int scaling(double num)
    {
        return (int) (num + 0.5);
    }

    //Initialization of Population
    static ArrayList<ArrayList<Integer>> population_Init(int itemsNum)
    {
        ArrayList<ArrayList<Integer>> pop = new ArrayList();

        for (int i = 0; i < 5; i++) {
            ArrayList<Integer> chromosome = new ArrayList();

            for (int x = 0; x < itemsNum; x++) {
                chromosome.add(scaling(Math.random()));
            }
            pop.add(chromosome);
        }
        return pop;
    }

    // fitness function
    static int fitness_Fun(ArrayList<Integer> individual , ArrayList<ArrayList<Integer>> items , int weight)
    {
        int total_val=0;
        int total_weight=0;

        for (int i=0 ; i < individual.size(); i++)
        {

            total_val += individual.get(i)* items.get(i).get(1);
            total_weight += individual.get(i)* items.get(i).get(0);

            if( total_weight > weight )
                return 0;
        }


        return total_val;
    }

    // selection Roulette Wheel
    static ArrayList<Integer> selection(ArrayList<Integer> fitness)
    {
        ArrayList<Integer> parents_index = new ArrayList<>();

        ArrayList<Integer> comm_fitness=new ArrayList<>();
        //add first fitness value
        comm_fitness.add(fitness.get(0));

        for(int i=1;i<fitness.size();i++)
        {
            comm_fitness.add(comm_fitness.get(i-1) + fitness.get(i));
        }

        //generate number between 0, last cumulative
        int r1 = getRandomNumber(0, comm_fitness.get(comm_fitness.size()-1));
        int r2 = getRandomNumber(0, comm_fitness.get(comm_fitness.size()-1));
        int index=0;

        //get r1
        for(int i=0;i< comm_fitness.size();i++)
        {
            if(r1 < comm_fitness.get(i))
            {
                parents_index.add(i);
                index=i;
                break;
            }
        }

        //get r2
        for(int i=0;i< comm_fitness.size();i++)
        {
            if(r2< comm_fitness.get(i))
            {
                if(index==i){
                    i=0 ;
                    r2= getRandomNumber(0, comm_fitness.get(comm_fitness.size()-1));
                    continue;
                }
                parents_index.add(i);
                break;
            }
        }

        return parents_index;

    }

    // crossover one point Xc
    static ArrayList<ArrayList<Integer>> crossover(ArrayList<ArrayList<Integer>> population, ArrayList<Integer>parents)
    {
        ArrayList<ArrayList<Integer>> new_population = new ArrayList();

        ArrayList<Integer> child1= population.get(parents.get(0));
        ArrayList<Integer> child2= population.get(parents.get(1));
        int Xc = getRandomNumber(0, population.get(0).size()-1);

        //TODO condition to do cross over or not use it with Pc
        //double rc = Math.random();

        for(int i=0;i<Xc;i++)
        {
            int temp= child1.get(i);
            child1.set(i, child2.get(i));
            child2.set(i, temp);
        }

        new_population.add(child1 );
        new_population.add(child2 );

        return new_population;
    }

    // mutation
    static ArrayList<ArrayList<Integer>> mutation(ArrayList<ArrayList<Integer>> children)
    {
        double Pm =0.1;

        for(int i=0;i< children.size(); i++)
        {
            for(int j=0;j<children.get(i).size();j++)
            {
                double r = Math.random();
                if(r<Pm)
                {
                    ArrayList<Integer> flipped =  children.get(i);
                    int x = children.get(i).get(j);
                    if(x==0)
                        flipped.set(j,1);
                    else
                        flipped.set(j,0);

                    children.set(i,flipped);
                }

            }
            
        }


        return children;
    }

    static int getMaxValueInd(ArrayList<Integer> fitness)
    {
        int max_ind=0,max_val=0;
        for(int i=0;i<fitness.size();i++)
        {
            if(fitness.get(i)>max_val)
            {
                max_val=fitness.get(i);
                max_ind=i;
            }

        }


        return max_ind;
    }

    // Replacement function
    static ArrayList<ArrayList<Integer>> replacement(ArrayList<Integer> old_fit , ArrayList<Integer> new_fit ,
                                                     ArrayList<ArrayList<Integer>> old_pop , ArrayList<ArrayList<Integer>> new_pop)
    {
        int least_fit =old_fit.get(0) ;
        //set new child fit to minimum fit value
        for (int i = 1 ; i < old_pop.size() ; i++)
        {
            if(old_fit.get(i) < least_fit )
                least_fit = old_fit.get(i) ;

        }

        least_fit = old_fit.indexOf(least_fit);

        if(old_fit.get(least_fit) < new_fit.get(0))
        {
            old_pop.set(least_fit, new_pop.get(0));
            old_fit.set(least_fit, new_fit.get(0));

            least_fit = old_fit.get(0) ;

            //set new second child fit to second minimum
            for (int i =1 ; i < old_pop.size() ; i++)
            {
                if(least_fit > old_fit.get(i))
                    least_fit = old_fit.get(i) ;

            }

            least_fit = old_fit.indexOf(least_fit);
            if(old_fit.get(least_fit) <new_fit.get(1))
            {
                old_pop.set(least_fit,new_pop.get(1));
                old_fit.set(least_fit, new_fit.get(1));
            }

        }

        return old_pop;
    }

    public static void main(String[] args) throws IOException {
        // getting data from file

        //hold test cases data
        ArrayList<ArrayList<ArrayList<Integer>>> Knapsack_details = new ArrayList();

        File myInput = new File("input.txt");
        Scanner reader = new Scanner(myInput);
        int testCases = reader.nextInt(); // number of test cases to solve

        for (int i = 0; i < testCases; i++)
            {

                ArrayList<ArrayList<Integer>> items = new ArrayList();


                int knapsack_size = reader.nextInt();
                ArrayList<Integer> size = new ArrayList<>();
                size.add(knapsack_size);
                items.add(size);

                int num_items = reader.nextInt();
                ArrayList<Integer> numItems = new ArrayList<>();
                numItems.add(num_items);
                items.add(numItems);

                for (int j = 0; j < num_items ; j++)
                {

                    ArrayList<Integer> item=new ArrayList(); // carry each item with w, v
                    item.add(reader.nextInt()); //weight
                    item.add(reader.nextInt()); //value

                    items.add(item);
                }

                Knapsack_details.add(items);
            }

         //System.out.println(Knapsack_details);


        for (int x=0 ; x < testCases; x++)
        {
            int number_items = Knapsack_details.get(x).get(1).get(0);

            //init population
            ArrayList<ArrayList<Integer>> population = population_Init(number_items);

            //System.out.println(population);
            int totalWeight=Knapsack_details.get(x).get(0).get(0);     // put total weight into variable
            Knapsack_details.get(x).remove(0);                // delete total weight and
            Knapsack_details.get(x).remove(0);                //num of items from my array list
            ArrayList<Integer> fitness =new ArrayList<>();

            //System.out.println(Knapsack_details);

            //fitness function
           for(int c =0 ; c < population.size(); c++)
           {
               int num = fitness_Fun(population.get(c),Knapsack_details.get(x),totalWeight);
                fitness.add(num);
           }

           //Selection get 2 indexes
            ArrayList<Integer> par_inds = selection(fitness);


           //cross over between two chromosomes in population (par_inds)
            ArrayList<ArrayList<Integer>> new_children = crossover( population, par_inds);


            // mutation Pm=0.1
            new_children = mutation(new_children);

            // new fitness to children
            ArrayList<Integer> new_fit = new ArrayList<>();
            for(int i=0;i<new_children.size();i++)
            {
                int f = fitness_Fun(new_children.get(i), Knapsack_details.get(x), totalWeight);
                new_fit.add(f);
            }

            //replacement
            population= replacement(fitness, new_fit, population,new_children);


            //index of test case
            System.out.println("index of test cases: " + x);

            //total value
            int max_value_ind = getMaxValueInd(fitness);
            System.out.println("Total value: " + fitness.get(max_value_ind));

            int selected_items=0;
            ArrayList<Integer> current_chromosome = population.get(max_value_ind);
            for(int i=0;i<Knapsack_details.get(x).size();i++)
            {
                if(current_chromosome.get(i)==1)
                {
                    selected_items++;

                    //weight
                    System.out.print("W,V " + Knapsack_details.get(x).get(i).get(0) + " " );

                    //value
                    System.out.println(Knapsack_details.get(x).get(i).get(1));
                }

            }
            System.out.println("number of selected items: " + selected_items);



            //System.out.println(fitness);
        }


    }

}