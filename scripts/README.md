### Overview
The _upload_media.py_ file located in this directory serves as a convenient means to automate the process of assiciating media with various terms within verses, and uploading the relevant media content to the cloud where it will then be retrieved by the backend API for use as required.


### Prerequisites
- Install python3. If you are unsure of how to do this, follow this [step by step guide](https://programwithus.com/learn/python/install-python3-mac).
- Define a single root directory under which all media content will be stored. For example; _C:/Users/JohnDoe/Maayan-media/_. This value can be anything, as long as it's a valid directory path ending with a forward slash as in the provided example.

### First time setup
 - Open up the _upload_media.py_ script in your preffered text editor and navigate to line number 163.  The lines' contents are `base_dir_path = 'C:/Maayan/'`. Change the value on the right hand side of the equals sign in between the single qoutes to whichever value you choose in the second step of prerequisites.
 - open a bash terminal and run the command `pip install python-graphql-client`. If that doesn't work, try running `pip3 install python-graphql-client`.

### Running the script
#### How it works
The script will look at all folders within the `base_dir_path` you've provided it in the previous step. The folder names serve as search terms. The files within them serve as actual media content. 
#### Adding descriptions to media files / images
If you wan't to add a description to a particular image within one of these folders, simply create a text file of the same name within that same folder with the same exact name as the image file - except for the file type suffix which will of course be .txt and not .jpeg / .png.
#### Running the script
navigate to the directory containing the _upload_media.py_ file (which is also the directory this file is in), open a bash terminal and run the following command:
```bash
python3 upload_media.py 
```
