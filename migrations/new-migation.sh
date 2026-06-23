#!/bin/bash
# Create an empty versioned migration with given name

timestamp=$(date +%Y%m%d%H%M)
read -r -p "Migration name: " name
touch V"$timestamp"__"${name// /_}".sql