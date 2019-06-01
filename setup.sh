#!/usr/bin/env bash

set -o errexit

echo -n "Enter the packagename (e.q. com.domain): "
read domain
echo -n "Enter name of the project: "
read project

package=$(echo $project | sed 's/[^[:alnum:]]//g')

secret=$(cat /dev/urandom | LC_ALL=C tr -dc 'a-zA-Z0-9' | fold -w 64 | head -n 1)

tld="$(echo $domain | cut -d'.' -f1)"
name="$(echo $domain | cut -d'.' -f2)"

mv './src/main/kotlin/$tld/$name/$package' "./src/main/kotlin/\$tld/\$name/${package}"
mv './src/main/kotlin/$tld/$name' "./src/main/kotlin/\$tld/${name}"
mv './src/main/kotlin/$tld' "./src/main/kotlin/${tld}"
mv './src/test/kotlin/$tld/$name/$package' "./src/test/kotlin/\$tld/\$name/${package}"
mv './src/test/kotlin/$tld/$name' "./src/test/kotlin/\$tld/${name}"
mv './src/test/kotlin/$tld' "./src/test/kotlin/${tld}"

find . -type f ! -name '*.sh' -not -path "*.git*" -print0 | LC_ALL=C xargs -0 sed -i '' -e "s/\$tld/${tld}/g"
find . -type f ! -name '*.sh' -not -path "*.git*" -print0 | LC_ALL=C xargs -0 sed -i '' -e "s/\$domain/${domain}/g"
find . -type f ! -name '*.sh' -not -path "*.git*" -print0 | LC_ALL=C xargs -0 sed -i '' -e "s/\$project/${project}/g"
find . -type f ! -name '*.sh' -not -path "*.git*" -print0 |LC_ALL=C xargs -0 sed -i '' -e "s/\$name/${name}/g"
find . -type f ! -name '*.sh' -not -path "*.git*" -print0 | LC_ALL=C xargs -0 sed -i '' -e "s/\$package/${package}/g"
find . -type f ! -name '*.sh' -not -path "*.git*" -print0 | LC_ALL=C xargs -0 sed -i '' -e "s/\$secret/${secret}/g"