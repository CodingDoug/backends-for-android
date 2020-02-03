#!/bin/sh

project_id=$1
service_id=$2

if [ -z "$project_id" ]; then
    echo "First argument must be the Google Cloud project ID" >&2
    exit 1
fi

if [ -z "$service_id" ]; then
    echo "Second argument must be the Cloud Run app name" >&2
    exit 1
fi

echo "Deploying $service_id to $project_id"

tag="gcr.io/$project_id/$service_id"

gcloud builds submit \
    --project "$project_id" \
    --tag "$tag" \
&& \
gcloud run deploy "$service_id" \
    --project "$project_id" \
    --image "$tag" \
    --platform managed \
    --update-env-vars "GOOGLE_CLOUD_PROJECT=$project_id" \
    --region us-central1 \
    --allow-unauthenticated

# Note above that the env var GOOGLE_CLOUD_PROJECT is being set. This allows
# Google SDKs (in particular, the Firebase Admin SDK) to initialize without
# any configuration.
