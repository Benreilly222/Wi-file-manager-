# This PC File Manager (Windows 11 style)

Android file manager with a Windows 11 File Explorer look ("This PC" start page, Mica colours, 4/8 dp corners, #0067C0 accent)
and the full feature set of [Strata](https://github.com/CluvexStudio/Strata): two panes, archives, APK/DEX tools, code/hex/PDF/image/video
viewers, FTP/SFTP/SMB/WebDAV, Wi-Fi sharing, encrypted vault, recycle bin, and more.

Build: push to GitHub -> Actions -> **Build APK** -> download artifact `ThisPC-FileManager-APK`.

Local build needs the Android SDK + NDK, Rust with the Android targets and `cargo-ndk`:

```bash
native/fetch-pdfium.sh
native/build.sh
./gradlew :app:assembleDebug
```

## License

This project contains code from Strata (c) Cluvex Studio, licensed under the **GNU AGPL v3.0 or later** (see `LICENSE`).
Because of that, this whole project is distributed under AGPL-3.0-or-later and its source must be made available to anyone who gets the app.
