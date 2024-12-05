# Molecular
Molecular is a simple particle simulator that allows for particles simulations to be run.
In this simulator, particles are just a perfectly spherical, indivisible circle, 
this is to keep the simulator as simple as possible.

## Motivation
Molecular was created as a simple school project, dedicated to the robotics club.
I, the creator of the project, have no intention of further updating this project
beyond its current state.

## Known bugs
* Unexpected bounces on collisions with small or high velocities
* Wall inconsistency, particles may often go beyond the wall and back
* Zero distance, placing two particles at the same position will cause both of them to disappear and 
their position and velocities to become NaN (Not a Number)
