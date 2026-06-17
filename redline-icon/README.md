# Redline – App Icon

Konzept: Sechseck als Siegel (eure Wortmarke), doppelte Kontur als Anspielung
auf die zweischichtige Verschlüsselung (SQLCipher + Feldverschlüsselung),
und der Schlüsselweg ist die rote Linie.

## Farben
- Hintergrund  #0b0b12
- Sechseck-Fläche #15151f
- Akzent (rot)  #e63946
- Innenkontur   #2a2a38

## Einbinden in Android Studio (empfohlen: Adaptive Icon)
1. Inhalt von `android/res/` in dein `app/src/main/res/` kopieren (mergen).
   Enthält: drawable/ (foreground, background, monochrome),
   mipmap-anydpi-v26/ (ic_launcher.xml + _round), und die mipmap-*dpi PNG-Fallbacks.
2. In `AndroidManifest.xml` sicherstellen:
   android:icon="@mipmap/ic_launcher"
   android:roundIcon="@mipmap/ic_launcher_round"
3. Das monochrome Layer aktiviert die "Themed Icons" ab Android 13.

## Andere Verwendung
- svg/redline-icon.svg  – Master, frei skalierbar (Folien, Web, README)
- png/redline-icon-512.png – Play Store / Präsentation
- png/redline-icon-1024.png – hochauflösend
