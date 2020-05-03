<h1 align="center">
  <br>
  Candela
</h1>

<h4 align="center">iOS style brightness control view. 🔆 🔅</h4>

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

  <attr name="cpbState" format="enum" >
            <enum name="clockwise" value="0"/>
            <enum name="counterClockwise" value="1"/>
        </attr>
        <attr name="cpbProgressValue" format="integer" />
        <attr name="cpbProgressColor" format="color" />
        <attr name="cpbProgressWidth" format="dimension" />
        <attr name="cpbBackgroundColor" format="color" />
        <attr name="cpbBackgroundWidth" format="dimension" />

Name | Description | Type | Default | Range
:--|:--|:-:|:--|:-:
cpbState | State of the Progress Bar | Enum | clockwise | clockwise or counterclockwise
cpbProgressValue | Progress's Value | integer | 0 | 0 to 100
cpbProgressColor | Color of the Progress Bar | Color | Color.BLACK | -
cpbProgressWidth | Width of the Progress Bar | Dimension | 8dp | -
cpbBackgroundColor | Bg Color of the Progress Bar | Color | Color.GRAY | -
cpbBackgroundWidth | Width of the Background | Dimension | 4dp | -

## Credits

While creating this one, I may have gotten some inspiration from the libraries below. :yum::innocent:

- [HueSeekBar](https://github.com/iammert/HueSeekBar)
- [BoxedVerticalSeekBar](https://github.com/alpbak/BoxedVerticalSeekBar)

## License

MIT
