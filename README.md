# Android Model — View — ViewModel (MVVM) using koin

<p align="center">
  <img src="https://raw.githubusercontent.com/MindorksOpenSource/MVVM-Architecture-Android-Beginners/master/assets/banner-mvvm-arch-beginners.jpg">
</p>
<br>
<p align="center">
  <img src="https://raw.githubusercontent.com/MindorksOpenSource/MVVM-Architecture-Android-Beginners/master/assets/mvvm-arch.png">
</p>
<br>
<br>

## Overview
This Repository contains a Detailed Sample app that Implements MVVM clean Architecture in Kotlin using
Retorfit2, Koin, Coroutines, AndroidX, Android Jetpack, DataBinding

### The app has following packages

  - **di** its include Dependencies Injections Modules using **Koin**

 - **model** its include given sub modules

   - **local** its include Entities and Shared Preferences Classes

   - **remote** its include Network Call classes like APIConstants,ApiServices using **Retrofit**

   - **repo** its include all repository classes which handle the data requests coming from viewmodels

   - **appinterface** its include all in the interface beiing used in the project

 - **utils** Utilities Classes

 - **ui** View Classes Fragments/Activities/Adapters

    - **base** its include base classes and adapter for the project




## MVVM (Model View ViewModel Representation) Flow of sample app

- **View**
  - >Request data from viewModel

  - >Observe viewmodel LiveData for response


- **ViewModel**
  - >Having all LiveData of DataModels

  - >Call getDataReqeust from Repository

  - > Send requested param and live data to Repository

- **Repository**
  - > Get RequestData & LiveData as Param from ViewModel

  - > Decide to fetch data from DB/Network

  - > Fetch data and post it on LiveData get from viewmodel


### Libraries Used
- **Koin**  Library is used for Define DI-Dependencies Injections

- **Retrofit2** Used to call Data Fetch  from network

- **Kotlin Coroutines** Used to Define a thread or scope to call API's