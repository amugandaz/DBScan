/*
 * Nom : Aaron Muganda
 * Numéro d'étudiant : 300246117
 */

import java.util.List;

public class Exp2 {
    public static void main(String[] args) throws Exception{

        double eps= Double.parseDouble(args[1]); // variable qui lit la valeur de eps
        
        // reads the csv file
        List<Point3D> points= Exp1.read(args[2]);

        int nbPointsVisited = 0;    //représente le nombre de points qui seront visités

        //exécute le code pour trouver les voisins d'un point en utilisant la méthode linéaire
        if(args[0].equals("lin")){
            // creates the NearestNeighbor instance
	        NearestNeighbors a = new NearestNeighbors(points);
            //temps nécessaire pour trouver les voisins de tous les points parcourus
            long tempsTotalLineair = 0;
            //temps moyen pour exécuter rangequery avec la méthode linéaire
            long tempsMoyenLineair = 0 ; 


            for(int i=0; i<= points.size() -1; i = i+Integer.parseInt(args[3])){
                Point3D point = points.get(i);  //le point dont on va trouver les voisins
                
                long startTime = System.nanoTime(); //moment où on commence à trouver les voisins du point de façon linéaire
                a.rangeQuery(point, eps);
                long endTime = System.nanoTime(); //fin de la recherce des voisins de façon linéaire
                long duration = (endTime - startTime);  //durée de la recherche en nanosecondes
                tempsTotalLineair = tempsTotalLineair + duration; //ajout de la durée au temps total de traversée des points de façon linéaire
                 
                nbPointsVisited++; //incrémentation du nombre de points visités
            }

            //calcul du temps moyen pour trouver les voisins d'un point avec l'approche linéaire
            tempsMoyenLineair = tempsTotalLineair / (nbPointsVisited * 1000000);

            System.out.println("Le temps moyen pour trouver les voisins d'un point avec l'approche linéaire est : "
            +tempsMoyenLineair+" ms");
        }

        //exécute le code pour trouver les voisins d'un point en utilisant l'arbre k-d tree
        else if(args[0].equals("kd")){
            // creates the NearestNeighborsKD instance
            NearestNeighborsKD nKd = new NearestNeighborsKD(points);
            //temps nécessaire pour trouver les voisins de tous les points parcourus
            long tempsTotalKdtree = 0;
            //temps moyen pour exécuter rangequery avec la méthode du kdtree
            long tempsMoyenKdtree = 0;

            

            for(int i=0; i<= points.size() -1; i = i+Integer.parseInt(args[3])){
                Point3D point = points.get(i);  //le point dont on va trouver les voisins
                
                long startTimeKd = System.nanoTime(); //moment où on commence à trouver les voisins du point dans le kdtree
                nKd.rangeQuery(point, eps);
                long endTimeKd = System.nanoTime(); //fin de la recherce des voisins dans le kdtree
                long durationKd = (endTimeKd - startTimeKd);  //durée de la recherche en nanosecondes dans le kdtree
                tempsTotalKdtree = tempsTotalKdtree + durationKd; //ajout de la durée au temps total de traversée des points dans le kdtree

                nbPointsVisited++; //incrémentation du nombre de points visités
            }

            //calcul du temps moyen pour trouver les voisins d'un point en utilisant un kdtree
            tempsMoyenKdtree = tempsTotalKdtree / (nbPointsVisited * 1000000);

            System.out.println("Le temps moyen pour trouver les voisins d'un point en utilisant un arbre kdtree est : "
            +tempsMoyenKdtree+" ms");
        }
       
    }
}
