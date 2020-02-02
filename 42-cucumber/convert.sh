#!/bin/bash

cd out/cucumber
for name in `ls *flv`; do
   newName="$(echo "$name" | sed s/flv/mp4/)"
   if [ -f "$newName" ]; then
      rm "$newName";
   fi
   docker run --rm -it \
     -v $(pwd):/config \
     linuxserver/ffmpeg \
     -i /config/"$name" \
     -c:v libx264 \
     /config/"$newName";
   rm "$name"
done