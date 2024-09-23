#!/bin/bash

# Set namespace (default is "default")
NAMESPACE="default"

# Paths to the YAML files
PV_FILE="persistentvolume.yaml"
PVC_FILE="persistentvolumeclaim.yaml"
CONFIGMAP_FILE="configmap.yaml"
DEPLOYMENT_FILE="deployment.yaml"
SERVICE_FILE="service.yaml"

# Dockerfile location and image name
DOCKERFILE_DIR="../"  # Adjust if the Dockerfile is in a different location
IMAGE_NAME="order-management"

# Check if kubectl is installed
if ! command -v kubectl &> /dev/null
then
    echo "kubectl could not be found. Please install kubectl."
    exit 1
fi

# Check if minikube is installed
if command -v minikube &> /dev/null
then
    # Set up environment to use Minikube's Docker daemon. Use the minikube profile
    eval $(minikube -p minikube docker-env)
else
    echo "minikube could not be found. Make sure Minikube is installed and running."
    exit 1
fi

# Build Docker image within minikube docker
echo "Building Docker image..."
docker build --no-cache -t $IMAGE_NAME $DOCKERFILE_DIR
if [ $? -ne 0 ]; then
    echo "Failed to build Docker image. Exiting."
    exit 1
fi

# Apply PersistentVolume
echo "Applying PersistentVolume..."
kubectl apply -f $PV_FILE --namespace=$NAMESPACE
if [ $? -ne 0 ]; then
    echo "Failed to apply PersistentVolume. Exiting."
    exit 1
fi

# Apply PersistentVolumeClaim
echo "Applying PersistentVolumeClaim..."
kubectl apply -f $PVC_FILE --namespace=$NAMESPACE
if [ $? -ne 0 ]; then
    echo "Failed to apply PersistentVolumeClaim. Exiting."
    exit 1
fi

# Apply ConfigMap
echo "Applying ConfigMap..."
kubectl apply -f $CONFIGMAP_FILE --namespace=$NAMESPACE
if [ $? -ne 0 ]; then
    echo "Failed to apply ConfigMap. Exiting."
    exit 1
fi

# Apply Deployment
echo "Applying Deployment..."
kubectl apply -f $DEPLOYMENT_FILE --namespace=$NAMESPACE
if [ $? -ne 0 ]; then
    echo "Failed to apply Deployment. Exiting."
    exit 1
fi

# Wait for the pod to be running
echo "Waiting for the pod to be running..."
kubectl wait --for=condition=ready pod -l app=order-management --namespace=$NAMESPACE
if [ $? -ne 0 ]; then
    echo "Pod did not become running in time. Exiting."
    exit 1
fi

# Get the pod name
POD_NAME=$(kubectl get pods -l app=order-management --namespace=$NAMESPACE -o jsonpath="{.items[0].metadata.name}")
if [ -z "$POD_NAME" ]; then
    echo "No pod found. Exiting."
    exit 1
fi

# Apply the Service
echo "Applying Service..."
kubectl apply -f $SERVICE_FILE --namespace=$NAMESPACE
if [ $? -ne 0 ]; then
    echo "Failed to apply Service. Exiting."
    exit 1
fi

# Show the logs from the pod
echo "Showing logs from pod $POD_NAME..."
kubectl logs $POD_NAME

# Show the service URL if Minikube is running
if command -v minikube &> /dev/null && minikube status &> /dev/null; then
    echo "Fetching service URL..."
    minikube service order-management-service --url
else
    echo "Minikube is not running or installed. Make sure to forward ports manually if needed."
fi

echo "Deployment completed."
