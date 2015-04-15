#!/bin/bash

LIST=./hoge.txt

# func
# FUNC_INIT()
# {
#   #
# }
FUNC_PARSE()
{
  while read line; do
    echo $line | awk '{if (NF == 2 $1 ~ /[0-9]*/ && $2 ~ /^http/) print $0}' | sed -e 's/\t.*\(^http\)/\1/g' -e 's/[ぁ-んァ-ヶ一-龠々ー]//g' >>url
  done <${LIST}
}

RUN()
{
  # FUNC_INIT $0
  FUNC_PARSE $0
}

# run
RUN $0