/*
 * Incomplete Experiment 1 
 *
 * CSI2510 Algorithmes et Structures de Donnees
 * www.uottawa.ca
 *
 * Robert Laganiere, 2022
 *
*/ 

/*
 * Nom : Aaron Muganda
 * Numéro d'étudiant : 300246117
 */

import java.util.List;
import java.util.ArrayList;

import java.io.*;  
import java.util.Scanner;  

public class Exp1 {
  
  // reads a csv file of 3D points (rethrow exceptions!)
  public static List<Point3D> read(String filename) throws Exception {
	  
    List<Point3D> points= new ArrayList<Point3D>(); 
	double x,y,z;
	
	Scanner sc = new Scanner(new File(filename));  
	// sets the delimiter pattern to be , or \n \r
	sc.useDelimiter(",|\n|\r");  

	// skipping the first line x y z
	sc.next(); sc.next(); sc.next();
	
	// read points
	while (sc.hasNext())  
	{  
		x= Double.parseDouble(sc.next());
		y= Double.parseDouble(sc.next());
		z= Double.parseDouble(sc.next());
		points.add(new Point3D(x,y,z));  
	}   
	
	sc.close();  //closes the scanner  
	
	return points;
  }

  //this method saves data in a file
  public static void save(String filename, List<Point3D> DB)
			throws FileNotFoundException, UnsupportedEncodingException {
		// initialize writer and set header
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		writer.println("x,y,z");
		// write each point with its corresponding color
		for (Point3D pt : DB) {
			String xyz = pt.getX() + "," + pt.getY() + "," + pt.getZ();
			writer.println(xyz);
		}
		writer.close();
	}
  
  public static void main(String[] args) throws Exception {  
  
    /*
	 * // not reading args[0]
	 * double eps= Double.parseDouble(args[1])
	 * 
	 * // reads the csv file
	 * List<Point3D> points= Exp1.read(args[2])
	 * 
	 * Point3D query= new Point3D(Double.parseDouble(args[3])
	 * Double.parseDouble(args[4])
	 * Double.parseDouble(args[5]))
	 * 
	 */

	String[] params = new String[] { "6", "0.05", "Point_Cloud_1.csv", "14.15982089", "4.680702457",
		"-0.133791584" };

	// not reading args[0]
	double eps = Double.parseDouble(params[1]);

	// reads the csv file
	List<Point3D> points = Exp1.read(params[2]);

	Point3D query = new Point3D(Double.parseDouble(params[3]),
			Double.parseDouble(params[4]),
			Double.parseDouble(params[5]));
	// creates the NearestNeighbor instance
	NearestNeighbors nn = new NearestNeighbors(points);
	List<Point3D> neighbors = nn.rangeQuery(query, eps);

	// creates the NearestNeighborsKD instance
	NearestNeighborsKD nKd = new NearestNeighborsKD(points);
	List<Point3D> neighborsKD = nKd.rangeQuery(query, eps);

	System.out.println("number of neighbors lin= " + neighbors.size());
	System.out.println("number of neighbors kd= " + neighborsKD.size());

	// sauvegarde le fichier contenant les voisins obtenus avec la méthode linéaire
	save("pt" + params[0] + "_lin" + ".txt", neighbors);

	// sauvegarde le fichier contenant les voisins obtenus avec la méthode de
	// l'arbre kd
	save("pt" + params[0] + "_kd" + ".txt", neighborsKD);
	// System.out.println(neighbors);
  }   
}
