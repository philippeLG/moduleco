#
# Compile the model MyModel in moduleco/build/models/MyModel
#

# Set the path to the build directory
build="../../build"

# Set the path to the lib directory
lib="../../lib"

# Create the build directory if it does not exist
echo "Creating build directory ..."
if ! [ -d $build ]; then mkdir $build; fi

# Compile
echo "Compiling ..."
javac -classpath $lib/moduleco.jar -d $build *.java

# End
echo "Done."
