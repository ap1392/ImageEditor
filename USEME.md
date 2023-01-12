#Aditya and Anna’s image GUI!

WARNING: IF YOU TRY AND SAVE A FILE TO A PATHNAME THAT ALREADY EXISTS THAT FILE WILL BE OVERRIDEN
AND ALL CONTENTS WILL BE LOST. We kept this functionality because we believe it is useful, but 
please don't accidentally delete an important image.

* In order to open up the GUI, please type "java -jar HW4_OOD.jar" into terminal
* You will be welcomed by a blank box where the image will be kept, one dropdown menu for 
operations, one button to load an image, one to save, and one to reset. 
* Clicking any button beside load won't do anything, you must click load and actually load an image 
in so that you can start performing operations. 
* We support 17 image files: JPG, jpg, tiff, bmp, BMP, gif, GIF, WBMP, png, PNG, JPEG, 
tif, TIF, TIFF, jpeg, wbmp, and ppm files. If you don't load in one of these images, you will
receive an error asking you to load again.
* Once you've loaded an image in, 4 histograms will appear on the right. The red one represents 
a histogram for the red RGB components, green one represents the green, blue represents the 
blue, and silver represents the intensity. These histograms automatically update if you make 
edits to the image or load in a new image.
* At this point, the operations drop down menu 
and save buttons will do something if u click them. Click the drop-down menu to see all the 
operations you can do to edit the image. We support brightening, darkening, blurring, sharpening,
sepia, flipping both vertically and horizontally, and 6 types of greyscale. (Please note, if u
brighten or darken an image, you must input a positive integer to specify how much you want to 
brighten or darken by).
* If you want to reset the image you're editing on, click reset. 
* If you want to save the image, click the save button, navigate to the file you want to save on,
and enter a name you would like to give your file. The name of the file must end with one of the 
17 formats listed above.
* WARNING: IF YOU TRY AND SAVE A FILE TO A PATHNAME THAT ALREADY EXISTS THAT FILE WILL BE OVERRIDEN
AND ALL CONTENTS WILL BE LOST. We kept this functionality because we believe it is useful, but
please don't accidentally delete an important image.