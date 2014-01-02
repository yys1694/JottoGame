# super simple makefile
# call it using 'make NAME=name_of_code_file_without_extension'
# (assumes a .java extension)

all:
	@echo "Compiling..."
	javac *.java model/*.java view/*.java

run: all
	@echo "Running..."
	java Main

clean:
	rm -rf *.class
	rm -rf view/*.class
	rm -rf model/*.class
