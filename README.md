![banner](https://lh3.googleusercontent.com/-X7pu3bBOF6E/VrXnZUji5qI/AAAAAAAAOB8/nvnX9iTjQ_4/s0/68747470733a2f2f7261772e6769746875622e636f6d2f5068696c4a61792f4d5043686172742f6d61737465722f64657369676e2f666561747572655f677261706869632e706e67+copy.png "banner")

**(localhost)** is a dynamic Java web project base on [JSP](https://en.wikipedia.org/wiki/JavaServer_Pages)/[Servlet](https://en.wikipedia.org/wiki/Java_servlet) technology, it provides some useful functions same as notes, upload/share files or compile/run simple code and so on. Especially, it is designed to run on PCs/Raspberry Pis so you can keep everything in your local computer and everyone could access your local website via Internet. Don't worry about deploy, managing or publish it to Internet, everything are very simple so anyone could use it.

<br>

![key ideas](https://docs.google.com/uc?authuser=0&id=0ByMQfqYoGjfUNG5oblVxdHVNUzQ&export=download)

## Installation(still incomplete)
1. Windows: so easy, you just download and run a setup file. This is only the key ideas for the installation on windows, it might be different in the future.
2. Linux: researching...

> To access your website base on this project, you must configure your router with a few step, it's so easy. I will tell you this way when I complete current project features.
> If you are developer, you can fork or download this project and build with [Eclipse IDE](https://www.eclipse.org/) then deploy on a [tomcat(7/8)](http://tomcat.apache.org/) server.

## Usage(processing)
Current project support four features, some features require an user account. When you deploy a own website base on this project you will have an admin account which use to manage another normal accounts. To add new account for your friends, you just login to admin account then create new account with user name and password. Here is welcome page which provide some information about this project, to use full functions you must login to system(contact administrator to take an account).

> Note: This project is designed to compatible with desktop and mobile browser, screenshots in examples below are captured from mobile browser.

**Welcome page**: This page appear when a guest enter the localhost web.

![Welcome page](https://lh3.googleusercontent.com/-kxunGOGlEcc/VrWiZqIcRCI/AAAAAAAAOAY/SwuE4IAq4Os/s0/screencapture-localhost-8080-welcome-1454743841438.png "Welcome page")

**Navigation**: A menu will appear when you touch on top right button.

![navigation](https://lh3.googleusercontent.com/-T2jUoJnkFHI/VrXIeFqxB2I/AAAAAAAAOA0/nbX4WUOV11g/s0/screencapture-localhost-8080-welcome-1454752786461.png "Navigation")

After you logged to system, you will be navigated to index page, here we will explore web features.

**Index**: User's home page when you logged to system, slip down to see more features.

![index](https://lh3.googleusercontent.com/-z4RTJXxJnnI/VrXLjxGhoFI/AAAAAAAAOBM/08DkGXtFXqs/s0/screencapture-localhost-8080-index-1454754539867.png "Index page")

When you browse in a desktop browser you will see full functions like this:

![index in desktop browser](https://lh3.googleusercontent.com/--m588bgEgjw/VrXMzxif_wI/AAAAAAAAOBk/-rMnqDVTP5k/s0/screencapture-localhost-8080-index-1454754834978.png "Index in desktop browser")

Now, let's research the main functions of the website.

### Note
Write simple note with title and text content. You can add new, edit, delete and search your notes. Here is some screenshots about quick note user interface when you browse on mobile device.

**Main interface:** To create new a note, you just touch on "Create now..." button. Followed by the textbox to enter search information, you can search any words in title or content of the note. Bottom are notes list which you added or search results.

![quick note interface](https://lh3.googleusercontent.com/-GkKnfhIBQfY/VrWXuNHvS3I/AAAAAAAAN-o/Vpy8gRimgHU/s0/screencapture-localhost-8080-note-1454735312919+copy.png"Quick note interface")

**Add new note dialog:** A dialog appear when you touch on "Create now..." button, you just need enter note title and note content then touch on "Add" button.

![Add new note](https://lh3.googleusercontent.com/-KhfKiXPIjw8/VrWY2AF1GiI/AAAAAAAAN_M/CXQRnq-uMW8/s0/screencapture-localhost-8080-note-1454741645189.png "Add new note")

**View note dialog:**  When you touch on a note from notes list then a dialog appear to show you this note information. Here you can delete note(touch on "Delete" button), to edit this note you just touch on "Edit..." button.

![View note](https://lh3.googleusercontent.com/-LuwCZWacB4Y/VrWchnHmyNI/AAAAAAAAN_o/E1fX4XyOwEs/s0/screencapture-localhost-8080-note-1454742582660.png "View note")

**Note editor:** This dialog will appear when you touch "Edit..." button in above dialog, so easy to edit note, you just need change what you want then touch on "Save" button to save changes.

![Edit note](https://lh3.googleusercontent.com/-lQ2IhzN10UE/VrWf2yUK2_I/AAAAAAAAOAA/u9D_wwz64YQ/s0/screencapture-localhost-8080-note-1454743471813.png "Edit note")

### Shared box(still incomplete)
A repository shared files by users, everyone(guest or any users) can see full this list. Such as public place which any users can put their files to share to world.

> It still an idea, I will complete it soon :|

### Serect box(still incomplete)
A personal repository which user can put private files and nobody can see them except owner.

> Shared box and Serect box such as a cloud storage service, it helps you save, manage and share files.

### Codebin(still incomplete)
You can type a simple code then share for your friend, it can also compile then run this code.

>It still an idea, to hightlight code, compile or run for result are difficult to implement in server.

## Contributing
1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

##Dependencies
This project using [bootstrap framework](http://getbootstrap.com/), [JQuery](https://jquery.com/) and [bootbox](http://bootboxjs.com/) library for front end. [sqlite4java](https://bitbucket.org/almworks/sqlite4java) library for communicate with SQLite database and  [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/download_lang.cgi) in back end.
Some styles from [http://tympanus.net/](http://tympanus.net/) and [http://bootboxjs.com/](http://bootboxjs.com/).

> I will say more about this part in a blog post.

## History
This is initial project, so it's incomplete!
## Author
Developed by sontx/noem, some useful information:

 - Home: [www.sontx.in](http://www.sontx.in)
 - My blog: [www.blog.sontx.in](http://www.blog.sontx.in)
 - Email: <a href="mailto:xuanson33bk@gmail.com">xuanson33bk@gmail.com</a>
 - Facebook: [No Em](https://mobile.facebook.com/Melkior.9x)

## License
Copyright 2016 Tran Xuan Son

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

>http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
