::##########################################################################
::# Shellscript:	moduleco.sh - launch Moduleco
::# Author     :	Gilles Daniel <gilles.daniel@cs.man.ac.uk>
::# Date       :	24-August-2004
::##########################################################################
::#
::# Description
::#    o	Launches Moduleco with or without a GUI, with or without an initial model.
::#    o	By default, launch Moduleco with a GUI, without an initial model.
::#
::# USAGE
::#        moduleco [--noGUI model] [noSimulations]
::# WHERE
::#        --noGUI model   lets you run Moduleco in command-line, without Graphical Interface.
::#        noSimulations   is the number of simulations to launch consecutively.
::#
::##########################################################################

@echo off
echo.

java -cp build;lib/moduleco.jar;lib/models.jar modulecoFramework.Core %1 %2 %3
