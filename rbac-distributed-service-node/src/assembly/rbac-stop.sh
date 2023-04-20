#!/bin/sh
# 程序的根目录
basedir="/usr/local/rbac"

PID=$(cat "$basedir/rbac.pid")
kill "$PID"

