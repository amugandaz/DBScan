/*
 * Nom : Aaron Muganda
 * Numéro d'étudiant : 300246117
 */

import java.util.*;

public class NearestNeighborsKD {

    private KDTree kdtree; // variable d instance de l' arbre kdtree
    private List<Point3D> neighbors; // variable qui représente la

    // construct with list of points
    public NearestNeighborsKD(List<Point3D> points) {
        kdtree = new KDTree();
        neighbors = points;
        // pour chaque point 3D dans la liste passé au constructeur on le met dans notre
        // arbre
        for (Point3D point : neighbors) {
            kdtree.add(point);
        }

    }

    // method searching that permit to find the neighbors of a point in a kdtree
    public List<Point3D> rangeQuery(Point3D P, double eps) {
        List<Point3D> neighbors = new ArrayList<Point3D>();
        rangeQuery(P, eps, neighbors, kdtree.root());
        return neighbors;

    }

    private void rangeQuery(Point3D P, double eps, List<Point3D> N, KDTree.KDnode node) {
        if (node == null) {
            return;
        }
        if (P.distance(node.point) < eps) {
            N.add(node.point);
        }
        if (P.get(node.axis) - eps <= node.value) {
            rangeQuery(P, eps, N, node.left);
        }
        if (P.get(node.axis) + eps > node.value) {
            rangeQuery(P, eps, N, node.right);
        }
        return;
    }

}