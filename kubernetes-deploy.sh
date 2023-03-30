#!/bin/sh
if [ -z "$DOCKER_ACCOUNT" ]; then
    DOCKER_ACCOUNT=timharms
fi;
kubectl create deployment apache --image=docker.io/$DOCKER_ACCOUNT/microservice-kubernetes-demo-apache:latest --port=80
kubectl expose deployment/apache --type="LoadBalancer" --port 80
kubectl create deployment category --image=docker.io/$DOCKER_ACCOUNT/hska-vis-legacy-categoryservice:latest --port=8081
kubectl expose deployment/category --type="LoadBalancer" --port 8081
kubectl create deployment product --image=docker.io/$DOCKER_ACCOUNT/hska-vis-legacy-productservice:latest --port=8082
kubectl expose deployment/product --type="LoadBalancer" --port 8082
kubectl create deployment webshop --image=docker.io/$DOCKER_ACCOUNT/hska-vis-legacy-legacywebshop:latest --port=8080
kubectl expose deployment/webshop --type="LoadBalancer" --port 8080
kubectl create deployment webshopdb --image=docker.io/$DOCKER_ACCOUNT/hska-vis-legacy-web-shop-db-image:latest --port=3306
kubectl expose deployment/webshopdb --type="LoadBalancer" --port 3306