#!/bin/bash
# NyatetDuwit - Build & Install Helper

export JAVA_HOME=/tmp/jdk/jdk-17.0.14+7
export ANDROID_HOME=/home/amerta/Android
export PATH=$PATH:$ANDROID_HOME/platform-tools

APK_PATH="app/build/outputs/apk/debug/app-debug.apk"

case "$1" in
    build)
        echo "Building debug APK..."
        ./gradlew assembleDebug --no-daemon
        ;;
    install)
        echo "Installing APK to connected device..."
        adb install -r "$APK_PATH"
        ;;
    run)
        echo "Building and installing..."
        ./gradlew assembleDebug --no-daemon && adb install -r "$APK_PATH"
        ;;
    devices)
        echo "Connected devices:"
        adb devices -l
        ;;
    log)
        echo "Showing app logs..."
        adb logcat | grep -i "nyatetduwit\|AndroidRuntime"
        ;;
    clean)
        echo "Cleaning build..."
        ./gradlew clean --no-daemon
        ;;
    *)
        echo "Usage: ./dev.sh {build|install|run|devices|log|clean}"
        echo ""
        echo "Commands:"
        echo "  build   - Build debug APK"
        echo "  install - Install APK to connected device"
        echo "  run     - Build and install"
        echo "  devices - List connected devices"
        echo "  log     - Show app logs"
        echo "  clean   - Clean build artifacts"
        ;;
esac
