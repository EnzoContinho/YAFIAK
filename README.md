# YAFIAK - Groupe 5

**Y**et **A**nother **F**ire **I**ncident **A**pplication **K**eeper

**Module Projet Transversal :** Enzo **CONTINI** - Derek **DURUPT** - Hugo **FERRER** - Brian **GASPARINI-BARRELON**

**Technologies utilisées :**  JavaScript, Java, Grafana, JSON, MQTT, PostgreSQL, InfluxDB, Micro:bit

L’objectif de ce projet est de réaliser d’une part un simulateur d’incendies permettant la création, le suivi et la consultation de la propagation de feux de différents types (localisés sur une carte), et d’autre part de créer un dispositif de gestion de services d’urgences permettant, à partir d’informations collectées par des capteurs, de déployer et gérer les dispositifs adaptés pour éteindre les incendies.

YAFIAK est un système de détection d’incendie permettant d’optimiser la gestion des acteurs compétents pour intervenir au plus vite sur des incendies localisés sur un territoire déterminé (la ville de Lyon et Villeurbanne dans notre cas).

## Organisation

Par soucis de facilité de mise en place et d'utilisation de notre solution, nous avons mis en place un repository général (celui-ci) qui contient nos différents projets, qui représentent les différentes parties de l'application : 

- YAFIAKBackEnd
- YAFIAKFrontEnd
- YAFIAKDashboard
- SimulationBackEnd
- SimulationFrontEnd
- IoT

## Contenu 

|Dossier|Contenu|
|--|--|
|YAFIAKBackEnd|Application JAVA, Serveur applicatif web et BDD PostgreSQL|
|YAFIAKFrontEnd|Application web JavaScript|
|YAFIAKDashboard|Application web Grafana de suivi de données et BDD InfluxDB|
|SimulationBackEnd|Application JAVA de simulation, Serveur applicatif web et BDD PostgreSQL|
|SimulationFrontEnd|Application web JavaScript|
|IoT|Applicatif capteurs Micro:bit, JSON et MQTT|



