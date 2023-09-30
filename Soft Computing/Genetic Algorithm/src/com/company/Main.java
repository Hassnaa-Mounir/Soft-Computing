package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File file=new File("input.txt");
        Scanner input =new Scanner(file);
        int testCaseNum=input.nextInt();


        FileWriter output = new FileWriter("output.txt");

        for(int i=1;i<=testCaseNum;i++)
        {
            int numOfPoints=input.nextInt();
            int degree=input.nextInt();
            Point[] points =new Point[numOfPoints];

            for(int j=0;j<numOfPoints;j++)
            {
                Point point=new Point();
                point.x=input.nextDouble();
                point.y=input.nextDouble();
                points[j]=point;
            }

            output.write("dataset index: "+i);
            output.write(System.lineSeparator());
            Chromosome chromosome;
            chromosome=FPGA.run(degree, points);
            output.write("Coefficients: ");
            for(int k=0;k<chromosome.genes.length;k++)
            {
                output.write(chromosome.genes[k]+"    ");
            }
            output.write(System.lineSeparator());

            output.write("Mean Square Error: "+(1/chromosome.fitness));
            output.write(System.lineSeparator());
        }
        input.close();
        output.close();

        System.out.println("Results added to output.txt Successfully");

    }
}
