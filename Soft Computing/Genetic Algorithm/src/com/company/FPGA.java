package com.company;

import java.util.Arrays;
import java.util.Random;


class Point {
    public double x;
    public double y;
}


public class FPGA
{

        private static final int popSize =100000;
        private static final double maxGeneration=5.0;
        private static final double LB = -10.0;
        private static final double UB = 10.0;

        private static Chromosome[] getRandomPopulation(int degree) {

            Chromosome[] population = new Chromosome[FPGA.popSize];

            for(int i = 0; i< FPGA.popSize; ++i) {
                population[i] = new Chromosome(degree);
                population[i].generateRandomGenes(LB, UB);
            }

            return population;
        }

        private static void fitness(Chromosome []population, Point[] points) {
            for (Chromosome chromosome : population) {
                chromosome.singleChromosomeFitness(points);
            }
        }

        private static Chromosome[] selection(Chromosome[] population) {
            Chromosome[] selectedChromosomes=new Chromosome[FPGA.popSize];
            calculateUpperBound(population);

            for(int i = 0; i< FPGA.popSize; ++i) {
                Random random=new Random();
                double r = (population[population.length-1].fitnessUpperBound)*random.nextDouble();
                selectedChromosomes[i]=population[upper_bound(population,r)];

            }
            return selectedChromosomes;
        }

        private static Chromosome[] crossover(Chromosome[] selectedChromosomes) {
            Chromosome[] offsprings = Arrays.copyOf(selectedChromosomes, selectedChromosomes.length);
            for(int i=0;i<selectedChromosomes.length-1;i+=2) {
                Random r1 = new Random();
                int point = r1.nextInt(selectedChromosomes[i].genes.length);
                double r2=r1.nextDouble();
                if(r2<0.6)
                {

                    Chromosome tmp = new Chromosome(offsprings[i]);
                    offsprings[i].singleCrossOver(point, offsprings[i+1]);
                    offsprings[i+1].singleCrossOver(point, tmp);
                }
            }
            return offsprings;
        }

        private static void mutation(Chromosome[] offsprings, int t, int degree) {

            Random r1 = new Random();
            double r ,doMutation,dependency = 0.5;
            double y,deltaL,deltaU,amounfOfMutation;

            for (Chromosome offspring : offsprings) {
                for (int j = 0; j < (degree + 1); j++) {
                    r = r1.nextDouble();
                    deltaL = offspring.genes[j] - LB;
                    deltaU = UB - offspring.genes[j];

                    if (r > 0.5)
                        y = deltaU;
                    else
                        y = deltaL;

                    amounfOfMutation = y * (1 - Math.pow(r, Math.pow(1 - (t / maxGeneration), dependency)));

                    doMutation = r1.nextDouble();
                    if (doMutation <= 0.01) {

                        if (r > 0.5) {
                            offspring.genes[j] += amounfOfMutation;
                        } else {
                            offspring.genes[j] -= amounfOfMutation;
                        }

                    }
                }
            }
        }

//        private static void replacement(Chromosome[] population, Chromosome [] offsprings) {
//            population=Arrays.copyOf(offsprings, offsprings.length);
//        }

    public static Chromosome run(int degree, Point[]points)
    {

        // Generate Random Population
        Chromosome[] population= getRandomPopulation(degree);

        for(int t=0;t<maxGeneration;t++)
        {
            //Fitness Function
            fitness(population, points);

            //Selection
            Chromosome[] selectedChromosomes = selection(population);

            //CrossOver
            Chromosome[] offsprings = crossover(selectedChromosomes);

            //Mutation
            mutation(offsprings,t, degree);

            //Replacement
            //replacement(population, offsprings);
        }

        // get best chromosome
        double fitness=population[0].fitness;
        int index=0;

        for(int k=1;k<population.length;k++)
        {
            if(fitness<population[k].fitness)
            {
                index=k;
                fitness=population[k].fitness;
            }
        }
        return population[index];
    }

        public static int upper_bound(Chromosome[] population, double key) {
            int size = population.length;
            int start = 0;
            int end = size-1;
            int mid = (start + end)/2;
            while (true) {
                int cmp = population[mid].fitnessUpperBound.compareTo(key);
                if (cmp == 0 || cmp < 0) {
                    start = mid+1;
                    if (end < start)
                        return mid<size-1?mid+1:size-1;
                } else {
                    end = mid-1;
                    if (end < start)
                        return mid;
                }
                mid = (start + end)/2;
            }
        }

        private static void calculateUpperBound(Chromosome[] population) {
            population[0].fitnessUpperBound=population[0].fitness;
            for(int i=1;i<population.length;++i) {
                population[i].fitnessUpperBound=population[i].fitness+population[i-1].fitnessUpperBound;
            }
        }


}
