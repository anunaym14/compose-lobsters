#!/usr/bin/env bash

# Creates and boots an emulator that exactly matches the one in our CI. It is recommended
# to use this as the target device for screenshot tests.

set -euo pipefail

[ -n "${ANDROID_SDK_ROOT:-}" ] || {
  echo "ANDROID_SDK_ROOT must be set to use this script"
  exit
  1
}
[ -n "${ANDROID_API_LEVEL:-}" ] || { echo "ANDROID_API_LEVEL not defined; defaulting to 30"; }

API_LEVEL="${ANDROID_API_LEVEL:-30}"

sdkmanager "system-images;android-${API_LEVEL};google_apis;x86_64"

echo no | "${ANDROID_SDK_ROOT}"/cmdline-tools/latest/bin/avdmanager create avd \
  --force \
  -n "Pixel_XL_API_${API_LEVEL}" \
  --abi 'google_apis/x86_64' \
  --package "system-images;android-${API_LEVEL};google_apis;x86_64" \
  --device 'pixel_xl'

"${ANDROID_SDK_ROOT}"/emulator/emulator \
  -avd "Pixel_XL_API_${API_LEVEL}" \
  -no-window \
  -noaudio \
  -no-boot-anim
