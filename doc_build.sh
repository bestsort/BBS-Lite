#!/bin/bash
vuepress build docs
cd docs/.vuepress/dist
git init
git add -A
git commit -m 'deploy'
git push -f git@github.com:bestsort/BBS-Lite.git master:gh-pages
cd ..
rm -rf dist
