#!/bin/bash

# YAML template file
TEMPLATE_FILE="config_template.yml"
OUTPUT_FILE="config_output.yml"

# Define an associative array for key-value replacements
declare -A replacements=(
  ["{{APP_NAME}}"]="MyApp"
  ["{{VERSION}}"]="1.0.0"
  ["{{ENVIRONMENT}}"]="production"
)

# Function to replace keys with values in the template
replace_values() {
  local input_file=$1
  local output_file=$2

  # Copy the template to the output file
  cp "$input_file" "$output_file"

  # Loop through the associative array and perform replacements
  for key in "${!replacements[@]}"; do
    sed -i "s|$key|${replacements[$key]}|g" "$output_file"
  done
}

# Call the function
replace_values "$TEMPLATE_FILE" "$OUTPUT_FILE"

echo "Replacements done! Output saved to $OUTPUT_FILE"




#YML
app:
  name: {{APP_NAME}}
  version: {{VERSION}}
  environment: {{ENVIRONMENT}}
