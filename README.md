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

## TOPSIS vs ELECTRE Rankings

| Rank | TOPSIS Ranking | Order Vector | ELECTRE Ranking | Rank | TOPSIS Ranking | Order Vector | ELECTRE Ranking |
|------|----------------|--------------|-----------------|------|----------------|--------------|-----------------|
| 1    | O10            | 1            | O10             | 26   | O43            | 27           | O9              |
| 2    | O12            | 2            | O12             | 27   | O9             | 17           | O36             |
| 3    | O24            | 8            | O47             | 28   | O38            | 19           | O19             |
| 4    | O13            | 9            | O18             | 29   | O4             | 23           | O5              |
| 5    | O22            | 3            | O24             | 30   | O50            | 29           | O4              |
| 6    | O37            | 6            | O37             | 31   | O8             | 36           | O30             |
| 7    | O2             | 13           | O11             | 32   | O21            | 22           | O46             |
| 8    | O47            | 4            | O13             | 33   | O31            | 25           | O3              |
| 9    | O18            | 7            | O2              | 34   | O45            | 31           | O8              |
| 10   | O48            | 16           | O20             | 35   | O6             | 34           | O45             |
| 11   | O39            | 5            | O22             | 36   | O30            | 32           | O21             |
| 12   | O27            | 20           | O16             | 37   | O44            | 38           | O41             |
| 13   | O11            | 21           | O49             | 38   | O41            | 42           | O23             |
| 14   | O1             | 12           | O27             | 39   | O26            | 35           | O6              |
| 15   | O29            | 11           | O39             | 40   | O15            | 37           | O44             |
| 16   | O20            | 14           | O1              | 41   | O7             | 41           | O7              |
| 17   | O36            | 24           | O17             | 42   | O23            | 43           | O42             |
| 18   | O34            | 18           | O34             | 43   | O42            | 39           | O26             |
| 19   | O19            | 10           | O48             | 44   | O14            | 40           | O15             |
| 20   | O16            | 28           | O38             | 45   | O33            | 45           | O33             |
| 21   | O49            | 26           | O43             | 46   | O35            | 48           | O28             |
| 22   | O46            | 33           | O31             | 47   | O25            | 44           | O14             |
| 23   | O5             | 15           | O29             | 48   | O28            | 49           | O32             |
| 24   | O17            | 30           | O50             | 49   | O32            | 47           | O25             |
| 25   | O3             | 46           | O35             | 50   | O40            | 50           | O40             |

## Ranking Comparison Results

We utilised the TOPSIS ranking results as the Rank Vector and generated the Order Vector using the ELECTRE results and the Rank Vector. Using Gigacalculator, we acquired the following results:

![image](https://github.com/user-attachments/assets/4f297fcb-55d7-4ae3-8ecd-ae4919a7f797)
gigacalculator.com

The ranking correlation between the rankings is above 0.5 for both Kendall's Tau and Spearman’s Rank Correlation Coefficient. These results demonstrate a high degree of similarity between the rankings generated by TOPSIS and ELECTRE when assessing ontologies based on the 11 quality metrics utilised in this study.


## Usage
To use this project, clone the repository and compile the Java files. Ensure you have a compatible Java development environment set up.
