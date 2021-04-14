
# TR-404 Drum sequencer

I have had an interest in electronic music, DJing, and it's production methods for a number of years. 
Pieces of hardware equipment such as the Roland Tr-808 Or Korg Volca Beats have been widely used to synthesise drum sequences to be used for a wide range of musical genres.

An example of such hardware is included: https://www.youtube.com/watch?v=sxZyjpd2KoM

I have decided to create a simple implementation of a drum machine; while the base logic of a sequencer is realatively simple, it will give me the opportunity to 
add a large amount of complexity and features to it.

________________________________________________________________________________________________
Features:

- Currently Includes 5 Multi-track, 16 beat/4 bar instrument rows
- Timing is synced up to provide repeating playback, allowing the user to change beat patterns in real time 
- playback functionality, such as play,stop and reset logic
- time keeping mechanics in the form of time elapsed and beat position
- beats per minute (BPM) counter, able to change playback speed in real time 
- Instrument changing mechanics, allowing user to change timbre to fit the users needs 

____________________________________________________________________________________________________________________________________________
To add:
This is an ongoing product. While i have integrated the base logic, I feel that there is a greater amount of features to add to make it attractive in a portfolio:

- Volume control, both master and individual tracks
- FM synthesiser
- Effects panel for individual instruments (Delay, Reverb etc)
- Pattern save states 
- Pattern recording and saving mechanics
- possible integration on web application
- Complete UI/UX 

____________________________________________________________________________________________________________________________________________
Bug List:
- Instruemnt changing mechanic puts that track out of sync with the others due to thread halt
- volume slider non functional
- 
