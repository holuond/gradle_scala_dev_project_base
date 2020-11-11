#!/bin/bash
##############################################################################################
# This script is a skeleton template meant to trigger your job (JAR).
# You can add any preprocessing to be done.
# If Docker is being used, this is meant to be a possible Docker entrypoint.
#
# Parameters:
#               j) jar: path to jar file
#               c) config: path to config file
#               l) log4j: path to log4j config file
# Example run:
#               ./bin/run.sh -j ./lib/jar.jar -c ./conf/app.conf -l ./conf/log4j.xml
# Exit Codes:
#               0 - success
#               1 - general failure
#               2 - incorrect script input parameters
#               3 - JAR file not found
#               4 - application config file not found
#               5 - log4j config file not found
##############################################################################################

# Strict mode
set -euo pipefail

function parse_args() {
  # @description Parses input arguments

  local OPTIND
  local opt

  while getopts "j:c:l:" opt; do
    case ${opt} in
    j)
      jar=$OPTARG
      ;;
    c)
      config=$OPTARG
      ;;
    l)
      log4j=$OPTARG
      ;;
    \?) #unrecognized option
      show_usage
      exit 2
      ;;
    esac
  done
  if [[ $OPTIND -ne 7 ]]; then
    echo "Incorrect parameters"
    show_usage
    exit 2
  fi
  shift "$((OPTIND - 1))"
}

function show_usage() {
  # @description Prints script usage to stdout

  echo -e "run.sh\n\t[-j <jar>][-c <config>][-l <log4j>]"
}

function validate_params() {
  # @description Validate input params

  if [[ ! -f "${jar}" ]]; then
    echo "Jar file ${jar} not found"
    exit 3
  fi

  if [[ ! -f "${config}" ]]; then
    echo "App configuration file ${config} not found"
    exit 4
  fi

  if [[ ! -f "${log4j}" ]]; then
    echo "Log4j configuration file ${log4j} not found"
    exit 5
  fi
}

function preprocess() {
  # @description Preprocessing before triggering of the job.
  # TODO custom preprocessing
  :
}

function run() {
  # @description Runs the job with given configuration and prints the command

  set -x
  # TODO run the job here
  # e.g.:
  java -jar -Dlog4j.configuration="file:${log4j}" ${jar} -c ${config}
  set +x
}

function main() {
  parse_args "$@"
  validate_params
  preprocess
  run
}

main "$@"
