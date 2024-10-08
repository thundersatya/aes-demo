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








#!/bin/bash

# Define the values for your variables
NAME="Aashrith"
AGE="12"
LOCATION="Hyderabad"

# Path to your template YAML file
TEMPLATE_FILE="template.yml"

# Create the output file
OUTPUT_FILE="output.yml"

# Use sed to replace placeholders with variable values
sed -e "s/\${NAME}/$NAME/g" \
    -e "s/\${AGE}/$AGE/g" \
    -e "s/\${LOCATION}/$LOCATION/g" \
    $TEMPLATE_FILE > $OUTPUT_FILE

echo "Replaced variables in $TEMPLATE_FILE and saved to $OUTPUT_FILE."


name: ${NAME}
age: ${AGE}
location: ${LOCATION}


# Set any custom environment variables or configurations here
# Example: Changing Prometheus configuration path
PROMETHEUS_CONFIG="/etc/prometheus/prometheus.yml"

# Start Prometheus with custom options
/bin/prometheus --config.file=$PROMETHEUS_CONFIG --storage.tsdb.path="/prometheus" --web.console.templates="/etc/prometheus/consoles" --web.console.libraries="/etc/prometheus/console_libraries"

wget http://ftp.us.debian.org/debian/pool/main/a/apt/apt_<version>.deb
sudo dpkg -i apt_<version>.deb




[
  {
    "targets": ["192.168.1.100:9100"],
    "labels": {
      "job": "node_exporter",
      "env": "production",
      "metrics_path": "/custom-node-metrics"
    }
  },
  {
    "targets": ["192.168.1.101:8080"],
    "labels": {
      "job": "app_metrics",
      "env": "staging",
      "metrics_path": "/app-metrics"
    }
  }
]

scrape_configs:
  - job_name: 'file_sd_job'
    file_sd_configs:
      - files:
          - '/etc/prometheus/file_sd.json'
    relabel_configs:
      - source_labels: ['__meta_filepath']
        action: replace
        target_label: '__metrics_path__'
        source_labels: ['metrics_path']
