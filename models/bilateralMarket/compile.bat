::#
::# Compile the model MyModel in moduleco/build/models/MyModel
::#
@echo off
echo.

::# Set the path to javac
set javac=C:\j2sdk1.4.2_04\bin

::# Set the path to the build directory
set build=..\..\build

::# Set the path to the lib directory
set lib=..\..\lib

::# Create the build directory if it does not exist
echo Creating build directory ...
mkdir %build%

::# Compile
echo Compiling ...
%javac%\javac -classpath %lib%\moduleco.jar -d %build% *.java

::# End
echo Done.
