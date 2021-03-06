#!/bin/bash
# This handy script is run by Travis CI; generates badges and other stuff

if ! [ "$TRAVIS" ]; then
    echo "This script is intended to be run by Travis CI"
    exit 1
fi

REMOTE=`git config remote.origin.url`
git remote set-url --push origin ${REMOTE/#git:/https:}
git remote set-branches --add origin gh-pages
git fetch

REVISION=`git rev-parse HEAD`

git config user.name "Anton Gustafsson Bot"
git config user.email "antag99bot@gmail.com"
git config credential.helper "store --file=.git/credentials"
printf "%s" "https://${GH_TOKEN}:@github.com" > .git/credentials

## Count lines of code
LINES_OF_CODE=`git ls-files | grep .java$ | xargs cat | wc -l`

# git reset --hard
# git clean -dffx .

git checkout gh-pages

## Update lines of code
if [ "$LINES_OF_CODE" != "$OLD_LINES_OF_CODE" ]; then
    echo "$LINES_OF_CODE" > loc.txt
    wget https://img.shields.io/badge/lines_of_code-${LINES_OF_CODE}-orange.svg -O loc.svg
    git add loc.txt
    git add loc.svg
    git commit -m "Update lines of code for $REVISION"
fi

git push origin gh-pages
rm .git/credentials

git checkout master
