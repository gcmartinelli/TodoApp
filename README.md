# To-do App - A Kotlin introduction and Dev diary

Learning project in the Kotlin language.

This is my first contact with Kotlin (and Java) and my first time attempting any kind of public development diary.

## Goals
* Gain initial insight into the Kotlin language and workflow
* Avoid using frameworks that obfuscates how Kotlin works (the goals is to LEARN Kotlin not make the code small or super efficient)
* Get a sense for the differences in philosophy compared to Python
* Have a working To-Do app with a browser interface

## Captain's log
### Date 1.0
This exercise brings many new challenges for me. From the language to the development environment. But my main concern is adapting to a statically typed mindset.

Although static typing is now available in Python I have not yet experienced it. The spare contact I've had with this methodology was in toy projects in the Unity game engine.

#### 1.1
I sometimes reflect how I make things harder and more painful for myself by using Arch, but overall I like how it makes me learn about what I'm doing instead of blindly copy pasting commands.

After a bit of work I managed to get [IntelliJ Idea](https://www.jetbrains.com/idea/) working on my environment.

Using an IDE with so many features is also going to be part of the new experiences... I'm mostly used to coding in VIM or at most a barebones Sublime Text.

#### 1.2
After a few hours skimming through Kotlin introductions my feeling is that 90% of the content is targeted at developers moving from Java to Kotlin. Which makes sense... 

But I feel Kotlin/JetBrains would benefit from having more resources made towards developers who are arriving at Kotlin having other experiences (or none at all).

One good resource I found for Python developers was [this blog post](https://khan.github.io/kotlin-for-python-developers/#constants) by the fine people of Khan Academy.

#### 1.3
I have to admit the thought "Why do you do this in such a complicated way" crossed my minds many times... Python is so elegant and readable in comparison :P

But Kotlin seems to also take a lot from Python and apply it to Java, which makes it wayyyyy more appealing to work with.

#### 1.4
Experimenting with very simple "Hello World" scripts.

Even tho I don't like the feeling of depending in a 'bloated' IDE, having it validate my code as I go is a very nice experience. Especially since compiling and running the code takes an eternity when compared to Python.

Static typing (and it's added complexity) are now making more sense to me. I think I get how this is beneficial in larger projects with multiple developers. I see how it could have saved me a world of pain in some projects.

---
### Date 2.0
Ok, I think I've watched enough of other people code, time to get my hands dirty and try it for myself. What could go wrong...
I just need an idea.

#### 2.1
Had a few ideas for mini projects:
* Multiplayer card game
* Terminal based multiplayer "detective" game
* A simple neural network autoencoder implementation
* A fraud detector using a Python based autoencoder (I really want to play around with these sometime) and a Kotlin API "frontend"
* A Tamagotchi virtual pet

In the end I ignored all of these and decided to KISS (although making a terminal based multiplayer game is something I definitely want to try out sometime!).

**I'm making a To-do app with a browser based front-end.**

#### 2.2
I want to avoid using too many frameworks since my goal is to learn Kotlin, not to present a "Make a To-Do app in 5 lines of code" keynote.

This is what I'm thinking for a basic structure:

```
. APP
|
|__ Task (class object)
|   |__ creationDate : Date (auto-generated)
|   |__ dueDate : Date
|   |__ title : String
|   |__ description : String
|   |__ status : String (todo | complete | late)
|   * method: complete() -> changes the status to complete
|   * method: delete() -> deletes the task
|__ Main (simple web server? probably use a minimalist web framework)
    |__ / -> taskListView : shows all tasks ordered by dueDate
    |__ /addTask -> addTask : form 
    |__ /deleteTask -> api endpoint?
    |__ /completeTask -> api endpoint?
``` 

Seems manageable for a first feel for the language.

I'll probably use SQLite3 or PostgreSQL to store the data. 
I need to search for a small/simple web framework since handling these request is out of my scope right now.

#### 2.3
[Javalin](http://javalin.io) looks like a good option for the webframework. 
For data persistence I'm considering initially just storing everything in memory (a list/array object of some sort) and then implementing a database in a later step.

#### 2.4
Started a project using Gradle. 
Honestly I have no idea what this thing does but apparently it helps managing my build and dependencies... automatic requirements.txt with PIP integrated? This is the best analogy I could think for it so far... I'm using it because Javalin recommended :P

#### 2.5
First thing I worked on was the task class. I remember reading about `data classes` so I'll be using those for the convenient methods it generates.
Most code examples I saw frequently import dependencies using *, which is a pet peeve of mine since I feel it makes code less explicit. I'll try doing explicit imports right now. If it gets too out of hand at least I'll learn why * is preferred.

#### 2.6
Pause to read up on Javalin.

#### 2.7
Ok. Found a [good tutorial](https://javalin.io/tutorials/simple-kotlin-example) on the Javalin website that is just what I need.

Of course I don't want to simply copy/paste code so I'll try my best to absorb it all and implement on my own. But overall the implementation of the CRUD and API seems straightforward.

And it looks like I made the right decision on using a `data class`.

#### 2.8
My [initial architecture](#22) was a bit too simplistic. I need to implement a DAO (data access object) as an intermediary between my database and my views/API.

This is what I'm thinking right now:
```
. taskDAO (class object)
|__ getTasks : returns all tasks in the database
|__ addTask : adds a new task
|__ deleteTask : deletes a given task using taskID as a reference (need to add taskID to my class)
|__ completeTask : marks task as complete
```

#### 2.9
The smallest things can take some time to get used to... Like writing strings with "" and never ''

Java does not accept named arguments in functions `whytho.png`

#### 2.A
Wasted a bit of time trying to figure out if I needed to import my classes or not. Main script seems to work without any explicit imports (I was expecting something like `import kotlin.TaskClass`)

Also decided to use a `mutableList` for now as a datastore just to make things a bit more familiar for my Pythonic brain.

#### 2.B
I've always had a very iterative process for developing in Python. I need to figure out a way to make this more iterative... right now I'm using a mock `main.kt` to run experiments. This is also making me want to learn TDD, but that will have to be on another project.

At the moment I have a working task class and a working DAO class with which I can add and remove elements from my datastore (a simple mutable list at the moment).

#### 2.C
Doing some research on error handling in Kotlin.