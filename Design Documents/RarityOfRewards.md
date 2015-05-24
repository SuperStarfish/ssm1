Rarity of rewards - DRAFT

How rewards work
================

When playing a stroll, an event can occur based on a certain probability (See design documents). 

By completing an event; a number, the amount of rewards completed during this stroll, c, is increased by 1.
The number c is the amount of times after a stroll which the random number generator will run (for both the shape and colour, separated). 


Each generated number is added to a set R (for shape) and set T (for colour).
 After generating, the maximum number x contained by set R and the maximum number y from set T are taken.
This number x will map to the shape of the reward given to the player. 
This number y will map to the colour of the reward given to the player.

Example:

Person A starts a stroll.
c = 0
An event occurs.
Person A completes an events.
c = 1
Another event occurs.
Person A completes this events.
c = 2
A third event occurs.
The stroll time is up.
Person A completes the third event (an event can be completed after the stroll time is over). 
c = 3
Now since c = 3, three numbers are generated.
For this example say the range for  shapes is between 0 - 4 and for colours is between 0 - 10. 
Example R = {1, 2, 2} = {1, 2}
Example T = {3, 5, 8}

Maximum of R, x = 2
Maximum of T, y = 8

So the reward given to the player will be x = 2 (shape of the fish), y = 8 (colour of the fish).


Reward structure
================

Each reward follows a predefined structure, according to the guidelines in this document.

Each visualization of a reward has:
A shape (e.g. a starfish shape or a flat fish shape)
A colour (e.g. red, green)

Each of these attributes defines their own scaling from common to rare.

#### Shapes

The fish shapes are predefined and stored in a list (or similar structure).

* The simple fish
* The starfish 

##### Colours

For the rarity of colours we take the wavelength of visible human colours.
A lower wavelength will map to a more common colour where as a higher wavelength will map to a more rare colour.

##### From a number to a reward

/// (Number x, Number y) ➝ Reward(shape, colour)


Building a reward image (proposed)
==================================

Take a PixMap of the fish image (the shape)
Take a second PixMap of the fish image with only the parts which will be modified by a colour filter.
Apply a colour filter over the second PixMap. 

This way, we only have to store the X amount of images of the shapes instead of loading X*Y images where Y is the amount of colours possible to get as reward.
