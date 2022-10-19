# sepia_coding

Used Dark Theme for the UI

Screen One :
1. Used Recycler view to load dynamic lists
2. Details of Pet along with photo is being is displayed.
3. Used Picasso library to fetch image.

Screen Two :
1. Used Webview to load the webpage.
2. Prevented web redirected by using { shouldOverrideUrlLoading }.

Json Parser :
1. Created a json parser { readJson } to parse json file to Json Object.

Pet Object :
1. Declared a pet object to save pet data.

Config parsing :
1. Created { parseWorkingHours } function to get data from config json object.
2. Used string split to reafctor data and separate date and time.
3. Created Dyanamic time parser from config file.
   Data is Mapped as 
   1. Monday = 'M' / 'MO'
   2. Tuesday = 'T'
   3. Wednesday = 'W'/'WE'
   4. Thrusday = 'TH'
   5. Friday = 'F'/'FR'
   6. Saturday = 'SA'
   7. Sunday = 'SU'
   
   for eg if i want to set working hours Tuesday to friday 10 am - 6 pm i would be using
   'TU-FR 10:00 - 18:00'
   
Files and Usage:
1. Constants.java - For storing Contant Strings
2. MainActivity - UI declarations to screen 1, readJson, parseWorkingHours.
3. PetInfo - Loading webpage with content_url info.
4. PetObject - Object Info and related fields.
5. RecyclerAdapter - Adapter for Recycler View.

