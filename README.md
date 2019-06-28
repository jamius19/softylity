About
---
I always felt irritated to deal with all the repetitive stuffs while installing an app on Linux platforms.
Surely it is powerful, but sometimes I'd rather have a easier way of installing apps (via  **apt-get** ).

It was always my plan to make a  **GUI ** program for this purpose, which would handle all the simple commands for me under the hood.

So I started this project two days back, and today I'm finished!
I've gave the name "Softylity" for this program. (Software + Utility!)

This app is primarily intended for simpler software installation. In near feature I intend to add some more functionality in this app.


Download
---
Get the jar file from release.

**You need to have OpenJDK or JRE installed on your machine!
If these are not installed, following **[**these**](http://askubuntu.com/questions/48468/how-do-i-install-java)** easy steps to install OpenJDK in your system!**


Feature
---
-  One click installation
-  Completely GUI Application with log and History of performed operations!
-  Program installation
-  Program un-installation
-  Option to remove with dependencies
-  Some basic problem handling regarding the installation process (such as dpkg interrupted).



**How to?**
-  **Adding Repository:**  Just enter the repository address (e.g **ppa:notepadqq-team/notepadqq)**.Then click on "Add repo and update" button.
It'll add the repository and update the package information list. If you click the button without supplying any repository address, then Softylity will just update the package information list!
- **Adding Key (Optional):** Just enter the key address (e.g **http://deb.opera.com/archive.key)**.Then click on "Add Key" button.
It'll download the key file (in a temp folder) and add the key. Then it'll remove the downloaded key file as it's no longer needed!
-  **Installing a package:** Just enter the package name (e.g  **notepadqq** ).Then click on "Install Package" button.
It'll install the package!
-  **Un Installing a package:**  Just enter the package name (e.g  **notepadqq**). Then click on "Uninstall Package" button.
It'll  **install ** the package! If you want to remove the dependencies as well, just check the " **w/ Dis" ** checkbox!
-  **One click Installation:**  Fill up the repository field (if needed), fill up the key address (if needed) and fill up the package name field (required) and then click **Add repository and install** button.

It'll add the repository (if given), key (if given), and will install the package!
- In the  **History ** tab you can view all the operations performed!
- You can clear the history log by pressing the **Reset Log**  button on **History** Tab.
- In the  **About & Settings**  Tab, you can change your password and reset all the the application!


Screenshots
---
![screenshot1](https://i.imgur.com/hPImrNH.png)
![screenshot2](https://i.imgur.com/MGAXo0X.png)
