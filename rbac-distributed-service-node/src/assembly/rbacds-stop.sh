#!/bin/bash
# 程序的根目录
basedir=/usr/local/rbacds

PID=$(cat $basedir/rbacds.pid)
kill "$PID"
