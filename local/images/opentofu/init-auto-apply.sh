#!/bin/sh

tofu -chdir=/workspace init -lockfile=readonly
tofu -chdir=/workspace apply -auto-approve -lock=false

ls -la
cd /tofu
ls -la
