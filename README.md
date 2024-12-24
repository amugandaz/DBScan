# Projet DBScan

Ce projet implémente l'algorithme **DBScan** (Density-Based Spatial Clustering of Applications with Noise) et inclut des expériences pour analyser son efficacité sur différents ensembles de données.

## Structure du Projet

### 1. Fichiers d'implémentation
- **DBScan.java** : Fichier principal contenant l'algorithme DBSCAN.
- **Point3D.java** : Classe pour représenter des points dans un espace tridimensionnel.
- **KDTree.java** et **NearestNeighbors.java** : Structures et fonctions pour optimiser la recherche de voisins.

### 2. Fichiers d'expériences
- **Exp1.java, Exp2.java, Exp3.java** : Expériences démontrant l'application de DBSCAN sur différents ensembles de données.
- **Point_cloud_1, Point_cloud_2, Point_cloud_3** : Ensembles de données de test avec différents nombres de clusters et configurations.

### 3. Rapport
- **Rapport.pdf** : Document détaillant les expériences, les configurations de paramètres (epsilon, MinPts) et les résultats obtenus.

## Résumé des Expériences
1. **Exp1** : Clustering sur un ensemble de données avec 1 cluster principal.
2. **Exp2** : Analyse des performances sur un ensemble de données avec 2 clusters.
3. **Exp3** : Application sur un ensemble plus complexe avec 3 clusters et des zones de bruit.

Les résultats sont sauvegardés dans les fichiers `Point_cloud_*`.

## Instructions d'Exécution
1. Compilez les fichiers Java :
   ```bash
   javac DBScan.java Exp1.java Exp2.java Exp3.java
