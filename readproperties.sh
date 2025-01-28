#!/bin/bash

# Check if a properties file is provided
if [ -z "$1" ]; then
  echo "Usage: $0 <properties-file>"
  exit 1
fi

# File path of the properties file
PROPERTIES_FILE="$1"

# Read the properties file line by line
while IFS= read -r line; do
  # Skip lines that are empty or start with a comment (# or ;)
  if [[ "$line" =~ ^[[:space:]]*# || "$line" =~ ^[[:space:]]*; || -z "$line" ]]; then
    continue
  fi
  
  # Split the line into key and value based on the first '=' character
  IFS='=' read -r key value <<< "$line"

  # Trim leading/trailing spaces from key and value
  key=$(echo "$key" | xargs)
  value=$(echo "$value" | xargs)

  # Export the key-value pair
  export "$key=$value"
done < "$PROPERTIES_FILE"

# Optionally, print all exported variables (for debugging purposes)
# env
