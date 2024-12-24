/*
 * Nom : Aaron Muganda
 * Numéro d'étudiant : 300246117
 */

public class KDTree {
    public class KDnode {
        public Point3D point;
        public int axis;
        public double value;
        public KDnode left;
        public KDnode right;

        public KDnode(Point3D pt, int axis) {
            this.point = pt;
            this.axis = axis;
            this.value = pt.get(axis);
            left = right = null;
        }

    }

    // ajoute un noeud dans l' arbre
    public void add(Point3D point) {
        this.root = insert(point, root, 0);

    }

    private KDnode root;

    // construct empty tree
    public KDTree() {

        root = null;
    }

    public KDnode insert(Point3D p, KDnode node, int axis) {
        if (node == null) {
            node = new KDnode(p, axis);
        } else if (p.get(axis) <= node.value) {
            node.left = insert(p, node.left, (axis + 1) % 3);
        } else {
            node.right = insert(p, node.right, (axis + 1) % 3);
        }

        return node;

    }

    public KDnode root() {
        return root;
    }

}
