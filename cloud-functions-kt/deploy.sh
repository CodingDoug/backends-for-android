#!/bin/sh

project_id=$1
function_name=$2
entry_point=$3

if [ -z "$project_id" ]; then
    echo "First argument must be the Google Cloud project ID" >&2
    exit 1
fi

if [ -z "$function_name" ]; then
    echo "Second argument must be the name of the Cloud Function" >&2
    exit 1
fi

if [ -z "$entry_point" ]; then
    echo "Third argument must be the Cloud Functions entry-point class" >&2
    exit 1
fi

gcloud functions deploy \
    "$function_name" \
    --project "$project_id" \
    --entry-point "$entry_point" \
    --runtime java11 \
    --trigger-http \
    --allow-unauthenticated
