# Use Android phone for soothing baby remotely

Very little functionality for now:
* Start app and slick the only button that you see: Starts web server on port 5000
* Accepts http GET requests to path /{filename}, where filename is name of the .mp3 file under /sdcard/Music
* Starts playing file if file is found, stop playing already playing one if file is not found

Example: 
* Start playing /sdcard/Music/whitenoise.mp3: `curl http://192.168.2.8:5000/whitenoies`
* Stop playing (assumeing there is not such file /sdcard/Music/stop.mp3): `curl http://192.168.2.8:5000/stop`
