#Aditya and Anna’s image editor!


##Interface overview

**ImageController interface **
This interface represents the controller. Our “go” method is perform operations, it takes an operation
and deals with it accordingly. It uses the parseInputs method to translate the user's input into an
operation. Our load method loads an image in, and our set view initializes this.view.

**ImageModel interface **
This interface represents an arbitrary Image, and on the client is able to greyscale(in 6 different
ways), brighten and darken by a desired amount, and flip an image vertically and horizontally. We
added three new methods in order to support the new functionality. We added returnAsBufferedImage,
which allows us to properly export our file as a buffered image. In the previous assignment, this
wasn't necessary since we were dealing with PPMs. But we needed to add this so that we were able to 
extract other types of files properly. We also added a method called filter that takes in a 
matrix. This matrix can be any size, so long as the rows and columns are odd 
(i.e. we can support matrices that are 3x3, 5x3, and 3x5 but not ones that are 2x3, 3x2, or 2x2). 
We also added a colorTransformation method that takes in a matrix in order to apply it to an image. 
This matrix would either be luma or sepia, but again we can support a bunch of different matrices 
(so long as they're 3x3). The reason we seperated these matrices from sharpen and blur is that 
they are color transformation and the math applied to each rgb values differs. Finally, we
added a returnColorFrequencies method that returns a Map<Integer, Integer> which represents a table
of values and frequency. 

**ImageView interface **
This interface represents the view, and it sends an image to a file for the client to see. It contains 
2 methods, one save method which sends the current image to a file using the model’s to string 
builder, and a second which uses a buffered image to deal with exporting files that are not a ppm.

##Class overview

**GUIManager class **
This class is in the view, and serves as the framework for our GUI. It contains all methods for 
formatting, and all functionality. This class extends JFrame, so it contains our actionPerformed 
method. This method handles all the user's inputs. First thing we check is if the load button was
pressed, if it was we call our load helper which loads an image into our code and then sets it
on screen. We have a class variable called imageOnScreen, which is a JLabel that contains a buffered
image. Everytime an image is loaded, this is mutated, so it changes. We have 2 imageModel variables:
imageToBeMutated and originalImage. originalImage is the image the user uploaded that we keep in
our code so that a user is able to reset an image. imageToBeMutated is the image the user is 
editing. The load helper method (called loadImage) mutates all of this for us. Next, we check
to see if the user has saved an image. If they have, we call the saveImage helper which opens up
a JFileChooser, gets the new pathname of the image the user wants to save, and saves accordingly. 
We then check to see if the operations button was clicked, and if it was we call our 
performGUIOperations method, which performs all the operations in the dropdown menu. Finally, we check
to see if the reset button was clicked, and if it was we reset to the original image. As for histograms,
we have one single set histograms method which we call after every operation (except save) so that
the histogram the user sees on screen is accurate to the image they're editing on (more informations
on histograms below).

**Histogram class**
Our Histogram class extends JPanel and creates a single histogram. The constructor takes in 
an image model and a string specifying the type of histogram to be made, and it creates it 
accordingly. For each value in our map, we draw a line that is as tall as the frequency to create
a bar graph. 

**HistogramPanel class**
This class represents the panel where all the histograms are stored. It takes in an array of
Histograms, and ads it to the panel. We used an array of histograms to support an arbitrary number
of histograms (let's say we have to support a histogram for luma-greyscale, we don't have to change
anything in this class. Just add 2 cases to our switch statements in our returnColorFrequencies method
and our Histogram class).

**Pixel class **
This class represents (you guessed it) a Pixel! Pixel’s represent pixels that make up an image, and
contain a row/column to indicate where they are on the image as well as an RGB color code to
represent what color they are.

**ImageModelImpl class **
This is the class that implements our ImageModel interface. The constructor takes in a scanner which
it uses to initialize the image, height, width, and max value fields. This class has 13 methods. The
load image method is the method that initializes everything that was said above. Change exposure
change’s the brightness/darkness of the image based on user input, and it returns a new Image that
was this image isn’t being mutated. The enhanceImage method is changeExposure’s helper that does all
the dirty work. The greyScale method greyscale’s the image based on what type of greyscale the user
inputs. Vertical and Horizontal flip do exactly what their names say, and we have our toStringBuilder
returns an image as a String Builder. We added more methods this time around to assure
functionality of the code for the updated assignment. We added filter, which returns a new ImageModel
once the filter has been applied to it in the form of a matrix. In order to properly execute this
method, we needed two helpers: getPixelList, and getNewPixelColors. getPixelList returns a list 
that is the same size as the inputted matrix. The center pixel (pixel that gets changed) 
corresponds to the center of the filter, and the same is true for all values. This brings me to 
our next method, getNewPixelColors. Because we were able to make an array the same size as the 
matrix, we can iterate over both at the same time, and this allows us to easily do the 
multiplication and addition necessary to filter the pixel's values.  Next, we have our
colorTransformation method that enables us to apply a color transformation to an ImageModel and then
return it once the rgb values of the pixels have been adjusted accordingly. It takes in a matrix 
and does the math accordingly. Lastly, we added the returnAsBufferedImage method that enables 
us to properly export our files, in order to be able to work with more than just a ppm. Finally, we
added a returnColorFrequencies method that returns a Map<Integer, Integer> which represents a table
of values and frequency. The makeMap helper supplies the returnColorFrequencies method with an 
map of values between 0 and 255 and each of the frequencies are set to 0 (the returnColorFrequencies
changes that obviously).

**ImageViewImpl class **
This class represents the view. It contains 2 methods, one save method which 
sends the current image to a file using the model’s to string builder, and a second which uses 
a buffered image to deal with exporting files that are not a ppm. 

**ImageControllerImpl class **
This interface represents the controller. Our “go” method is performOperations, it takes an operation
and deals with it accordingly. It uses the parseInputs method to translate the user's input into an
operation. Our load method loads an image in, and our set view initializes this.view. We added 3 
new cases to our switch statement: "sharpen," "blur," "sepia," and "greyscale". These are to 
obviously deal with the new types of filtering and color transformations. We also have
a check ifNotPPM method, this method takes in the file type and ensures that it isn't a PPM, and
that it is a valid file that the ImageIO class can use. 

**Operation class **
This class represents the possible operations a user can have.

**FilterMatrix Class**
This class represents our matrices that apply filters to images. We have a matrix for gaussian blur,
which is a 3X3 matrix that blurs an image by changing its rgb values. We have a sharpen matrix,
which is a 5X5 matrix that sharpens an image by changing its rgb values. We have a luma matrix
applies a color transformation to pixels, returning a grayscale image. Lastly, we have a matrix filter
for sepia that also applies a color transformation to our pixels, creating an image with a sepia
filter.

**ImageUtil class **
Represents our main method that starts the program. After the program is started, it checks to see
if the user has inputted a command line, and if it does it runs the command 
line. If -text is entered, it waits for the user to input something into the console/terminal. If
-file is entered, it runs the script file. If nothing is entered, it opens the GUI. 

**Citation for test image **
This image was created by Aditya Pathak and Anna Dendas, we give you permission to use it:)  

##Instructions on how to run script files/typing into terminal

** 1: Running a script file **
If you want to run a script file, you need to attach it as a command line argument.
Just attach the name of the file and we will be able to read it in.
We can also only read in txt files. Please make sure that you have valid
path names in the txt file. If you don't add any arguments, the code will run as it normally should.
To run our script file, type in "java -jar HW4_OOD.jar -file testJar.txt"

When running the JAR file, please copy "java -jar HW4_OOD.jar -file testJar.txt" into terminal,
or if you want to type your own commands please copy "java -jar HW4_OOD.jar -text" into the terminal.

** 2: Running the -text comamand **
The first word you enter in the console MUST be a valid operation. Operations are not case sensitive
and must be one of “load, greyscale-red, greyscale-green, greyscale-blue, greyscale-value,
greyscale-intensity, greyscale-luma, brighten, darken, horizontal-flip, vertical-flip, greyscale,
sepia, blur, sharpen, and save.”
If you enter brighten or darken, YOU MUST follow it up with a positive integer to represent how much
you want to brighten/darken by (all other operations do not need an integer following it). After that,
for all operations that are not load or save you must follow it up with the file you want to edit and
the name of the new file you want to store in memory. If you are loading an image, you must follow
it up with a pathname (can be absolute or relative, doesn’t matter) and then follow that up with the
name you would like to store it in memory. If you are saving an image, you must follow it up with
the name you would like the image to have and the image you want to save We can support JPG, jpg,
tiff, bmp, BMP, gif, GIF, WBMP, png, PNG, JPEG, tif, TIF, TIFF, jpeg, wbmp, and ppm files. Failure
to do these will result in a message on the console telling you how to correct your error,
and then you can do it again till it works. The user can also type "quit" to quit the program 
(case-insensitive), and the code will quit. Quit must be the first word you enter in order to quit.

