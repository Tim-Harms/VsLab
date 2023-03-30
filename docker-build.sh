#!/bin/sh
DOCKER_ACCOUNT=timharms

docker build --tag=microservice-kubernetes-demo-apache apache
docker tag microservice-kubernetes-demo-apache $DOCKER_ACCOUNT/microservice-kubernetes-demo-apache:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-demo-apache

#docker build --tag=hska-vis-legacy-categoryservice hska-vis-legacy-categoryservice
docker tag hska-vis-legacy-categoryservice $DOCKER_ACCOUNT/hska-vis-legacy-categoryservice:latest
docker push $DOCKER_ACCOUNT/hska-vis-legacy-categoryservice

#docker build --tag=hska-vis-legacy-productservice hska-vis-legacy-productservice/docker
docker tag hska-vis-legacy-productservice $DOCKER_ACCOUNT/hska-vis-legacy-productservice:latest
docker push $DOCKER_ACCOUNT/hska-vis-legacy-productservice

#docker build --tag=hska-vis-legacy-legacywebshop hska-vis-legacy-legacywebshop
docker tag hska-vis-legacy-legacywebshop $DOCKER_ACCOUNT/hska-vis-legacy-legacywebshop:latest
docker push $DOCKER_ACCOUNT/hska-vis-legacy-legacywebshop

#docker build --tag=hska-vis-legacy-web-shop-db-image hska-vis-legacy-web-shop-db-image
docker tag hska-vis-legacy-web-shop-db-image $DOCKER_ACCOUNT/hska-vis-legacy-web-shop-db-image:latest
docker push $DOCKER_ACCOUNT/hska-vis-legacy-web-shop-db-image