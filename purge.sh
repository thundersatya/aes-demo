#!/bin/bash

# Set Elasticsearch URL
ES_URL="http://localhost:9200"

# Detect OS type (Linux or macOS)
if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS date format
    CURRENT_DATE=$(date "+%Y.%m.%d")
    FIVE_DAYS_AGO=$(date -v-5d "+%Y.%m.%d")
else
    # Linux date format
    CURRENT_DATE=$(date "+%Y.%m.%d")
    FIVE_DAYS_AGO=$(date -d "5 days ago" "+%Y.%m.%d")
fi

# Get a list of all indices
indices=$(curl -s -X GET "$ES_URL/_cat/indices?v&h=index")

echo "Checking indices older than $FIVE_DAYS_AGO..."

# Iterate over each index
echo "$indices" | while read -r index_name; do
  # Extract the date from the index name (assuming format logs-YYYY.MM.DD)
  index_date=$(echo "$index_name" | grep -oE "[0-9]{4}\.[0-9]{2}\.[0-9]{2}")

  # Skip if no date found in the index name
  if [[ -z "$index_date" ]]; then
    continue
  fi

  # Compare the extracted date with the threshold date
  if [[ "$index_date" < "$FIVE_DAYS_AGO" ]]; then
    echo "Deleting index: $index_name (Created on: $index_date)"
    curl -X DELETE "$ES_URL/$index_name"
  fi
done
