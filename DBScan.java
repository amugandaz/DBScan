/*
 * Nom : Aaron Muganda
 * Numéro d'étudiant : 300246117
 */

import java.io.*;
import java.util.*;

public class DBScan {
	// Compteur de cluster
	int c;

	// le minimum de point pour qu'un point fasse partie d'un cluster
	int minPts;

	// Minpts par defaut, si un minimun de point n'est pas donnee au lancement du
	// programmme
	final public static int DEFAULT_MINPTS = 12;

	// le rayon du cercle autour du point servant a definir la region de voisinage
	Double eps;

	// epsilone par defaut, si un rayon eps n'est pas donnee au lancement du
	// programmme
	final public static double DEFAULT_EPS = 0.8;

	// Nombre de cluster
	int numOfClusters;
	public List<Point3D> listOfPoints;

	/*
	 * Constructeur
	 * 
	 */
	public DBScan(List<Point3D> newList) {
		listOfPoints = newList;
		numOfClusters = 0;
	}

	/*
	 * Main methode
	 */
	public static void main(String[] args) {
		DBScan scan;
		List<Point3D> listPoints;
		String fileName;

		// Si aucun argument n'a ete detecte lors du lancement du programme, Une liste
		// vide par default sera utilisee.
		if ((args.length == 0)) {
			System.out.println("Vous n'avez rentre aucun argument. Des valeurs definis par defaut seront utilisees.");
			System.out.println("Une liste de points vide sera utilise par defaut.");
			listPoints = new ArrayList<Point3D>();
			fileName = "No File Given.csv";

		} else {
			fileName = args[0];
			listPoints = DBScan.read(args[0]);

		}

		// Lancement de l'algorithm DBScan.
		scan = new DBScan(listPoints);

		/*
		 * Verifie la validite de epsilone avant de le laisser passer
		 * Si aucune valeurn'a ete rentrer pour epsilone lors du lancement du
		 * programme,une valeur par default sera utilisee.
		 * Si une valeur negative est rentre pour epsilone lors du lancement du
		 * programme,une valeur par default sera utilisee.
		 */
		try {
			if (Double.parseDouble(args[1]) > 0) {
				scan.setEps(Double.parseDouble(args[1]));
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(
					"Vous n'avez pas defini eps lors du lancement du programe , alors une valeur eps = 0.8 est utilisee par defaut pour ce test.");
			scan.setEps(DEFAULT_EPS);

		} catch (NumberFormatException e) {
			System.out.println(
					"Le format que vous avez rentre pour eps n'est pas valide, alors une valeur eps = 0.8 est utilisee par defaut pour ce test.");
			scan.setEps(DEFAULT_EPS);
		} catch (Exception e) {
			if (Double.parseDouble(args[1]) < 0)
				System.out.println(
						"Le rayon ne peut pas etre negative, alors une valeur eps = 0.8 est utilisee par defaut pour ce test.");
			scan.setEps(DEFAULT_EPS);
		}
		/*
		 * Verifie la validite de minPts avant de le laisser passer
		 * Si aucune valeur n'a ete rentrer pour minPts lors du lancement du
		 * programme,une valeur par default sera utilisee.
		 * Si une valeur negative est rentre pour epsilone lors du lancement du
		 * programme,une valeur par default sera utilisee.
		 */

		try {
			if (Integer.parseInt(args[2]) > 0) {
				scan.setMinPts(Integer.parseInt(args[2]));
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(
					"Vous n'avez pas defini MinPts lors du lancement du programe, une valeur MinPts = 12 sera utilisee par defaut.");
			scan.setMinPts(DEFAULT_MINPTS);
		} catch (NumberFormatException e) {
			System.out.println(
					"Le format que vous avez rentre pour minPts n'est pas valide, une valeur MinPts = 12 sera utilisee par defaut.");
			scan.setEps(DEFAULT_EPS);
		} catch (Exception e) {
			if (Double.parseDouble(args[2]) < 0) {
				System.out.println(
						"Le nombre de points ne peut pas etre negatif, une valeur MinPts = 12 sera utilisee par defaut.");
				scan.setMinPts(DEFAULT_MINPTS);
			}
		}

		// Cherche les clusters
		scan.findClusters();

		// Le nom du fichier a sortir
		String saveName = fileName + "_clusters_" + scan.eps + "_" + scan.minPts + "_" + scan.getNumberOfClusters()
				+ ".csv";

		// sauvegardes un fichier contenant les points classifies
		scan.save(saveName);

		/*
		 * Calcules le nombre de points que contient chaque cluster.
		 * Ces valeurs sont stoquer dans une liste
		 */
		int[] clusterSize = new int[scan.getNumberOfClusters() + 1];
		for (int i = 0; i < clusterSize.length; i++) {
			clusterSize[i] = 0;
		}

		for (int j = 0; j < clusterSize.length; j++) {
			for (int i = 0; i < scan.listOfPoints.size(); i++) {
				if (scan.listOfPoints.get(i).label == j) {
					clusterSize[j]++;
				}
			}
		}

		// Nombre d'outliers
		int outliers = clusterSize[0];

		// Fait sortir le nombre de cluster trouves
		System.out.println("Nombre de Clusters nC = " + scan.getNumberOfClusters());

		// Stoque les labels et leursquantite respective dans un dictionnaire.
		Map<String, String> aMap = scan.dictionary(clusterSize);

		// arrange la liste contenant le size de chaque cluster du plus grand au plus
		// petit.
		Arrays.sort(clusterSize);

		for (int k = clusterSize.length - 1; k > 0; k--) {
			if (aMap.containsKey(String.valueOf(clusterSize[k]))) {
				System.out.println(
						"Cluster " + aMap.get(String.valueOf(clusterSize[k])) + " have:" + clusterSize[k] + " points.");
			}

		}
		// Imprime le nombre de outliers<Noise> trouves.v
		System.out.println("On a trouve " + outliers + " outliers.");

	}

	/*
	 * Methode setEps pour definir eps
	 * 
	 * @param eps
	 * 
	 * @return la valeur de eps
	 */
	public double setEps(double eps) {
		this.eps = eps;
		return eps;
	}

	/*
	 * Methode MinPts pour definir eps.
	 * 
	 * @param minPts.
	 * 
	 * @retourne la valeur de minPts.
	 */
	public int setMinPts(int minPts) {
		this.minPts = minPts;
		return minPts;
	}

	/*
	 * Methode pour classifier les points.
	 * DBSscan Algorithm
	 */
	public void findClusters() {
		c = 0;

		for (Point3D pt : listOfPoints) {

			if (pt.label >= 0) {
				continue;
			}
			List<Point3D> neighbors;
			neighbors = new ArrayList<Point3D>();
			NearestNeighbors N = new NearestNeighbors(listOfPoints);
			neighbors = N.rangeQuery(pt, eps);
			if (neighbors.size() < minPts) {
				pt.label = 0;
				continue;
			}
			c = c + 1;
			pt.label = c;
			Stack<Point3D> S = new Stack<Point3D>();
			for (int i = 0; i < neighbors.size(); i++) {
				S.push(neighbors.get(i));
			}
			while (!S.isEmpty()) {
				Point3D Q = S.pop();
				if (Q.label == 0) {
					Q.label = c;
				}
				if (Q.label >= 0) {
					continue;
				}
				Q.label = c;
				NearestNeighbors newN = new NearestNeighbors(listOfPoints);
				List<Point3D> neighborsOfQ;
				neighborsOfQ = new ArrayList<Point3D>();
				neighborsOfQ = newN.rangeQuery(Q, eps);

				if (neighborsOfQ.size() >= minPts) {
					for (int i = 0; i < neighborsOfQ.size(); i++) {
						S.push(neighborsOfQ.get(i));

					}

				}

			}

		}

	}

	/*
	 * Methode pour trouver le nombre de cluster
	 * 
	 * @return le nombre de clusters
	 */
	public int getNumberOfClusters() {
		return c;
	}

	/*
	 * Methode pour sauvegarder les references aux clusters a leur quantite
	 * respective
	 * 
	 * @param clustersSize
	 * 
	 * @return un dictionnaire contenant
	 */
	public Map<String, String> dictionary(int[] clustersSize) {
		Map<String, String> map = new HashMap<>();

		for (int i = 0; i < clustersSize.length; i++) {
			map.put(String.valueOf(clustersSize[i]), String.valueOf(i));

		}

		return map;
	}

	/*
	 * This read a csv file and return a list of Point3D
	 * 
	 * @param filename
	 * 
	 * @return une liste de Point3D
	 */
	public static List<Point3D> read(String filename) {
		List<Point3D> raidList;
		raidList = new ArrayList<Point3D>();

		String line = "";
		try {
			// parsing a csv file into Scanner class constructor

			BufferedReader reader = new BufferedReader(new FileReader(filename));

			line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] point3d = line.split(",");
				double[] coord = new double[point3d.length];
				for (int i = 0; i < point3d.length; i++) {
					coord[i] = Double.parseDouble(point3d[i]);
				}
				raidList.add(new Point3D(coord[0], coord[1], coord[2]));

			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Votre ficher n'a pas ete retrouve!");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return raidList;

	}

	/*
	 * Return a list of point
	 */
	public List<Point3D> getPoints() {

		return listOfPoints;
	}

	/*
	 * Methode pour sauvegarder un fichier csv
	 * 
	 * @param filename
	 */
	public void save(String filename) {

		try {
			File csvfile = new File(filename);

			// Creates a PrintWriter
			PrintWriter output = new PrintWriter(csvfile);
			List<Double[]> colours = rGBColour();
			output.println("x" + "," + "y" + "," + "z" + "," + "C" + "," + "R" + "," + "G" + "," + "B");
			for (Point3D pt : listOfPoints) {
				output.println(toString(pt, colours));
			}
			// Close the writer
			output.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Methode pour definir une liste de couleurs egale au nombre de clusters trouve
	 * 
	 * @return une liste de liste de RGB valeurs
	 */
	public List<Double[]> rGBColour() {
		double r, g, b;
		Random rand = new Random();
		List<Double[]> colours = new ArrayList<Double[]>();
		for (int i = 0; i < this.getNumberOfClusters() + 1; i++) {
			r = rand.nextDouble(1.0);
			g = rand.nextDouble(1.0);
			b = rand.nextDouble(1.0);
			colours.add(new Double[] { r, g, b });
		}
		return colours;
	}

	/*
	 * Methode pour ecrire les informations d'un points
	 * 
	 * @param p
	 * 
	 * @param colors
	 * 
	 * @return un String contenant les coordonnees d'un point, son label et les
	 * valeurs R G B de sa couleur
	 */
	public String toString(Point3D p, List<Double[]> colors) {

		Double[] ptColour = colors.get(p.label);
		return p.getX() + "," + p.getY() + "," + p.getZ() + "," + p.label + "," + ptColour[0] + "," + ptColour[1] + ","
				+ ptColour[2];
	}

}
