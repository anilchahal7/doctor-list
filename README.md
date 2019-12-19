# Vivy Test Application
A Kotlin android app with Clean Architecture and MVVM Architecture pattern.

## Libraries used
1. Dagger2 - For Dependency Injection
2. Android Architecture Components
3. RxJava2
4. Retrofit - For making API Request 
5. DroidNet - For Internet Checking
6. Nhaarman Mockito for Testing

# Design Flow
To fulfill the requirement I designed app in simple manner, where All information comprised. 
I am using Tab adapter for segregation of list of all doctors vs recently contacted doctors.
Search View has been provided to search any doctor name from the list. 

# Use cases
After opening the Application, app will make a API call for fetching the Doctors List and Last key
for pagination request.
Task 1 - On Successful fetching of response, list down all the doctors in the list.
    I am showing all the doctors in a list with a header Vivy Doctors.
    As we scroll down, I am making a pagination call and added the result in the list untill there 
    are no more doctors left. 
    
Task 2 - Last 3 Recently contacted Doctor on top of doctor list.
    As we select any item in the list it will open detail page. If we press back from detail page,    
    on top of Vivy Doctor new header attached with name Recent Doctor. I am showing name of the doctor
    in a horizontal list. List can have maximum 3 item, Most recent will take precedence and will be 
    the first item in the list. 

Task 3 - More Information, whenever I select any doctor
    On click on any item it will take me to doctor detail page, where other than name and photo, 
    address, Phone no, rating, Review Count and Website address is listed. On Press back from detail 
    page, I am removing item from the list, as per requirement. You can find that doctor in recently 
    contacted doctor list on top, also first item in horizontal list.
    
#Acceptance Criteria
   1. As I scroll down the doctor list , more results should be added until there are no more
      results left. -  Done
   2. List of Doctors should have two sections. -  Done
   3. List displaying recently selected doctors sorted by most recent. This section
      should have heading "Recent Doctors" - Done
   4. List displaying Vivy doctors (followed by doctor list excluding recently
      selected doctor) sorted by highest rating. This section should have heading
      "Vivy Doctors". - Done
   5. Detailed doctor screen should display name, address and picture of selected doctor. - Done
   6. Code should be covered by tests. - Done <Other Than App Module, All is Covered.>
   7. Use Stable android studio version to do this challenge. Please don’t use release
      candidates or canary builds. - Done
   8. Please use Kotlin. - Done
      
#Tips
Only vertical screen orientation is required. - 
    Implemented for vertical orientation
    
There is no need to cache the doctor list locally which is fetched by the api. -
    No caching is used, only shared prefrences just for passing data to fragment.
    Clearing it on every app launch.

UI is simple as mentioned.

Project should be production ready please do your best. Our technical team isn’t just
checking the end result. They will be evaluating code challenge based on code quality, 
architecture and problem solving skills - 
    Clean Architecture pattern is used as mentioned in 1st round. Along with that, dagger2 , RxJava 
    and Livedata with MVVM design pattern has been used. To keep code neat and clean all module are 
    separated. 

#Bonus Task
   Internet connectivity is handled with the Toast message in ongoing API request.
   Search bar that filter search results by doctor name has been added.
     

## Screenshots
Application detailed screenshots has been added for understanding the complete App Use.