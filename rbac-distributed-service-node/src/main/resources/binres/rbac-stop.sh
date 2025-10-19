#!/bin/sh

# Set the root directory of the program.
basedir="$(cd "$(dirname "$0")/.." && pwd)"

PID=$(cat "$basedir/rbac.pid")
kill "$PID"
