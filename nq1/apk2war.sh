#!/bin/bash
APK_OUTPUT="mobile/platforms/android/app/build/outputs/apk/debug/app-debug.apk"
DEST="war/nq1.apk"
CORDOVA="/usr/local/bin/cordova" 
cd mobile
$CORDOVA clean android
$CORDOVA build android
cd ..
cp $APK_OUTPUT $DEST
