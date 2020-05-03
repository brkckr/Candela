<h1 align="center">
  <br>
  Candela
</h1>

<h4 align="center">iOS style brightness control view. 🔆 🔅</h4>

![screenshot](https://media.giphy.com/media/RLV1w1thh1Qzuic08s/giphy.gif)
![screenshot](https://media.giphy.com/media/9xk58iEZU0T9sic4Wk/giphy.gif)

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
<com.brkckr.circularprogressbar.CircularProgressBar
        android:id="@+id/circularProgressBar"
        app:cpbBackgroundColor="#F1992D"
        app:cpbBackgroundWidth="@dimen/background_width"
        app:cpbProgressColor="#9F3238"
        app:cpbProgressValue="15"
        app:cpbProgressWidth="@dimen/progress_width"
        app:cpbState="clockwise"
        android:layout_width="128dp"
        android:layout_height="128dp"/>
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
