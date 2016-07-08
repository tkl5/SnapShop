# SnapShop

TCSS 305: Programming Practicum, Spring 2016

# Assignment Overview:
Our task was to create the front end graphical user interface for an application that displays and changes the look of an image. The assignment will mainly test our understanding of Swing components in Java. Using a combination of border, grid, and flow layout managers the task will be to have the buttons align vertically and for each button to have a function.  

For the first seven buttons, we will call from the filter classes provided to us to manipulate the image. For the open and save buttons, JFileChooser will be used to navigate to the directory and open and display the image. The Close button will remove the image from the window, while frame resizes accordingly.


# Technical Impression:
Since I have not worked with GUI before, this assignment was long and difficult. Even after getting detailed help from a classmate, it took a day for me to get the layout to display correctly. Then it made perfect sense. 

I would start with a border layout. Inside that border, I will have another border layout hugging the west side. Inside that, I put two grid layouts – one aligned to the north and one to the south. Those grids will be where I will add the buttons. Next I started with the open and save buttons. This was straightforward, I used a lot of the code provided in the Oracle tutorial to guide me here. After taking a while to get my directory to open at the right place, the next biggest challenge for me is getting the image to show up. I reread the hint given to us about PixelImage over and over again, but it made no sense to me. I did not know what to put inside the load method of PixelImage. I did not know how to organize my try and catch blocks. Finally, after a few days of being stuck I was able to get the image to show by calling the getSelectedFile() method on my file chooser variable, and passing that through the load method. At this point the assignment was almost due so I needed to get an extension. 

My next challenge now was getting the filter buttons to work efficiently. After several hours of trying, I ended up creating two arrays. One filled with AbstractFilter classes, and one filled with filter JButton objects. Inside the actionPerformed method I made one for loop iterating through the array of JButton objects, and for each object I applied each element in the AbstractFilter array onto my image. Outside the class, the other for loop iterates through the same array, but tags each button with an action listener. Now each button does what it’s supposed to do. Lastly, I added pack methods and set minimum dimensions in the right places to resize the frame when necessary.


# Unresolved problems:
I get a PMD error saying my imports are out of order. I tried the organize import hotkey (ctrl+shift+o), but it did not work. When I resize the window and make it larger, I understand that the image has to stay to the right of the buttons. It does, but it remains in the center rather than the top of the window (since I added the JLabel to BorderLayout.CENTER). 

Inside the SnapShopGUI constructor, along with the super call I also declared a new JLabel. I found doing this was the only way for my image to show up, perhaps other instantiations could go here as well, or none at all. Also, my filter button code could have been written in a more efficient way.  

After I open an image, and close the directory box before I select an image, exceptions are thrown on the console. I do not know how to catch multiple exceptions. When this happens, there are both illegal argument and null pointer exceptions. 



