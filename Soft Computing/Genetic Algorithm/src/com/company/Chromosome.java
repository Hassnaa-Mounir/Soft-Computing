package com.company;

import java.util.Random;

public class Chromosome implements Comparable<Chromosome>
{
    public double[] genes;
    public double fitness;
    public Double fitnessUpperBound;

    public Chromosome(int degree) {
        genes = new double[degree+1];
        fitnessUpperBound=0.0;
    }

    public Chromosome(Chromosome c) {
        fitness=c.fitness;
        genes=new double[c.genes.length];
        for(int i=0;i<genes.length;++i) {
            genes[i]=c.genes[i];
        }
        fitnessUpperBound=c.fitnessUpperBound;
    }

    public void generateRandomGenes(double low,double high) {
        for(int i=0;i<genes.length;++i) {
            Random random = new Random();
            genes[i]=(high-low)*random.nextDouble()+low;
        }
    }

    public void singleChromosomeFitness(Point[] points) {

        fitness = 0.0;
        for (Point point : points) {
            int x = 1;
            double y = 0;

            for (double gene : genes) {
                y += x * gene;
                x *= point.x;
            }
            fitness += (point.y - y) * (point.y - y);
        }
        fitness /= points.length;
        fitness = 1.0/fitness;
    }

    public void singleCrossOver(int point, Chromosome c) {

        for(int i=0;i<point;++i)
            genes[i]=c.genes[i];
    }

    @Override
    public int compareTo(Chromosome chromosome) {
        return Double.compare(this.fitnessUpperBound, chromosome.fitnessUpperBound);
    }
}