#!/bin/bash

set -xe

# Generate the API docs
touch local.properties
.github/scripts/gradlew_recursive.sh javadocJar

cat readme.md | grep -v "project website" > docs/index.md
sed -i 's/\/docs\//\//' docs/index.md
sed -i 's/.md)/)/' docs/index.md

# cp CHANGELOG.md docs/changelog.md
mv javadocs docs/javadocs
