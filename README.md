# Multi-Attribute Decision-Making (MADM) Techniques in Ontology Ranking

## Introduction
This project implements two Multi-Criteria Decision-Making (MCDM) techniques, focusing on two Multi-Attribute Decision-Making (MADM) methods: **Elimination and Choice Expressing Reality (ELECTRE)** and **Technique for Order Preference by Similarity to Ideal Solution (TOPSIS)**.

This study implements and compares the performance of these techniques in ontology ranking. The dataset used in this project contains 50 ontologies and 11 quality metrics.

## Quality Metrics
The set of quality metrics used to evaluate the ontologies includes:

- **Absolute Root Cardinality (ARC)**: Quantifies the total number of root concepts in the ontology.
- **Absolute Leaf Cardinality (ALC)**: Measures the total count of leaf concepts.
- **Average Depth (AVD)**: Captures the average depth of concepts in the hierarchy.
- **Maximum Depth (MXD)**: Highlights the deepest level of specialisation.
- **Average Breadth (AVB)**: Indicates the average number of immediate children for each concept.
- **Maximum Breadth (MXB)**: Indicates the maximum number of immediate children for each concept.
- **Average Population (AVP)**: Reflects the average number of instances associated with each concept.
- **Class Richness (CR)**: Gauges the diversity of classes within the ontology.
- **Attribute Richness (AR)**: Gauges the diversity of attributes within the ontology.
- **Inheritance Richness (IR)**: Gauges the diversity of inheritance relationships within the ontology.
- **Relationship Richness (RR)**: Gauges the diversity of semantic associations within the ontology.

These metrics collectively provide a framework for assessing the quality and effectiveness of ontologies.

## Comparison of TOPSIS and ELECTRE
After obtaining the ranked lists from both algorithms, we decided to compare them to identify any similarities. We utilised Kendall's Tau and Spearman's Rank Correlation Coefficient to assess the degree of similarity between the lists.

## Rank Comparison
Here are the rankings obtained from both TOPSIS and ELECTRE:

| Rank | TOPSIS Ranking | Order Vector | ELECTRE Ranking |
|------|----------------|--------------|-----------------|
| 1    | O10            | 1            | O10             |
| 2    | O12            | 2            | O12             |
| 3    | O24            | 8            | O47             |
| 4    | O13            | 9            | O18             |
| 5    | O22            | 3            | O24             |
| 6    | O37            | 6            | O37             |
| 7    | O2             | 13           | O11             |
| 8    | O47            | 4            | O13             |
| 9    | O18            | 7            | O2              |
| 10   | O48            | 16           | O20             |
| 11   | O39            | 5            | O22             |
| 12   | O27            | 20           | O16             |
| 13   | O11            | 21           | O49             |
| 14   | O1             | 12           | O27             |
| 15   | O29            | 11           | O39             |
| 16   | O20            | 14           | O1              |
| 17   | O36            | 24           | O17             |
| 18   | O34            | 18           | O34             |
| 19   | O19            | 10           | O48             |
| 20   | O16            | 28           | O38             |
| 21   | O49            | 26           | O43             |
| 22   | O46            | 33           | O31             |
| 23   | O5             | 15           | O29             |
| 24   | O17            | 30           | O50             |
| 25   | O3             | 46           | O35             |
| 26   | O43            | 27           | O9              |
| 27   | O9             | 17           | O36             |
| 28   | O38            | 19           | O19             |
| 29   | O4             | 23           | O5              |
| 30   | O50            | 29           | O4              |
| 31   | O8             | 36           | O30             |
| 32   | O21            | 22           | O46             |
| 33   | O31            | 25           | O3              |
| 34   | O45            | 31           | O8              |
| 35   | O6             | 34           | O45             |
| 36   | O30            | 32           | O21             |
| 37   | O44            | 38           | O41             |
| 38   | O41            | 42           | O23             |
| 39   | O26            | 35           | O6              |
| 40   | O15            | 37           | O44             |
| 41   | O7             | 41           | O7              |
| 42   | O23            | 43           | O42             |
| 43   | O42            | 39           | O26             |
| 44   | O14            | 40           | O15             |
| 45   | O33            | 45           | O33             |
| 46   | O35            | 48           | O28             |
| 47   | O25            | 44           | O14             |
| 48   | O28            | 49           | O32             |
| 49   | O32            | 47           | O25             |
| 50   | O40            | 50           | O40             |

## Usage
To use this project, clone the repository and compile the Java files. Ensure you have a compatible Java development environment set up.

```bash
git clone https://github.com/yourusername/yourrepository.git
cd yourrepository
javac *.java
java MainClass
