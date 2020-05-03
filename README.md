<h1 align="left">
  <br>
  Candela
</h1>

<h4 align="left">iOS style brightness control view. 🔆 🔅</h4>

![screenshot](https://media.giphy.com/media/Vi08Qk4wM2BVx8KjHD/giphy.gif)

## How To Install

To get a Git project into your build:

* Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

* Step 2. Add the dependency

```
dependencies {
    implementation 'com.github.brkckr:Candela:1.0.0'
}
```
## How To Use

```
<com.github.brkckr.candela.Candela
        android:id="@+id/candela"
        android:layout_width="100dp"
        android:layout_height="500dp"
        app:colorEmpty="#F44336"
        app:colorFill="#FF9800"
        app:colorSun="#3F51B5"
        app:colorSunlight="#673AB7"
        app:cornerRadius="100"
        app:currentProgress="80"
        app:maxProgress="100"
        app:isSunlightRounded="true"
        app:isTouchEnabled="false"
        app:sunlightStrokeWidth="8"/>
```



## Attributes

<attr name="colorEmpty" format="color" />
<attr name="colorFill" format="color" />
<attr name="colorSun" format="color" />
<attr name="colorSunlight" format="color" />
<attr name="cornerRadius" format="int" />
<attr name="currentProgress" format="int" />
<attr name="maxProgress" format="int" />
<attr name="sunlightStrokeWidth" format="int" />
<attr name="isSunlightRounded" format="boolean" />
<attr name="isTouchEnabled" format="boolen" />


Name | Description | Type | Default | Range
:--|:--|:-:|:--|:-:
colorEmpty | Empty color of the view | Color | #212121 | -
colorFill | Fill color of the view | Color | #FFFFFF | -
colorSun | Color of the Progress Bar | Color | Color.BLACK | 1
colorSunlight | Width of the Progress Bar | Dimension | 8dp | 1
cornerRadius | Bg Color of the Progress Bar | Color | Color.GRAY |1
currentProgress | Width of the Background | Dimension | 4dp | 1
maxProgress | Width of the Background | Dimension | 4dp |123
sunlightStrokeWidth | Width of the Background | Dimension | 4dp | 123
isSunlightRounded | Width of the Background | Dimension | 4dp | 123
isTouchEnabled | Width of the Background | Dimension | 4dp | 123

## Credits

While creating this one, I may have gotten some inspiration from the libraries below. :yum::innocent:

- [HueSeekBar](https://github.com/iammert/HueSeekBar)
- [BoxedVerticalSeekBar](https://github.com/alpbak/BoxedVerticalSeekBar)

## License

MIT
