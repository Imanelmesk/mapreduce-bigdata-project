# **TP MapReduce - Hadoop en Local**

## **Description du Projet**

Ce projet consiste en plusieurs questions de traitement de données à l'aide du framework **Hadoop MapReduce**. Chaque question repose sur la structure suivante :

- **Mapper** : `MoviesQMapper.java`
- **Reducer** : `MoviesQReducer.java`
- **Driver** : `MoviesQDriver.java`

`Q` correspond au numéro de la question (exemple : Q0, Q1, Q2, ...).

Les tâches ont été exécutées en **local** avec une installation manuelle d'**Hadoop** sans utiliser **Docker**.

---

## **Pré-requis**

Avant d'exécuter ce projet, assure-toi d'avoir :

1. **Java 8+** installé.
2. **Hadoop 3.3.6** correctement configuré en mode local (sans Docker).
   - Configuration des variables d'environnement : `HADOOP_HOME`, `PATH`.
3. **IntelliJ IDEA** (ou tout autre IDE supportant Java) configuré pour exécuter des applications MapReduce.

---

## **Structure du Projet**

La structure des fichiers est organisée comme suit :

```plaintext
MapReduce/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── r403/
│   │   │   │   ├── Movies0Mapper.java
│   │   │   │   ├── Movies0Reducer.java
│   │   │   │   ├── Movies0Driver.java
│   │   │   │   ├── Movies1Mapper.java
│   │   │   │   ├── Movies1Reducer.java
│   │   │   │   ├── Movies1Driver.java
│   │   │   │   ├── Movies2Mapper.java
│   │   │   │   ├── Movies2Reducer.java
│   │   │   │   ├── Movies2Driver.java
│   │   │   │   ├── ...
│
├── resources/
│   ├── movies.csv
│   ├── productions.csv
│   ├── studios.csv
│
├── output/
│   └── (Résultats des traitements MapReduce)
│
└── pom.xml
```

---

## **Questions Réalisées**

### **Question 0 : Agrégation - Nombre de films par studio**

- **Mapper** : `Movies0Mapper.java`
- **Reducer** : `Movies0Reducer.java`
- **Driver** : `Movies0Driver.java`

### **Question 1 : Agrégation - Nombre de films par année**

- **Mapper** : `Movies1Mapper.java`
- **Reducer** : `Movies1Reducer.java`
- **Driver** : `Movies1Driver.java`

### **Question 2 : Projection - Liste des noms des studios**

- **Mapper** : `Movies2Mapper.java`
- **Reducer** : `Movies2Reducer.java`
- **Driver** : `Movies2Driver.java`

### **Question 3 : Sélection - Studios où le pays est FR**

- **Mapper** : `Movies3Mapper.java`
- **Reducer** : `Movies3Reducer.java`
- **Driver** : `Movies3Driver.java`

### **Question 4 : Agrégation et Comparaison - Film le plus long par année**

- **Mapper** : `Movies4Mapper.java`
- **Reducer** : `Movies4Reducer.java`
- **Driver** : `Movies4Driver.java`

### **Question 5 : Comparaison - Dernier film (le plus récent)**

- **Mapper** : `Movies5Mapper.java`
- **Reducer** : `Movies5Reducer.java`
- **Driver** : `Movies5Driver.java`

### **Question 6 : Comparaison - Pays avec le maximum de studios**

- **Mapper** : `Movies6Mapper.java`
- **Reducer** : `Movies6Reducer.java`
- **Driver** : `Movies6Driver.java`

---

## **Exécution des Scripts**

Pour chaque question, une **nouvelle configuration** a été créée pour exécuter le programme. Voici les détails :

1. **Configuration** : Une nouvelle configuration **Run/Debug** a été ajoutée dans IntelliJ IDEA.
2. **Nom du driver** : par exemple MoviesQDriver avec Q représentant les numéros des questions.

---

## **Points Importants**

1. **Suppression des résultats existants**  
   Avant chaque exécution, les répertoires de sortie existants sont supprimés automatiquement pour éviter les erreurs.

2. **Affichage des résultats dans la console**  
   Les résultats de chaque tâche MapReduce sont affichés directement dans la console en lisant le fichier `part-r-00000` généré.

---

## **Conclusion**

Ce projet illustre les différentes opérations de **MapReduce** appliquées à des fichiers CSV. Chaque question présente un cas concret d'utilisation des **Mappers** et **Reducers** pour effectuer des agrégations, sélections et comparaisons.

---

**Auteur** : Imane El Meskiri  
**Technologies** : Java, Hadoop 3.3.6  
**Environnement** : Exécution en local sans Docker
