#!/bin/sh

for file in *.plantuml; do
    plantuml -tlatex:nopreamble "$file" -o $(pwd)
done
