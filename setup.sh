#!/usr/bin/env bash
# Author: Andreas Resch <andreas@resch.io>
set -o errexit

echo -n "Enter your domainname (e.g. domain.com): "
read domain
echo -n "Enter name of the project: "
read project

git clone --depth 1 https://github.com/reschandreas/basic-rest-api.git $project

cd $project

#removing all non alphanumeric characters for the package name
package=$(echo $project | sed 's/[^[:alnum:]]//g')

# Creating a random string for signing the JWT Tokens
secret=$(cat /dev/urandom | LC_ALL=C tr -dc 'a-zA-Z0-9' | fold -w 64 | head -n 1)

tld="$(echo $domain | cut -d'.' -f2)"
name="$(echo $domain | cut -d'.' -f1)"

for dir in main test; do
  mv "./src/${dir}/kotlin/\$tld/\$name/\$package" "./src/${dir}/kotlin/\$tld/\$name/${package}"
  mv "./src/${dir}/kotlin/\$tld/\$name" "./src/${dir}/kotlin/\$tld/${name}"
  mv "./src/${dir}/kotlin/\$tld" "./src/${dir}/kotlin/${tld}"
done

for var in tld domain project name package secret; do
  find . -type f ! -name '*.sh' -not -path "*.git*" -print0 | LC_ALL=C xargs -0 sed -i '' -e "s/\$${var}/${!var}/g"
done

rm -rf .git
rm README.md
rm setup.sh
rm -- "$0"