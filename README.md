# User Project Browser

### Features
 
 - **Project List**
 - **Project Detail**

To run tests `./gradlew :project:jacocoTestReport`

### Architecture

The architecture of this project was based on **[Google's Architecture Components Guide](https://developer.android.com/topic/libraries/architecture/guide.html#the_final_architecture)**

### Layer Division

 - **View** *Activities, Fragments, Views, etc*
 - **ViewModel** *Hold data for the View*
 - **Repository** *Deliver data to the ViewModel*
 - **Data** *Knows how to get Remote or Local data*

### Modules
 
 - **sdk** *Data, Models, api, database* **NO VIEWS HERE**
 - **base** *Base implementation for the view modules* **NO DATA HANDLERS OR SPECIFIC FEATURE LOGIC HERE**
 - **base_test** *Base implementation for the instrumented tests on the view modules*
 - **project** *View **feature** module to list project and the detail* 

## Have fun!