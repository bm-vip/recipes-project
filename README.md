# recipes-project
## Installation
You have 2 ways for installation:\
Before running the application, the following commands must be executed in the terminal from the project root directory:
1. Prerequisites for using Dockerfile (recommended)
    1. Install Java 17
    2. Maven 3
    3. Install Docker
    4. run the following command:
```
mvn clean install
docker-compose up
```
2. Prerequisites for using current OS
    1. Install Java 17
    2. Install Postgres sql database
    3. Maven 3
    4. run the following command:
```
mvn clean install
mvn spring-boot:run
```
## Kubernetes configuration
For API scalability, Kubernetes should be installed and submit the yml files that definition to k8s directory with the following command:
```
kubectl apply -f k8s
```
to watch Pods coming alive with:
```
kubectl get pods --watch
```
Finally, for checking application interface, choose one of these solutions:<br/>
* port forward services on current OS and click on the [Link](http://localhost:2022/swagger-ui) after executing command:
    ```
    kubectl port-forward svc/recipes-project 2022:80&    
    ```
* Or get URL of the application service:
    ```
    minikube service service-user --url
    ```
Scaling the application to increase the number of replicas to 3 or more:
```
kubectl scale --replicas=3 deployment/recipes-project
```

## Objective
Create a standalone java application which allows users to manage their favourite recipes. It should
allow adding, updating, removing and fetching recipes. Additionally users should be able to filter
available recipes based on one or more of the following criteria:

1. Whether or not the dish is vegetarian
2. The number of servings
3. Specific ingredients (either include or exclude)
4. Text search within the instructions.

For example, the API should be able to handle the following search requests:
* All vegetarian recipes
* Recipes that can serve 4 persons and have “potatoes” as an ingredient
* Recipes without “salmon” as an ingredient that has “oven” in the instructions.

## Requirements 
Please ensure that we have some documentation about the architectural choices and also how to
run the application. The project is expected to be delivered as a GitHub (or any other public git
hosting) repository URL.

All these requirements needs to be satisfied:
1. It must be a REST application implemented using Java (use a framework of your choice)
2. Your code should be production-ready.
3. REST API must be documented
4. Data must be persisted in a database
5. Unit tests must be present
6. Integration tests must be present
