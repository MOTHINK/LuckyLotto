<div align="center">
  <h1>Android Application: LuckyLotto</h1>
</div>

<p align="center">
  <img src="https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/res/drawable/trebol.png" alt="App Screenshot" width="300"/>
</p>

[![Licence](https://img.shields.io/github/license/exyte/AndroidAnimatedNavigationBar)](https://github.com/exyte/AndroidAnimatedNavigationBar/blob/master/LICENSE)
[![API](https://img.shields.io/badge/API%20-26%2B-brightgreen)](https://github.com/exyte/AndroidAnimatedNavigationBar)
[![Maven-Central](https://img.shields.io/maven-central/v/com.exyte/animated-navigation-bar)](https://central.sonatype.com/artifact/com.exyte/animated-navigation-bar/1.0.0/overview)
![Status: Finished](https://img.shields.io/badge/Status-Finished-brightgreen)
![Updates: Ongoing](https://img.shields.io/badge/Updates-Ongoing-blue)


<div>
  <h1>Index</h1>
</div>

## Table of Contents
- [Description](#description)
- [Features](#features)
- [Libraries used](#libraries-used)
- [Third party libraries](#third-party-libraries)
- [Architecture](#architecture)
- [Example](#example)
- [How to use](#how-to-use)
  
<div>
  <h1>Description</h1>
</div>

<p>
  This is a simple android application that emulates a lottery service, it allows to you create and configure your own lottery, and also join others lottery.
  To join a lottery, you have to buy a ticket, to do so you have to have "virtual coins" that you can purchase by watching ads; Beside that you can share the lotteries
  you join with friends and family.
</p>

<div>
  <h1>Features</h1>
</div>

- `Signing up using Google credentials to play`
- `Creates and configure your own lotteries, setting maximum tickets to purchase, countdown timer, private or not, etc...`
- `Buy lottery tickets, using "virtual coins" that you obtain by watching ads`
- `Share the lotteries with friends by sending to them a link with a specific lottery`

<div>
  <h1>Built with</h1>
</div>

![Kotlin](https://img.shields.io/badge/Kotlin-purple?style=for-the-badge&logo=Kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-055ffa?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Firebase-f5430c?style=for-the-badge&logo=firebase&logoColor=white)

<div>
  <h1>Libraries used</h1>
</div>

- `Room Database`
- `Coil`
- `Admob`

<div>
  <h1>Third party libraries</h1>
</div>

- `Exyte Animated Navigation Bar` [Github link](https://github.com/exyte/AndroidAnimatedNavigationBar)

<div>
  <h1>Architecture</h1>
</div>

 - `MVVM (Model - View - ViewModel) Architecture`
 - `Sigle Acitivy`
 - `Single ViewModel`

<div>
  <h1>Example</h1>
</div>

<img align="left" src="https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/res/images/login.png" width=250 height=500>
<img align="left" src="https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/res/images/login_with.png" width=250 height=500>

`First, sign up using Google account.(In the meantime it only let you register using google credentials)`

<br clear="left"/>

<img align="left" src="https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/res/images/profile_screen.png" width=250 height=500>
<img align="left" src="https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/res/images/profile_screen_ads.png" width=250 height=500>

`Click on purchase coins, you will have to watch until the end the video ad to receive the reward which is 1 coin.
Remember, you will need 3 coins to purchase a lottery ticket.`

<br clear="left"/>

<img align="left" src="https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/res/images/create_pool_screen.png" width=250 height=500>
<img align="left" src="https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/res/images/profile_screen_ticket_purchased.png" width=250 height=500>

`Once you have 3 coins, you can either buy a ticket or create a new pool. remember, the last option creates a pool and also provides you with a ticket.`

<br clear="left"/>

<img align="left" src="https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/res/images/play_screen.png" width=250 height=500>
<img align="left" src="https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/res/images/play_screen_buy_ticket.png" width=250 height=500>

`You can buy tickets as much as you want but it requires always 3 coins. Make sure purchase tickets before the timer countdown is over.`

<br clear="left"/>

<img align="left" src="https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/res/images/profile_screen_close_time.png" width=250 height=500>
<img align="left" src="https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/res/images/profile_screen_claim_prize.png" width=250 height=500>

`Once the timer countdown is over, the app will generate a randowm winning number, to check if your ticket number won, update ticket and you will see the result.
If you won, click on claim prize button, this will make a request to the server and the "Admin" will send your prize, [This last option is theorically, because it depends 
of many things, such as number of tickets bought and the CPM (cost per mille) rates for AdMob ads videos, for example lets say CPM is 0.10 € per 1000 views, and the lottery pool sold 10.000 tickets,
then your prize should be 0.10 * 10.000 = 1.00 €]`

<br clear="left"/>

<img align="left" src="https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/res/images/play_screen_search_by_id.png" width=250 height=500>
<img align="left" src="https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/res/images/play_screen_sharing_link.png" width=250 height=500>

`You can search by pool ID, and also you can share any pool with anyone by clicking on share button [Share symbol], this will generate a deep link, so anyone has the app installed
can join the lottery.`

<br clear="left"/>

<img align="left" src="https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/res/images/profile_screen_update_and_delete.png" width=250 height=500>

`You can update the ticket data from firebase cloud by clicking on update button, this will retrieve the last current data, also you can delete your ticket in case
its an old ticket, when you delete a ticket you can't get again`.

<br clear="left"/>

<div>
  <h1>How to use</h1>  
</div>

`To use the app, you need to create your own firebase project and admob account, you have to follow a few steps:`
<br/>
- `Replace google-service.json file with new one, you can do this by following this:` [firebase tutorial](https://firebase.google.com/docs/android/)
- `Replace Admob app ID` [here](https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/AndroidManifest.xml) `and ad unit ID ` [here](https://github.com/MOTHINK/LuckyLotto/blob/main/app/src/main/java/com/example/luckylotto/data/core/admob/Admob.kt) `Follow this:` [google tutorial](https://support.google.com/admob/answer/9905527?hl=en)
