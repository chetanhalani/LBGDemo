# LBGDemo

MVVM with clean Architecture

API URL

https://api.artic.edu/api/v1/artworks?fields=id,title,artist_display,date_display,main_reference_number,image_id

Description

Single page app will fetch data from above api and store data in local database. if data not returned from the api then data will be used from local and records will be shown to the user.


Unit Tests Added

API url / Image url / Field params added in build.gradle used with help of BuildConfig

Components Used
- Hilt
- View Binding
- Coroutines
- ViewModel
- Room
- Retrofit
- Glide
- LiveData

Demo Screenshot
![lbg_demo](https://user-images.githubusercontent.com/72606824/211365324-b59db446-5442-4f67-b76b-cf66326d38c8.png)

