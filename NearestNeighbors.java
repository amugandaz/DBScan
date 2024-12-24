/*
 * Linear neighbors search
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

public class NearestNeighbors {

  private List<Point3D> list; 

  // construct with list of points
  public NearestNeighbors(List<Point3D> list) {
       
    this.list = list; 
  }

  // gets the neighbors of p (at a distance less than eps)
  public List<Point3D> rangeQuery(Point3D p, double eps) {
    // empty list to contain the neighbors
    List<Point3D> new_list = new ArrayList<Point3D>(); 

    for (int i = 0; i < list.size(); i++) {

       if (p.distance(list.get(i)) <= eps) {
        new_list.add(list.get(i));
	   }
    }
	
    return new_list;
  }  
}
